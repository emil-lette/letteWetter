import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
public class search extends JFrame {

    private JTextField cityField;
    private JButton searchButton;
    private JLabel resultLabel;

    public search() {
        setTitle("Stadt Koordinaten Finder");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Eingabefeld und Button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        cityField = new JTextField(20);
        searchButton = new JButton("search");

        inputPanel.add(cityField);
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Ergebnisanzeige
        resultLabel = new JLabel("...");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);



        // Button Action
        searchButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a city");
                return;
            }
            try {
                double[] coords = fetchCoordinates(city);
                fetchWeather(coords[0], coords[1]);   // 🔑 THIS is the key line
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data");
            }

        });

        setVisible(true);
    }


    private double[] fetchCoordinates(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, "UTF-8");
        String apiUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("HTTP error: " + connection.getResponseCode());
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(connection.getInputStream());
        if (root.has("results") && root.get("results").size() > 0) {
            JsonNode first = root.get("results").get(0);
            double latitude = first.get("latitude").asDouble();
            double longitude = first.get("longitude").asDouble();
            return new double[]{ latitude, longitude };
        }
        throw new RuntimeException("No city found");
    }

    private void fetchWeather(double latitude, double longitude) throws Exception {
        String apiUrl = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,precipitation_probability_max&hourly=temperature_2m,rain,precipitation_probability,weather_code&current=temperature_2m,is_day,precipitation,rain,showers,snowfall,weather_code",
                latitude,
                longitude
        );
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("HTTP error: " + connection.getResponseCode());
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(connection.getInputStream());
        // Debug: confirm weather arrived
        System.out.println(root.toPrettyString());
        resultLabel.setText("Weather loaded");
    }

    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(search::new);
    //}
    public static void main(String[] args) throws Exception {
        search s = new search();
        double[] coords = s.fetchCoordinates("Paris");
        s.fetchWeather(coords[0], coords[1]);
    }
}
