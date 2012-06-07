package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Test_Steuern {

	/**
	 * @param args
	 */
	public final static float WHEEL_DIAMETER = 5.00f;
	public final static float TRACK_WIDTH = 13.50f;
	public final static boolean REVERSE = true;

	
	public static void main(String[] args) {
		System.out.println("Steuern");
		Button.waitForAnyPress();
		Motor.B.setSpeed(270);
		Motor.B.rotate(-1300);
		Motor.B.rotate(2600);		
		Motor.B.rotate(-1300);

		

	}

}