import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class program1 {

	public static void main(String[] args) {
		LCD.drawString("Program 1", 0, 0);
		Button.waitForAnyPress();
		Motor.A.forward();
		LCD.drawString("Forward", 0, 1);
		Button.waitForAnyPress();
		Motor.A.backward();
		LCD.drawString("Forward", 0, 2);
		Button.waitForAnyPress();
		Motor.A.stop();
	}

}
