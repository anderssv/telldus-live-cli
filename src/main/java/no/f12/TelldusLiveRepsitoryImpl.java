package no.f12;

import static no.f12.JsonParser.parseJson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class TelldusLiveRepsitoryImpl implements TelldusRepository {

	@Override
	public List<Device> getDevices() {
		OAuthRequest request = createAndSignRequest("devices/list", null);
		Response response = request.send();

		MapNavigationWrapper jsonMap = parseJson(response.getBody());
		
		List<Map> deviceMaps = (List<Map>) jsonMap.get("device");
		List<MapNavigationWrapper> deviceMapList = new ArrayList<>();
		for (Map deviceMap : deviceMaps) {
			deviceMapList.add(new MapNavigationWrapper(deviceMap));
		}
		
		List<Device> devices = new ArrayList<>();
		for (MapNavigationWrapper deviceMap: deviceMapList) {
			devices.add(new Device(Integer.getInteger((String) deviceMap.get("id"))));
		}
		
		return devices;
	}
	
	@Override
	public Boolean getDeviceState(String id) {
		
		Map<String, String> params = new HashMap<>();
		params.put("id", id);
		params.put("supportedMethods", "1");
		OAuthRequest request = this.createAndSignRequest("device/info",
				params);
		Response response = request.send();

		MapNavigationWrapper jsonMap = parseJson(response.getBody());
		assertOk(response, jsonMap);
		
		String deviceState = (String) jsonMap.get("state");
		Boolean result = Boolean.FALSE;
		if (deviceState.equals("1")) {
			result = Boolean.TRUE;
		}
		
		return result;
	}



	@Override
	public void turnDeviceOn(String deviceId) {
		
	}

	private void assertOk(Response response, MapNavigationWrapper jsonMap) {
		if (!"HTTP/1.1 200 OK".equals(response.getHeader(null)) || response.getBody() == null || jsonMap.get("error") != null) {
			throw new RuntimeException("Result from server is nok ok! Header: " + response.getHeaders() + " --- Result: " + jsonMap.toString());
		}
	}
	
	public OAuthRequest createAndSignRequest(String url,
			Map<String, String> parameters) {

		OAuthRequest request = createRequest(url, parameters);

		Properties props = FileUtil.readPropertyFile();

		OAuthService oService = createAuthService(props);
		Token accessToken = createAccessToken(props);

		oService.signRequest(accessToken, request);

		return request;
	}

	private Token createAccessToken(Properties props) {
		String publicToken = props.getProperty("telldus.api.token.public");
		String secretToken = props.getProperty("telldus.api.token.secret");

		Token accessToken = new Token(publicToken, secretToken);
		return accessToken;
	}

	private OAuthService createAuthService(Properties props) {
		String publicKey = props.getProperty("telldus.api.key.public");
		String secretKey = props.getProperty("telldus.api.key.secret");

		OAuthService oService = new ServiceBuilder().provider(GoogleApi.class)
				.apiKey(publicKey).apiSecret(secretKey).build();
		return oService;
	}
	
	private OAuthRequest createRequest(String url, Map<String, String> parameters) {
		// Create, sign and send request.
		OAuthRequest request = new OAuthRequest(Verb.GET, telldusUrl(url));

		if (parameters != null) {
			for (String parameterName : parameters.keySet()) {
				String parameterValue = parameters.get(parameterName);

				request.addQuerystringParameter(parameterName, parameterValue);
			}
		}

		return request;
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
