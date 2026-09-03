import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Renderer extends JPanel {

    private final int width = 800;
    private final int height = 600;

    private final BufferedImage image =
            new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    private final int[] pixels =
            ((java.awt.image.DataBufferInt)
                    image.getRaster().getDataBuffer()).getData();

    public Renderer() {
        setPreferredSize(new Dimension(width, height));
    }


    public void setPixel(Point2D p, int color) {
        if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height)
            return;

        pixels[p.y * width + p.x] = color;
    }
    

    public void render() {

    }
}


