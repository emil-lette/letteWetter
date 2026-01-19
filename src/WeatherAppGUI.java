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

    /* ===== BACKGROUND IMAGES ===== */
    private static final String BG_LIGHT = "img/WetterApp_BG.png";
    private static final String BG_DARK  = "img/WetterApp_BG_DM.png";

    private BackgroundPanel backgroundPanel;

    private JTextField cityField;
    private JButton searchButton;
    private JLabel coordinatesLabel;
    private JLabel cityLabel;

    public WeatherAppGUI() {

        /* ===== FRAME ===== */
        setTitle("Weather App – Dark Mode");
        setSize(600, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* ===== BACKGROUND PANEL ===== */
        backgroundPanel = new BackgroundPanel(BG_DARK);
        setContentPane(backgroundPanel);

        /* ===== SEARCH PANEL ===== */
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setOpaque(false);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);

        cityField = new JTextField(20);
        searchButton = new JButton("Search");

        JToggleButton darkToggle = new JToggleButton("Dark");
        darkToggle.setSelected(true);

        darkToggle.addActionListener(e -> {
            if (darkToggle.isSelected()) {
                backgroundPanel.setBackgroundImage(BG_DARK);
            } else {
                backgroundPanel.setBackgroundImage(BG_LIGHT);
            }
        });

        inputPanel.add(cityField);
        inputPanel.add(searchButton);
        inputPanel.add(darkToggle);

        JPanel resultPanel = new JPanel(new GridLayout(2, 1));
        resultPanel.setOpaque(false);

        coordinatesLabel = new JLabel("Coordinates: 52.52, 13.42", SwingConstants.CENTER);
        cityLabel = new JLabel("City: Berlin", SwingConstants.CENTER);

        coordinatesLabel.setForeground(Color.WHITE);
        cityLabel.setForeground(Color.WHITE);

        resultPanel.add(coordinatesLabel);
        resultPanel.add(cityLabel);

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(resultPanel, BorderLayout.CENTER);

        add(searchPanel, BorderLayout.NORTH);

        /* ===== LOAD JSON ===== */
        double temperature = 0;
        int weatherCode = 0;
        JsonNode daily = null;
        JsonNode weekly = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File("src/Datenbank/test.json"));

            JsonNode weather = root.get("current_weather");
            temperature = weather.get("temperature").asDouble();
            weatherCode = weather.get("weathercode").asInt();

            daily = root.get("daily_weather");
            weekly = root.get("weekly_weather");

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* ===== CURRENT WEATHER ===== */
        JPanel weatherPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        weatherPanel.setOpaque(false);

        JLabel currentTemp =
                new JLabel(temperature + " °C", SwingConstants.CENTER);
        currentTemp.setFont(new Font("Arial", Font.BOLD, 26));
        currentTemp.setForeground(Color.WHITE);

        weatherPanel.add(new JLabel());
        weatherPanel.add(currentTemp);

        /* ===== DAILY PANEL ===== */
        JPanel tagesPanel = new JPanel(
                new GridLayout(daily != null ? daily.size() : 4, 4, 10, 10));
        tagesPanel.setOpaque(false);

        if (daily != null) {
            for (JsonNode entry : daily) {
                tagesPanel.add(createLabel(entry.get("time").asText()));
                tagesPanel.add(createLabel(entry.get("condition").asText()));
                tagesPanel.add(createLabel(entry.get("temperature").asText() + " °C"));
                tagesPanel.add(createLabel(entry.get("rain_chance").asText() + " %"));
            }
        }

        /* ===== WEEKLY PANEL ===== */
        JPanel wochenPanel = new JPanel(new GridLayout(4, 7, 5, 5));
        wochenPanel.setOpaque(false);

        if (weekly != null) {
            for (JsonNode entry : weekly)
                wochenPanel.add(createLabel(entry.get("day").asText()));
            for (JsonNode entry : weekly)
                wochenPanel.add(createLabel(entry.get("condition").asText()));
            for (JsonNode entry : weekly)
                wochenPanel.add(createLabel(entry.get("temperature").asText() + " °C"));
            for (JsonNode entry : weekly)
                wochenPanel.add(createLabel(entry.get("rain_chance").asText() + " %"));
        }

        /* ===== SCROLL ===== */
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        contentPanel.add(weatherPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(tagesPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(wochenPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);

        /* ===== SEARCH ACTION ===== */
        searchButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                fetchCoordinates(city);
            }
        });

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        return label;
    }

    /* ===== FETCH COORDINATES ===== */
    private void fetchCoordinates(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            URL url = new URL(
                    "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(connection.getInputStream());

                JsonNode first = root.get("results").get(0);
                coordinatesLabel.setText(
                        "Coordinates: " +
                                first.get("latitude").asDouble() + ", " +
                                first.get("longitude").asDouble());
                cityLabel.setText("City: " + first.get("name").asText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherAppGUI::new);
    }
}
