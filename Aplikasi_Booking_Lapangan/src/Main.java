import controller.login.LoginController;
import view.login.LoginView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Jalankan via LoginController
            LoginView view = new LoginView();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}