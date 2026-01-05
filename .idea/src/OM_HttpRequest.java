import java.net.SocketOption;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.net.http.HttpRequest;
import java.rmi.ServerError;
import java.util.Timer;
import java.util.TimerTask;



public class OM_HttpRequest {
    private HttpClient client;
    private Timer timer;

    public OM_HttpRequest() {
        this.client = HttpClient.newHttpClient();
        timer = new Timer();

    }

    public void sendRequest() {
        try {

            // Berlin (default)
            String url = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&daily=weather_code,temperature_2m_min,temperature_2m_max,sunset,sunrise,rain_sum,showers_sum,snowfall_sum,precipitation_hours,precipitation_probability_max,uv_index_max&hourly=temperature_2m,showers,rain,snowfall,precipitation_probability,weather_code,cloud_cover,uv_index,is_day&current=temperature_2m,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,wind_speed_10m";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            // Datei speichern
            Path path = Path.of("Datenbank/current_weather_berlin.json");
            Files.createDirectories(path.getParent());
            Files.writeString(path, json);

            System.out.println("Update erfolgreich!");

        } catch (Exception e) {
            System.err.println("Fehler beim Update!");
            e.printStackTrace();
        }
    }

    public void startUpdater() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendRequest();
            }
        }, 0, 5000);
    }

    // Startpunkt
    public static void main(String[] args) {
        OM_HttpRequest updater = new OM_HttpRequest();
        updater.startUpdater();
    }
}
