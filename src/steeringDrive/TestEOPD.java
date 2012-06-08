package steeringDrive;

import lejos.nxt.*;
import lejos.nxt.addon.EOPD;
import lejos.robotics.objectdetection.*;

public class TestEOPD implements FeatureListener {

	public static int MAX_DETECT = 80;

	public static void main(String[] args) throws Exception {

		TestEOPD listener = new TestEOPD();
		EOPD infaredSensor = new EOPD(SensorPort.S3);
		infaredSensor.setModeLong();
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
				buttonPressed(infaredSensor);
			}
		}

	}

	private static void buttonPressed(EOPD infaredSensor) {
		System.out
				.println("Processed Value: " + infaredSensor.processedValue());
		System.out.println("Raw Value: " + infaredSensor.readRawValue());

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
