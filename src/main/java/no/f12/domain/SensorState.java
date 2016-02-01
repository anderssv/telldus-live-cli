package no.f12.domain;

public class SensorState extends AbstractDeviceState {

	private AbstractDevice device;
	private int temperature;

	public SensorState(AbstractDevice device, int i) {
		this.device = device;
		this.temperature = i;
	}

}
