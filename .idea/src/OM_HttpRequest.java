import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class OM_HttpRequest {

    private HttpClient client;

    public OM_HttpRequest() {
        this.client = HttpClient.newHttpClient();
    }

    public String sendRequest() throws Exception {

        // Berlin (default)
        String url = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true";

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

        return json;
    }
}
