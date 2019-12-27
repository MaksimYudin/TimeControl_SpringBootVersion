package org.russianfeature.controllers;

import com.hibernate.crud.operations.manager.StudentManager;
import com.hibernate.crud.operations.manager.TeacherManager;
import com.utils.CommonUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import org.russianfeature.model.Teacher;
import org.russianfeature.model.TeacherLoadInfo;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PreloadFormController<T> {

    private Stage stage;
    private Main mainApp;
    private List<T> dataList;
    private AnchorPane pane;
    private Button btnOK = new Button("ОК");
    private Button btnCancel = new Button("ОТМЕНА");
    private ObservableList<T> entityInfoObsList;
    private TableView<T> tblView = new TableView<>();
    private boolean refreshData = false;
    private Class<T> typeParameterClass;
    private List<String> columnList = new ArrayList<>();

    @FXML
    void initialize() {
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stg) {
        stage = stg;
    }

    public void setDataList(List<T> dtList) {
        dataList = dtList;
    }

    public void setMainPane(AnchorPane pane) {
        this.pane = pane;
    }

    public void setTypeParameterClass(Class<T> clazz) {
        typeParameterClass = clazz;
    }

    public void setElementFormProperty() {

        setEntityInfoObsList();

        if (entityInfoObsList == null ||
                entityInfoObsList.size() == 0)
            return;

        // Main table view with data
        tblView.setEditable(true);

        for (Field fld : typeParameterClass.getDeclaredFields()) {

            String fieldName = fld.getName();
            if (!CommonUtil.isFieldVisible(fieldName))
                continue;

            if (fld.getType().equals(IntegerProperty.class)) {
                TableColumn<T, Integer> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                myColumn.setCellValueFactory(new PropertyValueFactory<T, Integer>(fieldName));
                myColumn.setId("tc_" + fieldName);
                tblView.getColumns().add(myColumn);
            } else if (fld.getType().equals(StringProperty.class)){
                TableColumn<T, String> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                myColumn.setCellValueFactory(new PropertyValueFactory<T, String>(fieldName));
                myColumn.setId("tc_" + fieldName);
                if (fld.getName() == "comment") {
                    myColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                    myColumn.setOnEditCommit(
                            new EventHandler<TableColumn.CellEditEvent<T, String>>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent<T, String> t) {
                                    ((StudentLoadInfo) t.getTableView().getItems().get(
                                            t.getTablePosition().getRow())
                                    ).setComment(t.getNewValue());
                                }
                            }
                    );
                }
                tblView.getColumns().add(myColumn);
            } else {
                TableColumn<T, Boolean> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                myColumn.setCellValueFactory(new PropertyValueFactory<T, Boolean>(fieldName));
                myColumn.setCellFactory( tc -> new CheckBoxTableCell<>());
                myColumn.setId("tc_" + fieldName);
                tblView.getColumns().add(myColumn);
            }
            columnList.add(fieldName);
        }
        /*
        tblStudentView.setRowFactory(row -> new TableRow<T>() {

            @Override
            public void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                    return;
                }

                if (!((StudentLoadInfo) item).getErrorText().isEmpty()) {
                    setStyle("-fx-background-color: lightgreen");
                }
            }
        });
        */
        AnchorPane.setLeftAnchor(tblView, 0.0);
        AnchorPane.setRightAnchor(tblView, 0.0);
        AnchorPane.setTopAnchor(tblView, 0.0);
        AnchorPane.setBottomAnchor(tblView, 50.0);

        tblView.setItems(entityInfoObsList);
        pane.getChildren().add(tblView);

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
                loadEntitiesInDB();
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

    // -----Load entity in DB -----
    private void loadEntitiesInDB() {
        if (typeParameterClass.equals(StudentLoadInfo.class))
            loadStudentsInDB();
        else if (typeParameterClass.equals(TeacherLoadInfo.class))
            loadTeacherInDB();
    }

    private void loadStudentsInDB() {
        StudentManager studentManager = new StudentManager();
        for (T studentInfo : tblView.getItems()) {
            StudentLoadInfo info = ((StudentLoadInfo) studentInfo);

            if (!info.isLoadFlag())
                continue;

            Student student = new Student();
            student.setFirstName(info.getFirstName());
            student.setSecondName(info.getSecondName());
            student.setLastName(info.getLastName());
            student.setBirthDate(info.getBirthDate());
            student.setComment(info.getComment());

            DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
            student.setCreateDate(sourceFormat.format(new Date()));
            studentManager.create(student);
        }
    }

    private void loadTeacherInDB() {
        TeacherManager teacherManager = new TeacherManager();
        for (T teacherInfo : tblView.getItems()) {
            TeacherLoadInfo info = ((TeacherLoadInfo) teacherInfo);

            if (!info.isLoadFlag())
                continue;

            Teacher teacher = new Teacher();
            teacher.setFirstName(info.getFirstName());
            teacher.setSecondName(info.getSecondName());
            teacher.setLastName(info.getLastName());
            teacher.setBirthDate(info.getBirthDate());
            teacher.setStartWorkDate(info.getStartWorkDate());
            teacher.setEndWorkDate(info.getEndWorkDate());
            teacher.setComment(info.getComment());

            DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
            teacher.setCreateDate(sourceFormat.format(new Date()));
            teacherManager.create(teacher);
        }
    }
    // ----------------------------

    public boolean mustRefreshData() {
        return refreshData;
    }

    private void setEntityInfoObsList() {
        if (typeParameterClass.equals(StudentLoadInfo.class)) {
            StudentManager studentManager = new StudentManager();
            entityInfoObsList = (ObservableList<T>) studentManager.getStudentLoadList((List<StudentLoadInfo>) dataList);
        } else if (typeParameterClass.equals(TeacherLoadInfo.class)) {
            TeacherManager teacherManager = new TeacherManager();
            entityInfoObsList = (ObservableList<T>) teacherManager.getTeacherLoadList((List<TeacherLoadInfo>) dataList);
        }
    }

}
