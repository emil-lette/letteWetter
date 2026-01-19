import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

public class OM_HttpRequest {

    private final HttpClient client;
    private final Timer timer;
    private final Path filePath;
    private final Path searchFile;

    public OM_HttpRequest() {
        this.client = HttpClient.newHttpClient();
        this.timer = new Timer();
        this.filePath = Path.of("src/current_weather_by_city.json");
        this.searchFile = Path.of("src/Datenbank/search.json");
    }

    private double[] getCoordinates() {
        // Standard-Koordinaten: Berlin
        double latitude = 52.52;
        double longitude = 13.41;

        try {
            if (Files.exists(searchFile)) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(searchFile.toFile());

                if (root.has("results") && root.get("results").size() > 0) {
                    JsonNode first = root.get("results").get(0);
                    latitude = first.get("latitude").asDouble();
                    longitude = first.get("longitude").asDouble();
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Auslesen von search.json, verwende Standard Berlin.");
            e.printStackTrace();
        }

        return new double[]{latitude, longitude};
    }

    public void sendRequest() {
        try {
            double[] coords = getCoordinates();
            double latitude = coords[0];
            double longitude = coords[1];

            String url = String.format(
                    "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,precipitation_probability_max&hourly=temperature_2m,rain,precipitation_probability,weather_code&current=temperature_2m,is_day,precipitation,rain,showers,snowfall,weather_code",
                    latitude, longitude
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // JSON speichern
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, json);

            System.out.println("Update erfolgreich! Datei gespeichert unter: " + filePath.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen oder Speichern!");
            e.printStackTrace();
        }
    }

    public void startUpdater() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendRequest();
            }
        }, 0, 15000); // alle 15 Sekunden
    }

    public static void main(String[] args) {
        OM_HttpRequest updater = new OM_HttpRequest();
        updater.startUpdater();
    }
}
