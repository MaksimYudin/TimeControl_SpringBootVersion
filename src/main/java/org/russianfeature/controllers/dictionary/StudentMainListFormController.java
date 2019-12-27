package org.russianfeature.controllers.dictionary;

import com.hibernate.crud.operations.manager.*;
import com.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.russianfeature.Main;
import org.russianfeature.controllers.PreloadFormController;
import org.russianfeature.controllers.QuestionYeaNoController;
import org.russianfeature.model.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentMainListFormController {

    private Main mainApp;
    private ObservableList<Student> dataList = FXCollections.observableArrayList();
    private Student currentEntity;
    private Stage currentStage;
    //private Class<T> typeParameterClass;
    private List<String> columnList = new ArrayList<>();

    @FXML
    private AnchorPane dictionaryMain;

    @FXML
    private TableView<Student> mainTable;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLoadFromExcel;

    @FXML
    private TableColumn<Student, String> col1;

    @FXML
    void btnAddOnClick(ActionEvent event) {

        showEditCreateWindow(EnumAction.CREATE);
        loadDataInTableView();

    }

    @FXML
    void btnDeleteOnClick(ActionEvent event) {

        ObservableList<Student> selectedEntity = mainTable.getSelectionModel().getSelectedItems();
        if (selectedEntity.size() == 0) return;

        StringBuilder msgText = new StringBuilder();
        if (selectedEntity.size() > 1) {
            msgText.append("Вы действительно хотите удалить записи?");
        } else {
            msgText.append("Вы действительно хотите удалить запись?");
        }

        CommonUtil.showYesNoQuestionWindow(mainApp, msgText.toString());
        if (QuestionYeaNoController.answer == EnumYesNo.NO)
            return;

        deleteStudents(selectedEntity);

        loadDataInTableView();

    }

    @FXML
    void btnEditOnClick(ActionEvent event) {
        showEditCreateWindow(EnumAction.UPDATE);
    }

    @FXML
    void btnLoadFromExcelOnClick(ActionEvent event) {

//        if (!CommonUtil.isLoadFromExcelButtonHandled(typeParameterClass))
//            return;

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Выберите файл для загрузки");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls")
        );

        File file = fileChooser.showOpenDialog(currentStage);
        if (file != null) {
            String msgText = "Вы действительно хотите выполнить загрузку?";
            CommonUtil.showYesNoQuestionWindow(mainApp, msgText);
            if (QuestionYeaNoController.answer == EnumYesNo.NO)
                return;

            //List<T> entityList = FXCollections.observableArrayList();
            List<StudentLoadInfo> entityList = getEntityListFromExcel(file);

            showPreloadForm(entityList);
        }

        System.gc();

    }

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(dictionaryMain, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(dictionaryMain, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(dictionaryMain, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(dictionaryMain, 0.0);

        //bindLayoutsBlocks();

    }

    void showDicList() {

        //Binding model and view elements

            for (Field fld : Student.class.getDeclaredFields()) {

                String fieldName = fld.getName();
                if (!CommonUtil.isFormListFieldVisible(fieldName))
                    continue;

                //if (fld.getType().equals(IntegerProperty.class)) {
//                    TableColumn<T, Integer> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
//                    myColumn.setCellValueFactory(new PropertyValueFactory<T, Integer>(fieldName));
//                    myColumn.setId(fieldName);
//                    mainTable.getColumns().add(myColumn);
//                } else {
                    TableColumn<Student, String> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                    myColumn.setCellValueFactory(new PropertyValueFactory<Student, String>(fieldName));
                    myColumn.setId(fieldName);
                    mainTable.getColumns().add(myColumn);
                //}
                columnList.add(fieldName);
            }

        loadDataInTableView();

    }

    // ----- Delete entity -----
//    private void deleteEntities(ObservableList<T> selectedEntity) {
//        if (typeParameterClass.equals(Student.class))
//            deleteStudents(selectedEntity);
//        else if (typeParameterClass.equals(Teacher.class))
//            deleteTeachers(selectedEntity);
//        else if (typeParameterClass.equals(GroupType.class))
//            deleteGroupType(selectedEntity);
//        else if (typeParameterClass.equals(GroupDOO.class))
//            deleteGroupDOO(selectedEntity);
//        else if (typeParameterClass.equals(Lesson.class))
//            deleteLesson(selectedEntity);
//        else if (typeParameterClass.equals(Position.class))
//            deletePosition(selectedEntity);
//        else if (typeParameterClass.equals(Regime.class))
//            deleteRegime(selectedEntity);, String>(fieldName));
//                    myColumn.setId(fieldName);
//                    mainTable.getColumns().add(myColumn);
//                //}
//    }

    private void deleteStudents(ObservableList<Student> selectedEntity) {
        StudentManager studentManager = new StudentManager();
        selectedEntity.forEach((student) -> {
            studentManager.delete((Student) student);
        });
    }
//
//    private void deleteTeachers(ObservableList<T> selectedEntity) {
//        TeacherManager teacherManager = new TeacherManager();
//        selectedEntity.forEach((teacher) -> {
//            teacherManager.delete((Teacher) teacher);
//        });
//    }
//
//    private void deleteGroupType(ObservableList<T> selectedEntity) {
//        GroupTypeManager gtm = new GroupTypeManager();
//        selectedEntity.forEach((gt) -> {
//            gtm.delete((GroupType) gt);
//        });
//    }
//
//    private void deleteGroupDOO(ObservableList<T> selectedEntity) {
//        GroupDOOManager gtm = new GroupDOOManager();
//        selectedEntity.forEach((gt) -> {
//            gtm.delete((GroupDOO) gt);
//        });
//    }
//
//    private void deleteLesson(ObservableList<T> selectedEntity) {
//        LessonManager gtm = new LessonManager();
//        selectedEntity.forEach((gt) -> {
//            gtm.delete((Lesson) gt);
//        });
//    }
//
//    private void deletePosition(ObservableList<T> selectedEntity) {
//        PositionManager gtm = new PositionManager();
//        selectedEntity.forEach((gt) -> {
//            gtm.delete((Position) gt);
//        });
//    }
//
//    private void deleteRegime(ObservableList<T> selectedEntity) {
//        RegimeManager gtm = new RegimeManager();
//        selectedEntity.forEach((gt) -> {
//            gtm.delete((Regime) gt);
//        });
//    }
//    //---------------------------

    void setBtnImages() {

        Image image = null;
        ImageView ImgView = null;
        try {
            image = new Image(getClass().getClassLoader().getResourceAsStream("images/add.png"));
            ImgView = new ImageView(image);
            ImgView.setFitHeight(30);
            ImgView.setFitWidth(30);
            btnAdd.setGraphic(ImgView);

            image = new Image(getClass().getClassLoader().getResourceAsStream("images/edit.png"));
            ImgView = new ImageView(image);
            ImgView.setFitHeight(30);
            ImgView.setFitWidth(30);
            btnEdit.setGraphic(ImgView);

            image = new Image(getClass().getClassLoader().getResourceAsStream("images/remove.png"));
            ImgView = new ImageView(image);
            ImgView.setFitHeight(30);
            ImgView.setFitWidth(30);
            btnDelete.setGraphic(ImgView);

            image = new Image(getClass().getClassLoader().getResourceAsStream("images/excel_load.png"));
            ImgView = new ImageView(image);
            ImgView.setFitHeight(30);
            ImgView.setFitWidth(30);
            btnLoadFromExcel.setGraphic(ImgView);

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private void setButtonProperties() {
        btnAdd.setTooltip(new Tooltip("Добавить запись"));
        btnEdit.setTooltip(new Tooltip("Редактировать запись"));
        btnDelete.setTooltip(new Tooltip("Удалить записи"));
        btnLoadFromExcel.setTooltip(new Tooltip("Загрузить из Excel"));
    }

    public void initForm() {

        showDicList();

        setPlaceProperty();

        setBtnImages();

        setButtonProperties();

        setColumnProperty();

        setFormEvents();

    }

    private void loadDataInTableView() {

        //setEntityList();
        dataList = getStudentList();
        if (dataList.size() == 0) {
            mainTable.getItems().clear();
            return;
        }

        mainTable.setItems(dataList);

        if (dataList.size() > 0) {
            if (currentEntity == null) {
                mainTable.getSelectionModel().select(0);
                mainTable.getFocusModel().focus(0);
            } else {
                Integer index = getIndexByStudent(currentEntity);
                if (index >= 0) {
                    mainTable.requestFocus();
                    mainTable.getSelectionModel().select(index);
                    mainTable.getFocusModel().focus(index);
                }
            }
        }
        mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // ----- Set entity list -----
//    private void setEntityList() {
//        if (typeParameterClass.equals(Student.class))
//            dataList = (ObservableList<T>) getStudentList();
//        else if (typeParameterClass.equals(Teacher.class))
//            dataList = (ObservableList<T>) getTeacherList();
//        else if (typeParameterClass.equals(GroupType.class))
//            dataList = (ObservableList<T>) getGroupTypeList();
//        else if (typeParameterClass.equals(GroupDOO.class))
//            dataList = (ObservableList<T>) getGroupDOOList();
//        else if (typeParameterClass.equals(Lesson.class))
//            dataList = (ObservableList<T>) getLessonList();
//        else if (typeParameterClass.equals(Position.class))
//            dataList = (ObservableList<T>) getPositionList();
//        else if (typeParameterClass.equals(Regime.class))
//            dataList = (ObservableList<T>) getRegimeList();
//    }

    private ObservableList<Student> getStudentList() {

        StudentManager studentManager = new StudentManager();
        List<Student> stList = studentManager.getAll();
        ObservableList<Student> studentList = FXCollections.observableArrayList(stList);

        for (Student student : stList) {
            student.setGroupName4Visual(student.getGroupDOO().getGroupName());
        }

        return studentList;
    }

//    private ObservableList<Teacher> getTeacherList() {
//
//        TeacherManager teacherManager = new TeacherManager();
//        List<Teacher> tList = teacherManager.getAll();
//        ObservableList<Teacher> teacherList = FXCollections.observableArrayList(tList);
//        /*
//        for (Teacher teacher : tList) {
//            teacherList.add(teacher);
//        }*/
//
//        return teacherList;
//    }
//
//    private ObservableList<GroupType> getGroupTypeList() {
//
//        GroupTypeManager gtManager = new GroupTypeManager();
//        List<GroupType> gtList = gtManager.getAll();
//        ObservableList<GroupType> gtoList = FXCollections.observableArrayList(gtList);
//        /*
//        for (GroupType gt : gtList) {
//            gtoList.add(gt);
//        }*/
//
//        return gtoList;
//    }
//
//    private ObservableList<GroupDOO> getGroupDOOList() {
//
//        GroupDOOManager gtManager = new GroupDOOManager();
//        List<GroupDOO> gtList = gtManager.getAll();
//        ObservableList<GroupDOO> gtoList = FXCollections.observableArrayList(gtList);
//        /*
//        for (GroupDOO gt : gtList) {
//            gtoList.add(gt);
//        }*/
//
//        return gtoList;
//    }
//
//    private ObservableList<Lesson> getLessonList() {
//
//        LessonManager gtManager = new LessonManager();
//        List<Lesson> gtList = gtManager.getAll();
//        ObservableList<Lesson> gtoList = FXCollections.observableArrayList(gtList);
//        /*
//        for (Lesson gt : gtList) {
//            gtoList.add(gt);
//        }*/
//
//        return gtoList;
//    }
//
//    private ObservableList<Position> getPositionList() {
//
//        PositionManager gtManager = new PositionManager();
//        List<Position> gtList = gtManager.getAll();
//        ObservableList<Position> gtoList = FXCollections.observableArrayList(gtList);
//        /*
//        for (Position gt : gtList) {
//            gtoList.add(gt);
//        }*/
//
//        return gtoList;
//    }
//
//    private ObservableList<Regime> getRegimeList() {
//
//        RegimeManager gtManager = new RegimeManager();
//        List<Regime> gtList = gtManager.getAll();
//        ObservableList<Regime> gtoList = FXCollections.observableArrayList(gtList);
//        /*
//        for (Regime gt : gtList) {
//            gtoList.add(gt);
//        }*/
//
//        return gtoList;
//    }
    // --------------------------

    // ----- Get entity index number in entity list -----
//    private Integer getIndexByEntity(T entity) {
//        if (typeParameterClass.equals(Student.class))
//            return getIndexByStudent((Student) entity);
//        else if (typeParameterClass.equals(Teacher.class))
//            return getIndexByTeacher((Teacher) entity);
//        else if (typeParameterClass.equals(GroupType.class))
//            return getIndexByGroupType((GroupType) entity);
//        else if (typeParameterClass.equals(GroupDOO.class))
//            return getIndexByGroupDOO((GroupDOO) entity);
//        else if (typeParameterClass.equals(Lesson.class))
//            return getIndexByLesson((Lesson) entity);
//        else if (typeParameterClass.equals(Position.class))
//            return getIndexByPosition((Position) entity);
//        else if (typeParameterClass.equals(Regime.class))
//            return getIndexByRegime((Regime) entity);
//
//        return -1;
//    }

    private Integer getIndexByStudent(Student entity) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (Student ent : dataList) {
            if (((Student) ent).getId() == entityId) {
                return index;
            }
            index++;
        }
        return -1;
    }

//    private Integer getIndexByTeacher(Teacher entity) {
//        Integer index = 0;
//        Integer entityId = entity.getId();
//        for (T ent : dataList) {
//            if (((Teacher) ent).getId() == entityId) {
//                return index;
//            }
//            index++;
//        }
//        return -1;
//    }
//
//    private Integer getIndexByGroupType(GroupType entity) {
//        Integer index = 0;
//        Integer entityId = entity.getId();
//        for (T ent : dataList) {
//            if (((GroupType) ent).getId() == entityId) {
//                return index;
//            }
//            index++;
//        }
//        return -1;
//    }
//
//    private Integer getIndexByGroupDOO(GroupDOO entity) {
//        Integer index = 0;
//        Integer entityId = entity.getId();
//        for (T ent : dataList) {
//            if (((GroupDOO) ent).getId() == entityId) {
//                return index;
//            }
//            index++;
//        }
//        return -1;
//    }
//
//    private Integer getIndexByLesson(Lesson entity) {
//        Integer index = 0;
//        Integer entityId = entity.getId();
//        for (T ent : dataList) {
//            if (((Lesson) ent).getId() == entityId) {
//                return index;
//            }
//            index++;
//        }
//        return -1;
//    }
//
//    private Integer getIndexByPosition(Position entity) {
//        Integer index = 0;
//        Integer entityId = entity.getId();
//        for (T ent : dataList) {
//            if (((Position) ent).getId() == entityId) {
//                return index;
//            }
//            index++;
//        }
//        return -1;
//    }
//
//    private Integer getIndexByRegime(Regime entity) {
//        Integer index = 0;
//        Integer entityId = entity.getId();
//        for (T ent : dataList) {
//            if (((Regime) ent).getId() == entityId) {
//                return index;
//            }
//            index++;
//        }
//        return -1;
//    }
    // --------------------------------------------------

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
                TableCell<Student, String> cell = new TableCell<>() {
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
                TableCell<Student, String> cell = new TableCell<>() {
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

    // Form events
    private void setFormEvents() {
        mainTable.setRowFactory( tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    showEditCreateWindow(EnumAction.UPDATE);
                }
            });
            return row ;
        });
    }

    private void showEditCreateWindow(EnumAction action) {

        ObservableList<Student> selectedEntity = mainTable.getSelectionModel().getSelectedItems();

        if (action == EnumAction.UPDATE) {
            if (selectedEntity.size() == 0)
                return;
        }

        if (selectedEntity.size() > 0)
            currentEntity = selectedEntity.get(0);

        try {

            Stage studentDialogAction = new Stage();
            studentDialogAction.setTitle("Создание/редактирование");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/StudentEditForm.fxml"));

            AnchorPane studentAction = loader.load();

            studentAction.setId("dictionaryFormAction");

            // Bind controller and main app
            StudentEditFormController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setStage(studentDialogAction);
            controller.setAction(action);
            //controller.setTypeParameterClass(typeParameterClass);
            if (action == EnumAction.UPDATE) {
                controller.setEntity(currentEntity);
            }
            controller.initForm();

            studentDialogAction.setScene(new Scene(studentAction));
            studentDialogAction.initOwner(mainApp.getPrimaryStage());
            studentDialogAction.initModality(Modality.APPLICATION_MODAL);
            studentDialogAction.showAndWait();

            currentEntity = (Student) controller.getEntity();

            System.gc();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showPreloadForm(List<StudentLoadInfo> stList) {

        try {

            Stage preloadFormStage = new Stage();
            preloadFormStage.setTitle("Форма предзагрузки");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/PreloadForm.fxml"));

            AnchorPane preloadForm = loader.load();

            preloadForm.setId("PreloadForm");

            // Bind controller and main app
            PreloadFormController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setStage(preloadFormStage);
            controller.setDataList(stList);
            controller.setMainPane(preloadForm);
            //controller.setTypeParameterClass(getTypeParameterClassForPreloadForm());
            controller.setTypeParameterClass(StudentLoadInfo.class);
            controller.setElementFormProperty();

            preloadFormStage.setScene(new Scene(preloadForm));
            preloadFormStage.initOwner(mainApp.getPrimaryStage());
            preloadFormStage.initModality(Modality.APPLICATION_MODAL);
            preloadFormStage.showAndWait();

            if (controller.mustRefreshData()) {
                loadDataInTableView();
            }

            System.gc();

            //currentStudent = controller.getStudent();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private Class getTypeParameterClassForPreloadForm() {
//        Class<?> act = null;
//        try {
//            act = Class.forName(typeParameterClass.getName() + "LoadInfo");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return act;
//    }

    private List<StudentLoadInfo> getEntityListFromExcel(File file) {
        List<StudentLoadInfo> entityList = new ArrayList<>();
        try {
            entityList = (List<StudentLoadInfo>) ExcelReader.getStudentListFromExcel(file);
//            if (typeParameterClass.equals(Student.class))
//                entityList = (List<T>) ExcelReader.getStudentListFromExcel(file);
//            else if (typeParameterClass.equals(Teacher.class))
//                entityList = (List<T>) ExcelReader.getTeacherListFromExcel(file);
        } catch (IOException exc) {
        exc.printStackTrace();
        } catch (InvalidFormatException exc) {
            exc.printStackTrace();
        }
        return entityList;
    }

}