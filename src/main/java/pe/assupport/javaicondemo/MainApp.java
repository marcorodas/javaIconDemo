package pe.assupport.javaicondemo;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BaseController.setAppTitle("Icon Demo");
        new DemoController().getStage().show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
