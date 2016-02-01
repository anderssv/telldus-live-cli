package no.f12;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import no.f12.action.DeviceAction;
import no.f12.domain.AbstractAction;
import no.f12.domain.AbstractDevice;
import no.f12.domain.AbstractDeviceState;
import no.f12.domain.SensorState;
import no.f12.rules.RulesBuilder;
import no.f12.telldus.live.domain.Device;

public class RulesTest {

	@Test
	public void testShouldTriggerWhenSensorFallsBelow() {
		RulesBuilder rule = new RulesBuilder().doIf(RulesBuilder.sensorValue("1").below(21))
				.perform(RulesBuilder.action(new Device(1)).on());
		
		Set<AbstractDeviceState> states = new HashSet<>();
		Set<AbstractDevice> devices = rule.getDevices();
		for (AbstractDevice device : devices) {
			states.add(new SensorState(device, 20));
		}
		
		List<AbstractAction> actions = rule.evaluate(states);
		assertEquals(actions.size(), 1);
		assertEquals(new DeviceAction(new Device(1), "on"), actions.get(0));
	}
}