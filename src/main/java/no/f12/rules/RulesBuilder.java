package no.f12.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.f12.domain.AbstractAction;
import no.f12.domain.AbstractDevice;
import no.f12.domain.AbstractDeviceState;
import no.f12.telldus.live.domain.Device;

public class RulesBuilder {

	private SensorValueCondition condition;
	private DeviceAction action;

	public static SensorValueCondition sensorValue(String sensorId) {
		return new SensorValueCondition(sensorId);
	}

	public RulesBuilder doIf(SensorValueCondition condition) {
		this.condition = condition;
		return this;
	}

	public static DeviceAction action(Device device) {
		return new DeviceAction(device);
	}

	public RulesBuilder perform(DeviceAction on) {
		this.action = on;

		return this;
	}

	public Set<AbstractDevice> getDevices() {
		Set<AbstractDevice> devices = new HashSet<>();

		return devices;
	}

	public List<AbstractAction> evaluate(Set<AbstractDeviceState> states) {
		ArrayList<AbstractAction> arrayList = new ArrayList<>();
		arrayList.add(new no.f12.action.DeviceAction(this.action.getDevice(), "on"));

		return arrayList;
	}

}
