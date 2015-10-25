package no.f12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class CommandLineTest {

	public static App createTestApplication() {
		Boolean stubbing = Boolean.FALSE;
		String stubEnvSetting = System.getenv("TELLDUS_CLI_STUB");
		if (stubEnvSetting != null) {
			stubbing = Boolean.TRUE;
		}

		TelldusRepository repo = new TelldusLiveRepsitoryImpl();
		if (stubbing) {
			repo = new TelldusLiveRepositoryStub();
		}

		App app = new App(repo);
		return app;
	}

	@Test
	public void shouldPassCommandLineChecksForDeviceOn() throws IOException {
		int returnCode = createTestApplication().handleCommandLine(
				new String[] { "switch", "on", deviceId() });
		assertEquals(0, returnCode);
	}

	@Test
	public void shouldNotPassCommandLineChecksForBlank() throws IOException {
		int returnCode = createTestApplication().handleCommandLine(
				new String[] {});
		assertEquals(1, returnCode);
	}

	@Test
	public void shouldTurnOnDevice() throws IOException, InterruptedException {
		App application = createTestApplication();

		String deviceId = deviceId();
		application
				.handleCommandLine(new String[] { "switch", "on", deviceId });
		Thread.sleep(3000);
		assertTrue(application.getDeviceState(deviceId));
	}

	@Test
	public void shouldGiveStatusForDevice() throws IOException {
		App application = createTestApplication();

		String deviceId = deviceId();
		application
				.handleCommandLine(new String[] { "switch", "on", deviceId });
		application.handleCommandLine(new String[] { "switch", "status",
				deviceId });
	}

	@Test
	public void shouldTurnOffDevice() throws IOException, InterruptedException {
		App application = createTestApplication();

		String deviceId = deviceId();
		application
				.handleCommandLine(new String[] { "switch", "off", deviceId });
		Thread.sleep(3000);
		assertFalse(application.getDeviceState(deviceId));
	}
	
	@Test
	public void shouldGetSensorValues() throws IOException {
		App application = createTestApplication();

		String deviceId = sensorId();
		application
				.handleCommandLine(new String[] { "sensor", deviceId });
		// TODO Need some way to validate that it's working, no real assertions here
	}

	private String sensorId() {
		return FileUtil.readPropertyFile().getProperty(
				"telldus.api.test.sensor");
	}

	private String deviceId() {
		return FileUtil.readPropertyFile().getProperty(
				"telldus.api.test.onoffdevice");
	}

}
