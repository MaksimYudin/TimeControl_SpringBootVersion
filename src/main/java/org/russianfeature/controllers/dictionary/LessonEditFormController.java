package org.russianfeature.controllers.dictionary;

import com.hibernate.crud.operations.manager.GroupDOOManager;
import com.hibernate.crud.operations.manager.LessonManager;
import com.utils.CommonUtil;
import com.utils.EnumAction;
import com.utils.EnumYesNo;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.russianfeature.Main;
import org.russianfeature.controllers.QuestionYeaNoController;
import org.russianfeature.model.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LessonEditFormController {

    private Main mainApp;
    private Stage stage;
    private Lesson currentEntity;
    private EnumAction action;
    private ObservableList<Lesson> doublesList = FXCollections.observableArrayList();
    //private Class<T> typeParameterClass;
    private Method method;
    private TableView<Lesson> tableDoubles;
    private List<String> columnList = new ArrayList<>();
    private Lesson entity;
    private HashMap<String, Integer> entitiesAttr = new HashMap<>();
    private GroupDOOManager groupManager = new GroupDOOManager();

    private static GroupDOO group;
    private GroupType groupType;
    private Position position;
    private User user;

    private GridPane gridPane4RefFields = new GridPane();

    @FXML
    private AnchorPane dictionaryMainEdit;

    @FXML
    void initialize() {
        //dictionaryMainEdit.setPrefHeight(300);
        //dictionaryMainEdit.setPrefWidth(500);
    }

    public void setAction(EnumAction action) {
        this.action = action;
    }

    public void setStage(Stage stg) {
        stage = stg;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setEntity(Lesson entity) {
        this.currentEntity = entity;
    }

    public Lesson getEntity() {
        return currentEntity;
    }

    public void initForm() {
        drawFormElements();
    }

    // Draw main form elements dinamyc
    public void addButtonCheckDoublesEventHandler(Button btnCheckDublicate) {
        btnCheckDublicate.setOnAction(event -> {
            StringBuilder msgError = new StringBuilder("");
            if (!isFormFieldsValueValid(msgError)) {
                CommonUtil.showNotificationWindow(mainApp, msgError);
                return;
            }

            doublesList = getDoublesLessonList();
            tableDoubles.setItems(doublesList);
            if (doublesList.size() == 0) {
                StringBuilder msg = new StringBuilder("Дублей не найдено");
                CommonUtil.showNotificationWindow(mainApp, msg);
            }
        });
    }

    private void drawFormElements() {

        VBox vbox = new VBox();
        drawFields(vbox);

        Button btnCheckDublicate = new Button("Проверка на дубли");
        addButtonCheckDoublesEventHandler(btnCheckDublicate);

        vbox.getChildren().add(btnCheckDublicate);
        drawTableView(vbox);

        HBox hBoxBtn = new HBox();

        Button btnOK = new Button("Сохранить");
        btnOK.setOnAction(event -> {
            saveEntity();
        });

        Button btnCancel = new Button("Отмена");
        btnCancel.setOnAction(event -> {
            stage.close();
        });

        hBoxBtn.getChildren().addAll(btnOK, btnCancel);
        vbox.getChildren().add(hBoxBtn);
        vbox.setStyle("-fx-border-color: red");

        AnchorPane.setBottomAnchor(vbox, 0.0);
        AnchorPane.setTopAnchor(vbox, 0.0);

        //VBox.setVgrow(btnOK, Priority.ALWAYS);

        dictionaryMainEdit.getChildren().add(vbox);
        dictionaryMainEdit.setMaxHeight(vbox.getPrefHeight());

    }

    // Draw text fields elements
    private void drawFields(VBox vbox) {
        //GridPane myGP = new GridPane();
        gridPane4RefFields.setVgap(5);
        gridPane4RefFields.setHgap(5);
        gridPane4RefFields.minWidthProperty().bind(dictionaryMainEdit.widthProperty());
        gridPane4RefFields.setPadding(new Insets(0, 0, 0, 5));
        gridPane4RefFields.setId("fld_mainList");

        int index = 0;
        // Dynamic add text fields
        for (Field fld : Lesson.class.getDeclaredFields()) {

            String fieldName = fld.getName();
            if (!CommonUtil.isFieldVisible(fieldName))
                continue;

            Label lbl = new Label(CommonUtil.getMapFieldName(fieldName));
            lbl.setMinWidth(dictionaryMainEdit.getPrefWidth() * 0.3);
            //lbl.setText(CommonUtil.getMapFieldName(fieldName));

            gridPane4RefFields.add(lbl, 0, index, 1, 1);


            if (CommonUtil.isSimplePropertieType(fld.getType())) {
                if (!fieldName.contains("Date")) {
                    // Add text fiels
                    addTextField(fieldName, index);
                } else {
                    // Add date picker field
                    addDatePickerField(fieldName, index);
                }
            } else {
                addRefField(fld, index);
            }

            index++;

            // Set field values from DB
            if (currentEntity != null) {

                Node node = gridPane4RefFields.lookup("#" + fieldName);
                if (node == null)
                    continue;

                try {
                    //String fieldName = fld.getName().substring(0, 1).toUpperCase() + fld.getName().substring(1);
                    method = currentEntity.getClass().getMethod("get" + CommonUtil.convertFirstLetterToUpperCase(fieldName));

                    if (!CommonUtil.isRefField(fieldName)) {
                        String fieldValue = (String) method.invoke(currentEntity);

                        if (!fieldName.contains("Date"))
                            ((TextField) node).setText(fieldValue);
                        else {
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                            if (!fieldValue.isEmpty())
                                ((DatePicker) node).setValue(format.parse(fieldValue).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        }
                    } else {
                        setEntityFormValue(gridPane4RefFields, fld, method);
                    }

                }
                catch (NoSuchMethodException exc) { exc.printStackTrace(); }
                catch (IllegalAccessException exc) { exc.printStackTrace(); }
                catch (InvocationTargetException exc) { exc.printStackTrace(); }
                catch (ParseException exc) { exc.printStackTrace(); }

            }
        }
        vbox.getChildren().add(gridPane4RefFields);

    }

    private void drawTableView(VBox vbox) {
        tableDoubles = new TableView<>();
        tableDoubles.setId("tbl_mainData");
        //tableDoubles.setMaxHeight(200);
        for (Field fld : Lesson.class.getDeclaredFields()) {

            String fieldName = fld.getName();
            if (fld.getType().equals(IntegerProperty.class)) {
                TableColumn<Lesson, Integer> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                myColumn.setCellValueFactory(new PropertyValueFactory<Lesson, Integer>(fieldName));
                myColumn.setId("tc_" + fieldName);
                tableDoubles.getColumns().add(myColumn);
            } else {
                TableColumn<Lesson, String> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                myColumn.setCellValueFactory(new PropertyValueFactory<Lesson, String>(fieldName));
                myColumn.setId("tc_" + fieldName);
                tableDoubles.getColumns().add(myColumn);
            }
            columnList.add(fld.getName());
        }
        vbox.getChildren().add(tableDoubles);
    }

    private void initializeDataPickerProperties(DatePicker fldBirthDate) {

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

    private void addTextField(String fieldName, int index) {
        TextField dataField = new TextField();
        dataField.setId(fieldName);
        dataField.setMinWidth(dictionaryMainEdit.getPrefWidth() * 0.7);
        gridPane4RefFields.add(dataField, 1, index, 1, 1);
    }

    private void addDatePickerField(String fieldName, int index) {
        DatePicker dataField = new DatePicker();
        dataField.setId(fieldName);
        dataField.setMinWidth(dictionaryMainEdit.getPrefWidth() * 0.4);
        gridPane4RefFields.add(dataField, 1, index, 1, 1);
        initializeDataPickerProperties(dataField);

        dataField.setOnAction(event -> {
            CommonUtil.setStyleBackgroundColor(dataField, "white");
        });
        GridPane.setHalignment(dataField, HPos.RIGHT);
    }

    private void addRefField(Field field, int index) {

        HBox hBox = new HBox();
        hBox.setId("hbox_refFields");

        TextField dataField = new TextField();
        dataField.setId(CommonUtil.convertFirstLetterToLowerCase(CommonUtil.getLastSplitString(field.getType().getName().split("\\."))));
        dataField.setMinWidth(dictionaryMainEdit.getPrefWidth() * 0.7);

        Button selectBtn = new Button();
        selectBtn.setMaxWidth(12);
        selectBtn.setMaxHeight(10);

        hBox.getChildren().add(dataField);
        hBox.getChildren().add(selectBtn);

        gridPane4RefFields.add(hBox, 1, index, 1, 1);

        // on button press open window to select entity
        addSelectButtonEventHandler(selectBtn, field.getType().getName());
    }

    public void addSelectButtonEventHandler(Button selectBtn, String fieldTypeName) {
//        selectBtn.setOnAction(event -> {
//            try {
//                Stage dialogAction = new Stage();
//                dialogAction.setTitle("Выбор");
//
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(Main.class.getResource("/fxml/dictionary/GroupListForm.fxml"));
//
//                AnchorPane entityAction = loader.load();
//
//                entityAction.setId("dictionaryFormAction");
//
//                // Bind controller and main app
//                GroupListFormController controller = loader.getController();
//                controller.setMainApp(mainApp);
//                controller.setCurrentStage(dialogAction);
//                //controller.setAction(action);
////                try {
////                    controller.setTypeParameterClass(Class.forName(fieldTypeName));
////                } catch (ClassNotFoundException exc) {
////                    System.out.println(exc.toString());
////                }
//                //if (action == EnumAction.UPDATE) {
//                //   controller.setEntity(currentEntity);
//                //}
//                controller.setEditForm(this);
//                controller.initForm();
//
//                dialogAction.setScene(new Scene(entityAction));
//                dialogAction.initOwner(mainApp.getPrimaryStage());
//                dialogAction.initModality(Modality.APPLICATION_MODAL);
//                dialogAction.showAndWait();
//
//                setRefFieldsValueInForm();
//
//            } catch (IOException e) {
//                System.out.printf(e.toString());
//            }
 //       });
    }

    // Validation of form fields
    private boolean isFormFieldsValueValid(StringBuilder msgError) {

        String errorColor = "red";
        String goodColor = "white";
        for (Field fld : Lesson.class.getDeclaredFields()) {

            String fieldName = fld.getName();
            if (fieldName.equals("id"))
                continue;

            if (!CommonUtil.isFieldValueMustBeNonEmpty(fieldName))
                continue;

            Node node = dictionaryMainEdit.lookup("#" + fieldName);
            if (node == null)
                continue;

            boolean isValidField = true;
            if (fieldName.toLowerCase().contains("date")) {
                if (((DatePicker) node).getValue() == null)
                    isValidField = false;
            } else {
                if (((TextField) node).getText().isEmpty())
                    isValidField = false;
            }

            if (!isValidField) {
                msgError.append((msgError.toString().isEmpty() ? "Не заполнены поля: " : ", ") +
                        CommonUtil.getMapFieldName(fieldName));
                CommonUtil.setStyleBackgroundColor(node, errorColor);
            } else
                CommonUtil.setStyleBackgroundColor(node, goodColor);
        }
        return (msgError.toString().isEmpty() ? true : false);
    }

    // ----- Get doubles list for entity
//    private ObservableList<T> getDoublesList() {
//        if (typeParameterClass.equals(Lesson.class))
//            return getDoublesLessonList();
//        if (typeParameterClass.equals(Lesson.class))
//            return getDoublesLessonList();
//        if (typeParameterClass.equals(GroupType.class))
//            return getDoublesGroupTypeList();
//        if (typeParameterClass.equals(GroupDOO.class))
//            return getDoublesGroupDOOList();
//        if (typeParameterClass.equals(Lesson.class))
//            return getDoublesLessonList();
//        if (typeParameterClass.equals(Position.class))
//            return getDoublesPositionList();
//        if (typeParameterClass.equals(Regime.class))
//            return getDoublesRegimeList();
//
//        return null;
//    }

//    private ObservableList<Lesson> getDoublesLessonList() {
//
//
//        Node birthDate = dictionaryMainEdit.lookup("#birthDate");
//        if (birthDate == null)
//            return null;
//
//        Node firstName = dictionaryMainEdit.lookup("#firstName");
//        if (firstName == null)
//            return null;
//
//        Node secondName = dictionaryMainEdit.lookup("#secondName");
//        if (secondName == null)
//            return null;
//
//        Node lastName = dictionaryMainEdit.lookup("#lastName");
//        if (lastName == null)
//            return null;
//
//
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        Date date = java.sql.Date.valueOf(((DatePicker) birthDate).getValue().toString());
//
//        Map<String, String> params = new HashMap<>();
//
//        if (action == EnumAction.UPDATE)
//            params.put("id", currentEntity.getId().toString());
//        else
//            params.put("id", "0");
//
//        params.put("firstName", ((TextField) firstName).getText());
//        params.put("secondName", ((TextField) secondName).getText());
//        params.put("lastName", ((TextField) lastName).getText());
//        params.put("birthDate", format.format(date));
//
//        LessonManager LessonManager = new LessonManager();
//        ObservableList<Lesson> LessonList = FXCollections.observableArrayList();
//        List<Lesson> stList = (List<Lesson>) LessonManager.getDoubles(params);
//
//        for (Lesson Lesson : stList) {
//            LessonList.add(Lesson);
//        }
//
//        return LessonList;
//    }

//    private ObservableList<Lesson> getDoublesLessonList() {
//
//        Node birthDate = dictionaryMainEdit.lookup("#birthDate");
//        if (birthDate == null)
//            return null;
//
//        Node firstName = dictionaryMainEdit.lookup("#firstName");
//        if (firstName == null)
//            return null;
//
//        Node secondName = dictionaryMainEdit.lookup("#secondName");
//        if (secondName == null)
//            return null;
//
//        Node lastName = dictionaryMainEdit.lookup("#lastName");
//        if (lastName == null)
//            return null;
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = java.sql.Date.valueOf(((DatePicker) birthDate).getValue().toString());
//
//        Map<String, String> params = new HashMap<>();
//        params.put("id", (action == EnumAction.UPDATE) ? currentEntity.getId().toString() : "0");
//
//        params.put("firstName", ((TextField) firstName).getText());
//        params.put("secondName", ((TextField) secondName).getText());
//        params.put("lastName", ((TextField) lastName).getText());
//        params.put("birthDate", format.format(date));
//        params.put("checkId", (action == EnumAction.CREATE) ? "f" : "t");
//
//        LessonManager LessonManager = new LessonManager();
//        ObservableList<Lesson> LessonList = FXCollections.observableArrayList();
//        List<Lesson> tList = (List<Lesson>) LessonManager.getDoubles(params);
//
//        for (Lesson Lesson : tList) {
//            LessonList.add(Lesson);
//        }
//
//        return LessonList;
//    }
//
//    private ObservableList<T> getDoublesGroupTypeList() {
//
//        Node groupKindName = dictionaryMainEdit.lookup("#groupKindName");
//        if (groupKindName == null)
//            return null;
//
//        Node groupTypeName = dictionaryMainEdit.lookup("#groupTypeName");
//        if (groupTypeName == null)
//            return null;
//
//        Map<String, String> params = new HashMap<>();
//
//        if (action == EnumAction.UPDATE)
//            params.put("id", ((GroupType) currentEntity).getId().toString());
//        else
//            params.put("id", "0");
//
//        params.put("groupKindName", ((TextField) groupKindName).getText());
//        params.put("groupTypeName", ((TextField) groupTypeName).getText());
//
//        GroupTypeManager gtm = new GroupTypeManager();
//        ObservableList<T> gtoList = FXCollections.observableArrayList();
//        List<T> gtList = (List<T>) gtm.getDoubles(params);
//
//        for (T gt : gtList) {
//            gtoList.add(gt);
//        }
//
//        return gtoList;
//    }
//
//    private ObservableList<T> getDoublesGroupDOOList() {
//
//        Node groupName = dictionaryMainEdit.lookup("#groupName");
//        if (groupName == null)
//            return null;
//
//        Map<String, String> params = new HashMap<>();
//
//        if (action == EnumAction.UPDATE)
//            params.put("id", ((GroupDOO) currentEntity).getId().toString());
//        else
//            params.put("id", "0");
//
//        params.put("groupName", ((TextField) groupName).getText());
//
//        GroupDOOManager gtm = new GroupDOOManager();
//        ObservableList<T> gtoList = FXCollections.observableArrayList();
//        List<T> gtList = (List<T>) gtm.getDoubles(params);
//
//        for (T gt : gtList) {
//            gtoList.add(gt);
//        }
//
//        return gtoList;
//    }
//
    private ObservableList<Lesson> getDoublesLessonList() {

        Node name = dictionaryMainEdit.lookup("#name");
        if (name == null)
            return null;

        Map<String, String> params = new HashMap<>();

        if (action == EnumAction.UPDATE)
            params.put("id", ((Lesson) currentEntity).getId().toString());
        else
            params.put("id", "0");

        params.put("name", ((TextField) name).getText());

        LessonManager gtm = new LessonManager();
        ObservableList<Lesson> gtoList = FXCollections.observableArrayList();
        List<Lesson> gtList = (List<Lesson>) gtm.getDoubles(params);

        for (Lesson gt : gtList) {
            gtoList.add(gt);
        }

        return gtoList;
    }
//
//    private ObservableList<T> getDoublesPositionList() {
//
//        Node name = dictionaryMainEdit.lookup("#name");
//        if (name == null)
//            return null;
//
//        Map<String, String> params = new HashMap<>();
//
//        if (action == EnumAction.UPDATE)
//            params.put("id", ((Position) currentEntity).getId().toString());
//        else
//            params.put("id", "0");
//
//        params.put("name", ((TextField) name).getText());
//
//        PositionManager gtm = new PositionManager();
//        ObservableList<T> gtoList = FXCollections.observableArrayList();
//        List<T> gtList = (List<T>) gtm.getDoubles(params);
//
//        for (T gt : gtList) {
//            gtoList.add(gt);
//        }
//
//        return gtoList;
//    }
//
//    private ObservableList<T> getDoublesRegimeList() {
//
//        Node name = dictionaryMainEdit.lookup("#name");
//        if (name == null)
//            return null;
//
//        Map<String, String> params = new HashMap<>();
//
//        if (action == EnumAction.UPDATE)
//            params.put("id", ((Regime) currentEntity).getId().toString());
//        else
//            params.put("id", "0");
//
//        params.put("name", ((TextField) name).getText());
//
//        RegimeManager gtm = new RegimeManager();
//        ObservableList<T> gtoList = FXCollections.observableArrayList();
//        List<T> gtList = (List<T>) gtm.getDoubles(params);
//
//        for (T gt : gtList) {
//            gtoList.add(gt);
//        }
//
//        return gtoList;
//    }
    // ----- END -----

    void saveEntity() {

        StringBuilder sbMsgText = new StringBuilder();
        if (!isFormFieldsValueValid(sbMsgText)) {
            CommonUtil.showNotificationWindow(mainApp, sbMsgText);
            return;
        }

        doublesList = getDoublesLessonList();
        boolean hasDoubles = (doublesList.size() > 0);
        if (hasDoubles) {
            if (action.equals(EnumAction.CREATE)) {
                StringBuilder msgText = new StringBuilder("Найдены дубли. Ошибка.");
                CommonUtil.showNotificationWindow(mainApp, msgText);
                return;
            }

            String msgText = "Найдены дубли. Продолжить без сравнения?";
            CommonUtil.showYesNoQuestionWindow(mainApp, msgText);
            if (QuestionYeaNoController.answer == EnumYesNo.NO) {
                tableDoubles.setItems(doublesList);
                return;
            }

            Node comment = dictionaryMainEdit.lookup("#comment");
            if (comment != null)
                if (((TextField) comment).getText().equals("")) {
                    sbMsgText.delete(0, sbMsgText.length());
                    sbMsgText.append("Для записи дублированной информации нужно заполнить комментарий.");
                    CommonUtil.showNotificationWindow(mainApp, sbMsgText);
                    return;
                }
        }

        if (action == EnumAction.UPDATE)
            updateLesson();
        else
            createLesson();

        stage.close();
    }

    private void saveLessonEntity() {
        StringBuilder sbMsgText = new StringBuilder();
        if (!isFormFieldsValueValid(sbMsgText)) {
            CommonUtil.showNotificationWindow(mainApp, sbMsgText);
            return;
        }

        doublesList = getDoublesLessonList();
        if (doublesList.size() > 0) {
            String msgText = "Найдены дубли. Продолжить без сравнения?";
            CommonUtil.showYesNoQuestionWindow(mainApp, msgText);
            if (QuestionYeaNoController.answer == EnumYesNo.NO) {
                tableDoubles.setItems(doublesList);
                return;
            }
        }
        if (action == EnumAction.UPDATE)
            updateLesson();
        else
            createLesson();
    }

    // ----- Update entity -----
//    private void updateEntity() {
//        if (typeParameterClass.equals(Lesson.class))
//            updateLesson();
//        else if (typeParameterClass.equals(Lesson.class))
//            updateLesson();
//        else if (typeParameterClass.equals(GroupType.class))
//            updateGroupType();
//        else if (typeParameterClass.equals(GroupDOO.class))
//            updateGroupDOO();
//        else if (typeParameterClass.equals(Lesson.class))
//            updateLesson();
//        else if (typeParameterClass.equals(Position.class))
//            updatePosition();
//        else if (typeParameterClass.equals(Regime.class))
//            updateRegime();
//    }

//    void updateLesson() {
//        LessonManager LessonManager = new LessonManager();
//        setEntityProperty();
//        //LessonManager.save((Lesson) entity);
//        LessonManager.save(currentEntity);
//    }

    void updateLesson() {
        LessonManager LessonManager = new LessonManager();
        setEntityProperty();
        LessonManager.save(currentEntity);
    }
//
//    void updateGroupType() {
//        GroupTypeManager gtManager = new GroupTypeManager();
//        setEntityProperty();
//        gtManager.save((GroupType) currentEntity);
//    }
//
//    void updateGroupDOO() {
//        GroupDOOManager gtManager = new GroupDOOManager();
//        setEntityProperty();
//        gtManager.save((GroupDOO) currentEntity);
//    }
//
//    void updateLesson() {
//        LessonManager gtManager = new LessonManager();
//        setEntityProperty();
//        gtManager.save((Lesson) currentEntity);
//    }
//
//    void updatePosition() {
//        PositionManager gtManager = new PositionManager();
//        setEntityProperty();
//        gtManager.save((Position) currentEntity);
//    }
//
//    void updateRegime() {
//        RegimeManager gtManager = new RegimeManager();
//        setEntityProperty();
//        gtManager.save((Regime) currentEntity);
//    }
    // ----- END -----

    // ----- Create entity -----
//    private void createEtnity() {
//        if (typeParameterClass.equals(Lesson.class))
//            createLesson();
//        else if (typeParameterClass.equals(Lesson.class))
//            createLesson();
//        else if (typeParameterClass.equals(GroupType.class))
//            createGroupType();
//        else if (typeParameterClass.equals(GroupDOO.class))
//            createGroupDOO();
//        else if (typeParameterClass.equals(Lesson.class))
//            createLesson();
//        else if (typeParameterClass.equals(Position.class))
//            createPosition();
//        else if (typeParameterClass.equals(Regime.class))
//            createRegime();
//    }

//    void createLesson() {
//        LessonManager LessonManager = new LessonManager();
//
//        currentEntity = (Lesson) new Lesson();
//        setEntityProperty();
//        LessonManager.save(currentEntity);
//    }

    void createLesson() {
        LessonManager LessonManager = new LessonManager();

        currentEntity = new Lesson();
        setEntityProperty();
        LessonManager.save(currentEntity);
    }
//
//    void createGroupType() {
//        GroupTypeManager gtManager = new GroupTypeManager();
//
//        currentEntity = (T) new GroupType();
//        setEntityProperty();
//        gtManager.save((GroupType) currentEntity);
//    }
//
//    void createGroupDOO() {
//        GroupDOOManager gtManager = new GroupDOOManager();
//
//
//        UserManager um = new UserManager();
//        User user = um.findById(2);
//        GroupDOO grp = new GroupDOO();
//        //grp.setCreateUser(user);
//        currentEntity = (T) grp;
//
//        //currentEntity = (T) new GroupDOO();
//        setEntityProperty();
//        gtManager.save((GroupDOO) currentEntity);
//    }
//
//    void createLesson() {
//        LessonManager gtManager = new LessonManager();
//
//        currentEntity = (T) new Lesson();
//        setEntityProperty();
//        gtManager.save((Lesson) currentEntity);
//    }
//
//    void createPosition() {
//        PositionManager gtManager = new PositionManager();
//
//        currentEntity = (T) new Position();
//        setEntityProperty();
//        gtManager.save((Position) currentEntity);
//    }
//
//    void createRegime() {
//        RegimeManager gtManager = new RegimeManager();
//
//        currentEntity = (T) new Regime();
//        setEntityProperty();
//        gtManager.save((Regime) currentEntity);
//    }
    // ----- END -----

    void setEntityProperty() {

        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");

        Class[] args = new Class[1];
        args[0] = String.class;
        //DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Field fld : Lesson.class.getDeclaredFields()) {

            String fieldName = fld.getName();
            if (!CommonUtil.isFieldVisible(fieldName))
                continue;

            Node node = dictionaryMainEdit.lookup("#" + fieldName);
            if (node == null)
                continue;

            if (fieldName.toLowerCase().contains("date")) {
                if (((DatePicker) node).getValue() != null) {
                    try {
                        //DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
                        Date date = sourceFormat.parse(((DatePicker) node).getEditor().getText());
                        //entity.setBirthDate(sourceFormat.format(date));
                        method = currentEntity.getClass().getMethod("set" + CommonUtil.convertFirstLetterToUpperCase(fieldName), args);
                        method.invoke(currentEntity, sourceFormat.format(date));
                    }
                    catch (ParseException e) {e.printStackTrace();}
                    catch (NoSuchMethodException exc) { exc.printStackTrace(); }
                    catch (IllegalAccessException exc) { exc.printStackTrace(); }
                    catch (InvocationTargetException exc) { exc.printStackTrace(); }
                }
            } else {

                    try {
                        if (CommonUtil.isRefField(fieldName)) {
                            args[0] = fld.getType();
                        }
                        method = currentEntity.getClass().getMethod("set" + CommonUtil.convertFirstLetterToUpperCase(fieldName), args);
                        setAttributeValueForMethod(fld, method, node);
                        //method.invoke(currentEntity, ((TextField) node).getText());
                    }
                    catch (NoSuchMethodException exc) { exc.printStackTrace(); }
                    //catch (IllegalAccessException exc) { exc.printStackTrace(); }
                    //catch (InvocationTargetException exc) { exc.printStackTrace(); }
            }
        }

        //DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
        if (action == EnumAction.CREATE) {
            Date dt = new Date();
            String dateToDB = sourceFormat.format(dt);
            try {
                method = currentEntity.getClass().getMethod("setCreateDate", args);
                method.invoke(currentEntity, dateToDB);
            }
            catch (NoSuchMethodException exc) { exc.printStackTrace(); }
            catch (IllegalAccessException exc) { exc.printStackTrace(); }
            catch (InvocationTargetException exc) { exc.printStackTrace(); }

            try {
                method = currentEntity.getClass().getMethod("setLastEditDate", args);
                method.invoke(currentEntity, dateToDB);
            }
            catch (NoSuchMethodException exc) { exc.printStackTrace(); }
            catch (IllegalAccessException exc) { exc.printStackTrace(); }
            catch (InvocationTargetException exc) { exc.printStackTrace(); }
        }
    }

    private void setAttributeValueForMethod(Field fld, Method method, Node node) {
        try {
            if (fld.getType().equals(GroupDOO.class)) {
                    method.invoke(currentEntity, group);
            } else {
                method.invoke(currentEntity, ((TextField) node).getText());
            }
        }
        catch (IllegalAccessException exc) { exc.printStackTrace(); }
        catch (InvocationTargetException exc) { exc.printStackTrace(); }

    }

    private void initializeDataPickerProperties1(DatePicker field) {

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
        field.setConverter(converter);
        field.setPromptText("Set date");

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
        field.setDayCellFactory(dayCellFactory);
        //fldDataPicker.setEditable(false);

    }

    public HashMap<String, Integer> getEntitiesAttr() {
        return entitiesAttr;
    }

    private void setRefFieldsValueInForm() {
        for(Map.Entry<String, Integer> entry : entitiesAttr.entrySet()) {
            if (entry.getKey().equals(GroupDOO.class.toString()))
                setGroupDooFormValue(entry.getValue());
        }
    }

    private void setGroupDooFormValue(Integer id) {
        //GroupDOOManager groupManager = new GroupDOOManager();
        if (group == null || !group.getId().equals(id))
            group = groupManager.findById((id));

        if (!group.equals(null)) {
            Node node = gridPane4RefFields.lookup("#" + CommonUtil.convertFirstLetterToLowerCase(CommonUtil.getLastSplitString(GroupDOO.class.getName().split("\\."))));
            if (node != null) {
                TextField groupField = (TextField) node;
                groupField.setText(group.getGroupName());
            }
        }
    }

    private void setEntityFormValue(GridPane myGP, Field field, Method method) {
        try {
            if (field.getType().equals(GroupDOO.class)) {
                group = (GroupDOO) method.invoke(currentEntity);
                setGroupDooFormValue(group.getId());
            }
        }
        catch (IllegalAccessException exc) {exc.printStackTrace();}
        catch (InvocationTargetException exc) {exc.printStackTrace();}
    }

}

