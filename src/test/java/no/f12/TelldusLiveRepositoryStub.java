package no.f12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.f12.telldus.live.api.TelldusRepository;
import no.f12.telldus.live.domain.Device;
import no.f12.telldus.live.domain.DeviceEvent;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;

import static org.mockito.Mockito.*;

public class TelldusLiveRepositoryStub implements TelldusRepository {

	private Map<String, String> deviceStates = new HashMap<>();

	@Override
	public List<Device> getDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuthRequest createAndSignRequest(String string, Map<String, String> params) {
		OAuthRequest request = mock(OAuthRequest.class);
		Response response = mock(Response.class);

		when(request.send()).thenReturn(response);
		when(response.getHeader(null)).thenReturn("HTTP/1.1 200 OK");
		when(response.getBody()).thenReturn("");

		return request;
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

	@Override
	public void turnDeviceOff(String deviceId) {
		this.deviceStates.put(deviceId, "false");
	}

	@Override
	public String getSensorValues(String deviceId) {
		return "10";
	}

	@Override
	public List<DeviceEvent> getDeviceHistory(String deviceId) {
		return null;
	}

}
