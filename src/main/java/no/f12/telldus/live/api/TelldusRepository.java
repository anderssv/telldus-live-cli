package no.f12.telldus.live.api;

import java.util.List;
import java.util.Map;

import no.f12.telldus.live.domain.Device;
import no.f12.telldus.live.domain.DeviceEvent;

import org.scribe.model.OAuthRequest;

public interface TelldusRepository {

	List<Device> getDevices();

	OAuthRequest createAndSignRequest(String string, Map<String, String> parameters);

	Boolean getDeviceState(String id);

	void turnDeviceOn(String deviceId);

	void turnDeviceOff(String deviceId);

	String getSensorValues(String deviceId);

	List<DeviceEvent> getDeviceHistory(String deviceId);

}
