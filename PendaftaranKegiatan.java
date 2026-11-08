import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

/**
 * Tugas 2 - Event handling, validasi, dan navigasi Swing
 * Aplikasi Pendaftaran Kegiatan (CardLayout: Formulir, Ringkasan, Bantuan)
 */
public class PendaftaranKegiatan extends JFrame {

    private static final String CARD_FORM = "FORM";
    private static final String CARD_RINGKASAN = "RINGKASAN";
    private static final String CARD_BANTUAN = "BANTUAN";

    private static final String PLACEHOLDER_KEGIATAN = "-- Pilih kegiatan --";
    private static final String[] DAFTAR_KEGIATAN = {
            PLACEHOLDER_KEGIATAN, "Seminar Teknologi", "Workshop Java",
            "Lomba Coding", "Pelatihan UI/UX", "Kunjungan Industri"
    };

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    // Komponen formulir
    private final JTextField txtNama = new JTextField(22);
    private final JTextField txtEmail = new JTextField(22);
    private final JTextField txtUmur = new JTextField(22);
    private final JComboBox<String> cmbKegiatan = new JComboBox<>(DAFTAR_KEGIATAN);
    private final JLabel lblStatus = new JLabel(" ");

    // Komponen ringkasan
    private final JTextArea areaRingkasan = new JTextArea();

    // Status perubahan
    private boolean belumDisimpan = false;
    private boolean sedangMengisiOtomatis = false;

    public PendaftaranKegiatan() {
        super("Pendaftaran Kegiatan");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);

        cardPanel.add(buatPanelForm(), CARD_FORM);
        cardPanel.add(buatPanelRingkasan(), CARD_RINGKASAN);
        cardPanel.add(buatPanelBantuan(), CARD_BANTUAN);
        add(cardPanel);

        pasangPelacakPerubahan();

        // Tombol X jendela diperlakukan sama seperti tombol Keluar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                keluar();
            }
        });

        tampilkan(CARD_FORM);
    }

    // ------------------------------------------------------------ PANEL FORM
    private JPanel buatPanelForm() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel judul = new JLabel("Formulir Pendaftaran Kegiatan");
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 16f));
        root.add(judul, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 4, 6, 4);
        c.anchor = GridBagConstraints.WEST;

        tambahBaris(form, c, 0, "Nama *", txtNama);
        tambahBaris(form, c, 1, "Email *", txtEmail);
        tambahBaris(form, c, 2, "Umur (15-100) *", txtUmur);
        tambahBaris(form, c, 3, "Kegiatan *", cmbKegiatan);

        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        lblStatus.setForeground(new Color(180, 0, 0));
        form.add(lblStatus, c);

        root.add(form, BorderLayout.CENTER);

        JButton btnSimpan = new JButton("Simpan/Tampilkan");
        JButton btnReset = new JButton("Reset");
        JButton btnBantuan = new JButton("Bantuan");
        JButton btnKeluar = new JButton("Keluar");

        btnSimpan.addActionListener(e -> simpanDanTampilkan());
        btnReset.addActionListener(e -> resetForm());
        btnBantuan.addActionListener(e -> tampilkan(CARD_BANTUAN));
        btnKeluar.addActionListener(e -> keluar());

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        tombol.add(btnSimpan);
        tombol.add(btnReset);
        tombol.add(btnBantuan);
        tombol.add(btnKeluar);
        root.add(tombol, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnSimpan);
        return root;
    }

    private void tambahBaris(JPanel p, GridBagConstraints c, int baris, String label, JComponent input) {
        c.gridwidth = 1;
        c.gridx = 0; c.gridy = baris; c.weightx = 0;
        p.add(new JLabel(label), c);
        c.gridx = 1; c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(input, c);
        c.fill = GridBagConstraints.NONE;
    }

    // -------------------------------------------------------- PANEL RINGKASAN
    private JPanel buatPanelRingkasan() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel judul = new JLabel("Ringkasan Pendaftaran");
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 16f));
        root.add(judul, BorderLayout.NORTH);

        areaRingkasan.setEditable(false);
        areaRingkasan.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        root.add(new JScrollPane(areaRingkasan), BorderLayout.CENTER);

        JButton btnKembali = new JButton("Kembali");
        JButton btnKeluar = new JButton("Keluar");
        btnKembali.addActionListener(e -> tampilkan(CARD_FORM));
        btnKeluar.addActionListener(e -> keluar());

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        tombol.add(btnKembali);
        tombol.add(btnKeluar);
        root.add(tombol, BorderLayout.SOUTH);
        return root;
    }

    // ----------------------------------------------------------- PANEL BANTUAN
    private JPanel buatPanelBantuan() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel judul = new JLabel("Bantuan");
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 16f));
        root.add(judul, BorderLayout.NORTH);

        JTextArea teks = new JTextArea(
                "Cara menggunakan aplikasi:\n\n"
              + "1. Isi Nama, Email, Umur, dan pilih Kegiatan pada formulir.\n"
              + "2. Klik 'Simpan/Tampilkan' untuk memvalidasi dan melihat ringkasan.\n"
              + "3. Klik 'Reset' untuk mengosongkan seluruh isian.\n"
              + "4. Klik 'Kembali' untuk kembali ke formulir (isian tetap dipertahankan).\n"
              + "5. Klik 'Keluar' untuk menutup aplikasi. Jika ada perubahan yang\n"
              + "   belum disimpan, aplikasi akan meminta konfirmasi.\n\n"
              + "Aturan validasi:\n"
              + "- Nama wajib diisi.\n"
              + "- Email harus berformat wajar, contoh: nama@domain.com.\n"
              + "- Umur berupa bilangan bulat antara 15 dan 100.\n"
              + "- Kegiatan wajib dipilih.");
        teks.setEditable(false);
        teks.setLineWrap(true);
        teks.setWrapStyleWord(true);
        teks.setOpaque(false);
        root.add(new JScrollPane(teks), BorderLayout.CENTER);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> tampilkan(CARD_FORM));
        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        tombol.add(btnKembali);
        root.add(tombol, BorderLayout.SOUTH);
        return root;
    }

    // ------------------------------------------------------------------ EVENT
    private void simpanDanTampilkan() {
        if (!validasiInput()) {
            return; // input salah tidak diproses
        }
        lblStatus.setText(" ");

        areaRingkasan.setText(
                  "Nama     : " + txtNama.getText().trim() + "\n"
                + "Email    : " + txtEmail.getText().trim() + "\n"
                + "Umur     : " + txtUmur.getText().trim() + " tahun\n"
                + "Kegiatan : " + cmbKegiatan.getSelectedItem() + "\n");

        belumDisimpan = false;
        tampilkan(CARD_RINGKASAN);
    }

    private void resetForm() {
        sedangMengisiOtomatis = true;
        txtNama.setText("");
        txtEmail.setText("");
        txtUmur.setText("");
        cmbKegiatan.setSelectedIndex(0);
        sedangMengisiOtomatis = false;

        lblStatus.setText(" ");
        belumDisimpan = false;
        txtNama.requestFocusInWindow();
    }

    private void keluar() {
        if (belumDisimpan) {
            int pilihan = JOptionPane.showConfirmDialog(this,
                    "Ada perubahan yang belum disimpan.\nYakin ingin keluar?",
                    "Konfirmasi Keluar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (pilihan != JOptionPane.YES_OPTION) {
                return; // pembatalan: tetap di aplikasi
            }
        }
        dispose();
        System.exit(0);
    }

    private void tampilkan(String namaCard) {
        cardLayout.show(cardPanel, namaCard);
        if (CARD_FORM.equals(namaCard)) {
            txtNama.requestFocusInWindow();
        }
    }

    // --------------------------------------------------------------- VALIDASI
    private boolean validasiInput() {
        String nama = txtNama.getText().trim();
        String email = txtEmail.getText().trim();
        String umurStr = txtUmur.getText().trim();

        if (nama.isEmpty()) {
            return gagal("Nama wajib diisi.", txtNama);
        }
        if (email.isEmpty()) {
            return gagal("Email wajib diisi.", txtEmail);
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return gagal("Format email tidak valid. Contoh: nama@domain.com", txtEmail);
        }
        if (umurStr.isEmpty()) {
            return gagal("Umur wajib diisi.", txtUmur);
        }
        int umur;
        try {
            umur = Integer.parseInt(umurStr);
        } catch (NumberFormatException ex) {
            return gagal("Umur harus berupa bilangan bulat (tanpa huruf, spasi, atau desimal).", txtUmur);
        }
        if (umur < 15 || umur > 100) {
            return gagal("Umur harus berada di antara 15 dan 100 tahun.", txtUmur);
        }
        if (cmbKegiatan.getSelectedIndex() <= 0) {
            return gagal("Silakan pilih salah satu kegiatan.", cmbKegiatan);
        }
        return true;
    }

    private boolean gagal(String pesan, JComponent kontrol) {
        lblStatus.setText(pesan);
        JOptionPane.showMessageDialog(this, pesan, "Validasi Gagal", JOptionPane.ERROR_MESSAGE);
        kontrol.requestFocusInWindow();
        if (kontrol instanceof JTextField) {
            ((JTextField) kontrol).selectAll();
        }
        return false;
    }

    // ----------------------------------------------- PELACAK PERUBAHAN (DIRTY)
    private void pasangPelacakPerubahan() {
        DocumentListener dl = new DocumentListener() {
            private void tandai() {
                if (!sedangMengisiOtomatis) belumDisimpan = true;
            }
            @Override public void insertUpdate(DocumentEvent e) { tandai(); }
            @Override public void removeUpdate(DocumentEvent e) { tandai(); }
            @Override public void changedUpdate(DocumentEvent e) { tandai(); }
        };
        txtNama.getDocument().addDocumentListener(dl);
        txtEmail.getDocument().addDocumentListener(dl);
        txtUmur.getDocument().addDocumentListener(dl);
        cmbKegiatan.addActionListener(e -> {
            if (!sedangMengisiOtomatis) belumDisimpan = true;
        });
    }

    // ------------------------------------------------------------------- MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PendaftaranKegiatan().setVisible(true));
    }
}