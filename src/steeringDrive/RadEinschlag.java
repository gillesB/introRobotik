package steeringDrive;

import lejos.nxt.Button;
import lejos.nxt.Motor;

/**
 * Bewegt die Steuerung des Wagens. Wir gehen davon aus, dass der Motor der die
 * Steuerung bewegt, um n Grad drehen muss, um den Einschlagspunkt der Räder zu
 * erreichen. Diese 2 Gradzahlen (linker und rechter Einschlagspunkt) wurden
 * experimentell ermittelt.
 * 
 */
public class RadEinschlag {

	private static final int MAXIMALER_EINSCHLAG_LINKS = -1150; // in Grad
	private static final int MAXIMALER_EINSCHLAG_RECHTS = 1150; // in Grad
	private static final int OFFSET_LEFT_TO_RIGHT = 150;
	private static final int OFFSET_RIGHT_TO_LEFT = -150;

	public static void max_einschlag_links() {
		max_einschlag_links(false);
	}

	public static void max_einschlag_rechts() {
		max_einschlag_rechts(false);
	}

	/**
	 * Bewegt die Räder des Wagens auf den linken Einschlagpunkt
	 * 
	 * @param immediateReturn
	 */
	public static void max_einschlag_links(boolean immediateReturn) {
		Motor.B.rotateTo(MAXIMALER_EINSCHLAG_LINKS, immediateReturn);
	}

	/**
	 * Bewegt die Räder des Wagens auf den rechten Einschlagpunkt
	 * 
	 * @param immediateReturn
	 */
	public static void max_einschlag_rechts(boolean immediateReturn) {
		Motor.B.rotateTo(MAXIMALER_EINSCHLAG_RECHTS, immediateReturn);
	}

	public static void einschlag_prozent(int prozent) {
		einschlag_prozent(prozent, false);
	}


	private static int currentSteeringLock = 0;
	private static enum lastDirectionEnum {LEFT, RIGHT};
	private static lastDirectionEnum lastDirection;
	//TODO aufraumen!
	/**
	 * Bewegt die Steuerung des Wagens um einen absoluten Wert. Dieser Wert wird
	 * in Prozent angegeben. Wobei -100% den linke Einschlagpunkt darstellt, 0%
	 * die Ausgangsstellung der Räder darstellt und 100% den rechten
	 * Einschlagpunkt dartsellt.
	 * 
	 * @param prozent
	 * @param immediateReturn
	 *            iff true, method returns immediately, thus allowing monitoring
	 *            of sensors in the calling thread.
	 */
	public static void einschlag_prozent(int prozent, boolean immediateReturn) {
		Motor.B.stop();
		int restZumDrehen = prozent - currentSteeringLock;
		currentSteeringLock = prozent;
		int angle = 0;
		angle = MAXIMALER_EINSCHLAG_RECHTS / 100 * restZumDrehen;
		
		Motor.B.rotate(angle);
	}
	
	public static void stop(){
		Motor.B.stop();
	}

}
