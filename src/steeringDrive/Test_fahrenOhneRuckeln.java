package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Test_fahrenOhneRuckeln {

	/**
	 * @param args
	 */
	public final static float WHEEL_DIAMETER = 5.00f;
	public final static float TRACK_WIDTH = 13.50f;
	public final static boolean REVERSE = true;

	
	public static void main(String[] args) {
		System.out.println("Test");
		Button.waitForAnyPress();
		DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.C, REVERSE);
		pilot.setTravelSpeed(8);
		pilot.travel(50);
		System.out.println("May travel speed: "+ pilot.getMaxTravelSpeed());
		Button.waitForAnyPress();

		

	}

}
