package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class MaxRadeinschlag {

	public final static float WHEEL_DIAMETER = 5.00f;
	public final static float TRACK_WIDTH = 13.50f;
	public final static boolean REVERSE = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Maximaler_Radeinschlag");

		DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER,
				TRACK_WIDTH, Motor.A, Motor.C, REVERSE);
		pilot.setTravelSpeed(8);

		Motor.B.setSpeed(270);
		System.out.println(Motor.B.getTachoCount());

		while (true) {
			switch (Button.waitForAnyPress()) {
			case Button.ID_RIGHT:
				rightButtonPressed();
				break;
			case Button.ID_LEFT:
				leftButtonPressed();
				break;
			case Button.ID_ENTER:
				enterButtonPressed();
				break;
			case Button.ID_ESCAPE:
				escapeButtonPressed();
				break;
			default:
				System.out.println("Unkown Button Pressed");
				Button.waitForAnyPress();

			}
		}
	}

	private static void leftButtonPressed() {
		Motor.B.backward();
		Button.LEFT.waitForPress();
		Motor.B.stop();
		System.out.println(Motor.B.getTachoCount());
	}

	private static void rightButtonPressed() {
		Motor.B.forward();
		Button.RIGHT.waitForPress();
		Motor.B.stop();
		System.out.println(Motor.B.getTachoCount());
	}

	private static void escapeButtonPressed() {
		System.exit(0);

	}

	private static void enterButtonPressed() {
		// TODO Auto-generated method stub

	}

}
