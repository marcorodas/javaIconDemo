package pe.assupport.javaicondemo.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import javafx.concurrent.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;
import pe.assupport.javaicondemo.BaseController;

/**
 *
 * @author skynet
 */
@UtilityClass
public class HelperGui {

    public interface ThrowingRunnable {

        void run() throws Exception;
    }

    public void handle(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            HelperGui.alertError(e);
        }
    }

    //STRING UTIL (PRIVATE)
    private String join(String[] msjs) {
        return msjs == null || msjs.length == 0 ? "" : String.join(", ", msjs);
    }

    //MOUSE EVENT HANDLE SECTION
    public void onPrimaryDoubleClick(MouseEvent e, ThrowingRunnable runnable) {
        if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
            HelperGui.handle(runnable);
        }
    }

    //BASIC JAVAFX
    public void setAppIcons(Stage stage) {
        if (stage.getIcons().isEmpty()) {
            Arrays.asList("16x16", "32x32", "48x48", "60x60").stream()
                    .map(icon -> new Image("images/favicon_" + icon + ".png"))
                    .forEach(image -> stage.getIcons().add(image));
        }
    }

    //ALERT SECTION
    private Alert getAlert(Alert.AlertType type, String contentText) {
        Alert alert = new Alert(type, contentText);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        HelperGui.setAppIcons(alertStage);
        return alert;
    }

    public void alertInfo(String... msjs) {
        HelperGui.getAlert(Alert.AlertType.INFORMATION, HelperGui.join(msjs)).showAndWait();
    }

    public void alertError(Throwable ex, String... msjs) {
        String join = HelperGui.join(msjs), exMsj = ex.getMessage() == null
                ? ex.toString() : ex.getMessage();
        HelperGui.getAlert(Alert.AlertType.ERROR, join.isEmpty() ? exMsj : (exMsj + ": " + join))
                .showAndWait();
    }

    public void alertError(Service<?> service) {
        HelperGui.alertError(service.getException(), service.getTitle());
    }

    //ICON HORIZONTAL CENTERED
//    public VBox getIconCenterAlign(FontAwesomeIcon icon) {
//        VBox box = new VBox(new FontAwesomeIconView(icon));
//        box.setAlignment(Pos.CENTER);
//        return box;
//    }
    //IMAGE SECTION
    public ImageView getImageView(File imageFile, double requestedWidth, boolean backgroundLoading) throws MalformedURLException {
        String url = imageFile.toURI().toURL().toString();
        Image image = new Image(url, requestedWidth, 0, true, true, backgroundLoading);
        return new ImageView(image);
    }

}
