import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String resourcePath) {
        setLayout(new BorderLayout());
        setBackgroundImage(resourcePath);
    }

    public void setBackgroundImage(String resourcePath) {
        URL imgUrl = getClass().getClassLoader().getResource(resourcePath);

        if (imgUrl == null) {
            System.err.println("❌ Background nicht gefunden: " + resourcePath);
            backgroundImage = null;
            return;
        }

        backgroundImage = new ImageIcon(imgUrl).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(
                    backgroundImage,
                    0, 0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }
}
