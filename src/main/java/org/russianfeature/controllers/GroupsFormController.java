package org.russianfeature.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.utils.Config;
import org.russianfeature.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupsFormController {

    private Main mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane groupsWorkPlace;

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(groupsWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(groupsWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(groupsWorkPlace, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(groupsWorkPlace, 0.0);

        //bindLayoutsBlocks();

    }


}
