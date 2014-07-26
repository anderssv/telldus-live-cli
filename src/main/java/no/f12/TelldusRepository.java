package no.f12;

import java.util.Set;

import org.scribe.model.OAuthRequest;

public interface TelldusRepository {

	Set<Device> getDevices();

	OAuthRequest createAndSignRequest(String string, String deviceId);

}
