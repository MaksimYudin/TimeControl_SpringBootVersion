package org.russianfeature.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.utils.Config;
import org.russianfeature.Main;

public class SettingsFormController {

    private Main mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane settingsWorkPlace;

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(settingsWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(settingsWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(settingsWorkPlace, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(settingsWorkPlace, 0.0);

        //bindLayoutsBlocks();

    }


}
