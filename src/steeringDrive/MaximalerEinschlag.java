package steeringDrive;

import lejos.nxt.Motor;

/**
 * Bewegt die Steuerung des Wagens.
 * Wir gehen davon aus, dass der Motor der die Steuerung bewegt, um n Grad drehen muss, um den Einschlagspunkt der Räder zu erreichen.
 * Diese 2 Gradzahlen (linker und rechter Einschlagspunkt) wurden experimentell ermittelt.
 * 
 */
public class MaximalerEinschlag {
	
	private static final int MAXIMALER_EINSCHLAG_LINKS = -1000; //in Grad
	private static final int MAXIMALER_EINSCHLAG_RECHTS = 1000; //in Grad	
	
	public static void einschlag_links(){
		einschlag_links(false);
	}
	
	public static void einschlag_rechts(){
		einschlag_rechts(false);
	}
	
	/**
	 * Bewegt die Räder des Wagens auf den linken Einschlagpunkt
	 * @param immediateReturn
	 */
	public static void einschlag_links(boolean immediateReturn){
		Motor.B.rotateTo(MAXIMALER_EINSCHLAG_LINKS, immediateReturn);
	}
	
	/**
	 * Bewegt die Räder des Wagens auf den rechten Einschlagpunkt
	 * @param immediateReturn
	 */
	public static void einschlag_rechts(boolean immediateReturn){
		Motor.B.rotateTo(MAXIMALER_EINSCHLAG_RECHTS, immediateReturn);
	}
	
	public static void einschlag_prozent(int prozent){
		einschlag_prozent(prozent, false);
	}
	
	/**
	 * Bewegt die Steuerung des Wagens um einen absoluten Wert. Dieser Wert wird in Prozent angegeben.
	 * Wobei -100% den linke Einschlagpunkt darstellt, 0% die Ausgangsstellung der Räder darstellt
	 * und 100% den rechten Einschlagpunkt dartsellt.
	 * @param prozent
	 * @param immediateReturn iff true, method returns immediately, thus allowing monitoring of sensors in the calling thread.
	 */
	public static void einschlag_prozent(int prozent, boolean immediateReturn){
		int angle = 0;
		//TODO pruefe ob nicht doch float benutzt werden muss
		if(prozent < 0){
			angle = MAXIMALER_EINSCHLAG_LINKS/100*(-prozent);			
		} else if (prozent > 0){
			angle = MAXIMALER_EINSCHLAG_RECHTS/100*prozent;
		}
		Motor.B.rotateTo(angle, immediateReturn);
	}

}
