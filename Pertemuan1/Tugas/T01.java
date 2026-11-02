import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Tugas 1 - Dasar GUI Swing dan Layout
 * IF21W0508 - Praktikum Pemrograman II
 *
 * Aplikasi formulir pendaftaran kegiatan.
 *
 * Layout manager yang digunakan (tanpa null layout / absolute positioning):
 *   1. BorderLayout   - kerangka utama jendela (judul, isi, tombol)
 *   2. GridBagLayout  - panel Identitas (label di kiri, input di kanan, input ikut melebar)
 *   3. GridLayout     - panel pilihan jenis peran dan opsi tambahan
 *   4. FlowLayout     - baris tombol aksi dan baris radio jenis kelamin
 */
public class T01 extends JFrame {

    private static final long serialVersionUID = 1L;

    // Identitas
    final JTextField txtNama = new JTextField(20);
    final JTextField txtNim = new JTextField(20);
    final JTextField txtEmail = new JTextField(20);
    final JRadioButton rbLaki = new JRadioButton("Laki-laki");
    final JRadioButton rbPerempuan = new JRadioButton("Perempuan");
    final ButtonGroup grupJenisKelamin = new ButtonGroup();

    // Pilihan kegiatan
    final JComboBox<String> cmbKegiatan = new JComboBox<>(new String[] {
            "Seminar Teknologi Informasi",
            "Workshop Pemrograman Java",
            "Lomba Desain Antarmuka",
            "Pelatihan Basis Data",
            "Kunjungan Industri"
    });
    final JRadioButton rbPeserta = new JRadioButton("Peserta");
    final JRadioButton rbPanitia = new JRadioButton("Panitia");
    final JRadioButton rbRelawan = new JRadioButton("Relawan");
    final ButtonGroup grupPeran = new ButtonGroup();
    final JCheckBox chkSertifikat = new JCheckBox("Membutuhkan sertifikat");
    final JCheckBox chkKonsumsi = new JCheckBox("Membutuhkan konsumsi");
    final JCheckBox chkLaptop = new JCheckBox("Membawa laptop sendiri");

    // Catatan
    final JTextArea txtCatatan = new JTextArea(5, 20);

    // Tombol
    final JButton btnKirim = new JButton("Kirim");
    final JButton btnReset = new JButton("Reset");

    public T01() {
        super("Formulir Pendaftaran Kegiatan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Kerangka utama: BorderLayout
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setContentPane(root);

        root.add(buatJudul(), BorderLayout.NORTH);
        root.add(buatIsiForm(), BorderLayout.CENTER);
        root.add(buatPanelTombol(), BorderLayout.SOUTH);

        aturFokus();

        getRootPane().setDefaultButton(btnKirim);
        pack();
        // Batas minimum agar label dan input tetap terbaca saat jendela diperkecil
        setMinimumSize(new Dimension(Math.max(getWidth() - 40, 460), Math.max(getHeight() - 60, 520)));
        setLocationRelativeTo(null);
    }

    private JPanel buatJudul() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel judul = new JLabel("Formulir Pendaftaran Kegiatan", SwingConstants.CENTER);
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 18f));
        JLabel sub = new JLabel("Lengkapi data di bawah ini lalu tekan Kirim", SwingConstants.CENTER);
        sub.setForeground(Color.DARK_GRAY);
        panel.add(judul);
        panel.add(sub);
        return panel;
    }

    /** Tiga kelompok: Identitas, Pilihan Kegiatan, Catatan. */
    private JPanel buatIsiForm() {
        JPanel isi = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 8, 0);

        gbc.gridy = 0;
        gbc.weighty = 0;
        isi.add(buatPanelIdentitas(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        isi.add(buatPanelKegiatan(), gbc);

        // Catatan menyerap sisa tinggi saat jendela diperbesar
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        isi.add(buatPanelCatatan(), gbc);
        return isi;
    }

    /** Panel identitas memakai GridBagLayout: label kiri, input kanan melebar. */
    private JPanel buatPanelIdentitas() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Identitas"));

        GridBagConstraints lbl = new GridBagConstraints();
        lbl.gridx = 0;
        lbl.anchor = GridBagConstraints.LINE_START;
        lbl.insets = new Insets(4, 8, 4, 8);

        GridBagConstraints fld = new GridBagConstraints();
        fld.gridx = 1;
        fld.weightx = 1.0;
        fld.fill = GridBagConstraints.HORIZONTAL;
        fld.insets = new Insets(4, 0, 4, 8);

        tambahBaris(panel, lbl, fld, 0, "Nama lengkap:", txtNama);
        tambahBaris(panel, lbl, fld, 1, "NIM:", txtNim);
        tambahBaris(panel, lbl, fld, 2, "Email:", txtEmail);

        grupJenisKelamin.add(rbLaki);
        grupJenisKelamin.add(rbPerempuan);
        JPanel barisJk = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 0));
        barisJk.add(rbLaki);
        barisJk.add(rbPerempuan);
        tambahBaris(panel, lbl, fld, 3, "Jenis kelamin:", barisJk);
        return panel;
    }

    private void tambahBaris(JPanel panel, GridBagConstraints lbl, GridBagConstraints fld,
            int baris, String teks, java.awt.Component input) {
        lbl.gridy = baris;
        fld.gridy = baris;
        JLabel label = new JLabel(teks);
        label.setLabelFor(input);
        panel.add(label, lbl);
        panel.add(input, fld);
    }

    /** Panel pilihan kegiatan: GridBagLayout untuk baris kegiatan, GridLayout untuk peran dan opsi. */
    private JPanel buatPanelKegiatan() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Pilihan Kegiatan"));

        GridBagConstraints lbl = new GridBagConstraints();
        lbl.gridx = 0;
        lbl.anchor = GridBagConstraints.LINE_START;
        lbl.insets = new Insets(4, 8, 4, 8);

        GridBagConstraints fld = new GridBagConstraints();
        fld.gridx = 1;
        fld.weightx = 1.0;
        fld.fill = GridBagConstraints.HORIZONTAL;
        fld.insets = new Insets(4, 0, 4, 8);

        tambahBaris(panel, lbl, fld, 0, "Kegiatan:", cmbKegiatan);

        grupPeran.add(rbPeserta);
        grupPeran.add(rbPanitia);
        grupPeran.add(rbRelawan);
        rbPeserta.setSelected(true);
        JPanel barisPeran = new JPanel(new GridLayout(1, 3, 6, 0));
        barisPeran.add(rbPeserta);
        barisPeran.add(rbPanitia);
        barisPeran.add(rbRelawan);
        tambahBaris(panel, lbl, fld, 1, "Mendaftar sebagai:", barisPeran);

        JPanel barisOpsi = new JPanel(new GridLayout(3, 1));
        barisOpsi.add(chkSertifikat);
        barisOpsi.add(chkKonsumsi);
        barisOpsi.add(chkLaptop);
        lbl.gridy = 2;
        lbl.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(new JLabel("Opsi tambahan:"), lbl);
        fld.gridy = 2;
        panel.add(barisOpsi, fld);
        return panel;
    }

    private JPanel buatPanelCatatan() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Catatan"));
        txtCatatan.setLineWrap(true);
        txtCatatan.setWrapStyleWord(true);
        // Tab berpindah fokus, bukan menyisipkan karakter tab
        txtCatatan.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        txtCatatan.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        panel.add(new JScrollPane(txtCatatan), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buatPanelTombol() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 8, 0));
        btnReset.addActionListener(e -> resetForm());
        btnKirim.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Tombol Kirim belum menyimpan data (belum diwajibkan pada Tugas 1).",
                "Informasi", JOptionPane.INFORMATION_MESSAGE));
        panel.add(btnReset);
        panel.add(btnKirim);
        return panel;
    }

    /**
     * Urutan fokus mengikuti urutan visual dari atas ke bawah. Karena kontrol dimasukkan ke layout
     * sesuai urutan tersebut, kebijakan fokus bawaan (LayoutFocusTraversalPolicy) sudah masuk akal.
     * Fokus awal diberikan ke kolom Nama.
     */
    private void aturFokus() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                txtNama.requestFocusInWindow();
            }
        });
    }

    private void resetForm() {
        txtNama.setText("");
        txtNim.setText("");
        txtEmail.setText("");
        grupJenisKelamin.clearSelection();
        cmbKegiatan.setSelectedIndex(0);
        rbPeserta.setSelected(true);
        chkSertifikat.setSelected(false);
        chkKonsumsi.setSelected(false);
        chkLaptop.setSelected(false);
        txtCatatan.setText("");
        txtNama.requestFocusInWindow();
    }

    /** Dipakai oleh program uji untuk memeriksa semua pilihan pada sebuah ButtonGroup. */
    static int hitungTerpilih(ButtonGroup g) {
        int n = 0;
        Enumeration<AbstractButton> e = g.getElements();
        while (e.hasMoreElements()) {
            if (e.nextElement().isSelected()) {
                n++;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        // Aplikasi dibuka melalui Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
                // gunakan look and feel bawaan
            }
            new T01().setVisible(true);
        });
    }
}