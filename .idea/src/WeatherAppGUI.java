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

        setLayout(new GridLayout(1, 2));

        currentCondition = new JLabel(new ImageIcon "C:\\Users\\e.reichmann\\IdeaProjects\\letteWetter\\.idea\\src\\img");
        currentTemp = new JLabel ("10°C");



        add(currentCondition);
        add(currentTemp);

        //add(refreshButton);

        setVisible(true);
    }
}
