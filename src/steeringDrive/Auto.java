package steeringDrive;

import lejos.nxt.ADSensorPort;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.EOPD;
import lejos.robotics.navigation.DifferentialPilot;

public class Auto {

	double f = 20.5; // Abstand von Hinterachse zur Wagenfront
	double w = 13.5; // Breite des Autos
	double R = 47.75; // Wendekreis
	double b = 0; // Abstand von Hinterachse zum Wagenende
	double r = Math.sqrt(Math.pow(R, 2) - Math.pow(f, 2)) - w / 2; // sqrt(R^2-f^2)-w/2
	double p = 5.00;
	double alpha = Math.acos((2 * r - w -p) / (2 * r));
	double g = Math.sqrt(2 * r * w + Math.pow(f, 2)) + b; // minLaengeParkluecke
	double kreisbogen = r * alpha;

	public final static float WHEEL_DIAMETER = 5.00f;
	public final static float TRACK_WIDTH = 13.50f;
	public final static boolean REVERSE = true;
	DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER,
			TRACK_WIDTH, Motor.A, Motor.C, REVERSE);
	
	EOPD eopdRechts = new EOPD(SensorPort.S1, true);
	EOPD eopdHinten = new EOPD(SensorPort.S2, true);


	public void fahreWinkelAlpha(boolean direction) {
		int dir = 1;
		if (direction == false){
			dir = -1;
		}
		MaximalerEinschlag.einschlag_prozent(-100*dir);
		System.out.println("r: " + r);
		System.out.println("alpha: " + alpha);
		System.out.println("kreisbogen: " + kreisbogen);
		System.out.println("minLaengeParkluecke: " + g);
		pilot.travel(kreisbogen*dir);
		MaximalerEinschlag.einschlag_prozent(100*dir);
		pilot.travel(kreisbogen*dir);
		MaximalerEinschlag.einschlag_prozent(0);
	}
	
	float gemesseneDistanzStart;
	public void messeStreckeStart(){
		gemesseneDistanzStart = pilot.getMovement().getDistanceTraveled();
	}
	
	public float messeStreckeStop(){
		float gemesseneDistanzStop = pilot.getMovement().getDistanceTraveled();
		//System.out.println("Gefahrene Disanz seit letztem Stop:" + (gemesseneDistanzStop-gemesseneDistanzStart));		
		return gemesseneDistanzStop-gemesseneDistanzStart;
	}
	
	public boolean gegenstandRechts(){
		if (eopdRechts.readRawValue() < 1000){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean gegenstandHinten(){
		if (eopdHinten.readRawValue() < 800){
			return true;
		} else {
			return false;
		}
	}

}
