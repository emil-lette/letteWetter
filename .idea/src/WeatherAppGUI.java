import javax.swing.*;
import java.awt.*;

public class WeatherAppGUI extends JFrame {

    public WeatherAppGUI() {
        setTitle("Wetter App");
        setSize(1000, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        /*============================================================================*/


        // current weather:
        JPanel weatherPanel = new JPanel(new GridLayout(1, 2));
        ImageIcon sunIcon = new ImageIcon("src/img/sun.png");
        Image sunImage = sunIcon.getImage();
        Image scaledSun = sunImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        JLabel currentCondition = new JLabel(new ImageIcon(scaledSun));
        JLabel currentTemp = new JLabel("10°C");

        weatherPanel.add(currentCondition);
        weatherPanel.add(currentTemp);


        /*============================================================================*/

        // Tagesübersicht
        JPanel tagesPanel = new JPanel(new GridLayout(4, 4));
        JLabel morgens = new JLabel("6 Uhr");
        JLabel morgensT = new JLabel("10°C");
        JLabel morgensC = new JLabel("regen");
        JLabel morgensR = new JLabel("100%");


        JLabel mittags = new JLabel("12 Uhr");
        JLabel mittagsT = new JLabel("10°C");
        JLabel mittagsC = new JLabel("sonnig");
        JLabel mittagsR = new JLabel("50%");


        JLabel abends = new JLabel("18 Uhr");
        JLabel abendsT = new JLabel("10°C");
        JLabel abendsC = new JLabel("sonnig");
        JLabel abendsR = new JLabel("50%");

        JLabel nachts = new JLabel("24 Uhr");
        JLabel nachtsT = new JLabel("10°C");
        JLabel nachtsC = new JLabel("sonnig");
        JLabel nachtsR = new JLabel("50%");

        tagesPanel.add(morgens);
        tagesPanel.add(mittags);
        tagesPanel.add(abends);
        tagesPanel.add(nachts);

        tagesPanel.add(morgensC);
        tagesPanel.add(mittagsC);
        tagesPanel.add(abendsC);
        tagesPanel.add(nachtsC);

        tagesPanel.add(morgensT);
        tagesPanel.add(mittagsT);
        tagesPanel.add(abendsT);
        tagesPanel.add(nachtsT);

        tagesPanel.add(morgensR);
        tagesPanel.add(mittagsR);
        tagesPanel.add(abendsR);
        tagesPanel.add(nachtsR);
        
        /*============================================================================*/

        // Wochenübersicht
        JPanel wochenPanel = new JPanel(new GridLayout(4, 7));
        
        JLabel montag = new JLabel("Montag");
        JLabel montagT = new JLabel("10°C");
        JLabel montagC = new JLabel("sonnig");
        JLabel montagR = new JLabel("50%");


        JLabel dienstag = new JLabel("Dienstag");
        JLabel dienstagT = new JLabel("10°C");
        JLabel dienstagC = new JLabel("sonnig");
        JLabel dienstagR = new JLabel("50%");

        JLabel mittwoch = new JLabel("Mittwoch");
        JLabel mittwochT = new JLabel("10°C");
        JLabel mittwochC = new JLabel("sonnig");
        JLabel mittwochgR = new JLabel("50%");


        JLabel donnerstag = new JLabel("Donnerstag");
        JLabel donnerstagT = new JLabel("10°C");
        JLabel donnerstagC = new JLabel("sonnig");
        JLabel donnerstagR = new JLabel("50%");

        JLabel freitag = new JLabel("Freitag");
        JLabel freitagT = new JLabel("10°C");
        JLabel freitagC = new JLabel("sonnig");
        JLabel freitagR = new JLabel("50%");

        JLabel samstag = new JLabel("Samstag");
        JLabel samstagT = new JLabel("10°C");
        JLabel samstagC = new JLabel("sonnig");
        JLabel samstagR = new JLabel("50%");

        JLabel sonntag = new JLabel("Sonntag");
        JLabel sonntagT = new JLabel("10°C");
        JLabel sonntagC = new JLabel("sonnig");
        JLabel sonntagR = new JLabel("50%");

        wochenPanel.add(montag);
        wochenPanel.add(dienstag);
        wochenPanel.add(mittwoch);
        wochenPanel.add(donnerstag);
        wochenPanel.add(freitag);
        wochenPanel.add(samstag);
        wochenPanel.add(sonntag);

        wochenPanel.add(montagC);
        wochenPanel.add(dienstagC);
        wochenPanel.add(mittwochC);
        wochenPanel.add(donnerstagC);
        wochenPanel.add(freitagC);
        wochenPanel.add(samstagC);
        wochenPanel.add(sonntagC);

        wochenPanel.add(montagT);
        wochenPanel.add(dienstagT);
        wochenPanel.add(mittwochT);
        wochenPanel.add(donnerstagT);
        wochenPanel.add(freitagT);
        wochenPanel.add(samstagT);
        wochenPanel.add(sonntagT);

        wochenPanel.add(montagR);
        wochenPanel.add(dienstagR);
        wochenPanel.add(mittwochgR);
        wochenPanel.add(donnerstagR);
        wochenPanel.add(freitagR);
        wochenPanel.add(samstagR);
        wochenPanel.add(sonntagR);


        /*============================================================================*/


        // Panels zum JFrame hinzufügen
        add(weatherPanel);
        add(wochenPanel);
        add(tagesPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new WeatherAppGUI();
    }
}
