import javax.swing.*;
import java.awt.*;

public class View {
    public static void view() {
        JFrame frame = new JFrame("Java Image Generator");
        frame.setSize(800, 500);

        // LATERAL PANEL
        JPanel panel = new JPanel();
        BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);  // Correção aqui
        panel.setLayout(box);

        panel.add(new JLabel("Dataset:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Output:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Round:"));
        panel.add(new JSpinner(new SpinnerNumberModel(15, 0, 255, 1)));

        panel.add(new JLabel("Image Count:"));
        panel.add(new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)));

        panel.add(new JButton("Generate"));

        frame.add(panel, BorderLayout.WEST);

        // IMAGE

        frame.setVisible(true);
    }
}
