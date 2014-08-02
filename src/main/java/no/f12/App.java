package no.f12;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.docopt.clj;

/**
 * Hello world!
 *
 */
public class App {

	private Map<String, String> deviceStates = new HashMap<>();
	
	public App() {
		
	}
	
	public static void main(String[] args) throws IOException {
		App app = new App();
		int exitCode = app.handleCommandLine(args);
		System.exit(exitCode);
	}

	public int handleCommandLine(String[] args) throws IOException {
		String usage = FileUtil.readClassPathFile(App.class, "usage.txt");
		@SuppressWarnings("unchecked")
		AbstractMap<String, Object> result = clj.docopt(usage, args);

		if (result == null) {
			print(usage);
			return 1;
		} else if ((Boolean) result.get("--help")) {
			print(usage);
		} else if ((Boolean) result.get("device")) {
			if ((Boolean) result.get("on")) {
				String deviceId = (String) result.get("<device_id>");
				this.deviceStates.put(deviceId, "on");
			} else {
				
			}
		}
		
		return 0;
	}

	public static void print(Object print) {
		System.out.println(print);
	}

	public boolean getDeviceState(String id) {
		return this.deviceStates.containsKey(id);
	}

}
