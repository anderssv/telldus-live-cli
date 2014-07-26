package no.f12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;

public class SignOnTests {

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
		String deviceId = ((TelldusLiveRepsitoryImpl) repo).readPropertyFile()
				.getProperty("telldus.api.test.onoffdevice");

		OAuthRequest request = repo.createAndSignRequest("device/turnOn",
				deviceId);
		Response response = request.send();

		assertOk(response);
	}

	@Test
	public void shouldFailWhenWrongDeviceId() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();

		OAuthRequest request = repo.createAndSignRequest("device/turnOn",
				"777777777");
		Response response = request.send();

		assertNotOk(response);
	}

	private void assertOk(Response response) {
		assertEquals("HTTP/1.1 200 OK", response.getHeader(null));
		assertNotNull(response.getBody());
	}

	private void assertNotOk(Response response) {
		assertEquals("HTTP/1.1 200 OK", response.getHeader(null));
		assertTrue(response.getBody().contains("not found!"));
	}

}
