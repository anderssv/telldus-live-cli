package no.f12;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;

import org.apache.commons.io.IOUtils;

import org.docopt.clj;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws IOException {
		int exitCode = parseCommandLine(args);
		System.exit(exitCode);
	}

	public static int parseCommandLine(String[] args) throws IOException {
		String usage = readClassPathFile(App.class, "usage.txt");
		AbstractMap<String, Object> result = clj.docopt(usage, args);

		if (result == null) {
			print(usage);
			return 1;
		} else if ((Boolean) result.get("--help")) {
			print(usage);
		} else if ((Boolean) result.get("device")) {
			print("Device on!!!");
		}
		
		return 0;
	}

	public static void print(Object print) {
		System.out.println(print);
	}

	public static String readClassPathFile(Class clazz, String filename)
			throws IOException {
		URL resource = clazz.getClassLoader().getResource(filename);
		String json = IOUtils.toString(resource.openStream());
		return json;
	}

}
