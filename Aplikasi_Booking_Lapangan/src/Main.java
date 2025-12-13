import view.lapangan.ManageFieldView;
import controller.lapangan.FieldController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Jalankan GUI di Thread yang aman
        SwingUtilities.invokeLater(() -> {
            
            // Siapkan View
            ManageFieldView view = new ManageFieldView();
            
            // Siapkan Controller (Controller otomatis megang View dan Model)
            new FieldController(view);
            
            // Tampilkan
            view.setVisible(true);
        });
    }
}