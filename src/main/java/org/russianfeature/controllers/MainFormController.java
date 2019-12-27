package org.russianfeature.controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import com.hibernate.crud.operations.HibernateUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import com.utils.Config;
import org.russianfeature.Main;
import org.springframework.stereotype.Component;

@Component
public class MainFormController {

    private Main mainApp;
    private boolean isMenuHide = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label programCaption;

    @FXML
    private AnchorPane mainStage;

    @FXML
    private AnchorPane topPane;

    @FXML
    private AnchorPane bottomPane;

    @FXML
    private VBox leftMenu;

    @FXML
    private Button leftMenuMain;

    @FXML
    private Button leftMenuSettings;

    @FXML
    private Button leftMenuDictionary;

    @FXML
    private Button leftMenuGroup;

    @FXML
    private Button leftMenuShedule;

    @FXML
    private Button leftMenuManual;

    @FXML
    private Button leftMenuReports;

    @FXML
    private Button btnSeparator;

    @FXML
    private Button btnTest;

    @FXML
    void initialize() {

        startTask();

        setleftMenuImages();

        setFormElementProperty();

    }

    @FXML
    void onClickSeparator(ActionEvent event) {

        if (leftMenu == null || btnSeparator == null)
            return;

        if (isMenuHide) {
            leftMenu.setVisible(true);
            AnchorPane.setLeftAnchor(btnSeparator, (double) Config.BTN_MENU_WIDTH + 1);
            btnSeparator.setText("<");
            AnchorPane.setLeftAnchor(mainApp.getCurrentWorkPlace(), (double) Config.WORK_PLACE_LEFT_ANCOR_WIDTH);
            isMenuHide = false;
        } else {
            leftMenu.setVisible(false);
            AnchorPane.setLeftAnchor(btnSeparator, 0.0);
            btnSeparator.setText(">");
            AnchorPane.setLeftAnchor(mainApp.getCurrentWorkPlace(), (double) Config.SEPARATOR_BTN_WIDTH);
            isMenuHide = true;
        }


    }

    public MainFormController() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setleftMenuImages() {

        Image image = null;
        ImageView ImgView = null;

        TreeMap<String, String> menuBtns = Config.getMenuBtnMap();
        try {
            for (Map.Entry e : menuBtns.entrySet()) {
                image = new Image(getClass().getClassLoader().getResourceAsStream("images/" + ((String) e.getKey()).substring(2) + ".png"));
                ImgView = new ImageView(image);
                ImgView.setFitHeight(Config.BTN_MENU_IMAGE_SIZE);
                ImgView.setFitWidth(Config.BTN_MENU_IMAGE_SIZE);
                Node node = leftMenu.lookup("#" + (String) e.getValue());
                if (node != null) {
                    Button btn = (Button) node;
                    btn.setGraphic(ImgView);
                    btn.setAlignment(Pos.CENTER_LEFT);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    void setFormElementProperty() {

        // Left menu properties
        AnchorPane.setTopAnchor(leftMenu, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setBottomAnchor(leftMenu, (double) Config.TOP_BOTTOM_PANE_HEIGHT);

        leftMenu.setPrefWidth(Config.BTN_MENU_WIDTH);
        for (Node node : leftMenu.getChildren())
        {
            Button btn = (Button) node;
            btn.setPrefHeight(Config.BTN_MENU_HEIGHT);
            btn.setFont(new Font("Akaash", Config.BTN_MENU_FONT_SIZE));
        }

        leftMenuMain.prefWidthProperty().bind(leftMenu.widthProperty());
        leftMenuSettings.prefWidthProperty().bind(leftMenu.widthProperty());
        leftMenuDictionary.prefWidthProperty().bind(leftMenu.widthProperty());
        leftMenuGroup.prefWidthProperty().bind(leftMenu.widthProperty());
        leftMenuShedule.prefWidthProperty().bind(leftMenu.widthProperty());
        leftMenuManual.prefWidthProperty().bind(leftMenu.widthProperty());
        leftMenuReports.prefWidthProperty().bind(leftMenu.widthProperty());

        // Separator button properties
        AnchorPane.setLeftAnchor(btnSeparator, (double) Config.BTN_MENU_WIDTH + 1);
        AnchorPane.setTopAnchor(btnSeparator, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setBottomAnchor(btnSeparator, (double) Config.TOP_BOTTOM_PANE_HEIGHT);

        topPane.setPrefHeight(Config.TOP_BOTTOM_PANE_HEIGHT);
        bottomPane.setPrefHeight(Config.TOP_BOTTOM_PANE_HEIGHT);

        programCaption.setPrefHeight(Config.PROGRAM_CAPTION_HEIGHT);
        programCaption.setFont(new Font("System", Config.PROGRAM_CAPTION_FONT_SIZE));

    }

    @FXML
    void btnDictionaryOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("dictionaryWorkPlace");

        mainApp.showDictionaryWorkPlace();
    }

    @FXML
    void btnGroupsOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("groupsWorkPlace");

        mainApp.showGroupsWorkPlace();
    }

    @FXML
    void btnManualOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("manualWorkPlace");

        mainApp.showManualWorkPlace();
    }

    @FXML
    void btnMainOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("mainWorkPlace");

        mainApp.showMainWorkPlace();
    }

    @FXML
    void btnReportsOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("reportsWorkPlace");

        mainApp.showReportsWorkPlace();
    }

    @FXML
    void btnSettingsOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("settingsWorkPlace");

        mainApp.showSettingsWorkPlace();
    }

    @FXML
    void btnSheduleOnClick(ActionEvent event) {
        mainApp.removeWorkPlace("sheduleWorkPlace");

        mainApp.showSheduleWorkPlace();
    }


    public void startTask()
    {
        // Create a Runnable
        Runnable task = new Runnable()
        {
            public void run()
            {
                runTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    public void runTask()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                //SessionFactory sf = HibernateUtil.getSessionFactory();
                HibernateUtil h = new HibernateUtil();
            }
        });

    }






}
