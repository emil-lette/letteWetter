public class WeatherMain {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            new WeatherAppGUI();
        });

    }
}
