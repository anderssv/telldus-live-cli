package no.f12;

import java.util.HashMap;
import java.util.Map;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;

public class TelldusCommandTemplate {

	TelldusRepository repo;

	public TelldusCommandTemplate(TelldusRepository repo) {
		this.repo = repo;
	}

	public <T> T query(String resourceUrl, CommandCallback<T> callback) {
		JsonNavigator jsonMap = performRequest(resourceUrl, new HashMap<>());

		return callback.doCommand(jsonMap);
	}

	public <T> T execute(String resourceUrl, String deviceId, Map<String, String> parameters,
			CommandCallback<T> callback) {
		parameters.put("id", deviceId);
		JsonNavigator jsonMap = performRequest(resourceUrl, parameters);

		return callback.doCommand(jsonMap);
	}

	public <T> T execute(String resourceUrl, Map<String, String> parameters, CommandCallback<T> callback) {
		JsonNavigator jsonMap = performRequest(resourceUrl, parameters);

		return callback.doCommand(jsonMap);
	}

	public <T> T execute(String resourceUrl, String deviceId, CommandCallback<T> callback) {
		Map<String, String> params = new HashMap<>();
		params.put("id", deviceId);

		return this.execute(resourceUrl, params, callback);
	}

	private JsonNavigator performRequest(String resourceUrl, Map<String, String> params) {
		OAuthRequest request = this.repo.createAndSignRequest(resourceUrl, params);
		Response response = request.send();

		JsonNavigator jsonMap = new JsonNavigator(response.getBody());
		assertOk(response, jsonMap);
		return jsonMap;
	}

	private void assertOk(Response response, JsonNavigator jsonMap) {
		if (!"HTTP/1.1 200 OK".equals(response.getHeader(null)) || response.getBody() == null
				|| jsonMap.exists("$.error")) {
			throw new RuntimeException("Result from server is nok ok! Header: " + response.getHeaders()
					+ " --- Result: " + jsonMap.toString());
		}
	}

}
