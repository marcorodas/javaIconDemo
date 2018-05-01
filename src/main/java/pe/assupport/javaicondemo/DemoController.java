package pe.assupport.javaicondemo;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIcons;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 * @author skynet
 */
public class DemoController extends BaseController {

    @FXML
    private TableView<GlyphIcon<?>> tblFontawesome;
    @FXML
    private TableColumn<GlyphIcon<?>, String> colFontawesome;
    @FXML
    private TableView<GlyphIcon<?>> tblMaterial;
    @FXML
    private TableColumn<GlyphIcon<?>, String> colMaterial;
    @FXML
    private TableView<GlyphIcon<?>> tblWeather;
    @FXML
    private TableColumn<GlyphIcon<?>, String> colWeather;
    @FXML
    private ComboBox<IconType> cmbIconSet;
    @FXML
    private TilePane tilePane;
    @FXML
    private TextField txtSearch;

    @Override
    protected String getFxmlFile() {
        return "Demo.fxml";
    }

    @Override
    protected String getTitle() {
        return null;
    }

    private TableCell<GlyphIcon<?>, String> getTableCell(IconType type) {
        int size = this.getGlyphsSize(type);
        return new TableCell<GlyphIcon<?>, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    this.setGraphic(null);
                } else {
                    GlyphIcon<?> icon = type.getGlyph(item);
                    icon.setGlyphSize(size);
                    VBox iconBox = new VBox(icon);
                    iconBox.setAlignment(Pos.CENTER);
                    iconBox.setPadding(new Insets(1));
                    iconBox.setPrefWidth(20);
                    HBox box = new HBox(10, iconBox, new Text(item));
                    box.setOnMouseClicked(event -> DemoController.this.onIconClicked(icon));
                    this.setGraphic(box);
                }
            }
        };
    }

    private void setTableViewIcons(TableView<GlyphIcon<?>> table, TableColumn<GlyphIcon<?>, String> column, IconType type) {
        List<GlyphIcon<?>> icons = Arrays.stream(type.getGlyphIcons())
                .map(icon -> type.getGlyph(icon.name()))
                .collect(Collectors.toList());
        FilteredList<GlyphIcon<?>> filteredData = new FilteredList<>(FXCollections.observableArrayList(icons), p -> true);
        txtSearch.textProperty().addListener((obs, old, newValue) -> {
            filteredData.setPredicate(icon -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowCaseValue = newValue.toLowerCase();
                return icon.getGlyphName().toLowerCase().contains(lowCaseValue);
            });
        });
        SortedList<GlyphIcon<?>> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        column.setCellValueFactory(cellData -> cellData.getValue().glyphNameProperty());
        column.setCellFactory(col -> this.getTableCell(type));
    }

    private int getGlyphsColumns(IconType type) {
        switch (type) {
            case FONTAWESOME:
                return 27;
            case MATERIALDESIGN:
                return 28;
            case WEATHERICON:
                return 24;
        }
        return 0;
    }

    private int getGlyphsSize(IconType type) {
        switch (type) {
            case FONTAWESOME:
                return 20;
            case MATERIALDESIGN:
                return 25;
            case WEATHERICON:
                return 19;
        }
        return 0;
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
            tilePane.getChildren().clear();
            tilePane.setPrefColumns(this.getGlyphsColumns(newValue));
            for (GlyphIcons icon : newValue.getGlyphIcons()) {
                GlyphIcon<?> glyphIcon = newValue.getGlyph(icon.name());
                glyphIcon.setGlyphSize(this.getGlyphsSize(newValue));
                glyphIcon.setOnMouseClicked(e -> this.onIconClicked(glyphIcon));
                tilePane.getChildren().add(glyphIcon);
            }
        });
        cmbIconSet.getSelectionModel().selectFirst();
        this.setTableViewIcons(tblFontawesome, colFontawesome, IconType.FONTAWESOME);
        this.setTableViewIcons(tblMaterial, colMaterial, IconType.MATERIALDESIGN);
        this.setTableViewIcons(tblWeather, colWeather, IconType.WEATHERICON);
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

}
