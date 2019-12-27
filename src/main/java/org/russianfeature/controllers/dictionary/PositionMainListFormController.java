package org.russianfeature.controllers.dictionary;

import com.hibernate.crud.operations.manager.PositionManager;
import com.utils.CommonUtil;
import com.utils.Config;
import com.utils.EnumAction;
import com.utils.EnumYesNo;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.russianfeature.Main;
import org.russianfeature.controllers.QuestionYeaNoController;
import org.russianfeature.model.Position;
import org.russianfeature.model.Position;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PositionMainListFormController {

    private Main mainApp;
    private ObservableList<Position> dataList = FXCollections.observableArrayList();
    private Position currentEntity;
    private Stage currentStage;
    //private Class<T> typeParameterClass;
    private List<String> columnList = new ArrayList<>();

    @FXML
    private AnchorPane dictionaryMain;

    @FXML
    private TableView<Position> mainTable;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLoadFromExcel;

    @FXML
    private TableColumn<Position, String> col1;

    @FXML
    void btnAddOnClick(ActionEvent event) {

        showEditCreateWindow(EnumAction.CREATE);
        loadDataInTableView();

    }

    @FXML
    void btnDeleteOnClick(ActionEvent event) {

        ObservableList<Position> selectedEntity = mainTable.getSelectionModel().getSelectedItems();
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

        deletePositions(selectedEntity);

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

//        FileChooser fileChooser = new FileChooser();
//
//        fileChooser.setTitle("Выберите файл для загрузки");
//        fileChooser.setInitialDirectory(
//                new File(System.getProperty("user.home"))
//        );
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"),
//                new FileChooser.ExtensionFilter("XLS", "*.xls")
//        );
//
//        File file = fileChooser.showOpenDialog(currentStage);
//        if (file != null) {
//            String msgText = "Вы действительно хотите выполнить загрузку?";
//            CommonUtil.showYesNoQuestionWindow(mainApp, msgText);
//            if (QuestionYeaNoController.answer == EnumYesNo.NO)
//                return;
//
//            //List<T> entityList = FXCollections.observableArrayList();
//            List<PositionLoadInfo> entityList = getEntityListFromExcel(file);
//
//            showPreloadForm(entityList);
//        }
//
//        System.gc();

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

            for (Field fld : Position.class.getDeclaredFields()) {

                String fieldName = fld.getName();
                if (!CommonUtil.isFormListFieldVisible(fieldName))
                    continue;

                //if (fld.getType().equals(IntegerProperty.class)) {
//                    TableColumn<T, Integer> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
//                    myColumn.setCellValueFactory(new PropertyValueFactory<T, Integer>(fieldName));
//                    myColumn.setId(fieldName);
//                    mainTable.getColumns().add(myColumn);
//                } else {
                    TableColumn<Position, String> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                    myColumn.setCellValueFactory(new PropertyValueFactory<Position, String>(fieldName));
                    myColumn.setId(fieldName);
                    mainTable.getColumns().add(myColumn);
                //}
                columnList.add(fieldName);
            }

        loadDataInTableView();

    }

    private void deletePositions(ObservableList<Position> selectedEntity) {
        PositionManager gtm = new PositionManager();
        selectedEntity.forEach((gt) -> {
            gtm.delete(gt);
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
        dataList = getPositionList();
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
                Integer index = getIndexByPosition(currentEntity);
                if (index >= 0) {
                    mainTable.requestFocus();
                    mainTable.getSelectionModel().select(index);
                    mainTable.getFocusModel().focus(index);
                }
            }
        }
        mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private ObservableList<Position> getPositionList() {

        PositionManager gtManager = new PositionManager();
        List<Position> gtList = gtManager.getAll();
        ObservableList<Position> gtoList = FXCollections.observableArrayList(gtList);
        /*
        for (Position gt : gtList) {
            gtoList.add(gt);
        }*/

        return gtoList;
    }

    private Integer getIndexByPosition(Position entity) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (Position ent : dataList) {
            if (ent.getId() == entityId) {
                return index;
            }
            index++;
        }
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
                TableCell<Position, String> cell = new TableCell<>() {
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
                TableCell<Position, String> cell = new TableCell<>() {
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
            TableRow<Position> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    showEditCreateWindow(EnumAction.UPDATE);
                }
            });
            return row ;
        });
    }

    private void showEditCreateWindow(EnumAction action) {

        ObservableList<Position> selectedEntity = mainTable.getSelectionModel().getSelectedItems();

        if (action == EnumAction.UPDATE) {
            if (selectedEntity.size() == 0)
                return;
        }

        if (selectedEntity.size() > 0)
            currentEntity = selectedEntity.get(0);

        try {

            Stage PositionDialogAction = new Stage();
            PositionDialogAction.setTitle("Создание/редактирование");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/PositionEditForm.fxml"));

            AnchorPane PositionAction = loader.load();

            PositionAction.setId("dictionaryFormAction");

            // Bind controller and main app
            PositionEditFormController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setStage(PositionDialogAction);
            controller.setAction(action);
            //controller.setTypeParameterClass(typeParameterClass);
            if (action == EnumAction.UPDATE) {
                controller.setEntity(currentEntity);
            }
            controller.initForm();

            PositionDialogAction.setScene(new Scene(PositionAction));
            PositionDialogAction.initOwner(mainApp.getPrimaryStage());
            PositionDialogAction.initModality(Modality.APPLICATION_MODAL);
            PositionDialogAction.showAndWait();

            currentEntity = controller.getEntity();

            System.gc();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}