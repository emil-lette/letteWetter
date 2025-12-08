import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonConverter {

    public static void main(String[] args) {

        try {

            // --- Load JSON ---
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File("src/Datenbank/current_weather_berlin.json"));


            // --- Top-level fields ---
            double latitude = root.get("latitude").asDouble();

            double longitude = root.get("longitude").asDouble();

            double generationTime = root.get("generationtime_ms").asDouble();

            int utcOffset = root.get("utc_offset_seconds").asInt();

            String timezone = root.get("timezone").asText();

            String timezoneAbbr = root.get("timezone_abbreviation").asText();

            double elevation = root.get("elevation").asDouble();


            // --- Current weather ---
            JsonNode weather = root.get("current_weather");

            String time = weather.get("time").asText();

            double temperature = weather.get("temperature").asDouble();

            double windspeed = weather.get("windspeed").asDouble();

            int winddirection = weather.get("winddirection").asInt();

            int isDay = weather.get("is_day").asInt();

            int weatherCode = weather.get("weathercode").asInt();


            // --- Pretty print ---
            System.out.println("========= Weather Databank =========");

            System.out.println("Latitude:            " + latitude);

            System.out.println("Longitude:           " + longitude);

            System.out.println("Elevation:           " + elevation + " m");

            System.out.println("Timezone:            " + timezone + " (" + timezoneAbbr + ")");

            System.out.println("Generation Time:     " + generationTime + " ms");

            System.out.println("UTC Offset Seconds:  " + utcOffset);

            System.out.println("--------- Current Weather ---------");

            System.out.println("Time:                " + time);

            System.out.println("Temperature:         " + temperature + " °C");

            System.out.println("Wind Speed:          " + windspeed + " km/h");

            System.out.println("Wind Direction:      " + winddirection + " °");

            System.out.println("Is Day:              " + (isDay == 1 ? "Yes" : "No"));

            System.out.println("Weather Code:        " + weatherCode);

            System.out.println("===================================");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}