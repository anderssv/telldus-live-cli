package no.f12;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class JsonParser {

	static MapNavigationWrapper parseJson(String json) {
		Map<String, Object> map = new Gson().fromJson(json, HashMap.class);
		MapNavigationWrapper wrapper = new MapNavigationWrapper(map);
		return wrapper;
	}

}
