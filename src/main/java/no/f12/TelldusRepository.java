package no.f12;

import java.util.List;

import org.scribe.model.OAuthRequest;

public interface TelldusRepository {

	List<Device> getDevices();

	OAuthRequest createAndSignRequest(String string, String deviceId);

	Boolean getDeviceState(String id);

	void turnDeviceOn(String deviceId);

}
