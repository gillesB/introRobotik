package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Motor;

public class Offset_steuerung {

	public static void main(String[] args) {
//		Motor.B.rotate(1150);
//		Motor.B.rotate(-1250);
//		Button.waitForAnyPress();
//		Motor.B.rotate(-1150);
//		Motor.B.rotate(1300);
		MaximalerEinschlag.einschlag_prozent(-100);
		//Button.waitForAnyPress();
		MaximalerEinschlag.einschlag_prozent(0);
		//Button.waitForAnyPress();
		MaximalerEinschlag.einschlag_prozent(100);
		//Button.waitForAnyPress();
		MaximalerEinschlag.einschlag_prozent(-50);
		//Button.waitForAnyPress();
		MaximalerEinschlag.einschlag_prozent(0);
		//Button.waitForAnyPress();
		
		
	}
	
}
