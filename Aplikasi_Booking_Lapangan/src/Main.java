import view.booking.ManageBookingView;
import controller.booking.BookingController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageBookingView view = new ManageBookingView();
            new BookingController(view);
            view.setVisible(true);
        });
    }
}