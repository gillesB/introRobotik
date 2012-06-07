package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class FahreSteuern {

	/**
	 * @param args
	 */
	public final static float WHEEL_DIAMETER = 5.00f;
	public final static float TRACK_WIDTH = 13.50f;
	public final static boolean REVERSE = true;
	public final static boolean IMMEDIATE_RETURN = true;

	
	public static void main(String[] args) {
		System.out.println("FahrenOhneRuckeln_steuern");
		Button.waitForAnyPress();
		DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.C, REVERSE);
		pilot.setTravelSpeed(8);
		pilot.travel(250, IMMEDIATE_RETURN);
		pilot.travel(250, IMMEDIATE_RETURN);
		//Button.waitForAnyPress();

		Motor.B.setSpeed(270);
		Motor.B.rotate(-1000);
		Motor.B.rotate(2000);		
		Motor.B.rotate(-1000);
		
		

		

	}

}
