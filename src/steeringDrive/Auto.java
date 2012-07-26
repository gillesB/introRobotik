package steeringDrive;

import java.io.File;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.EOPD;
import lejos.robotics.navigation.DifferentialPilot;

public class Auto {

	double f = 20.5; // Abstand von Hinterachse zur Wagenfront
	double w = 13.5; // Breite des Autos
	double R = 47.75; // Wendekreis
	double b = 3.0; // Abstand von Hinterachse zum Wagenende
	// r der Radius des kleinsten Kreises, den der Automittelpunkt, beschreiben
	// kann
	double r = (Math.sqrt(Math.pow(R, 2) - Math.pow(f, 2)) - w / 2); // sqrt(R^2-f^2)-w/2
	double p = 15.00;
	double alpha = Math.acos((2 * r - w - p) / (2 * r));
	double g = Math.sqrt(2 * r * w + Math.pow(f, 2)) + b; // minLaengeParkluecke
	double kreisbogen = r * alpha;

	public final float WHEEL_DIAMETER = 5.00f;
	public final float TRACK_WIDTH = 13.50f;
	public final boolean REVERSE = true;
	private DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER,
			TRACK_WIDTH, Motor.A, Motor.C, REVERSE);

	public final double DISTANZ_SENSOR_HINTERACHSE = 9.00;
	public final float MAXIMALE_PARKLUECKE = 50;

	private EOPD eopdRechts = new EOPD(SensorPort.S1, true);
	private EOPD eopdHinten = new EOPD(SensorPort.S2, true);

	public Auto() {
		super();
		this.pilot.setTravelSpeed(15); // cm/s
	}

	public void rueckwaertsEinparken(float parkluecke) {
		RadEinschlag.einschlag_prozent(100);
		System.out.println("r: " + r);
		System.out.println("alpha: " + alpha);
		System.out.println("kreisbogen: " + kreisbogen);
		System.out.println("minLaengeParkluecke: " + g);
		pilot.travel(-kreisbogen, true);
		while (pilot.isMoving()) {
			if (gegenstandHinten()) {
				pilot.stop();
				Sound.buzz();
			}
		}
		RadEinschlag.einschlag_prozent(-100);
		pilot.travel(-kreisbogen, true);
		while (pilot.isMoving()) {
			if (gegenstandHinten()) {
				pilot.stop();
				Sound.buzz();
			}
		}
		RadEinschlag.einschlag_prozent(0);
		nachVorneRangieren(parkluecke);
	}

	public void rausfahren() {
		this.hintenAnGegenstandFahren();
		RadEinschlag.einschlag_prozent(-100);
		System.out.println("r: " + r);
		System.out.println("alpha: " + alpha);
		System.out.println("kreisbogen: " + kreisbogen);
		System.out.println("minLaengeParkluecke: " + g);
		pilot.travel(kreisbogen * 0.80, true);
		RadEinschlag.einschlag_prozent(100);
		pilot.travel(kreisbogen, true);
	}

	float gemesseneDistanzStart;

	public void messeStreckeStart() {
		gemesseneDistanzStart = pilot.getMovement().getDistanceTraveled();
	}

	public float messeStrecke() {
		return pilot.getMovement().getDistanceTraveled()
				- gemesseneDistanzStart;
	}

	public void passiereErstenGegenstand() {
		pilot.forward();
		while (!this.gegenstandRechts())
			;
		System.out.println("Gegenstand Rechts gefunden");
		Sound.beep();
		while (this.gegenstandRechts())
			;
		System.out.println("Gegenstand Rechts passiert");
		Sound.twoBeeps();
	}

	public float fahreBisZweiterGegenstandUndErmittleParkluecke() {
		float parkluecke = vermesseParkluecke();

		if (parkluecke < this.MAXIMALE_PARKLUECKE) {
			while (this.messeStrecke() < parkluecke
					+ this.DISTANZ_SENSOR_HINTERACHSE)
				;
		}

		pilot.stop();
		return parkluecke;

	}

	public float vermesseParkluecke() {
		System.out.println("Starte Vermessung Parkluecke");
		this.messeStreckeStart();
		while (!this.gegenstandRechts()
				&& this.messeStrecke() < this.MAXIMALE_PARKLUECKE)
			;
		float parkluecke = this.messeStrecke();
		System.out.println("Vermessung Parkluecke abgeschlossen");
		Sound.beep();
		return parkluecke;
	}

	public boolean gegenstandRechts() {
		if (eopdRechts.readRawValue() < 1000) {
			return true;
		} else {
			return false;
		}
	}

	public boolean gegenstandHinten() {
		if (eopdHinten.readRawValue() < 800) {
			return true;
		} else {
			return false;
		}
	}

	public boolean kannEinparken(float parkluecke) {
		System.out.println("Parkluecke < g?: " + parkluecke + " < " + this.g);
		if (parkluecke < this.g) { // Parkluecke kleiner als errechneter
									// Mindestmass fuer Parkluecke
			Sound.buzz();
			Button.waitForAnyPress();
			return false;
		}
		return true;
	}

	public void hintenAnGegenstandFahren() {
		this.pilot.backward();
		while (!this.gegenstandHinten())
			;
		Sound.beep();
		this.pilot.stop();
	}

	public void nachVorneRangieren(float parkluecke) {
		hintenAnGegenstandFahren();
		System.out.println("Parkluecke: " + parkluecke);
		this.pilot.travel((parkluecke - this.f) / 2);
	}

	public void schlussfahrt() {
		
		RadEinschlag.einschlag_prozent(0);
		
		Sound.setVolume(100);
		File soundFile = new File("smw_course_clear.wav");
		System.out.println(soundFile.length());
		Sound.playSample(soundFile,100);
		
		this.pilot.travel(50);
		
	}

}
