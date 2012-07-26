package steeringDrive;

import java.io.File;

import lejos.nxt.Button;
import lejos.nxt.Sound;


public class Parke2Objekte {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Auto auto = new Auto();

		Sound.setVolume(100);
		System.out.println("Druecke Knopf");
		Button.waitForAnyPress();
		
		//auto.pilot.forward();
		auto.passiereErstenGegenstand();
		float parkluecke = auto.fahreBisZweiterGegenstandUndErmittleParkluecke();
		if (!auto.kannEinparken(parkluecke)){
			return;
		}
		

		auto.rueckwaertsEinparken(parkluecke);
		Button.waitForAnyPress();
		auto.rausfahren();
		
		auto.schlussfahrt();

	}

}
