package no.f12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class CommandLineTest {

	private static String DEVICE_ID = "444444";

	private App createTestApplication() {
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
				new String[] { "device", "on", DEVICE_ID });
		assertEquals(0, returnCode);
	}

	@Test
	public void shouldNotPassCommandLineChecksForBlank() throws IOException {
		int returnCode = createTestApplication().handleCommandLine(
				new String[] {});
		assertEquals(1, returnCode);
	}

	@Test
	public void shouldTurnOnDevice() throws IOException {
		App application = createTestApplication();

		String deviceId = deviceId();
		application
				.handleCommandLine(new String[] { "device", "on", deviceId });
		assertTrue(application.getDeviceState(deviceId));
	}

	private String deviceId() {
		return FileUtil.readPropertyFile().getProperty(
				"telldus.api.test.onoffdevice");
	}

}
