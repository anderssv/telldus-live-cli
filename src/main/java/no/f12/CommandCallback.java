package no.f12;

public abstract class CommandCallback<T> {
	abstract T doCommand(MapNavigationWrapper result);
	
	void verifySuccess(MapNavigationWrapper result, String deviceId, String operation) {
		String deviceState = (String) result.get("status");
		if (!deviceState.equals("success")) {
			throw new IllegalStateException("Could not "+ operation + " device with id: " + deviceId);
		}
	}
}
