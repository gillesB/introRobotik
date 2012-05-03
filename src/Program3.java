import lejos.nxt.Button;
import lejos.nxt.Motor;

public class Program3 {

	public static void main(String[] args) {
		System.out.println("Program 3");
		Button.waitForAnyPress();
		Motor.A.rotate(1480);
		System.out.println("motor angle: " + Motor.A.getTachoCount());
		Motor.A.rotateTo(0);
		System.out.println("motor angle: " + Motor.A.getTachoCount());
		Button.waitForAnyPress();
	}
}
