package pe.assupport.javaicondemo;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import pe.assupport.javaicondemo.util.HelperGui;

/**
 *
 * @author skynet
 */
public abstract class BaseController {

    @Getter
    private static String appTitle;

    private BaseController controller;
    private Scene scene;
    private boolean isLoaded;
    private Stage stage;

    protected abstract String getFxmlFile();

    protected abstract String getTitle();

    public abstract void initialize();

    protected abstract void config(Stage stage);

    public static void setAppTitle(String appTitle) {
        BaseController.appTitle = appTitle == null ? "" : appTitle.trim();
    }

    private void load() throws IOException {
        if (!isLoaded) {
            String file = this.getFxmlFile();
            if (file != null && !file.trim().isEmpty()) {
                URL url = this.getClass().getResource("/fxml/" + file);
                FXMLLoader loader = new FXMLLoader(url);
                scene = new Scene(loader.load());
                controller = loader.getController();
            }
            isLoaded = true;
        }
    }

    private void initStage() throws IOException {
        this.load();
        if (scene != null) {
            stage.setScene(scene);
        }
        HelperGui.setAppIcons(stage);
        String title = this.getTitle() == null ? "" : this.getTitle().trim();
        title = appTitle.isEmpty() ? title : (title.isEmpty() ? appTitle : (appTitle + " - " + title));
        stage.setTitle(title);
        this.config(stage);
    }

    public void setStage(@NonNull Stage stage) throws IOException {
        this.stage = stage;
        this.initStage();
    }

    public Stage getStage() throws IOException {
        if (stage == null) {
            stage = new Stage();
            this.initStage();
        }
        return stage;
    }

    /**
     * Allow to access methods that manipulates FXML fields.
     * 
     * <pre> //Convenient way to override method in subclass
     * public class SampleController extends BaseController {
     *    /.../
     *    &#64;Override
     *    public SampleController getController() throws IOException {
     *      return (SampleController) super.getController();
     *    }
     *    /.../
     * }</pre>
     * <pre>
     * //Sample Use:
     * controller.getController().setComboBox(2);
     * </pre>
     *
     * @return Controller with valid FXML fields
     * @throws java.io.IOException
     */
    public BaseController getController() throws IOException {
        this.load();
        return controller;
    }

}
