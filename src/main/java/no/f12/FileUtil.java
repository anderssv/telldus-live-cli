package no.f12;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class FileUtil {

	public static Properties readPropertyFile() {
		Properties props = new Properties();
		try {
			File file = new File("telldus-auth.properties");
			FileInputStream fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(
					"Could not locate properties for Telldus authentication", e);
		} catch (IOException e) {
			throw new RuntimeException(
					"Error reading properties file for Telldus authentication",
					e);
		}
		return props;
	}

	@SuppressWarnings("rawtypes")
	public static String readClassPathFile(Class clazz, String filename)
			throws IOException {
		URL resource = clazz.getClassLoader().getResource(filename);
		String json = IOUtils.toString(resource.openStream());
		return json;
	}

}
