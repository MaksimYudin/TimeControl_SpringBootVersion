package org.russianfeature.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.russianfeature.Main;

public class NotificationFormController {

    private Stage stage;
    private Main mainApp;

    @FXML
    private Button btnOK;

    @FXML
    private Label msgLabel;

    @FXML
    void btnOKOnClick(ActionEvent event) {
        stage.close();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stg) {
        stage = stg;
    }

}