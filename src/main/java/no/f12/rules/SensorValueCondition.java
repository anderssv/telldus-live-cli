package no.f12.rules;

public class SensorValueCondition {

	private String sensorId;
	private int maxValue;

	public SensorValueCondition(String sensorId) {
		this.sensorId = sensorId;
	}

	public SensorValueCondition below(int i) {
		this.maxValue = i;
		return this;
	}

}
