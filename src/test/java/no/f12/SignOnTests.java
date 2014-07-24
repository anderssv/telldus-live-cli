package no.f12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class SignOnTests {

	@Test
	public void shouldSignOnToTelldusLiveApiAndMakeRequest() {
		OAuthRequest request = createAndSignRequest(telldusUrl("sensors/list"),
				null);
		Response response = request.send();

		assertOk(response);
	}

	@Test
	public void shouldTurnOnDevice() {
		String deviceId = readPropertyFile().getProperty("telldus.api.test.onoffdevice");

		OAuthRequest request = createAndSignRequest(
				telldusUrl("device/turnOn"), idMap(deviceId));
		Response response = request.send();

		assertOk(response);
	}

	@Test
	public void shouldFailWhenWrongDeviceId() {
		OAuthRequest request = createAndSignRequest(
				telldusUrl("device/turnOn"), idMap("777777777"));
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

	private Map<String, String> idMap(String deviceId) {
		Map<String, String> idParam = new HashMap<String, String>();
		idParam.put("id", deviceId);
		return idParam;
	}

	private OAuthRequest createAndSignRequest(String url,
			Map<String, String> parameters) {

		Properties props = readPropertyFile();

		String publicKey = props.getProperty("telldus.api.key.public");
		String secretKey = props.getProperty("telldus.api.key.secret");

		String publicToken = props.getProperty("telldus.api.token.public");
		String secretToken = props.getProperty("telldus.api.token.secret");

		OAuthService oService = new ServiceBuilder().provider(GoogleApi.class)
				.apiKey(publicKey).apiSecret(secretKey).build();

		Token accessToken = new Token(publicToken, secretToken);

		// Create, sign and send request.
		OAuthRequest request = new OAuthRequest(Verb.GET, url);

		if (parameters != null) {
			for (String parameterName : parameters.keySet()) {
				String parameterValue = parameters.get(parameterName);

				request.addQuerystringParameter(parameterName, parameterValue);
			}
		}

		oService.signRequest(accessToken, request);

		return request;
	}

	private Properties readPropertyFile() {
		Properties props = new Properties();
		try {
			File file = new File("telldus-auth.properties");
			FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(
					"Could not locate properties for Telldus authentication", e);
		} catch (IOException e) {
			throw new RuntimeException(
					"Error reading properties file for Telldus authentication",
					e);
		}
		return props;
	}

	private String telldusUrl(String extension) {
		return "http://api.telldus.com/json/" + extension;
	}

}
