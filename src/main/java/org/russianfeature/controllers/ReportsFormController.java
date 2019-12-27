package org.russianfeature.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.utils.Config;
import org.russianfeature.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsFormController {

    private Main mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane reportsWorkPlace;

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(reportsWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(reportsWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(reportsWorkPlace, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(reportsWorkPlace, 0.0);

        //bindLayoutsBlocks();

    }


}
