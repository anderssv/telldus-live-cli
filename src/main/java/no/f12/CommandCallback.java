package no.f12;

public abstract class CommandCallback<T> {
	abstract T doCommand(JsonNavigator result);
	
	void verifySuccess(JsonNavigator result, String deviceId, String operation) {
		String deviceState = result.get("$.status", String.class);
		if (!deviceState.equals("success")) {
			throw new IllegalStateException("Could not "+ operation + " device with id: " + deviceId);
		}
	}
}
