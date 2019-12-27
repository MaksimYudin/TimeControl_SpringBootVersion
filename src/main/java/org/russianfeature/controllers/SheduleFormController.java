package org.russianfeature.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.utils.Config;
import org.russianfeature.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class SheduleFormController {

    private Main mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane sheduleWorkPlace;

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(sheduleWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(sheduleWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(sheduleWorkPlace, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(sheduleWorkPlace, 0.0);

        //bindLayoutsBlocks();

    }


}
