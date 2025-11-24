import javax.swing.*;
import java.awt.*;

public class WeatherAppGUI extends JFrame {

    private JTextField cityField;
    private JLabel tempLabel;
    private JLabel weatherLabel;
    private JButton refreshButton;

    public WeatherAppGUI() {
        setTitle("Wetter App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(3, 2, 10, 10));

        cityField = new JTextField("Berlin");
        tempLabel = new JLabel("Temperatur: -");
        weatherLabel = new JLabel("Wettercode: -");
        refreshButton = new JButton("Aktualisieren");

        add(new JLabel("Stadt:"));
        add(cityField);

        add(tempLabel);
        add(weatherLabel);

        add(refreshButton);

        // Button → API + JSON laden
        refreshButton.addActionListener(e -> {
            try {
                OM_HttpRequest api = new OM_HttpRequest();
                String json = api.sendRequest();

                // JSON parsen
                WeatherData data = JsonConverter.parse(json);

                // GUI updaten
                tempLabel.setText("Temperatur: " + data.temperature + " °C");
                weatherLabel.setText("Wettercode: " + data.weatherCode);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
