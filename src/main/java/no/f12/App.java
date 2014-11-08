package no.f12;

import java.io.IOException;
import java.util.AbstractMap;

import org.docopt.clj;

/**
 * Hello world!
 *
 */
public class App {

	private TelldusRepository repository;
	
	public App(TelldusRepository repo) {
		this.repository = repo;
		
		if (this.repository == null) {
			throw new IllegalStateException("Cannot create application without repository");
		}
	}
	
	public static void main(String[] args) throws IOException {
		App app = new App(new TelldusLiveRepsitoryImpl());
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
				this.repository.turnDeviceOn(deviceId);
			} else {
				String deviceId = (String) result.get("<device_id>");
				this.repository.turnDeviceOff(deviceId);
			}
		}
		
		return 0;
	}

	public static void print(Object print) {
		System.out.println(print);
	}

	public Boolean getDeviceState(String id) {
		return this.repository.getDeviceState(id);
	}

	public TelldusRepository getTelldusRepository() {
		return this.repository;
	}

}
