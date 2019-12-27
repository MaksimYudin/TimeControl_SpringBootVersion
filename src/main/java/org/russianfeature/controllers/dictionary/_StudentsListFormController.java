package org.russianfeature.controllers.dictionary;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import com.hibernate.crud.operations.manager.StudentManager;
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
import org.russianfeature.model.Student;
import org.russianfeature.model.StudentLoadInfo;

public class _StudentsListFormController<T> {

    private Main mainApp;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private Student currentStudent;
    private Stage currentStage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane dictionaryStudentsMain;

    @FXML
    private TableView<Student> tableStudents;

    @FXML
    private TableColumn<Student, Integer> id;

    @FXML
    private TableColumn<Student, String> firstName;

    @FXML
    private TableColumn<Student, String> secondName;

    @FXML
    private TableColumn<Student, String> lastName;

    @FXML
    private TableColumn<Student, String> createDate;

    @FXML
    private TableColumn<Student, String> birthDate;

    @FXML
    private TableColumn<Student, String> comment;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLoadFromExcel;

    @FXML
    void initialize() {
        showStudents();

        setColumnProperty();

        setBtnImages();

        setFormEvents();

        setButtonProperties();

        System.gc();
    }

    @FXML
    void btnAddOnClick(ActionEvent event) {
        showEditCreateWindow(EnumAction.CREATE);
        loadDataInTableView();
        System.gc();
    }

    @FXML
    void btnDeleteOnClick(ActionEvent event) {

        ObservableList<Student> selectedStudent = tableStudents.getSelectionModel().getSelectedItems();
        StringBuilder msgText = new StringBuilder();
        if (selectedStudent.size() > 1) {
            msgText.append("Вы действительно хотите удалить записи?");
        } else {
            msgText.append("Вы действительно хотите удалить запись?");
        }

        CommonUtil.showYesNoQuestionWindow(mainApp, msgText.toString());
        if (QuestionYeaNoController.answer == EnumYesNo.NO)
            return;

        // Check relationships
        if (selectedStudent.size() == 0) return;
        StudentManager studentManager = new StudentManager();
        selectedStudent.forEach((student) -> {
            studentManager.delete(student);
        });

        loadDataInTableView();
    }

    @FXML
    void btnEditOnClick(ActionEvent event) {
        showEditCreateWindow(EnumAction.UPDATE);
        System.gc();
    }

    @FXML
    void btnLoadFromExcelOnClick(ActionEvent event) {
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

            List<StudentLoadInfo> studentList = FXCollections.observableArrayList();
            try {
                studentList = ExcelReader.getStudentListFromExcel(file);
                int a = 1;
            } catch (IOException exc) {
                exc.printStackTrace();
            } catch (InvalidFormatException exc) {
                exc.printStackTrace();
            }

            showPreloadForm(studentList);
        }
        System.gc();
    }

    void showEditCreateWindow(EnumAction action) {

        ObservableList<Student> selectedStudent = tableStudents.getSelectionModel().getSelectedItems();
        if (selectedStudent.size() == 0
                && action == EnumAction.UPDATE)
            return;
        Student student = selectedStudent.get(0);

        try {

            Stage studentDialogAction = new Stage();
            studentDialogAction.setTitle("Создание/редактирование");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/_StudentEditForm.fxml"));

            AnchorPane studentAction = loader.load();

            studentAction.setId("dictionaryStudentsFormAction");

            // Bind controller and main app
            _StudentEditFormController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setStage(studentDialogAction);
            controller.setAction(action);
            if (action == EnumAction.UPDATE) {
                controller.setStudent(student);
                controller.setFieldValues();
            }

            studentDialogAction.setScene(new Scene(studentAction));
            studentDialogAction.initOwner(mainApp.getPrimaryStage());
            studentDialogAction.initModality(Modality.APPLICATION_MODAL);
            studentDialogAction.showAndWait();

            currentStudent = controller.getStudent();

            System.gc();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showPreloadForm(List<StudentLoadInfo> stList) {

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
            controller.setElementFormProperty();

            preloadFormStage.setScene(new Scene(preloadForm));
            preloadFormStage.initOwner(mainApp.getPrimaryStage());
            preloadFormStage.initModality(Modality.APPLICATION_MODAL);
            preloadFormStage.showAndWait();

            if (controller.mustRefreshData()) {
                loadDataInTableView();
            }

            //currentStudent = controller.getStudent();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPlaceProperty() {
        AnchorPane.setBottomAnchor(dictionaryStudentsMain, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setTopAnchor(dictionaryStudentsMain, (double) Config.TOP_BOTTOM_PANE_HEIGHT);
        AnchorPane.setLeftAnchor(dictionaryStudentsMain, (double) Config.BTN_MENU_WIDTH + 20);
        AnchorPane.setRightAnchor(dictionaryStudentsMain, 0.0);

        //bindLayoutsBlocks();

    }

    void showStudents() {

        //Binding model and view elements
        id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        secondName.setCellValueFactory(new PropertyValueFactory<Student, String>("secondName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        createDate.setCellValueFactory(new PropertyValueFactory<Student, String>("createDate"));
        birthDate.setCellValueFactory(new PropertyValueFactory<Student, String>("birthDate"));
        comment.setCellValueFactory(new PropertyValueFactory<Student, String>("comment"));

        loadDataInTableView();

    }

    void setColumnProperty() {
        tableStudents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        firstName.prefWidthProperty().bind(dictionaryStudentsMain.widthProperty().divide(5.0));
        secondName.prefWidthProperty().bind(dictionaryStudentsMain.widthProperty().divide(5.0));
        lastName.prefWidthProperty().bind(dictionaryStudentsMain.widthProperty().divide(5.0));
        createDate.setPrefWidth(80);
        birthDate.setPrefWidth(80);
        id.setVisible(false);

        // set column createDate date format
        createDate.setCellFactory(column -> {
            TableCell<Student, String> cell = new TableCell<>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
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

        // set column birthDate date format
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

    // Form events
    private void setFormEvents() {
        tableStudents.setRowFactory( tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    showEditCreateWindow(EnumAction.UPDATE);
                }
            });
            return row ;
        });
    }

    private ObservableList<Student> getStudentList() {

        StudentManager studentManager = new StudentManager();
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        List<Student> stList = studentManager.getAll();

        for (Student student : stList) {
            studentList.add(student);
        }

        return studentList;
    }

    private void loadDataInTableView() {
        studentList = getStudentList();
        tableStudents.setItems(getStudentList());
        if (currentStudent == null
                && studentList.size() > 0) {
            tableStudents.getSelectionModel().select(0);
            tableStudents.getFocusModel().focus(0);
        } else {
            Integer index = getIndexByStudent(currentStudent);
            if (index >= 0) {
                tableStudents.requestFocus();
                tableStudents.getSelectionModel().select(index);
                tableStudents.getFocusModel().focus(index);
            }
        }
        tableStudents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private Integer getIndexByStudent(Student student) {
        Integer index = 0;
        Integer studentId = student.getId();
        for (Student st : studentList) {
            if (st.getId() == studentId) {
                return index;
            }
            index++;
        }
        return -1;
    }

}