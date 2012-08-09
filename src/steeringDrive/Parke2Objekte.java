package steeringDrive;

import lejos.nxt.Button;


public class Parke2Objekte {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Auto auto = new Auto();
		System.out.println("Druecke Knopf");
		Button.waitForAnyPress();

		auto.passiereErstenGegenstand();
		float parkluecke = auto.fahreBisZweiterGegenstandUndErmittleParkluecke();
		if (!auto.kannEinparken(parkluecke)){
			return;
		}		

		auto.rueckwaertsEinparken(parkluecke);
//		Button.waitForAnyPress();
//		auto.rausfahren();
		
//		auto.schlussfahrt();

	}

}
