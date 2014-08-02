package no.f12;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class JsonNavigationTest {

	@Test
	public void shouldNavigateToSpecificDeviceInArray() throws IOException {
		String json = FileUtil.readClassPathFile(this.getClass(), "device-list.json");
		
		MapNavigationWrapper jsonMap = JsonParser.parseJson(json);
		
		assertNotNull(json);
		assertNotNull(jsonMap);
		assertNotNull(jsonMap.get("device"));
		assertNotNull(jsonMap.get("device[0]"));
		assertTrue(((Map) jsonMap.get("device[0]")).get("id").equals("1"));
	}
	
	@Test
	public void shouldNavigateToSpecificDeviceBasedOnAttribute() throws IOException {
		String json = FileUtil.readClassPathFile(this.getClass(), "device-list.json");
		
		MapNavigationWrapper jsonMap = JsonParser.parseJson(json);
		
		assertNotNull(jsonMap.get("device[id=2]"));
		assertTrue(jsonMap.get("device[id=2]") instanceof Map);
		assertTrue(((Map) jsonMap.get("device[id=2]")).get("id").equals("2"));
	}
	
	
	
}
