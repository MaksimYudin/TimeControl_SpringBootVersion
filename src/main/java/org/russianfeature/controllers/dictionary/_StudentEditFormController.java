package org.russianfeature.controllers.dictionary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.hibernate.crud.operations.manager.StudentManager;
import com.utils.EnumYesNo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.russianfeature.Main;
import com.utils.EnumAction;
import org.russianfeature.controllers.QuestionYeaNoController;
import org.russianfeature.model.Student;
import com.utils.CommonUtil;

public class _StudentEditFormController {

    private Main mainApp;
    private Stage stage;
    private Student student;
    private EnumAction action;
    private ObservableList<Student> doublesStudentList = FXCollections.observableArrayList();

    /*
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    */

    @FXML
    private TextField fldID;

    @FXML
    private TextField fldFirstName;

    @FXML
    private TextField fldSecondName;

    @FXML
    private TextField fldLastName;

    @FXML
    private TextArea taComment;

    @FXML
    private DatePicker fldBirthDate;

    @FXML
    private TableView<Student> tableStudentDoubles;

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
    private Button btnCheckDoubles;

    @FXML
    void btnCancelOnClick(ActionEvent event) {
        stage.close();
    }

    @FXML
    void btnOKOnClick(ActionEvent event) {

        StringBuilder sbMsgText = new StringBuilder();
        if (!isFormFieldsValueValid(sbMsgText)) {
            CommonUtil.showNotificationWindow(mainApp, sbMsgText);
            return;
        }

        doublesStudentList = getDoublesStudentList();
        if (doublesStudentList.size() > 0) {
            String msgText = "Найдены дубли. Продолжить без сравнения?";
            CommonUtil.showYesNoQuestionWindow(mainApp, msgText);
            if (QuestionYeaNoController.answer == EnumYesNo.NO) {
                showDoublesStudents();
                return;
            }
        }
        if (action == EnumAction.UPDATE)
            updateStudent();
        else
            createStudent();

        stage.close();
    }

    @FXML
    void initialize() {

        initializeDataPickerProperties();

        //showStudents();
    }

    @FXML
    void firstNameOnKeyPressed(KeyEvent event) {
        CommonUtil.setStyleBackgroundColor(fldFirstName, "white");
    }

    @FXML
    void secondNameOnKeyPressed(KeyEvent event) {
        CommonUtil.setStyleBackgroundColor(fldSecondName, "white");
    }

    @FXML
    void lastNameOnKeyPressed(KeyEvent event) {
        CommonUtil.setStyleBackgroundColor(fldLastName, "white");
    }

    @FXML
    void bdOnChange(InputMethodEvent event) {
        //CommonUtil.setStyleBackgroundColor(fldBirthDate, "white");
        //fldBirthDate.setStyle("-fx-control-inner-background: white");
    }

    @FXML
    void bdOnAction(ActionEvent event) {
        CommonUtil.setStyleBackgroundColor(fldBirthDate, "white");
        //fldBirthDate.setStyle("-fx-control-inner-background: white");
    }

    @FXML
    void firstNameOnEnter(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        setTextFileldCorrectValue(event, textField);
    }

    @FXML
    void lastNameOnEnter(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        setTextFileldCorrectValue(event, textField);
    }

    @FXML
    void secondNameOnEnter(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        setTextFileldCorrectValue(event, textField);
    }

    void updateStudent() {
        StudentManager studentManager = new StudentManager();
        setStudentProperty(student);
        studentManager.save(student);
    }

    @FXML
    void btnCheckDoublesOnClick(ActionEvent event) {
        StringBuilder msgError = new StringBuilder("");
        if (!isFormFieldsValueValid(msgError)) {
            CommonUtil.showNotificationWindow(mainApp, msgError);
            return;
        }

        doublesStudentList = getDoublesStudentList();
        showDoublesStudents();
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAction(EnumAction action) {
        this.action = action;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stg) {
        stage = stg;
    }

    public Student getStudent() {
        return student;
    }

    void createStudent() {
        StudentManager studentManager = new StudentManager();
        Student newStudent = new Student();
        setStudentProperty(newStudent);
        studentManager.save(newStudent);
        student = newStudent;
    }

    // Set entity property value
    void setStudentProperty(Student entity) {
        entity.setFirstName(fldFirstName.getText());
        entity.setSecondName(fldSecondName.getText());
        entity.setLastName(fldLastName.getText());
        entity.setComment(taComment.getText());
        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
        if (action == EnumAction.CREATE) {
            Date dt = new Date();
            String dateToDB = sourceFormat.format(dt);
            entity.setCreateDate(dateToDB);
        }

        if (fldBirthDate.getValue() != null) {
            try {
                Date date = sourceFormat.parse(fldBirthDate.getEditor().getText());
                entity.setBirthDate(sourceFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    void setTextFileldCorrectValue(ActionEvent event, TextField textField) {
        TextField firstName = (TextField) event.getSource();
        String text = firstName.getText();
        firstName.setText(CommonUtil.convertFirstLetterToUpperCase(text));
    }

    // Set fields value on form
    public void setFieldValues() {
        fldID.setText(String.valueOf(student.getId()));
        fldFirstName.setText(student.getFirstName());
        fldSecondName.setText(student.getSecondName());
        fldLastName.setText(student.getLastName());
        taComment.setText(student.getComment());

        if (student.getBirthDate() != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                fldBirthDate.setValue(format.parse(student.getBirthDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void showDoublesStudents() {

        //Binding model and view elements
        id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        secondName.setCellValueFactory(new PropertyValueFactory<Student, String>("secondName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        createDate.setCellValueFactory(new PropertyValueFactory<Student, String>("createDate"));
        birthDate.setCellValueFactory(new PropertyValueFactory<Student, String>("birthDate"));
        comment.setCellValueFactory(new PropertyValueFactory<Student, String>("comment"));

        tableStudentDoubles.setItems(doublesStudentList);

    }

    private ObservableList<Student> getDoublesStudentList() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(fldBirthDate.getValue().toString());

        Map<String, String> params = new HashMap<>();
        params.put("id", fldID.getText());
        params.put("firstName", fldFirstName.getText());
        params.put("secondName", fldSecondName.getText());
        params.put("lastName", fldLastName.getText());
        params.put("birthDate", format.format(date));
        params.put("checkId", (action == EnumAction.CREATE) ? "f" : "t");

        StudentManager studentManager = new StudentManager();
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        List<Student> stList = studentManager.getDoubles(params);

        for (Student student : stList) {
            studentList.add(student);
        }

        return studentList;
    }

    private void initializeDataPickerProperties() {

        Locale.setDefault(new Locale("ru"));
        // Converter
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        fldBirthDate.setConverter(converter);
        fldBirthDate.setPromptText("Set date");

        // Create a day cell factory
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>()
        {
            public DateCell call(final DatePicker datePicker)
            {
                return new DateCell()
                {
                    @Override
                    public void updateItem(LocalDate item, boolean empty)
                    {
                        // Must call super
                        super.updateItem(item, empty);

                        // Show Weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                        {
                            this.setTextFill(Color.BLUE);
                        }

                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now()))
                        {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        // Set the day cell factory to the DatePicker
        fldBirthDate.setDayCellFactory(dayCellFactory);
        //fldDataPicker.setEditable(false);

    }

    // Validation of form fields
    private boolean isFormFieldsValueValid(StringBuilder msgError) {

        String errorColor = "red";
        String goodColor = "white";
        if (fldFirstName.getText().isEmpty()) {
            msgError.append("Не заполнена фамилия");
            CommonUtil.setStyleBackgroundColor(fldFirstName, errorColor);
        } else
            CommonUtil.setStyleBackgroundColor(fldFirstName, goodColor);

        if (fldSecondName.getText().isEmpty()) {
            msgError.append((msgError.toString().isEmpty() ? "" : ", ") + "Не заполнено имя");
            CommonUtil.setStyleBackgroundColor(fldSecondName, errorColor);
        } else
            CommonUtil.setStyleBackgroundColor(fldSecondName, goodColor);

        if (fldLastName.getText().isEmpty()) {
            msgError.append((msgError.toString().isEmpty() ? "" : ", ") + "Не заполнено отчество");
            CommonUtil.setStyleBackgroundColor(fldLastName, errorColor);
        } else
            CommonUtil.setStyleBackgroundColor(fldLastName, goodColor);

        if (fldBirthDate.getValue() == null) {
            msgError.append((msgError.toString().isEmpty() ? "" : ", ") + "Не заполнено дата рождения");
            CommonUtil.setStyleBackgroundColor(fldBirthDate, errorColor);
        } else
            CommonUtil.setStyleBackgroundColor(fldBirthDate, goodColor);

        return (msgError.toString().isEmpty() ? true : false);
    }
}

