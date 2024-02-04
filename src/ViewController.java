import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ViewController extends JFrame {
    private final View view;
    private ImageIcon image;
    public void View() {
        setTitle("Java Image Generator");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 500));

        add(
                createSidebar(),
                BorderLayout.WEST
        );

        add(
                createImage(),
                BorderLayout.CENTER
        );

        setVisible(true);
    }
    public ViewController(View view) {
        this.view = view;
        this.image = new ImageIcon("src/output/-1707078643011.jpg");
        View();
    }
    private JComponent createOptions () {
        JPanel options = new JPanel();
        options.setBackground(Color.LIGHT_GRAY);
        options.setLayout(new GridLayout(6, 2, 10, 10));

        options.add(new JLabel("Dataset:"));
        options.add(view.getInput());

        options.add(new JLabel("Output:"));
        options.add(view.getOutput());

        options.add(new JLabel("Type:"));
        options.add(view.getGentype());

        options.add(new JLabel("Size:"));
        options.add(view.getImgsize());

        options.add(new JLabel("Round:"));
        options.add(view.getRound());

        options.add(new JLabel("Input Count:"));
        options.add(view.getInputCount());

        //options.add(new JLabel("Output Count:"));
        //options.add(view.getOutputCount());

        return options;
    }
    private JButton createButton() {
        JButton button = new JButton("Gen!");
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> {
            String in = view.getInput().getText();
            String out = view.getOutput().getText();
            String gentype = (String) view.getGentype().getSelectedItem();
            int imgsize = (int) view.getImgsize().getValue();
            int round = (int) view.getRound().getValue();
            int inputCount = (int) view.getInputCount().getValue();
            int outputCount = (int) view.getOutputCount().getValue();

            for (int i = 0; i < outputCount; i++) {
                ArrayList<int[][]> data = Files.dataProcessor(imgsize, in, inputCount);
                File file;
                switch (gentype) {
                    case "overlay":
                        file = Generation.overlay(imgsize, data);
                        updateImage(file);
                        break;
                    case "gen":
                        file = Generation.gen(imgsize, data);
                        updateImage(file);
                        break;
                    case "newgen":
                        file = Generation.newgen(imgsize, data, round);
                        updateImage(file);
                        break;
                }
            }
        });

        return button;
    }
    private void updateImage(File file) {
        image = new ImageIcon(file.getAbsolutePath());
        System.out.println("Imagem gerada com sucesso: " + file.getAbsolutePath());

        SwingUtilities.invokeLater(() -> {
            repaint();
            updateImageLabel(new ImageIcon(file.getAbsolutePath()));
        });
    }
    private JComponent createSidebar() {
        JPanel sidebar = new JPanel();

        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setLayout(new GridBagLayout());
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.NORTH;

        sidebar.add(
                createOptions(),
                c
        );

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;

        JButton button = createButton();
        sidebar.add(
                button,
                c
        );


        return sidebar;
    }
    private JLabel createImageLabel() {
        return new JLabel(image);
    }
    public void updateImageLabel(ImageIcon newImage) {
        image = newImage;
        SwingUtilities.invokeLater(() -> {
            getContentPane().removeAll();
            getContentPane().add(createSidebar(), BorderLayout.WEST);
            getContentPane().add(createImage(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }
    private JComponent createImage() {
        JPanel imagePanel = new JPanel();
        imagePanel.add(createImageLabel());
        return imagePanel;
    }
    public static void start() {
        SwingUtilities.invokeLater(View::new);
    }
}
