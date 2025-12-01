import javax.swing.*;
import java.awt.*;

public class WeatherAppGUI extends JFrame {

    public WeatherAppGUI() {
        setTitle("Wetter App");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // current weather
        JPanel weatherPanel = new JPanel(new GridLayout(1, 2));
        ImageIcon sunIcon = new ImageIcon("src/img/sun.png");
        Image sunImage = sunIcon.getImage();
        Image scaledSun = sunImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel currentCondition = new JLabel(new ImageIcon(scaledSun));
        JLabel currentTemp = new JLabel("10°C");
        weatherPanel.add(currentCondition);
        weatherPanel.add(currentTemp);

        // test
        JPanel testPanel = new JPanel();
        JLabel testLabel = new JLabel("test test", SwingConstants.CENTER);
        testPanel.add(testLabel);

        // Panels zum JFrame hinzufügen
        add(weatherPanel);
        add(testPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new WeatherAppGUI();
    }
}
