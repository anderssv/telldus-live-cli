package no.f12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scribe.model.OAuthRequest;

public class TelldusLiveRepositoryStub implements TelldusRepository {

	private Map<String, String> deviceStates = new HashMap<>();

	@Override
	public List<Device> getDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuthRequest createAndSignRequest(String string, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getDeviceState(String id) {
		if (!this.deviceStates.containsKey(id)) {
			throw new IllegalStateException("No device with ID: " + id);
		}
		return new Boolean(this.deviceStates.get(id));
	}

	@Override
	public void turnDeviceOn(String deviceId) {
		this.deviceStates.put(deviceId, "true");
	}

}
