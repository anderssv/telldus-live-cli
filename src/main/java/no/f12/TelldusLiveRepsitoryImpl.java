package no.f12;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import static no.f12.JsonParser.parseJson;

public class TelldusLiveRepsitoryImpl implements TelldusRepository {

	@Override
	public Set<Device> getDevices() {
		OAuthRequest request = createAndSignRequest("devices/list", (String) null);
		Response response = request.send();

		MapNavigationWrapper jsonMap = parseJson(response.getBody());
		
		return new HashSet<>();
	}
	
	public OAuthRequest createAndSignRequest(String url, String id) {
		Map<String, String> parameters = idMap(id);
		return createAndSignRequest(url, parameters);
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
		OAuthRequest request = new OAuthRequest(Verb.GET, telldusUrl(url));

		if (parameters != null) {
			for (String parameterName : parameters.keySet()) {
				String parameterValue = parameters.get(parameterName);

				request.addQuerystringParameter(parameterName, parameterValue);
			}
		}

		oService.signRequest(accessToken, request);

		return request;
	}

	Properties readPropertyFile() {
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

	private Map<String, String> idMap(String deviceId) {
		Map<String, String> idParam = new HashMap<String, String>();
		if (deviceId != null) {
			idParam.put("id", deviceId);
		}
		return idParam;
	}


}
