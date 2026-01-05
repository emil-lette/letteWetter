import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class WeatherAppGUI extends JFrame {

    public WeatherAppGUI() {
        setTitle("Wetter App");
        setSize(600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1)); // 4 Panels: Current, Tages, Wochen, Button

        double temperature = 0;
        int weatherCode = 0;
        JsonNode daily = null;
        JsonNode weekly = null;

        // JSON laden
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

        // Icon auswählen anhand weatherCode
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
        tagesPanel.setLayout(new GridLayout(rows, 4, 10, 10)); // 4 Spalten: Zeit, Zustand, Temp, Regen%

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
        } else {
            System.out.println("Fehler beim Auslesen der Tagesübersicht");
        }

        /* ===== WOCHENÜBERSICHT PANEL ===== */
        JPanel wochenPanel = new JPanel(new GridLayout(4, 7)); // 4 Reihen, 7 Tage

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
        } else {
            System.out.println("Fehler beim Auslesen der Wochenübersicht");
        }

        /* ===== BUTTON PANEL ===== */
        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search starten");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Button klick -> search.main starten
        searchButton.addActionListener(e -> search.main(new String[]{}));

        buttonPanel.add(searchButton);

        // Panels zum JFrame hinzufügen
        add(weatherPanel);
        add(tagesPanel);
        add(wochenPanel);
        add(buttonPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherAppGUI::new);
    }
}
