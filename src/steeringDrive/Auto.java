package steeringDrive;

import java.io.File;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.EOPD;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Beschreibt die Ausmasse des Autos sowie die ben�tigten Funktionen, die es dem
 * Auto erlaubt r�ckw�rts ein- und auszuparken. Beim Erstellen der Klasse wurde
 * sich an das Paper "Ein mathematisches Modell zum Parallelparken" von Norbert
 * Herrmann orientiert.
 */
public class Auto {

	private double f = 20.5; // Abstand von Hinterachse zur Wagenfront
	private double w = 13.5; // Breite des Autos
	private double R = 47.75; // Wendekreis
	private double b = 3.0; // Abstand von Hinterachse zum Wagenende
	// r der Radius des kleinsten Kreises, den der Automittelpunkt, beschreiben
	// kann
	private double r = (Math.sqrt(Math.pow(R, 2) - Math.pow(f, 2)) - w / 2); // sqrt(R^2-f^2)-w/2
	private double p = 15.00;
	private double alpha = Math.acos((2 * r - w - p) / (2 * r));
	private double g = Math.sqrt(2 * r * w + Math.pow(f, 2)) + b; // minLaengeParkluecke
	private double kreisbogen = r * alpha;

	private float reifenDurchmesser = 5.00f;
	private float spurweite = 13.50f;
	private boolean rueckwaertslaufend = true;
	private DifferentialPilot pilot = new DifferentialPilot(reifenDurchmesser,
			spurweite, Motor.A, Motor.C, rueckwaertslaufend);

	private double distanzSensorHinterachse = 9.00;
	private float maximaleParkluecke = 65;

	private EOPD eopdRechts = new EOPD(SensorPort.S1, true);
	private EOPD eopdHinten = new EOPD(SensorPort.S2, true);

	private float gemesseneStreckeStart;

	public Auto() {
		super();
		this.pilot.setTravelSpeed(15); // cm/s
		Sound.setVolume(50);
	}

	/**
	 * Der Wagen f�hrt vorw�rts und passiert einen Gegenstand. Nach dem
	 * passieren f�hrt der Wagen weiterhin vorw�rts.
	 */
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

	/**
	 * Der fahrende Wagen f�hrt entlang der potentiellen Parkl�cke, diese wird
	 * dabei vermessen. Das Fahrzeug f�hrt, bis es einen zweiten Gegenstand
	 * findet oder bis die Parkl�cke gross genug ist um einzuparken.
	 * 
	 * @return die L�nge der Parkl�cke
	 */
	public float fahreBisZweiterGegenstandUndErmittleParkluecke() {
		float parkluecke = vermesseParkluecke();

		if (parkluecke < this.maximaleParkluecke) {
			while (this.messeStrecke() < parkluecke
					+ this.distanzSensorHinterachse)
				;
		}

		pilot.stop();
		return parkluecke;

	}

	/**
	 * �berpr�ft ob der Wagen in eine Parkl�cke einparken kann, oder nicht. Gibt
	 * dem entsprechend true oder false zur�ck
	 * 
	 * @param parkluecke
	 *            , L�nge einer Parkl�cke
	 * @return true, Wagen kann einparken, falls andernfalls.
	 */
	public boolean kannEinparken(float parkluecke) {
		System.out.println("Parkluecke < g?: " + parkluecke + " < " + this.g);
		if (parkluecke < this.maximaleParkluecke) { // Parkluecke kleiner als
													// errechneter
			// Mindestmass fuer Parkluecke
			Sound.buzz();
			Button.waitForAnyPress();
			return false;
		}
		return true;
	}

	/**
	 * der Wagen f�hrt r�ckw�rts in die Parkl�cke rein und rangiert sich in die
	 * mitte von dieser.
	 * 
	 * @param parkluecke
	 */
	public void rueckwaertsEinparken(float parkluecke) {
		RadEinschlag.einschlag_prozent(110);
		pilot.travel(-kreisbogen, true);
		this.achteAufGegenstandHinten();
		RadEinschlag.einschlag_prozent(-110);
		pilot.travel(-kreisbogen, true);
		this.achteAufGegenstandHinten();
		// Ausgleich einer Unpraezision der Lenkung
		int offset = 20;
		RadEinschlag.einschlag_prozent(0 + offset);

		nachVorneRangieren(parkluecke);
	}

	/**
	 * der Wagen f�hrt vorw�rts aus der Parkl�cke raus.
	 */
	public void rausfahren() {
		this.hintenAnGegenstandFahren();
		RadEinschlag.einschlag_prozent(-100);
		pilot.travel(kreisbogen);
		RadEinschlag.einschlag_prozent(100);
		pilot.travel(kreisbogen);
		int offset = -10;
		RadEinschlag.einschlag_prozent(0 + offset);
	}

	/**
	 * der Wagen f�hrt geradeaus und spielt ein Lied
	 */
	public void schlussfahrt() {
		Sound.setVolume(100);
		File soundFile = new File("smw_course_clear.wav");
		System.out.println(soundFile.length());
		Sound.playSample(soundFile, 100);

		this.pilot.travel(50);

	}

	/**
	 * der fahrende Wagen stoppt sobald sich ein Gegenstand zu nahe hinter dem
	 * Wagen befindet.
	 */
	private void achteAufGegenstandHinten() {
		while (pilot.isMoving()) {
			if (gegenstandHinten()) {
				pilot.stop();
				Sound.buzz();
			}
		}
	}

	private float vermesseParkluecke() {
		System.out.println("Starte Vermessung Parkluecke");
		this.messeStreckeStart();
		while (!this.gegenstandRechts()
				&& this.messeStrecke() < this.maximaleParkluecke)
			;
		float parkluecke = this.messeStrecke();
		System.out.println("Vermessung Parkluecke abgeschlossen");
		Sound.beep();
		return parkluecke;
	}

	private void messeStreckeStart() {
		gemesseneStreckeStart = pilot.getMovement().getDistanceTraveled();
	}

	private float messeStrecke() {
		return pilot.getMovement().getDistanceTraveled()
				- gemesseneStreckeStart;
	}

	/**
	 * Gibt true zur�ck wenn sich ein Gegenstand nah an der rechten Seite des
	 * Wagens befindet. False, andernfalls.
	 * 
	 * @return
	 */
	private boolean gegenstandRechts() {
		if (eopdRechts.readRawValue() < 1000) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gibt true zur�ck wenn sich ein Gegenstand nah hinter dem Wagen befindet.
	 * False, andernfalls.
	 * 
	 * @return
	 */
	private boolean gegenstandHinten() {
		if (eopdHinten.readRawValue() < 900) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Der Wagen F�hrt r�ckw�rts, nahe an ein Gegenstand ran und stoppt dann.
	 */
	private void hintenAnGegenstandFahren() {
		this.pilot.backward();
		while (!this.gegenstandHinten())
			;
		Sound.beep();
		this.pilot.stop();
	}

	/**
	 * Der Wagen f�hrt nach hinten nah an einen Gegenstand, und f�hrt dann nach
	 * vorne bis es in der Mitte der Parkl�cke steht.
	 * 
	 * @param parkluecke
	 */
	private void nachVorneRangieren(float parkluecke) {
		hintenAnGegenstandFahren();
		System.out.println("Parkluecke: " + parkluecke);
		this.pilot.travel((parkluecke - this.f) / 2);
	}

}
