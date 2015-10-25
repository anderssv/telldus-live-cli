package no.f12;


public class JsonParser {

	static JsonNavigator parseJson(String json) {
		return new JsonNavigator(json);
	}

}
