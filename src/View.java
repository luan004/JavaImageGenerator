import javax.swing.*;
import java.awt.*;

public class View {
    public static void view() {
        JFrame frame = new JFrame("Java Image Generator");
        frame.setSize(800,500);

        // LATERAL PANEL
        JPanel column = new JPanel(new GridLayout(3, 1));

        column.add(new JLabel("Dataset:"));
        column.add(new JTextField());

        column.add(new JLabel("Output:"));
        column.add(new JTextField());

        column.add(new JButton("Generate"));

        frame.add(column, BorderLayout.WEST);

        // IMAGE

        frame.setVisible(true);
    }
}
