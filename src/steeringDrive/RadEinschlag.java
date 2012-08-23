package steeringDrive;

import lejos.nxt.Motor;

/**
 * Bewegt die Steuerung des Wagens. Wir gehen davon aus, dass der Motor der die
 * Steuerung bewegt, um n Grad drehen muss, um den Einschlagspunkt der Räder zu
 * erreichen. Die Gradzahl fuer den Einschlagspunkt wurde experimentell
 * ermittelt. Weiterhin gehen wir davon aus, dass beim Einschalten des Wagens
 * die Räder gerade stehen.
 * 
 */
public class RadEinschlag {

	// private static final int MAXIMALER_EINSCHLAG = 1150; // in Grad
	private static final int MAXIMALER_EINSCHLAG = 990;

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
		Motor.B.rotateTo(-MAXIMALER_EINSCHLAG, immediateReturn);
	}

	/**
	 * Bewegt die Räder des Wagens auf den rechten Einschlagpunkt
	 * 
	 * @param immediateReturn
	 */
	public static void max_einschlag_rechts(boolean immediateReturn) {
		Motor.B.rotateTo(MAXIMALER_EINSCHLAG, immediateReturn);
	}

	public static void einschlag_prozent(int prozent) {
		einschlag_prozent(prozent, false);
	}

	private static int aktuellerEinschlag = 0;

	/**
	 * Bewegt die Steuerung des Wagens um einen absoluten Wert. Dieser Wert wird
	 * in Prozent angegeben. Wobei -100% den linke Einschlagpunkt darstellt, 0%
	 * die Ausgangsstellung der Räder darstellt und 100% den rechten
	 * Einschlagpunkt darstellt.
	 * 
	 * @param prozent
	 * @param immediateReturn
	 *            iff true, method returns immediately, thus allowing monitoring
	 *            of sensors in the calling thread.
	 */
	public static void einschlag_prozent(int prozent, boolean immediateReturn) {
		Motor.B.stop();
		int restZumDrehen = prozent - aktuellerEinschlag;
		aktuellerEinschlag = prozent;
		int angle = 0;
		angle = MAXIMALER_EINSCHLAG / 100 * restZumDrehen;

		Motor.B.rotate(angle);
	}

	public static void stop() {
		Motor.B.stop();
	}

}
