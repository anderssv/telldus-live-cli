package no.f12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class JsonNavigationTest {

	@Test
	public void shouldNavigateToSpecificDeviceInArray() throws IOException {
		String json = FileUtil.readClassPathFile(this.getClass(), "device-list.json");
		
		MapNavigationWrapper jsonMap = JsonParser.parseJson(json);
		
		assertNotNull(json);
		assertNotNull(jsonMap);
		assertNotNull(jsonMap.get("device"));
		assertTrue(jsonMap.get("device") instanceof List);
		assertNotNull(jsonMap.get("device[0]"));
		assertTrue(jsonMap.get("device[0]") instanceof Map);
		assertEquals("1", ((Map) jsonMap.get("device[0]")).get("id"));
	}
	
	@Test
	public void shouldNavigateToSpecificDeviceBasedOnAttribute() throws IOException {
		String json = FileUtil.readClassPathFile(this.getClass(), "device-list.json");
		
		MapNavigationWrapper jsonMap = JsonParser.parseJson(json);
		
		assertNotNull(jsonMap.get("device[id=2]"));
		assertTrue(jsonMap.get("device[id=2]") instanceof Map);
		assertTrue(((Map) jsonMap.get("device[id=2]")).get("id").equals("2"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionOnInvalidPath() throws IOException {
		String json = FileUtil.readClassPathFile(this.getClass(), "device-list.json");
		
		MapNavigationWrapper jsonMap = JsonParser.parseJson(json);
		jsonMap.get("nononono");
		
	}
	
	@Test
	@Ignore
	public void shouldCreateMap() throws IOException {
		String json = FileUtil.readClassPathFile(this.getClass(), "sensor-list.json");
		MapNavigationWrapper jsonMap = JsonParser.parseJson(json);

		Map<String, String> tempMap = jsonMap.getMap("sensor.id", "sensor.temp");
		assertNotNull(tempMap);
		assertEquals("22.9", tempMap.get("1"));
	}
	
	
	
}
