package pe.assupport.javaicondemo;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIcons;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 *
 * @author skynet
 */
public class DemoController extends BaseController {

    @FXML
    private ComboBox<IconType> cmbIconSet;
    @FXML
    private TilePane tilePane;
    @FXML
    private TextField txtSearch;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableView<?> tableViewIcons;

    private int glyphsColumns, glyphSize;

    @Override
    protected String getFxmlFile() {
        return "Demo.fxml";
    }

    @Override
    protected String getTitle() {
        return null;
    }

    private void setIconConfig(IconType type) {
        switch (type) {
            case FONTAWESOME:
                glyphsColumns = 27;
                glyphSize = 20;
                break;
            case MATERIALDESIGN:
                glyphsColumns = 28;
                glyphSize = 25;
                break;
            case WEATHERICON:
                glyphsColumns = 24;
                glyphSize = 19;
                break;
        }
    }

    private void onIconClicked(GlyphIcon<?> iconView) {
        ClipboardContent content = new ClipboardContent();
        content.putString(iconView.getGlyphName());
        Clipboard.getSystemClipboard().setContent(content);
        Label text = new Label("Icon '" + iconView.getGlyphName() + "' copied!");
        VBox box = new VBox(text);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(0, 20, 0, 20));
        PopOver popOver = new PopOver(box);
        popOver.setDetachable(false);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.show(iconView, -2);
    }

    @Override
    public void initialize() {
        cmbIconSet.getItems().addAll(IconType.values());
        cmbIconSet.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
            this.setIconConfig(newValue);
            tilePane.getChildren().clear();
            tilePane.setPrefColumns(glyphsColumns);
            for (GlyphIcons icon : newValue.getGlyphIcons()) {
                GlyphIcon<?> glyphIcon = newValue.getGlyph(icon.name());
                glyphIcon.setGlyphSize(glyphSize);
                glyphIcon.setOnMouseClicked(e -> this.onIconClicked(glyphIcon));
                tilePane.getChildren().add(glyphIcon);
            }
        });
        cmbIconSet.getSelectionModel().selectFirst();
    }

    @Override
    protected void config(Stage stage) {
        stage.setResizable(false);
        stage.sizeToScene();
    }

    @Override
    public DemoController getController() throws IOException {
        return (DemoController) super.getController();
    }

    public void setCmb(int index) {
        cmbIconSet.getSelectionModel().select(index);
    }

    private void showMap(boolean value) {
        scrollPane.setVisible(value);
        scrollPane.setManaged(value);
        tableViewIcons.setVisible(!value);
        tableViewIcons.setManaged(!value);
        txtSearch.setVisible(!value);
        if (!value) {
            txtSearch.requestFocus();
        }
    }

    @FXML
    private void onBtnMapClick(ActionEvent event) {
        this.showMap(true);
    }

    @FXML
    private void onBtnSearchClick(ActionEvent event) {
        this.showMap(false);
    }

}
