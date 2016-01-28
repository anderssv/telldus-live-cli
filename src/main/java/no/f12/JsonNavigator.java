package no.f12;

import java.util.List;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class JsonNavigator {

	private String json;

	public JsonNavigator(String json) {
		this.json = json;
	}

	public <T> T get(String path, Class<T> type) {
		return JsonPath.read(this.json, path);
	}

	public List findAll(String path) {
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

	public String toString() {
		return this.json;
	}

}
