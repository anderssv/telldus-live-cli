package no.f12.telldus.live.domain;

import java.time.Instant;

public class DeviceEvent {

	private Integer eventState;
	private Instant time;

	public DeviceEvent(Integer eventState, Instant timeStamp) {
		this.eventState = eventState;
		this.time = timeStamp;
	}

}
