import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OM_HttpRequest {

    private HttpClient client;

    public OM_HttpRequest() {
        this.client = HttpClient.newHttpClient();
    }

    public String sendRequest() throws Exception {

        // Berlin
        String url = "https://api.open-meteo.com/v1/forecast" + "?latitude=52.52&longitude=13.41&current_weather=true";
        ;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    // main methode mit print test
    public static void main(String[] args) throws Exception {
        OM_HttpRequest req = new OM_HttpRequest();
        System.out.println(req.sendRequest());
    }
}
