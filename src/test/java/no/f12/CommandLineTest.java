package no.f12;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommandLineTest {

	private static String DEVICE_ID = "444444";
	
	
	private App createTestApplication() {
		App app = new App();
		return app;
	}
	
	@Test
	public void shouldPassCommandLineChecksForDeviceOn() throws IOException {
		int returnCode = createTestApplication().handleCommandLine(new String[] { "device", "on", DEVICE_ID });
		assertEquals(0, returnCode);
	}

	@Test
	public void shouldNotPassCommandLineChecksForBlank() throws IOException {
		int returnCode = createTestApplication().handleCommandLine(new String[] {});
		assertEquals(1, returnCode);
	}

	@Test
	public void shouldTurnOnDevice() throws IOException {
		App application = new App();
		
		application.handleCommandLine(new String[] {"device", "on", DEVICE_ID});
		assertTrue(application.getDeviceState(DEVICE_ID));
	}
	
	@Test
	public void shouldListAllDevices() {
		TelldusRepository repo = new TelldusLiveRepsitoryImpl();
		Set<Device> devices = repo.getDevices();
		assertNotNull(devices);
	}
	

}
