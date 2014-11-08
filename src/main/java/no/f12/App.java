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
			String deviceId = (String) result.get("<device_id>");
			if ((Boolean) result.get("on")) {
				this.repository.turnDeviceOn(deviceId);
			} else if((Boolean) result.get("off")) {
				this.repository.turnDeviceOff(deviceId);
			} else if ((Boolean) result.get("status")) {
				System.out.println(this.repository.getDeviceState(deviceId));
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
