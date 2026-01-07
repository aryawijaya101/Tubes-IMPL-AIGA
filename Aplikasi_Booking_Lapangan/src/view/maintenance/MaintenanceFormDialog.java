package view.maintenance;

import model.entity.Lapangan;
import javax.swing.*;
import java.awt.*;

public class MaintenanceFormDialog extends JDialog {
    
    private JComboBox<Lapangan> comboField; // Pilihan Lapangan (Object Lapangan langsung)
    private JTextField txtDate; // Input Tanggal (Format YYYY-MM-DD)
    private JTextArea txtDesc;
    private JComboBox<String> comboStatus;
    private JButton btnSave, btnCancel;
    
    private boolean succeeded;

    public MaintenanceFormDialog(Frame parent) {
        super(parent, "Form Jadwal Maintenance", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Pilih Lapangan
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Pilih Lapangan:"), gbc);
        
        gbc.gridx = 1;
        comboField = new JComboBox<>(); // Nanti diisi oleh Controller
        panel.add(comboField, gbc);

        // Tanggal
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Tanggal (YYYY-MM-DD):"), gbc);
        
        gbc.gridx = 1;
        txtDate = new JTextField(15);
        txtDate.setToolTipText("Contoh: 2025-12-31");
        panel.add(txtDate, gbc);

        // Deskripsi
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Deskripsi Perbaikan:"), gbc);
        
        gbc.gridx = 1;
        txtDesc = new JTextArea(3, 20);
        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        panel.add(scrollDesc, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Status Pengerjaan:"), gbc);
        
        gbc.gridx = 1;
        String[] statusList = {"Scheduled", "In Progress", "Completed"};
        comboStatus = new JComboBox<>(statusList);
        panel.add(comboStatus, gbc);

        // Tombol
        JPanel btnPanel = new JPanel();
        btnSave = new JButton("Simpan");
        btnCancel = new JButton("Batal");
        
        btnSave.addActionListener(e -> {
            succeeded = true;
            dispose();
        });
        
        btnCancel.addActionListener(e -> {
            succeeded = false;
            dispose();
        });
        
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    // Getter Inputan
    public Lapangan getSelectedField() { return (Lapangan) comboField.getSelectedItem(); }
    public String getDateInput() { return txtDate.getText().trim(); }
    public String getDescription() { return txtDesc.getText().trim(); }
    public String getStatus() { return (String) comboStatus.getSelectedItem(); }
    
    // Getter Komponen (Untuk Controller mengisi data ComboBox)
    public JComboBox<Lapangan> getComboField() { return comboField; }

    // Setter (Untuk Mode Edit)
    public void setSelectedField(int fieldId) {
        for (int i = 0; i < comboField.getItemCount(); i++) {
            Lapangan l = comboField.getItemAt(i);
            if (l.getFieldId() == fieldId) {
                comboField.setSelectedIndex(i);
                break;
            }
        }
    }
    public void setDateInput(String v) { txtDate.setText(v); }
    public void setDescription(String v) { txtDesc.setText(v); }
    public void setStatus(String v) { comboStatus.setSelectedItem(v); }

    public boolean isSucceeded() { return succeeded; }
}