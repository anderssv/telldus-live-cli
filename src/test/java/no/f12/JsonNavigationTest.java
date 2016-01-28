package no.f12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class JsonNavigationTest {

	@Test
	public void shouldNavigateToSpecificDeviceInArray2() throws IOException {
		String json = FileUtil.readClassPathFile(this.getClass(), "device-list.json");

		JsonNavigator jsonNav = new JsonNavigator(json);

		assertNotNull(jsonNav.get("$.device", Map.class));
		assertTrue(jsonNav.get("$.device", List.class) instanceof List);
		assertEquals("1", jsonNav.get("$.device[0].id", String.class));
		assertTrue(jsonNav.get("$.device[0]", Map.class) instanceof Map);
		assertTrue(jsonNav.get("$.device[?(@.id == 2)]", Object.class).toString(),
				jsonNav.findAll("$.device[?(@.id == 2)]").get(0) instanceof Map);
		assertEquals("2", ((Map) jsonNav.findAll("$.device[?(@.id == 2)]").get(0)).get("id"));
	}

}
