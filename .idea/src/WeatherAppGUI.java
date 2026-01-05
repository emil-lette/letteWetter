import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherAppGUI extends JFrame {

    private JTextField cityField;
    private JButton searchButton;
    private JLabel coordinatesLabel;
    private JLabel cityLabel;

    public WeatherAppGUI() {
        setTitle("Wetter App + Search");
        setSize(600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        /* ===== SEARCH PANEL ===== */
        JPanel searchPanel = new JPanel(new BorderLayout(5,5));

        // Eingabefeld + Button
        JPanel inputPanel = new JPanel(new FlowLayout());
        cityField = new JTextField(20);
        searchButton = new JButton("Search");
        inputPanel.add(cityField);
        inputPanel.add(searchButton);

        // Ergebnisanzeige
        JPanel resultPanel = new JPanel(new GridLayout(2,1));
        coordinatesLabel = new JLabel("Coordinates: 52.52 13.42", SwingConstants.CENTER);
        cityLabel = new JLabel("City: Berlin", SwingConstants.CENTER);
        resultPanel.add(coordinatesLabel);
        resultPanel.add(cityLabel);

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(resultPanel, BorderLayout.CENTER);

        add(searchPanel, BorderLayout.NORTH);

        // Button Action
        searchButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                fetchCoordinates(city);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a city");
            }
        });

        /* ===== WEATHER DATA ===== */
        double temperature = 0;
        int weatherCode = 0;
        JsonNode daily = null;
        JsonNode weekly = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(".idea/src/Datenbank/test.json"));

            JsonNode weather = root.get("current_weather");
            temperature = weather.get("temperature").asDouble();
            weatherCode = weather.get("weathercode").asInt();

            daily = root.get("daily_weather");
            weekly = root.get("weekly_weather");

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* ===== CURRENT WEATHER PANEL ===== */
        JPanel weatherPanel = new JPanel(new GridLayout(1, 2));
        String iconPath;
        switch (weatherCode) {
            case 0 -> iconPath = "src/img/sun.png";
            case 1, 2, 3 -> iconPath = "src/img/cloud.png";
            case 61, 63, 65 -> iconPath = "src/img/rain.png";
            case 71, 73, 75 -> iconPath = "src/img/snow.png";
            default -> iconPath = "src/img/cloud.png";
        }

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        JLabel currentCondition = new JLabel(new ImageIcon(scaledIcon));
        JLabel currentTemp = new JLabel(temperature + " °C", SwingConstants.CENTER);
        currentTemp.setFont(new Font("Arial", Font.BOLD, 24));

        weatherPanel.add(currentCondition);
        weatherPanel.add(currentTemp);

        /* ===== TAGESÜBERSICHT PANEL ===== */
        JPanel tagesPanel = new JPanel();
        int rows = (daily != null) ? daily.size() : 4;
        tagesPanel.setLayout(new GridLayout(rows, 4, 10, 10));

        if (daily != null) {
            for (JsonNode entry : daily) {
                String time = entry.get("time").asText();
                String condition = entry.get("condition").asText();
                String temp = entry.get("temperature").asText() + " °C";
                String rain = entry.get("rain_chance").asText() + " %";

                tagesPanel.add(new JLabel(time));
                tagesPanel.add(new JLabel(condition));
                tagesPanel.add(new JLabel(temp));
                tagesPanel.add(new JLabel(rain));
            }
        }

        /* ===== WOCHENÜBERSICHT PANEL ===== */
        JPanel wochenPanel = new JPanel(new GridLayout(4, 7));
        if (weekly != null) {
            for (JsonNode entry : weekly) {
                wochenPanel.add(new JLabel(entry.get("day").asText()));
            }
            for (JsonNode entry : weekly) {
                wochenPanel.add(new JLabel(entry.get("condition").asText()));
            }
            for (JsonNode entry : weekly) {
                wochenPanel.add(new JLabel(entry.get("temperature").asText() + " °C"));
            }
            for (JsonNode entry : weekly) {
                wochenPanel.add(new JLabel(entry.get("rain_chance").asText() + " %"));
            }
        }

        /* ===== ADD WEATHER PANELS TO CENTER ===== */
        JPanel weatherMainPanel = new JPanel();
        weatherMainPanel.setLayout(new BorderLayout(5,5));
        weatherMainPanel.add(weatherPanel, BorderLayout.NORTH);
        weatherMainPanel.add(tagesPanel, BorderLayout.CENTER);
        weatherMainPanel.add(wochenPanel, BorderLayout.SOUTH);

        add(weatherMainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void fetchCoordinates(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            String apiUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseStream);

                // JSON speichern
                File outFile = new File(".idea/src/Datenbank/search.json");
                mapper.writerWithDefaultPrettyPrinter().writeValue(outFile, root);

                if (root.has("results") && root.get("results").size() > 0) {
                    JsonNode first = root.get("results").get(0);
                    double latitude = first.get("latitude").asDouble();
                    double longitude = first.get("longitude").asDouble();
                    String name = first.get("name").asText();

                    coordinatesLabel.setText("Coordinates: " + latitude + ", " + longitude);
                    cityLabel.setText("City: " + name);
                } else {
                    coordinatesLabel.setText("Coordinates: N/A");
                    cityLabel.setText("City: N/A");
                }
            } else {
                coordinatesLabel.setText("Coordinates: error");
                cityLabel.setText("City: error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            coordinatesLabel.setText("Coordinates: error");
            cityLabel.setText("City: error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherAppGUI::new);
    }
}
