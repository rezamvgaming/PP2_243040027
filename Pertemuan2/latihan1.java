package Pertemuan2;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class latihan1 {
    private int jumlahklik = 0;

    private void tampilan(){
        JFrame frame = new JFrame("Contooh modul 1");
        JLabel pesan = new JLabel("Tekan Tombol ini");
        JButton tombol = new JButton("Klik saya");

        tombol.addActionListener(event -> {
            jumlahklik++;
            pesan.setText("Saya sudah klik tombol ini sebanyak " + jumlahklik);
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(pesan, BorderLayout.NORTH);
        panel.add(tombol, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new latihan1().tampilan();
    }
}