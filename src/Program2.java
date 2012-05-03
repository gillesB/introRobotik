import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.util.Delay;

public class Program2 {

	public static void main(String[] args) {
		int line = 0;
		LCD.drawString("Program 2", 0, line++);
		Motor.A.setSpeed(720);
		
		Motor.A.forward();
		Delay.msDelay(2000);

		int motorAngle = Motor.A.getTachoCount();
		LCD.drawString("motor angle: " + motorAngle, 0, line++);

		Motor.A.stop();

		motorAngle = Motor.A.getTachoCount();
		LCD.drawString("motor angle: " + motorAngle, 0, line++);

		Motor.A.backward();
		// wait till tacho reaches 0
		while (Motor.A.getTachoCount() > 0) {
			;
		}
		motorAngle = Motor.A.getTachoCount();
		LCD.drawString("motor angle: " + motorAngle, 0, line++);

		Motor.A.stop();

		motorAngle = Motor.A.getTachoCount();
		LCD.drawString("motor angle: " + motorAngle, 0, line++);
		
		Button.waitForAnyPress();

	}

}
