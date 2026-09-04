import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class Renderer extends JPanel {

    // origin at center (x: (-400, 400), y: (-300, 300))
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


    public void render() {


        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0x202020;
        }

        Point2D p1 = new Point2D(300, 300);
        Point2D p2 = new Point2D(400, 250);
        Point2D p3 = new Point2D(700, 500);

        drawTriangle(p1, p2, p3, 0xeb4034);
    }

    public void setPixel(int x, int y, int color) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return;

        pixels[y * width + x] = color;
    }

    public int getPixel(int x, int y) {
        return pixels[y * width + x];
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {

        int dx = x2 - x1;
        int dy = y2 - y1;

        float step = Math.max(Math.abs(dx), Math.abs(dy));
        if (step != 0) {
            float stepX = dx / step;
            float stepY = dy / step;
            for (int i = 0; i <= step; i++) {
                setPixel(Math.round(x1 + i * stepX), Math.round(y1 + i * stepY), color);
            }
        }
    }

    public void drawTriangle(Point2D p1, Point2D p2, Point2D p3, int color) {

        // rita outline
        drawLine(p1.x, p1.y, p2.x, p2.y, color);
        drawLine(p1.x, p1.y, p3.x, p3.y, color);
        drawLine(p2.x, p2.y, p3.x, p3.y, color);

        // skanna
        int minY = Math.min(p1.y, Math.min(p2.y, p3.y));
        int maxY = Math.max(p1.y, Math.max(p2.y, p3.y));
        int minX = Math.min(p1.x, Math.min(p2.x, p3.x));
        int maxX = Math.max(p1.x, Math.max(p2.x, p3.x));

        ArrayList<ArrayList<Integer>> pointRows = new ArrayList<ArrayList<Integer>>();
        for (int y = minY; y <= maxY; y++) {
            ArrayList<Integer> points = new ArrayList<Integer>();
            int firstPixel = -1;
            for (int x = minX; x <= maxX; x++) {
                if (getPixel(x, y) == color) {
                    points.add(x);
                    if (firstPixel == -1) {
                        firstPixel = x;
                    }
                }
            }
            if (points.size() <= 1) {
                points.add(firstPixel);
            }
            pointRows.add(points);
        }
        
        // fyll i triangel
        for (int i = 0; i < pointRows.size(); i++) { 
            ArrayList<Integer> points = pointRows.get(i);
            int firstX = points.get(0);
            int lastX = points.get(points.size() - 1);
            int currY = minY + i;
            drawLine(firstX, currY, lastX, currY, color);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args) {

        JFrame window = new JFrame("My Software Renderer");

        Renderer renderer = new Renderer();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(renderer);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        while (true) {

            renderer.render();

            renderer.repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}