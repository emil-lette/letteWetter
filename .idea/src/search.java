import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
            if (!city.isEmpty()) {
                fetchCoordinates(city);
            } else {
                JOptionPane.showMessageDialog(this, "city");
            }
        });

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

                // Koordinaten anzeigen
                if (root.has("results") && root.get("results").size() > 0) {
                    JsonNode first = root.get("results").get(0);
                    double latitude = first.get("latitude").asDouble();
                    double longitude = first.get("longitude").asDouble();

                    resultLabel.setText("lat.: " + latitude + ", long.: " + longitude);
                } else {
                    resultLabel.setText("no results");
                }

            } else {
                resultLabel.setText("error: HTTP " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultLabel.setText("error.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(search::new);
    }
}
