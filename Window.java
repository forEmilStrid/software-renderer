import javax.swing.*;

public class Window {

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
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
