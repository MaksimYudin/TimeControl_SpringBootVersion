package org.russianfeature.controllers.dictionary;

import com.hibernate.crud.operations.manager.*;
import com.utils.*;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.russianfeature.Main;
import org.russianfeature.model.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class _DictionaryListFormController<T> {

    private Main mainApp;
    private ObservableList<T> dataList = FXCollections.observableArrayList();
    private T currentEntity;
    private Stage currentStage;
    private Class<T> typeParameterClass;
    private List<String> columnList = new ArrayList<>();
    private _DictionaryEditFormController editForm;

    @FXML
    private AnchorPane dictionaryMain;

    @FXML
    private TableView<T> mainTable;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLoadFromExcel;

    @FXML
    private TableColumn<T, String> col1;

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setTypeParameterClass(Class<T> clazz) {
        typeParameterClass = clazz;
    }

    public void setEditForm(_DictionaryEditFormController editForm) {
        this.editForm = editForm;
    }

    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    public void setPlaceProperty() {

        AnchorPane.setBottomAnchor(dictionaryMain, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(dictionaryMain, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(dictionaryMain, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(dictionaryMain, 0.0);

    }

    public void setCurrentEntity() {

    }

    void showDicList() {

        //Binding model and view elements

            for (Field fld : typeParameterClass.getDeclaredFields()) {

                String fieldName = fld.getName();
                if (!CommonUtil.isFormListFieldVisible(fieldName))
                    continue;

                if (fieldName.equals(IntegerProperty.class)) {
                    TableColumn<T, Integer> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                    myColumn.setCellValueFactory(new PropertyValueFactory<T, Integer>(fieldName));
                    myColumn.setId(fieldName);
                    mainTable.getColumns().add(myColumn);
                } else {
                    TableColumn<T, String> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                    myColumn.setCellValueFactory(new PropertyValueFactory<T, String>(fieldName));
                    myColumn.setId(fieldName);
                    mainTable.getColumns().add(myColumn);
                }
                columnList.add(fieldName);
            }

        loadDataInTableView();

    }



    public void initForm() {

        showDicList();

        setPlaceProperty();

        setColumnProperty();

        setFormEvents();

    }

    private void loadDataInTableView() {

        setEntityList();
        if (dataList.size() == 0) {
            mainTable.getItems().clear();
            return;
        }

        mainTable.setItems(dataList);
        //if (dataList.size() > 0) {
        if (currentEntity == null) {
            mainTable.getSelectionModel().select(0);
            mainTable.getFocusModel().focus(0);
        } else {
            Integer index = getIndexByEntity(currentEntity);
            if (index >= 0) {
                mainTable.requestFocus();
                mainTable.getSelectionModel().select(index);
                mainTable.getFocusModel().focus(index);
            }
        }
       // }
        mainTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    // ----- Set entity list -----
    private void setEntityList() {
        if (typeParameterClass.equals(Student.class))
            //dataList = (ObservableList<T>) getStudentList();
            dataList = (ObservableList<T>) StudentManager.getStudentObservableList();
        else if (typeParameterClass.equals(Teacher.class))
            //dataList = (ObservableList<T>) getTeacherList();
            dataList = (ObservableList<T>) TeacherManager.getTeacherObservableList();
        else if (typeParameterClass.equals(GroupType.class))
            //dataList = (ObservableList<T>) getGroupTypeList();
            dataList = (ObservableList<T>) GroupTypeManager.getGroupTypeObservableList();
        else if (typeParameterClass.equals(GroupDOO.class))
            //dataList = (ObservableList<T>) getGroupDOOList();
            dataList = (ObservableList<T>) GroupDOOManager.getGroupDOOObservableList();
        else if (typeParameterClass.equals(Lesson.class))
            //dataList = (ObservableList<T>) getLessonList();
            dataList = (ObservableList<T>) LessonManager.getLessonObservableList();
        else if (typeParameterClass.equals(Position.class))
            //dataList = (ObservableList<T>) getPositionList();
            dataList = (ObservableList<T>) PositionManager.getPositionObservableList();
        else if (typeParameterClass.equals(Regime.class))
            //dataList = (ObservableList<T>) getRegimeList();
            dataList = (ObservableList<T>) RegimeManager.getRegimeObservableList();
    }

    private void setFormEvents() {
        mainTable.setRowFactory( tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    T item = row.getItem();
                    int a = 0;
                    HashMap<String, Integer> eAttr = editForm.getEntitiesAttr();
                    if (typeParameterClass.equals(GroupDOO.class))
                        eAttr.put(typeParameterClass.toString(), ((GroupDOO) item).getId());
                    else if (typeParameterClass.equals(GroupType.class))
                        eAttr.put(typeParameterClass.toString(), ((GroupType) item).getId());
                    else if (typeParameterClass.equals(Lesson.class))
                        eAttr.put(typeParameterClass.toString(), ((Lesson) item).getId());
                    else if (typeParameterClass.equals(Position.class))
                        eAttr.put(typeParameterClass.toString(), ((Position) item).getId());
                    else if (typeParameterClass.equals(Regime.class))
                        eAttr.put(typeParameterClass.toString(), ((Regime) item).getId());
                    else if (typeParameterClass.equals(Student.class))
                        eAttr.put(typeParameterClass.toString(), ((Student) item).getId());
                    else if (typeParameterClass.equals(Teacher.class))
                        eAttr.put(typeParameterClass.toString(), ((Teacher) item).getId());

                    currentStage.close();
                }
            });
            return row ;
        });
    }


    // ----- Get entity index number in entity list -----
    private Integer getIndexByEntity(T entity) {
        if (typeParameterClass.equals(Student.class))
            return CommonUtil.getIndexByStudent((Student) entity, (ObservableList<Student>) dataList);
        else if (typeParameterClass.equals(Teacher.class))
            return CommonUtil.getIndexByTeacher((Teacher) entity, (ObservableList<Teacher>) dataList);
        else if (typeParameterClass.equals(GroupType.class))
            return CommonUtil.getIndexByGroupType((GroupType) entity, (ObservableList<GroupType>) dataList);
        else if (typeParameterClass.equals(GroupDOO.class))
            return CommonUtil.getIndexByGroupDOO((GroupDOO) entity, (ObservableList<GroupDOO>) dataList);
        else if (typeParameterClass.equals(Lesson.class))
            return CommonUtil.getIndexByLesson((Lesson) entity, (ObservableList<Lesson>) dataList);
        else if (typeParameterClass.equals(Position.class))
            return CommonUtil.getIndexByPosition((Position) entity, (ObservableList<Position>) dataList);
        else if (typeParameterClass.equals(Regime.class))
            return CommonUtil.getIndexByRegime((Regime) entity, (ObservableList<Regime>) dataList);

        return -1;
    }

    void setColumnProperty() {
        mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn id = findColumnByName("id");
        if (id != null)
            id.setVisible(false);

        TreeMap<String, String> fieldTM = Config.getVisibleFormListFields();
        for (Map.Entry e : fieldTM.entrySet()) {
            if (e.getKey().toString().contains("createDate")
                || e.getKey().toString().contains("birthDate"))
                continue;

            TableColumn field = findColumnByName(e.getKey().toString());
            if (field != null)
                field.prefWidthProperty().bind(dictionaryMain.widthProperty().divide(4.0));
        }

        TableColumn createDate = findColumnByName("createDate");
        // set column createDate date format
        if (createDate != null) {
            createDate.setPrefWidth(80);
            createDate.setCellFactory(column -> {
                TableCell<T, String> cell = new TableCell<>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            //setText(format.format(java.sql.Date.valueOf(item)));
                            try {
                                setText(format.format(format.parse(item)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                return cell;
            });
        }

        TableColumn birthDate = findColumnByName("birthDate");
        // set column birthDate date format
        if (birthDate != null) {
            birthDate.setPrefWidth(80.0);
            birthDate.setCellFactory(column -> {
                TableCell<T, String> cell = new TableCell<>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        }
                        else {
                            String dateCurrent = "01.01.1500";
                            try {
                                if (item != null)
                                    dateCurrent = format.format(format.parse(item));

                                setText(dateCurrent);

                            } catch (ParseException exc) {
                                exc.printStackTrace();
                            }

                        }
                    }
                };

                return cell;
            });
        }

    }

    private TableColumn findColumnByName(String name) {
        int colIndex = columnList.indexOf(name);
        if (colIndex >= 0)
            return mainTable.getColumns().get(colIndex);
        else
            return null;
    }



}