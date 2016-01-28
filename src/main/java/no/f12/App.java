package no.f12;

import java.io.IOException;
import java.util.AbstractMap;

import org.docopt.clj;

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
		AbstractMap<String, Object> doResult = clj.docopt(usage, args);

		if (doResult == null) {
			print(usage);
			return 1;
		} else if ((Boolean) doResult.get("--help")) {
			print(usage);
		} else if ((Boolean) doResult.get("switch")) {
			String deviceId = (String) doResult.get("<device_id>");
			if ((Boolean) doResult.get("on")) {
				this.repository.turnDeviceOn(deviceId);
			} else if ((Boolean) doResult.get("off")) {
				this.repository.turnDeviceOff(deviceId);
			} else if ((Boolean) doResult.get("status")) {
				print("Device " + deviceId + " state: " + this.getDeviceState(deviceId));
			} else if ((Boolean) doResult.get("history")) {

			}
		} else if ((Boolean) doResult.get("sensor")) {
			String deviceId = (String) doResult.get("<sensor_id>");
			String temperature = this.repository.getSensorValues(deviceId);
			print("Sensor " + deviceId + " temperature: " + temperature);
		} else {
			throw new IllegalArgumentException("Don't know what to do with that command line:" + doResult);
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
