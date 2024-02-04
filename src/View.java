import javax.swing.*;
import java.awt.*;

public class View{
    private JTextField input = new JTextField("src/data/cars");
    private JTextField output = new JTextField("src/output");
    private JComboBox<String> gentype = new JComboBox<>(new String[] {"newgen", "overlay", "gen"});
    private JSpinner imgsize = new JSpinner(new SpinnerNumberModel(1024, 0, 4096, 1));
    private JSpinner round = new JSpinner(new SpinnerNumberModel(15, 0, 255, 1));
    private JSpinner inputCount = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
    private JSpinner outputCount = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

    // GETTERS
    public JTextField getInput() {
        return input;
    }
    public JTextField getOutput() {
        return output;
    }
    public JComboBox<String> getGentype() {
        return gentype;
    }
    public JSpinner getImgsize() {
        return imgsize;
    }
    public JSpinner getRound() {
        return round;
    }
    public JSpinner getInputCount() {
        return inputCount;
    }
    public JSpinner getOutputCount() {
        return outputCount;
    }
}
