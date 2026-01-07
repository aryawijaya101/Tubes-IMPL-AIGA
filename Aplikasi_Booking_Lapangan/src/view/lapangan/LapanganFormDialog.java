package view.lapangan;

import javax.swing.*;
import java.awt.*;

/**
* Dialog (Jendela Kecil) untuk Input Data Lapangan Baru atau Edit.
*/
public class LapanganFormDialog extends JDialog {
    private JTextField txtNama;
    private JTextField txtLokasi;
    private JComboBox<String> comboTipe;
    private JTextField txtHarga;
    private JComboBox<String> comboStatus;
    private JButton btnSave;
    private JButton btnCancel;
    
    private boolean succeeded; // Penanda apakah user klik Save atau Cancel

    public LapanganFormDialog(Frame parent) {
        super(parent, "Form Data Lapangan", true); // true = Modal (User gabisa klik belakang sebelum ini nutup)
        
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10)); // Grid layout 6 baris 2 kolom
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Nama
        panel.add(new JLabel("Nama Lapangan:"));
        txtNama = new JTextField(20);
        panel.add(txtNama);

        // Lokasi
        panel.add(new JLabel("Lokasi:"));
        txtLokasi = new JTextField(20);
        panel.add(txtLokasi);

        // Tipe (ComboBox)
        panel.add(new JLabel("Tipe Olahraga:"));
        String[] types = {"Futsal", "Badminton", "Basket", "Voli", "Tennis", "Padel"};
        comboTipe = new JComboBox<>(types);
        panel.add(comboTipe);

        // Harga
        panel.add(new JLabel("Harga per Jam (Rp):"));
        txtHarga = new JTextField(20);
        panel.add(txtHarga);

        // Status
        panel.add(new JLabel("Status:"));
        String[] statuses = {"Available", "Maintenance", "Booked"};
        comboStatus = new JComboBox<>(statuses);
        panel.add(comboStatus);
        
        // Tombol
        btnSave = new JButton("Simpan");
        btnCancel = new JButton("Batal");
        
        // Event Listener sederhana untuk tombol
        btnSave.addActionListener(e -> {
            succeeded = true;
            dispose(); // Tutup dialog
        });
        
        btnCancel.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        pack(); // Sesuaikan ukuran otomatis
        setLocationRelativeTo(parent);
    }

    // Getter Data Inputan
    public String getNama() { return txtNama.getText().trim(); }
    public String getLokasi() { return txtLokasi.getText().trim(); }
    public String getTipe() { return (String) comboTipe.getSelectedItem(); }
    public String getStatus() { return (String) comboStatus.getSelectedItem(); }
    
    // Parsing Harga (Handle error jika user input huruf)
    public double getHarga() {
        try {
            return Double.parseDouble(txtHarga.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Setter (Untuk mode Edit, mengisi form dengan data lama)
    public void setNama(String v) { txtNama.setText(v); }
    public void setLokasi(String v) { txtLokasi.setText(v); }
    public void setTipe(String v) { comboTipe.setSelectedItem(v); }
    public void setHarga(double v) { txtHarga.setText(String.valueOf(v)); }
    public void setStatus(String v) { comboStatus.setSelectedItem(v); }

    public boolean isSucceeded() { return succeeded; }
}
