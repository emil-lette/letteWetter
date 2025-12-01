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

        // Wochenübersicht
        JPanel testPanel = new JPanel(new GridLayout(3, 7));
        JLabel montag = new JLabel("Montag");
        JLabel montagT = new JLabel("10°C");
        JLabel montagC = new JLabel("sonnig");

        JLabel dienstag = new JLabel("Dienstag");
        JLabel dienstagT = new JLabel("10°C");
        JLabel dienstagC = new JLabel("sonnig");

        JLabel mittwoch = new JLabel("Mittwoch");
        JLabel mittwochT = new JLabel("10°C");
        JLabel mittwochC = new JLabel("sonnig");

        JLabel donnerstag = new JLabel("Donnerstag");
        JLabel donnerstagT = new JLabel("10°C");
        JLabel donnerstagC = new JLabel("sonnig");

        JLabel freitag = new JLabel("Freitag");
        JLabel freitagT = new JLabel("10°C");
        JLabel freitagC = new JLabel("sonnig");

        JLabel samstag = new JLabel("Samstag");
        JLabel samstagT = new JLabel("10°C");
        JLabel samstagC = new JLabel("sonnig");

        JLabel sonntag = new JLabel("Sonntag");
        JLabel sonntagT = new JLabel("10°C");
        JLabel sonntagC = new JLabel("sonnig");

        testPanel.add(montag);
        testPanel.add(dienstag);
        testPanel.add(mittwoch);

        testPanel.add(montag);
        testPanel.add(dienstag);
        testPanel.add(mittwoch);
        testPanel.add(donnerstag);
        testPanel.add(freitag);
        testPanel.add(samstag);
        testPanel.add(sonntag);

        testPanel.add(montagC);
        testPanel.add(dienstagC);
        testPanel.add(mittwochC);
        testPanel.add(donnerstagC);
        testPanel.add(freitagC);
        testPanel.add(samstagC);
        testPanel.add(sonntagC);

        testPanel.add(montagT);
        testPanel.add(dienstagT);
        testPanel.add(mittwochT);
        testPanel.add(donnerstagT);
        testPanel.add(freitagT);
        testPanel.add(samstagT);
        testPanel.add(sonntagT);





        // Panels zum JFrame hinzufügen
        add(weatherPanel);
        add(testPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new WeatherAppGUI();
    }
}
