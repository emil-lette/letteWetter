import javax.swing.*;
import java.awt.*;

public class WeatherAppGUI extends JFrame {

    private JLabel currentCondition;
    private JLabel currentTemp;

    public WeatherAppGUI() {
        setTitle("Wetter App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sonne laden > skalieren
        ImageIcon sunIcon = new ImageIcon(".idea/src/img/sun.png");
        Image sunImage = sunIcon.getImage();
        Image scaledSun = sunImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        // JLabel
        currentCondition = new JLabel(new ImageIcon(scaledSun));
        currentTemp = new JLabel("10°C");

        add(currentCondition);
        add(currentTemp);

        setVisible(true);

        System.out.println("Bild geladen? " + (sunIcon.getIconWidth() > 0));

    }

    public static void main(String[] args) {
        new WeatherAppGUI();
    }
}

