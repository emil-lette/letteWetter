import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

    public static WeatherData parse(String jsonString) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        JsonNode weather = root.get("current_weather");

        WeatherData data = new WeatherData();
        data.temperature = weather.get("temperature").asDouble();
        data.weatherCode = weather.get("weathercode").asInt();

        return data;
    }
}
