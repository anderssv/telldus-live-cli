package no.f12.telldus.live.api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import no.f12.FileUtil;
import no.f12.JsonNavigator;
import no.f12.telldus.live.domain.Device;
import no.f12.telldus.live.domain.DeviceEvent;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class TelldusLiveRepsitoryImpl implements TelldusRepository {

	@Override
	public void turnDeviceOn(String deviceId) {
		new TelldusCommandTemplate(this).execute("device/turnOn", deviceId, new CommandCallback<String>() {
			@Override
			public String doCommand(JsonNavigator jsonMap) {
				this.verifySuccess(jsonMap, deviceId, "turn on");
				return "";
			}
		});
	}

	@Override
	public void turnDeviceOff(String deviceId) {
		new TelldusCommandTemplate(this).execute("device/turnOff", deviceId, new CommandCallback<String>() {
			@Override
			public String doCommand(JsonNavigator jsonMap) {
				this.verifySuccess(jsonMap, deviceId, "turn off");
				return "";
			}
		});
	}

	@Override
	public List<Device> getDevices() {
		return new TelldusCommandTemplate(this).query("devices/list", new CommandCallback<List<Device>>() {
			@Override
			public List<Device> doCommand(JsonNavigator jsonMap) {
				List<String> deviceIds = jsonMap.findAll("$.device[*].id");

				List<Device> devices = new ArrayList<>();
				for (String devideId : deviceIds) {
					devices.add(new Device(Integer.parseInt(devideId)));
				}

				return devices;
			}
		});
	}

	@Override
	public Boolean getDeviceState(String id) {
		Map<String, String> params = new HashMap<>();
		params.put("id", id);
		params.put("supportedMethods", "1");

		return new TelldusCommandTemplate(this).execute("device/info", params, new CommandCallback<Boolean>() {
			@Override
			public Boolean doCommand(JsonNavigator jsonMap) {
				String deviceState = (String) jsonMap.get("$.state", String.class);
				Boolean result = Boolean.FALSE;
				if (deviceState.equals("1")) {
					result = Boolean.TRUE;
				}

				return result;
			}
		});
	}

	public OAuthRequest createAndSignRequest(String url, Map<String, String> parameters) {

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

		OAuthService oService = new ServiceBuilder().provider(GoogleApi.class).apiKey(publicKey).apiSecret(secretKey)
				.build();
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

	@Override
	public String getSensorValues(String deviceId) {
		Map<String, String> params = new HashMap<>();
		params.put("includeValues", "1");

		Map<String, String> sensorValues = new TelldusCommandTemplate(this).execute("sensors/list", params,
				new CommandCallback<Map<String, String>>() {
					@Override
					public Map<String, String> doCommand(JsonNavigator jsonMap) {
						List<Map<String, String>> sensors = jsonMap.findAll("$.sensor");

						Map<String, String> temperatureValues = new HashMap<String, String>();
						for (Map<String, String> sensor : sensors) {
							temperatureValues.put(sensor.get("id"), sensor.get("temp"));
						}

						return temperatureValues;
					}
				});

		return sensorValues.get(deviceId);
	}

	@Override
	public List<DeviceEvent> getDeviceHistory(String deviceId) {
		Map<String, String> params = new HashMap<>();
		params.put("to", "1000000000000");
		return new TelldusCommandTemplate(this).execute("device/history", deviceId, params,
				new CommandCallback<List<DeviceEvent>>() {

					@Override
					List<DeviceEvent> doCommand(JsonNavigator result) {
						List<Map<String, Integer>> rawEvents = result.findAll("$.history");
						List<DeviceEvent> deviceEvents = new ArrayList<DeviceEvent>();

						for (Map<String, Integer> event : rawEvents) {
							deviceEvents.add(new DeviceEvent(event.get("state"), Instant.ofEpochSecond(event.get("ts"))));
						}

						return deviceEvents;
					}
				});
	}

}
