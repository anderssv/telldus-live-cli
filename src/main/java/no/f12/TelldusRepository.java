package no.f12;

import java.util.List;
import java.util.Map;

import org.scribe.model.OAuthRequest;

public interface TelldusRepository {

	List<Device> getDevices();

	OAuthRequest createAndSignRequest(String string, Map<String, String> parameters);

	Boolean getDeviceState(String id);

	void turnDeviceOn(String deviceId);

	void turnDeviceOff(String deviceId);

	Map<String, String> getSensorValues(String deviceId);

}
