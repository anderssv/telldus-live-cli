package no.f12.telldus.live.domain;

public class DeviceEvent {

	private Integer eventState;
	private Integer timeStamp;

	public DeviceEvent(Integer eventState, Integer timeStamp) {
		this.eventState = eventState;
		this.timeStamp = timeStamp;
	}

}
