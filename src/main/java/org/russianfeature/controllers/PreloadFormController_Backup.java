package org.russianfeature.controllers;

import com.hibernate.crud.operations.manager.StudentManager;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.russianfeature.Main;
import org.russianfeature.model.Student;
import org.russianfeature.model.StudentLoadInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PreloadFormController_Backup {

    private Stage stage;
    private Main mainApp;
    private List<StudentLoadInfo> dataList;
    private AnchorPane pane;
    private Button btnOK = new Button("ОК");
    private Button btnCancel = new Button("ОТМЕНА");
    private ObservableList<StudentLoadInfo> studentInfoObsList;
    private TableView<StudentLoadInfo> tblStudentView = new TableView<>();
    private boolean refreshData = false;

    @FXML
    void initialize() {
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stg) {
        stage = stg;
    }

    public void setDataList(List<StudentLoadInfo> dtList) {
        dataList = dtList;
    }

    public void setMainPane(AnchorPane pane) {
        this.pane = pane;
    }

    public void setElementFormProperty() {

        StudentManager studentManager = new StudentManager();
        studentInfoObsList = studentManager.getStudentLoadList(dataList);

        // Main table view with data
        //TableView<StudentLoadInfo> tblStudentView = new TableView<>();
        tblStudentView.setEditable(true);

        // Columns properties for table view
        TableColumn<StudentLoadInfo, Boolean> loadFlagCol = new TableColumn<>("loadFlag");
        loadFlagCol.setCellValueFactory(new PropertyValueFactory<StudentLoadInfo, Boolean>("loadFlag"));
        loadFlagCol.setCellFactory( tc -> new CheckBoxTableCell<>());
        tblStudentView.getColumns().add(loadFlagCol);

        TableColumn<StudentLoadInfo, String> firstNameCol = new TableColumn<>("First name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<StudentLoadInfo, String>("firstName"));
        tblStudentView.getColumns().add(firstNameCol);

        TableColumn<StudentLoadInfo, String> secondNameCol = new TableColumn<>("Second name");
        secondNameCol.setCellValueFactory(new PropertyValueFactory<StudentLoadInfo, String>("secondName"));
        tblStudentView.getColumns().add(secondNameCol);

        TableColumn<StudentLoadInfo, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<StudentLoadInfo, String>("lastName"));
        tblStudentView.getColumns().add(lastNameCol);

        TableColumn<StudentLoadInfo, String> birthDateCol = new TableColumn<>("Birth date");
        birthDateCol.setCellValueFactory(new PropertyValueFactory<StudentLoadInfo, String>("birthDate"));
        tblStudentView.getColumns().add(birthDateCol);

        TableColumn<StudentLoadInfo, String> errorTextCol = new TableColumn<>("Error text");
        errorTextCol.setCellValueFactory(new PropertyValueFactory<StudentLoadInfo, String>("errorText"));
        tblStudentView.getColumns().add(errorTextCol);

        TableColumn<StudentLoadInfo, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(new PropertyValueFactory<StudentLoadInfo, String>("comment"));
        commentCol.setCellFactory(TextFieldTableCell.forTableColumn());
        commentCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<StudentLoadInfo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<StudentLoadInfo, String> t) {
                        ((StudentLoadInfo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setComment(t.getNewValue());
                    }
                }
        );
        tblStudentView.getColumns().add(commentCol);

        /*
        errorTextCol.setCellFactory(column -> {
            return new TableCell<StudentLoadInfo, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null) {
                        setStyle("");
                        return;
                    }

                    StudentLoadInfo studentInfo = getTableView().getItems().get(getIndex());

                    if (!studentInfo.getErrorText().isEmpty()) {
                        setStyle("-fx-background-color: lightgreen");
                    }
                }
            };
        });
        */

        tblStudentView.setRowFactory(row -> new TableRow<StudentLoadInfo>() {

            @Override
            public void updateItem(StudentLoadInfo item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                    return;
                }

                if (!item.getErrorText().isEmpty()) {
                    setStyle("-fx-background-color: lightgreen");
                }
            }
        });

        AnchorPane.setLeftAnchor(tblStudentView, 0.0);
        AnchorPane.setRightAnchor(tblStudentView, 0.0);
        AnchorPane.setTopAnchor(tblStudentView, 0.0);
        AnchorPane.setBottomAnchor(tblStudentView, 50.0);

        tblStudentView.setItems(studentInfoObsList);
        pane.getChildren().add(tblStudentView);

        HBox btnList = new HBox();
        AnchorPane.setBottomAnchor(btnList, 0.0);

        btnOK.setPrefSize(80, 40);
        btnCancel.setPrefSize(80, 40);
        btnList.getChildren().addAll(btnOK, btnCancel);
        setButtonHandlers();

        pane.getChildren().add(btnList);
    }

    private void setButtonHandlers() {
        btnOK.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadStudentsInDB();
                refreshData = true;
                stage.close();
            }
        });

        btnCancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                refreshData = false;
                stage.close();
            }
        });
    }

    private void loadStudentsInDB() {
        StudentManager studentManager = new StudentManager();
        for (StudentLoadInfo studentInfo : tblStudentView.getItems()) {
            if (!studentInfo.isLoadFlag())
                continue;

            Student student = new Student();
            student.setFirstName(studentInfo.getFirstName());
            student.setSecondName(studentInfo.getSecondName());
            student.setLastName(studentInfo.getLastName());
            student.setBirthDate(studentInfo.getBirthDate());
            student.setComment(studentInfo.getComment());

            DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
            student.setCreateDate(sourceFormat.format(new Date()));
            studentManager.create(student);
        }
    }

    public boolean mustRefreshData() {
        return refreshData;
    }

}
