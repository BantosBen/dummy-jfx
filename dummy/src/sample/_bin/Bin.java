package sample._bin;

import javafx.geometry.Pos;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Bin {
    Notifications notifications;

    public Bin(Window window) {
        notifications = Notifications.create()
                .hideAfter(Duration.seconds(2))
                .hideCloseButton()
                .owner(window)
                .position(Pos.TOP_CENTER)
                .title("Dummy JFX App");
    }

    public void showSuccessMessage(String msg) {
        notifications.text(msg)
                .showInformation();
    }
    public void showErrorMessage(String msg) {
        notifications.text(msg)
                .showError();
    }
}
