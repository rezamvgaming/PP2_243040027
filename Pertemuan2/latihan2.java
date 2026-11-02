import javax.swing.*;
import java.awt.*;

public class latihan2{ 
    private static final int BATAS_STOK = 100_000;
    private final JFrame frame = new JFrame("Contoh 2 - Validasi Input");
    private final JTextField namaField = new JTextField(24);
    private final JTextField stokField = new JTextField(24);

    private void tampilkan() {
        JPanel fieldPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        fieldPanel.add(new JLabel("Nama barang:"));
        fieldPanel.add(namaField);
        fieldPanel.add(new JLabel("Stok (0-100000):"));
        fieldPanel.add(stokField);

        JButton periksaButton = new JButton("Periksa input");
        periksaButton.addActionListener(event -> periksaInput());

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(fieldPanel, BorderLayout.CENTER);
        panel.add(periksaButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void periksaInput() {
        String nama = namaField.getText().trim();
        String teksStok = stokField.getText().trim();

        if (nama.isEmpty()) {
            tampilkanKesalahan("Nama barang wajib diisi.", namaField);
            return;
        }

        if (teksStok.isEmpty()) {
            tampilkanKesalahan("Stok wajib diisi. Contoh: 25.", stokField);
            return;
        }

        final int stok;
        
        try {
            stok = Integer.parseInt(teksStok);
        } catch (NumberFormatException e) {
            tampilkanKesalahan(
                    "Stok harus bilangan bulat 0-100000 tanpa pemisah ribuan.",
                    stokField);
            return;
        }

        if (stok < 0 || stok > BATAS_STOK) {
            tampilkanKesalahan("Stok harus antara 0 dan 100000.", stokField);
            return;
        }

        JOptionPane.showMessageDialog(frame,
                "Input valid. Stok awal: " + stok,
                "Hasil pemeriksaan", JOptionPane.INFORMATION_MESSAGE);
        }
    

    private void tampilkanKesalahan(String pesan, JTextField field) {
        JOptionPane.showMessageDialog(frame, pesan,
                "Perbaiki input", JOptionPane.WARNING_MESSAGE);
        // Input tetap tersedia agar pengguna cukup memperbaiki field bermasalah.
        field.requestFocusInWindow();
        field.selectAll();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new latihan2().tampilkan());
    }
}
