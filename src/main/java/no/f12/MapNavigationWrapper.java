package no.f12;

import java.util.Arrays;
import java.util.Map;

public class MapNavigationWrapper {

	private Map<String, Object> map;

	public MapNavigationWrapper(Map<String, Object> map) {
		this.map = map;
	}

	public Object get(String path) {
		return recurse(map, path.split("\\."));

	}

	private Object recurse(Object currentLevel, String[] elements) {
		if (currentLevel instanceof Map) {
			return recurse(((Map) currentLevel).get(elements[0]),
					Arrays.copyOfRange(elements, 1, elements.length));
		}
		return currentLevel;
	}

}
