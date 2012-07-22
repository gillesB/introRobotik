package steeringDrive;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.EOPD;
import lejos.robotics.navigation.DifferentialPilot;

public class Auto {

	double f = 20.5; // Abstand von Hinterachse zur Wagenfront
	double w = 13.5; // Breite des Autos
	double R = 47.75; // Wendekreis
	double b = 3.0; // Abstand von Hinterachse zum Wagenende
	//r der Radius des kleinsten Kreises, den der Automittelpunkt, beschreiben kann
	double r = (Math.sqrt(Math.pow(R, 2) - Math.pow(f, 2)) - w / 2); // sqrt(R^2-f^2)-w/2
	double p = 15.00;
	double alpha = Math.acos((2 * r - w - p) / (2 * r));
	double g = Math.sqrt(2 * r * w + Math.pow(f, 2)) + b; // minLaengeParkluecke
	double sicherheit = 1;
	double kreisbogen = r * alpha * sicherheit;

	public final static float WHEEL_DIAMETER = 5.00f;
	public final static float TRACK_WIDTH = 13.50f;
	public final static boolean REVERSE = true;
	DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER,
			TRACK_WIDTH, Motor.A, Motor.C, REVERSE);

	EOPD eopdRechts = new EOPD(SensorPort.S1, true);
	EOPD eopdHinten = new EOPD(SensorPort.S2, true);

	public void fahreWinkelAlpha(boolean direction) {
		int dir = 1;
		if (direction == false) {
			dir = -1;
		}
		RadEinschlag.einschlag_prozent(-100 * dir);
		System.out.println("r: " + r);
		System.out.println("alpha: " + alpha);
		System.out.println("kreisbogen: " + kreisbogen);
		System.out.println("minLaengeParkluecke: " + g);
		pilot.travel(kreisbogen * dir, true);
		while(pilot.isMoving()){
			if (dir == -1 && gegenstandHinten()){
				pilot.stop();
				Sound.buzz();
			}
		}
		RadEinschlag.einschlag_prozent(100 * dir);
		pilot.travel(kreisbogen * dir, true);
		while(pilot.isMoving()){
			if (dir == -1 && gegenstandHinten()){
				pilot.stop();
				Sound.buzz();
			}
		}
		RadEinschlag.einschlag_prozent(0);
	}
	
	public void fahreWinkelAlphaVorwaerts(boolean direction) {
		int dir = 1;
		if (direction == false) {
			dir = -1;
		}
		RadEinschlag.einschlag_prozent(-100 * dir);
		System.out.println("r: " + r);
		System.out.println("alpha: " + alpha);
		System.out.println("kreisbogen: " + kreisbogen);
		System.out.println("minLaengeParkluecke: " + g);
		pilot.travel(kreisbogen * dir * 0.80, true);
		while(pilot.isMoving()){
			if (dir == -1 && gegenstandHinten()){
				pilot.stop();
				Sound.buzz();
			}
		}
		RadEinschlag.einschlag_prozent(100 * dir);
		pilot.travel(kreisbogen * dir, true);
		while(pilot.isMoving()){
			if (dir == -1 && gegenstandHinten()){
				pilot.stop();
				Sound.buzz();
			}
		}

	}

	float gemesseneDistanzStart;

	public void messeStreckeStart() {
		gemesseneDistanzStart = pilot.getMovement().getDistanceTraveled();
	}

	public float messeStreckeStop() {
		float gemesseneDistanzStop = pilot.getMovement().getDistanceTraveled();
		// System.out.println("Gefahrene Disanz seit letztem Stop:" +
		// (gemesseneDistanzStop-gemesseneDistanzStart));
		return gemesseneDistanzStop - gemesseneDistanzStart;
	}

	public boolean gegenstandRechts() {
		if (eopdRechts.readRawValue() < 1000) {
			return true;
		} else {
			return false;
		}
	}

	public boolean gegenstandHinten() {
		if (eopdHinten.readRawValue() < 800) {
			return true;
		} else {
			return false;
		}
	}

	int distance;
	int MotorBAnfangsgeschw;
	public void fahreAnMauerEntlangStart(){
		distance = eopdRechts.readRawValue();
		System.out.println(distance);
		MotorBAnfangsgeschw = Motor.B.getRotationSpeed();
		Motor.B.setSpeed(Motor.B.getMaxSpeed());
	}

	public void fahreAnMauerEntlang() {
		int currentDistance = eopdRechts.readRawValue();
		//Motor.B.flt();
		System.out.println("currentDistance: " + currentDistance);
		if (currentDistance > distance) { //Distanz zur Mauer ist groesser geworden
			if (Motor.B.getTachoCount() < 200) {
				Motor.B.forward();
			} else {
				Motor.B.flt();
			}
			System.out.println("Nach rechts bewegen: "+Motor.B.getTachoCount());
		} else if (currentDistance < distance) {
			if(Motor.B.getTachoCount() > -200){
				Motor.B.backward();	
			} else {
				Motor.B.flt();
			}
			System.out.println("Nach links bewegen: "+ Motor.B.getTachoCount());
		} else {
			System.out.println("Nicht bewegen.");
		}
	}

	public void fahreAnMauerEntlangStop() {
		RadEinschlag.einschlag_prozent(0, true);
		Motor.B.setSpeed(MotorBAnfangsgeschw);		
	}

}
