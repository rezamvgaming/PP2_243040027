import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Latihan1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Latihan1());
    }

    public Latihan1() {
        JFrame frame = new JFrame("latihan 1");
        JLabel label = new JLabel("Hello World", SwingConstants.CENTER);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}