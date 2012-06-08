package steeringDrive;

import lejos.nxt.*;
import lejos.nxt.addon.EOPD;
import lejos.robotics.objectdetection.*;

public class TestUltraS implements FeatureListener {

	public static int MAX_DETECT = 80;

	public static void main(String[] args) throws Exception {

		TestUltraS listener = new TestUltraS();
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
		// UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		// RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT,
		// 500);

		// infaredSensor.addListener(listener);

		while (true) {
			switch (Button.waitForAnyPress()) {
			case Button.ID_ESCAPE:
				escapeButtonPressed();
				break;
			default:
				buttonPressed(ultrasonicSensor);
			}
		}

	}

	private static void buttonPressed(UltrasonicSensor ultrasonicSensor) {
		System.out.println("Range: "+ ultrasonicSensor.getRange());;

	}

	private static void escapeButtonPressed() {
		System.exit(0);

	}

	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int) feature.getRangeReading().getRange();
		Sound.playTone(1200 - (range * 10), 100);
		System.out.println("Range:" + range);
	}

}
