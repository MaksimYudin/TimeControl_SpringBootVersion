package org.russianfeature.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import com.utils.Config;
import org.russianfeature.Main;

public class MainWorkPlaceController {

    private Main mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainWorkPlace;

    @FXML
    private GridPane dictionaryBlock;

    @FXML
    private GridPane settingsBlock;

    @FXML
    private GridPane reportsBlock;

    @FXML
    private GridPane sheduleBlock;

    @FXML
    private GridPane groupsBlock;

    @FXML
    private ImageView imgMenuReports;

    @FXML
    private ImageView imgMenuShedule;

    @FXML
    private ImageView imgMenuGroup;

    @FXML
    private ImageView imgMenuDictionary;

    @FXML
    private ImageView imgMenuSettings;

    @FXML
    void initialize() {
        setPlaceProperty();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {

        AnchorPane.setBottomAnchor(mainWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(mainWorkPlace, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(mainWorkPlace, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(mainWorkPlace, 0.0);

        for (String imgId : Config.getMainWorkPlaceImages()) {
            Node node = mainWorkPlace.lookup("#" + imgId);
            if (node != null) {
                ImageView imgView = (ImageView) node;
                imgView.setFitWidth(Config.MAIN_WP_IMG_WIDTH);
                imgView.setFitHeight(Config.MAIN_WP_IMG_HEIGHT);
            }
        }

        try {
            for (String mainWPId : Config.getMainWorkPlaceBlocks()) {
                Node node = mainWorkPlace.lookup("#" + mainWPId);
                GridPane gridPane = (GridPane) node;
                gridPane.setPrefHeight(Config.MAIN_WP_GRIDPANE_HEIGHT);
                gridPane.setPrefWidth(Config.MAIN_WP_GRIDPANE_WIDTH);

                ObservableList<Node> childrens = gridPane.getChildren();
                for (Node gpNode : childrens) {
                    if (gpNode.getClass().getName() == "javafx.scene.control.Label") {
                        Label lbl = (Label) gpNode;
                        lbl.setFont(new Font("System", Config.BTN_MENU_FONT_SIZE));
                        lbl.setPrefWidth(Config.MAIN_WP_GRIDPANE_WIDTH);
                        lbl.setPrefHeight(Config.MAIN_WP_GRIDPANE_LABEL_HEIGHT);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bindLayoutsBlocks();

    }

    private void updateWidthConstaints(GridPane gp, double width) {

        AnchorPane.setRightAnchor(gp, width * 1 / 2.2 + 5);

    }

    private void updateHeightConstaints(GridPane gp, double height) {

        AnchorPane.setTopAnchor(gp, height * 1 / 3 + 5);

    }

    private void bindLayoutsBlocks() {
        mainWorkPlace.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateWidthConstaints(dictionaryBlock, newValue.doubleValue());
            }
        });

        mainWorkPlace.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateHeightConstaints(settingsBlock, newValue.doubleValue());
            }
        });

        mainWorkPlace.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //updateHeightConstaints(gpGroups, newValue.doubleValue());
                AnchorPane.setLeftAnchor(reportsBlock, newValue.doubleValue() * 1 / 10 + 5);
            }
        });

        mainWorkPlace.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //updateHeightConstaints(gpGroups, newValue.doubleValue());
                AnchorPane.setRightAnchor(sheduleBlock, newValue.doubleValue() * 1 / 10 + 5);
            }
        });

        mainWorkPlace.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateHeightConstaints(groupsBlock, newValue.doubleValue());
            }
        });
    }

}

