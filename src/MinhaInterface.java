import javax.swing.*;
import java.awt.*;

public class MinhaInterface extends JFrame {
    public MinhaInterface() {
        // Configurações básicas do frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minha Interface Swing");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Formulário à esquerda
        JPanel formularioPanel = new JPanel(new BoxLayout(this, BoxLayout.Y_AXIS));
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formularioPanel.add(new JLabel("Nome:"));
        formularioPanel.add(new JTextField());

        formularioPanel.add(new JLabel("Idade:"));
        formularioPanel.add(new JTextField());

        formularioPanel.add(new JLabel("E-mail:"));
        formularioPanel.add(new JTextField());

        add(formularioPanel, BorderLayout.WEST);

        // Imagem à direita
        ImageIcon imagem = new ImageIcon("C:/xampp/htdocs/JavaImageGenerator/src/output/1698070983934.jpg"); // Substitua pelo caminho real da sua imagem
        JLabel imagemLabel = new JLabel(imagem);
        add(imagemLabel, BorderLayout.CENTER);

        // Exibir a interface
        pack();
        setLocationRelativeTo(null); // Centralizar na tela
        setVisible(true);
    }

    public static void start() {
        SwingUtilities.invokeLater(() -> new MinhaInterface());
    }
}
