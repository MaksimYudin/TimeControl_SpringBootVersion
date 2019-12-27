package org.russianfeature.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.utils.Config;
import org.russianfeature.Main;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

public class ManualFormController {

    private Main mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane manualWorkPlace;

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(manualWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(manualWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(manualWorkPlace, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(manualWorkPlace, 0.0);

        //bindLayoutsBlocks();

    }


}
