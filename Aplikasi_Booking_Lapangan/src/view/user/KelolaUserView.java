package view.user;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class KelolaUserView extends JFrame{
    private JTable tableUser;
    private DefaultTableModel tableModel;
    private JButton btnRefresh, btnAdd, btnEdit, btnDelete;
    private JTextField txtSearch;
    private JButton btnSearch;

    public KelolaUserView() {
        setTitle("Kelola Data User (Administrator)");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Atas
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel("Daftar Pengguna Sistem");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("Cari Nama");

        topPanel.add(lblTitle);
        topPanel.add(Box.createHorizontalStrut(30));
        topPanel.add(new JLabel("Cari:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // Panel Tengah (Tabel)
        String[] columnNames = {"ID", "Nama Lengkap", "Email", "No. Telepon", "Role"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableUser = new JTable(tableModel);
        tableUser.setRowHeight(25);

        // Atur lebar kolom
        tableUser.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableUser.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableUser.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tableUser);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Bawah (Tombol)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnRefresh = new JButton("Refresh");
        btnAdd = new JButton("Tambah User");
        btnEdit = new JButton("Edit User");
        btnDelete = new JButton("Hapus User");

        // Warna Tombol
        btnAdd.setBackground(new Color(46, 204, 113)); // Hijau
        btnAdd.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(231, 76, 60)); // Merah
        btnDelete.setForeground(Color.WHITE);

        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // GETTER
    public JTable getTableUser() { return tableUser; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
}
