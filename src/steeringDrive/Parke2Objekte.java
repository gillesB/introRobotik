package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Sound;

public class Parke2Objekte {

	static Auto a = new Auto();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		a.pilot.setTravelSpeed(15); // cm/s
		Sound.setVolume(50);
		Button.waitForAnyPress();

		a.pilot.forward();
		passiereErstenGegenstand();
		float parkluecke = vermesseParkluecke();		
		a.pilot.stop();
		
		//9cm differenz von Sensor zu Hinterachse
		a.pilot.travel(9);
		
		System.out.println("Parkluecke < g?: " + parkluecke + " < " + a.g);
		if (parkluecke < a.g) { //Parkluecke kleiner als errechneter Mindestmass fuer Parkluecke
			Sound.buzz();
			return;
		}
		a.fahreWinkelAlpha(false);
		nachVorneRangieren(parkluecke);
		Button.waitForAnyPress();

	}

	public static void passiereErstenGegenstand() {
		while (!a.gegenstandRechts())
			;
		Sound.beep();
		while (a.gegenstandRechts())
			;
		Sound.twoBeeps();
	}

	public static float vermesseParkluecke() {
		a.messeStreckeStart();
		while (!a.gegenstandRechts())
			;
		float parkluecke = a.messeStreckeStop();
		Sound.beep();
		return parkluecke;
	}

	public static void hintenAnGegenstandFahren() {
		a.pilot.backward();
		while (!a.gegenstandHinten());
		Sound.beep();
		a.pilot.stop();
	}

	public static void nachVorneRangieren(float parkluecke) {
		hintenAnGegenstandFahren();
		System.out.println("Parkluecke: " + parkluecke);		
		a.pilot.travel((parkluecke-a.f) / 2);
	}

}
