package no.f12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;

public class SignOnTests {

	private static final String DEVICE_MISSING="666666";
	
	@Test
	public void shouldSignOnToTelldusLiveApiAndMakeRequest() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();
		OAuthRequest request = repo.createAndSignRequest("sensors/list", null);
		Response response = request.send();

		assertOk(response);
	}

	@Test
	public void shouldTurnOnDevice() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();

		Map<String, String> params = new HashMap<>();
		params.put("id", deviceId());
		OAuthRequest request = repo.createAndSignRequest("device/turnOn",
				params);
		Response response = request.send();

		assertOk(response);
	}
	
	private String deviceId() {
		return FileUtil.readPropertyFile()
				.getProperty("telldus.api.test.onoffdevice");
	}

	@Test
	public void shouldFailWhenWrongDeviceId() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();

		Map<String, String> params = new HashMap<>();
		params.put("id", this.DEVICE_MISSING);
		OAuthRequest request = repo.createAndSignRequest("device/turnOn",
				params);
		Response response = request.send();

		assertNotOk(response);
	}

	private void assertOk(Response response) {
		assertEquals("HTTP/1.1 200 OK", response.getHeader(null));
		assertNotNull(response.getBody());
	}

	private void assertNotOk(Response response) {
		assertEquals("HTTP/1.1 200 OK", response.getHeader(null));
		assertTrue(response.getBody(), response.getBody().contains("not found!"));
	}

}
