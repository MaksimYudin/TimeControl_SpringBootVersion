package org.russianfeature.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import com.utils.Config;
import javafx.scene.text.Font;
import org.russianfeature.Main;
import org.russianfeature.model.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DictionaryFormController {

    private Main mainApp;

    @FXML
    private AnchorPane dictionaryWorkPlace;

    @FXML
    private Button btnStudents;

    @FXML
    private Button btnTeachers;

    @FXML
    private Button btnLessons;

    @FXML
    private Button btnGroups;

    @FXML
    private Button btnGroupType;

    @FXML
    private Button btnRegime;

    @FXML
    private Button btnPosition;

    @FXML
    void btnStudentOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryStudentsMain");

        mainApp.showDictionaryStudentsMain();

        Node lbl = mainApp.getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Воспитанники");
    }

    @FXML
    void btnTeacherOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryTeacherMain");

        mainApp.showDictionaryTeacherMain();

        Node lbl = mainApp.getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Преподаватели");
    }

    @FXML
    void btnGroupTypeOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryGroupTypeMain");

        mainApp.showDictionaryGroupTypeMain();

        Node lbl = mainApp.getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Виды групп");

    }

    @FXML
    void btnGroupsOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryGroupDOO");

        mainApp.showDictionaryGroupMain();

        Node lbl = mainApp.getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Группы ДОО");
    }

    @FXML
    void btnLessonsOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryLesson");

        mainApp.showDictionaryLessonMain();

        Node lbl = mainApp.getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Предметы");
    }

    @FXML
    void btnRegimeOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryRegime");

        mainApp.showDictionaryRegimeMain();

        Node lbl = mainApp.getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Режимные моменты дня");
    }

    @FXML
    void btnPositionOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryPosition");

        mainApp.showDictionaryPositionMain();

        Node lbl = mainApp.getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Должности");
    }

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(dictionaryWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(dictionaryWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(dictionaryWorkPlace, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(dictionaryWorkPlace, 0.0);

        List<String> btтNameList = Config.getDictionaryBtnName();
        Button currentButton;
        for (String btnName : btтNameList) {
            Node node = dictionaryWorkPlace.lookup("#" + btnName);
            if (node != null) {
                currentButton = (Button) node;
                currentButton.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);
                currentButton.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
            }
        }

        /*
        btnGroups.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);
        btnStudents.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);
        btnTeachers.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);
        btnLessons.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);
        btnGroupType.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);
        btnRegime.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);
        btnPosition.setPrefSize(Config.DICTIONARY_MENU_WIDTH, Config.DICTIONARY_MENU_HEIGHT);

        btnGroups.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
        btnStudents.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
        btnTeachers.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
        btnLessons.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
        btnGroupType.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
        btnRegime.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
        btnPosition.setFont(new Font("System", Config.DICTIONARY_MENU_FONT_SIZE));
        */

    }


}
