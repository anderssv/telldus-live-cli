package no.f12.rules;

import no.f12.telldus.live.domain.Device;

public class DeviceAction {

	private Device device;

	public DeviceAction(Device device) {
		this.device = device;
	}

	public DeviceAction on() {
		return this;
	}

	public Device getDevice() {
		return this.device;
	}

}
