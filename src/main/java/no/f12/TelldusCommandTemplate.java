package no.f12;

import static no.f12.JsonParser.parseJson;

import java.util.HashMap;
import java.util.Map;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;

public class TelldusCommandTemplate {
	
	TelldusRepository repo;
	
	public TelldusCommandTemplate(TelldusRepository repo) {
		this.repo = repo;
	}

	public <T> T execute(String resourceUrl, String deviceId, CommandCallback<T> callback) {
		Map<String, String> params = new HashMap<>();
		params.put("id", deviceId);
		OAuthRequest request = this.repo.createAndSignRequest(resourceUrl,
				params);
		Response response = request.send();

		MapNavigationWrapper jsonMap = parseJson(response.getBody());
		assertOk(response, jsonMap);

		return callback.doCommand(jsonMap);
	}
	
	private void assertOk(Response response, MapNavigationWrapper jsonMap) {
		if (!"HTTP/1.1 200 OK".equals(response.getHeader(null)) || response.getBody() == null || jsonMap.check("error") != null) {
			throw new RuntimeException("Result from server is nok ok! Header: " + response.getHeaders() + " --- Result: " + jsonMap.toString());
		}
	}

}
