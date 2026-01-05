package view.lapangan;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldPembayaran extends JFrame {

    // Data dari halaman sebelumnya
    private int fieldId;
    private double pricePerHour;

    // Komponen Input
    private JTextField txtFieldName;
    private JTextField txtPrice;
    private JTextField txtDate; // Format YYYY-MM-DD
    private JComboBox<String> comboStartTime;
    private JComboBox<String> comboEndTime;
    private JComboBox<String> comboPaymentMethod; // Sesuai Entity Pembayaran
    private JLabel lblTotalAmount;
    private JButton btnPay, btnCancel;

    public FieldPembayaran(int fieldId, String fieldName, double pricePerHour) {
        this.fieldId = fieldId;
        this.pricePerHour = pricePerHour;

        setTitle("Form Booking & Pembayaran");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER
        JLabel lblTitle = new JLabel("Rincian Pemesanan", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        // FORM PANEL
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Nama Lapangan (Read Only)
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nama Lapangan:"), gbc);
        txtFieldName = new JTextField(fieldName);
        txtFieldName.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(txtFieldName, gbc);

        // 2. Harga per Jam (Read Only)
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Harga / Jam:"), gbc);
        txtPrice = new JTextField(String.valueOf(pricePerHour));
        txtPrice.setEditable(false);
        gbc.gridx = 1;
        formPanel.add(txtPrice, gbc);

        // 3. Tanggal Booking
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tanggal (YYYY-MM-DD):"), gbc);
        txtDate = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // Default hari ini
        gbc.gridx = 1;
        formPanel.add(txtDate, gbc);

        // 4. Jam Mulai & Selesai
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Jam Mulai:"), gbc);
        String[] hours = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"};
        comboStartTime = new JComboBox<>(hours);
        gbc.gridx = 1;
        formPanel.add(comboStartTime, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Jam Selesai:"), gbc);
        comboEndTime = new JComboBox<>(hours);
        comboEndTime.setSelectedIndex(1); // Default 1 jam setelahnya
        gbc.gridx = 1;
        formPanel.add(comboEndTime, gbc);

        // 5. Metode Pembayaran (Sesuai Entity Pembayaran)
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Metode Pembayaran:"), gbc);
        String[] methods = {"Transfer Bank", "E-Wallet", "Cash", "Credit Card"};
        comboPaymentMethod = new JComboBox<>(methods);
        gbc.gridx = 1;
        formPanel.add(comboPaymentMethod, gbc);

        // 6. Total Bayar (Kalkulasi Otomatis Sederhana)
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Total Pembayaran:"), gbc);
        lblTotalAmount = new JLabel("Rp 0");
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalAmount.setForeground(new Color(46, 204, 113));
        gbc.gridx = 1;
        formPanel.add(lblTotalAmount, gbc);

        add(formPanel, BorderLayout.CENTER);

        // TOMBOL PANEL
        JPanel buttonPanel = new JPanel();
        btnPay = new JButton("Bayar Sekarang");
        btnPay.setBackground(new Color(52, 152, 219));
        btnPay.setForeground(Color.WHITE);

        btnCancel = new JButton("Batal");
        btnCancel.setBackground(new Color(231, 76, 60));
        btnCancel.setForeground(Color.WHITE);

        buttonPanel.add(btnPay);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- EVENT LISTENER ---

        // Listener Hitung Harga saat Jam Berubah
        comboStartTime.addActionListener(e -> calculateTotal());
        comboEndTime.addActionListener(e -> calculateTotal());

        // Hitung awal saat buka
        calculateTotal();

        // Tombol Cancel
        btnCancel.addActionListener(e -> dispose());

        // Tombol Bayar (Placeholder Logic)
        btnPay.addActionListener(e -> {
            // Nanti di sini panggil Controller untuk simpan ke Tabel Booking & Pembayaran
            JOptionPane.showMessageDialog(this,
                    "Pembayaran Berhasil!\nMetode: " + comboPaymentMethod.getSelectedItem() +
                            "\nTotal: " + lblTotalAmount.getText());
            dispose();
        });
    }

    private void calculateTotal() {
        try {
            int startIdx = comboStartTime.getSelectedIndex();
            int endIdx = comboEndTime.getSelectedIndex();

            // Logic sederhana: Index combo box merepresentasikan urutan jam
            // Jika jam selesai <= jam mulai, anggap tidak valid atau minimal 1 jam
            int duration = endIdx - startIdx;

            if (duration <= 0) {
                lblTotalAmount.setText("Jam tidak valid");
                btnPay.setEnabled(false);
            } else {
                double total = duration * pricePerHour;
                lblTotalAmount.setText("Rp " + String.format("%.0f", total));
                btnPay.setEnabled(true);
            }
        } catch (Exception ex) {
            lblTotalAmount.setText("Error");
        }
    }
}