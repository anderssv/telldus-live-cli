package no.f12;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;

import static no.f12.CommandLineTest.createTestApplication;
public class RepositoryTest {

	private static final String DEVICE_MISSING = "666666";

	@Test
	public void shouldSignOnToTelldusLiveApiAndMakeRequest() {
		App app = createTestApplication();
		TelldusRepository repo = app.getTelldusRepository();

		OAuthRequest request = repo.createAndSignRequest("sensors/list", null);
		Response response = request.send();

		assertOk(response);
	}

	@Test
	public void shouldFailWhenWrongDeviceId() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();

		Map<String, String> params = new HashMap<>();
		params.put("id", RepositoryTest.DEVICE_MISSING);
		OAuthRequest request = repo.createAndSignRequest("device/turnOn",
				params);
		Response response = request.send();

		assertNotOk(response);
	}

	@Test
	public void shouldListAllDevices() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();
		List<Device> devices = repo.getDevices();
		assertNotNull(devices);
		assertTrue(devices.size() > 0);
	}

	private void assertOk(Response response) {
		assertEquals("HTTP/1.1 200 OK", response.getHeader(null));
		assertNotNull(response.getBody());
	}

	private void assertNotOk(Response response) {
		assertEquals("HTTP/1.1 200 OK", response.getHeader(null));
		assertTrue(response.getBody(), response.getBody()
				.contains("You do not own"));
	}

}
