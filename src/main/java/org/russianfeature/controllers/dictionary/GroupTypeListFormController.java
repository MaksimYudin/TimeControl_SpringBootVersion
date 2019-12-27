package org.russianfeature.controllers.dictionary;

import com.hibernate.crud.operations.manager.GroupDOOManager;
import com.hibernate.crud.operations.manager.GroupTypeManager;
import com.utils.CommonUtil;
import com.utils.Config;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.russianfeature.Main;
import org.russianfeature.model.GroupDOO;
import org.russianfeature.model.GroupType;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GroupTypeListFormController {

    private Main mainApp;
    private ObservableList<GroupType> dataList = FXCollections.observableArrayList();
    private GroupType currentEntity;
    private Stage currentStage;
    //private Class<T> typeParameterClass;
    private List<String> columnList = new ArrayList<>();
    private GroupEditFormController editForm;

    @FXML
    private AnchorPane dictionaryMain;

    @FXML
    private TableView<GroupType> mainTable;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLoadFromExcel;

    @FXML
    private TableColumn<GroupDOO, String> col1;

    @FXML
    void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setEditForm(GroupEditFormController editForm) {
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

            for (Field fld : GroupType.class.getDeclaredFields()) {

                String fieldName = fld.getName();
                if (!CommonUtil.isFormListFieldVisible(fieldName))
                    continue;

                if (fld.getClass().equals(IntegerProperty.class)) {
                    TableColumn<GroupType, Integer> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                    myColumn.setCellValueFactory(new PropertyValueFactory<GroupType, Integer>(fieldName));
                    myColumn.setId(fieldName);
                    mainTable.getColumns().add(myColumn);
                } else {
                    TableColumn<GroupType, String> myColumn = new TableColumn<>(CommonUtil.getMapFieldName(fieldName));
                    myColumn.setCellValueFactory(new PropertyValueFactory<GroupType, String>(fieldName));
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
        dataList = (ObservableList<GroupType>) GroupTypeManager.getGroupTypeObservableList();

    }

    private void setFormEvents() {
        mainTable.setRowFactory( tv -> {
            TableRow<GroupType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    GroupType item = row.getItem();
                    int a = 0;
                    HashMap<String, Integer> eAttr = editForm.getEntitiesAttr();
                    eAttr.put(GroupType.class.toString(), item.getId());

                    currentStage.close();
                }
            });
            return row ;
        });
    }


    // ----- Get entity index number in entity list -----
    private Integer getIndexByEntity(GroupType entity) {
        return CommonUtil.getIndexByGroupType(entity, (ObservableList<GroupType>) dataList);

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
                TableCell<GroupDOO, String> cell = new TableCell<>() {
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
                TableCell<GroupDOO, String> cell = new TableCell<>() {
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