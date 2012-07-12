package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.util.Delay;

public class testMauer {
	static Auto auto = new Auto();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		auto.pilot.setTravelSpeed(15); // cm/s
		Sound.setVolume(50);
		System.out.println("Druecke Knopf");
		Button.waitForAnyPress();
		
		auto.fahreAnMauerEntlangStart();
		auto.pilot.forward();
		for(int i = 0; i < 50; i++){
			auto.fahreAnMauerEntlang();
			System.out.println(i);
			Delay.msDelay(100);
		}
		auto.pilot.stop();
		Motor.B.stop();
		RadEinschlag.einschlag_prozent(0);
		Button.waitForAnyPress();
		

	}

}
