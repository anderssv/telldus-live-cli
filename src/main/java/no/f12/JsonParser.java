package no.f12;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class JsonParser {

	static JsonNavigator parseJson(String json) {
		return new JsonNavigator(json);
	}

}
