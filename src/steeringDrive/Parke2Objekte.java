package steeringDrive;

import java.io.File;

import lejos.nxt.Button;
import lejos.nxt.Sound;

public class Parke2Objekte {

	static Auto auto = new Auto();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		auto.pilot.setTravelSpeed(15); // cm/s
		Sound.setVolume(50);
		System.out.println("Druecke Knopf");
		Button.waitForAnyPress();

		auto.pilot.forward();
		passiereErstenGegenstand();
		float parkluecke = vermesseParkluecke();		
		auto.pilot.stop();
		
		//9cm differenz von Sensor zu Hinterachse
		auto.pilot.travel(9);
		
		System.out.println("Parkluecke < g?: " + parkluecke + " < " + auto.g);
		//TODO programm sturzt ab
		if (parkluecke < auto.g) { //Parkluecke kleiner als errechneter Mindestmass fuer Parkluecke
			Sound.buzz();
			Button.waitForAnyPress();
			return;
		}
		auto.fahreWinkelAlpha(false);
		nachVorneRangieren(parkluecke);
		Button.waitForAnyPress();
		hintenAnGegenstandFahren();
		auto.fahreWinkelAlphaVorwaerts(true);

		Sound.setVolume(100);
		File soundFile = new File("smw_course_clear.wav");
		System.out.println(soundFile.length());
		Sound.playSample(soundFile,100);
		
		RadEinschlag.einschlag_prozent(0);
		auto.pilot.travel(50);

	}

	public static void passiereErstenGegenstand() {
		while (!auto.gegenstandRechts())
			;
		System.out.println("Gegenstand Rechts gefunden");
		Sound.beep();
		while (auto.gegenstandRechts())
			;
		System.out.println("Gegenstand Rechts passiert");
		Sound.twoBeeps();
	}

	public static float vermesseParkluecke() {
		System.out.println("Starte Vermessung Parkluecke");
		auto.messeStreckeStart();
		while (!auto.gegenstandRechts())
			;
		float parkluecke = auto.messeStreckeStop();
		System.out.println("Vermessung Parkluecke abgeschlossen");
		Sound.beep();
		return parkluecke;
	}

	public static void hintenAnGegenstandFahren() {
		auto.pilot.backward();
		while (!auto.gegenstandHinten());
		Sound.beep();
		auto.pilot.stop();
	}

	public static void nachVorneRangieren(float parkluecke) {
		hintenAnGegenstandFahren();
		System.out.println("Parkluecke: " + parkluecke);		
		auto.pilot.travel((parkluecke-auto.f) / 2);
	}

}