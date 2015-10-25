package no.f12;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class JsonNavigator {

	private String json;

	public JsonNavigator(String json) {
		this.json = json;
	}

	public Object get(String path) {
		return JsonPath.read(this.json, path);
	}

	public Boolean exists(String path) {
		try {
			JsonPath.read(this.json, path);
			return Boolean.TRUE;
		} catch (PathNotFoundException e) {
			return Boolean.FALSE;
		}
	}

}
