package view.lapangan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListFieldView extends JFrame {
    // Komponen GUI
    private JTable tableLapangan;
    private DefaultTableModel tableModel;
    private JButton btnRefresh; // Tombol lain opsional
    private JTextField txtSearch;
    private JButton btnSearch;

    public ListFieldView() {
        setTitle("Daftar Lapangan & Booking");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // PANEL ATAS
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel("Daftar Lapangan Olahraga");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("Cari");

        topPanel.add(lblTitle);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(new JLabel("Cari Nama:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // PANEL TENGAH (TABEL)
        String[] columnNames = {"ID", "Nama Lapangan", "Lokasi", "Tipe", "Harga/Jam", "Status", "Aksi"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Hanya kolom tombol yang bisa diedit
            }
        };

        tableLapangan = new JTable(tableModel);
        tableLapangan.setRowHeight(35);

        // SETTING RENDERER & EDITOR TOMBOL
        tableLapangan.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tableLapangan.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Lebar kolom
        tableLapangan.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableLapangan.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tableLapangan);
        add(scrollPane, BorderLayout.CENTER);

        // PANEL BAWAH
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Refresh Data");
        bottomPanel.add(btnRefresh);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- INNER CLASS RENDERER ---
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Booking" : value.toString());
            setBackground(new Color(52, 152, 219));
            setForeground(Color.WHITE);
            return this;
        }
    }

    // --- INNER CLASS EDITOR (LOGIC TOMBOL DI SINI) ---
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "Booking" : value.toString();
            button.setText(label);
            button.setBackground(new Color(41, 128, 185));
            button.setForeground(Color.WHITE);
            isPushed = true;
            selectedRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // 1. Ambil Data dari Tabel
                try {
                    int fieldId = Integer.parseInt(tableLapangan.getValueAt(selectedRow, 0).toString());
                    String fieldName = tableLapangan.getValueAt(selectedRow, 1).toString();

                    // Ambil harga (konversi object ke double)
                    String hargaStr = tableLapangan.getValueAt(selectedRow, 4).toString();
                    double price = Double.parseDouble(hargaStr);

                    // 2. Buka Halaman FieldPembayaran
                    // Kita oper data lapangan ke halaman baru
                    FieldPembayaran paymentPage = new FieldPembayaran(fieldId, fieldName, price);
                    paymentPage.setVisible(true);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(button, "Error mengambil data lapangan: " + e.getMessage());
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    // Getter
    public JTable getTableLapangan() { return tableLapangan; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTextField getTxtSearch() { return txtSearch; }
}