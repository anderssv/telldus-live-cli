package no.f12;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class FileUtil {

	@SuppressWarnings("rawtypes")
	public static String readClassPathFile(Class clazz, String filename)
			throws IOException {
		URL resource = clazz.getClassLoader().getResource(filename);
		String json = IOUtils.toString(resource.openStream());
		return json;
	}

}
