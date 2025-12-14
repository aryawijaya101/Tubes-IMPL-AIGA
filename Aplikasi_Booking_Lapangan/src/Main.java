import controller.kelolaUser.ManageUserController;
import view.Lapangan.ManageFieldView;
import controller.lapangan.FieldController;
import view.kelolaUser.ManageUserView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Jalankan GUI di Thread yang aman
        SwingUtilities.invokeLater(() -> {
            
            // Siapkan View
            ManageUserView view = new ManageUserView();
            
            // Siapkan Controller (Controller otomatis megang View dan Model)
            new ManageUserController(view);
            
            // Tampilkan
            view.setVisible(true);
        });
    }
}