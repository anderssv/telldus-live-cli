package no.f12;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommandLineTest {

	@Test
	public void shouldPassCommandLineChecksForDeviceOn() throws IOException {
		App.parseCommandLine(new String[] { "device", "on", "444444" });
	}

	@Test
	public void shouldNotPassCommandLineChecksForBlank() throws IOException {
		int returnCode = App.parseCommandLine(new String[] {});
		assertEquals(1, returnCode);
	}

}
