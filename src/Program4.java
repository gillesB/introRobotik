import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.util.Delay;

public class Program4 {

	public static void main(String[] args) {
		System.out.println("Program 4");
		Motor.A.rotate(1440, true);

		while (Motor.A.isMoving()) {
			Delay.msDelay(200);
			LCD.drawInt(Motor.A.getTachoCount(), 0, 0);
			if (Button.readButtons() > 0) {
				Motor.A.stop();
			}
		}
		LCD.drawInt(Motor.A.getTachoCount(), 0, 1);
		Button.waitForAnyPress();
	}
}
