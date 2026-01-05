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

    public OM_HttpRequest() {
        this.client = HttpClient.newHttpClient();
        this.timer = new Timer();
        // Datei wird im Projektordner gespeichert
        this.filePath = Path.of("src/Datenbank/current_weather_by_city.json");
    }

    // Methode zum Abrufen und Speichern
    public void sendRequest() {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,precipitation_probability_max&hourly=temperature_2m,rain,precipitation_probability,weather_code&current=temperature_2m,is_day,precipitation,rain,showers,snowfall,weather_code";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // JSON in Datei schreiben
            Files.writeString(filePath, json);

            System.out.println("Update erfolgreich! Datei gespeichert unter: " + filePath.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen oder Speichern!");
            e.printStackTrace();
        }
    }

    // Timer starten, alle 15 Sekunden
    public void startUpdater() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendRequest();
            }
        }, 0, 15000); // 15.000 ms = 15 Sekunden
    }

    // Startpunkt
    public static void main(String[] args) {
        OM_HttpRequest updater = new OM_HttpRequest();
        updater.startUpdater();
    }
}
