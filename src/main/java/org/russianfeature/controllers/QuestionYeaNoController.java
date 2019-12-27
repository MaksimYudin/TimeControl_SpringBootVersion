package org.russianfeature.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.russianfeature.Main;
import com.utils.EnumYesNo;

public class QuestionYeaNoController {

    private Stage stage;
    private Main mainApp;
    public static EnumYesNo answer = EnumYesNo.NO;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnYes;

    @FXML
    private Button btnNo;

    @FXML
    private Label msgLabel;

    @FXML
    void btnNoOnClick(ActionEvent event) {
        answer = EnumYesNo.NO;
        stage.close();
    }

    @FXML
    void btnYesOnClick(ActionEvent event) {
        answer = EnumYesNo.YES;
        stage.close();
    }

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stg) {
        stage = stg;
    }



}
