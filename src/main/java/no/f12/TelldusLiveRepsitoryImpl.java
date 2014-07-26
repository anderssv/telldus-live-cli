package no.f12;

import java.util.HashSet;
import java.util.Set;

public class TelldusLiveRepsitoryImpl implements TelldusRepository {

	@Override
	public Set<Device> getDevices() {
		return new HashSet<>();
	}

}
