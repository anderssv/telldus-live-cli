package no.f12;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class RepositoryTest {
	@Test
	public void shouldListAllDevices() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();
		Set<Device> devices = repo.getDevices();
		assertNotNull(devices);
		//assertTrue(devices.size() > 0);
	}

}
