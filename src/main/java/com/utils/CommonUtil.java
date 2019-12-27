package com.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;
import org.russianfeature.Main;
import org.russianfeature.controllers.NotificationFormController;
import org.russianfeature.controllers.QuestionYeaNoController;
import org.russianfeature.model.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.TreeMap;
import java.util.Timer;

public class CommonUtil {
    public static String convertFirstLetterToUpperCase(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static String convertFirstLetterToLowerCase(String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    public static void showYesNoQuestionWindow(Main mainApp, String msgText) {

        try {

            Stage yesNoDialog = new Stage();
            //yesNoDialog.setTitle(msgText);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/QuestionYesNo.fxml"));

            AnchorPane studentAction = loader.load();

            Label lbl = (Label) studentAction.lookup("#msgLabel");
            if (lbl != null)
                lbl.setText(msgText);

            studentAction.setId("QuestionYesNo");

            // Bind controller and main app
            QuestionYeaNoController controller = loader.getController();
            controller.setStage(yesNoDialog);
            controller.setMainApp(mainApp);

            yesNoDialog.setScene(new Scene(studentAction));
            yesNoDialog.initOwner(mainApp.getPrimaryStage());
            yesNoDialog.initModality(Modality.APPLICATION_MODAL);
            yesNoDialog.showAndWait();

            //tableStudents.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNotificationWindow(Main mainApp, StringBuilder msgText) {

        try {

            Stage notification = new Stage();
            //yesNoDialog.setTitle(msgText);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/NotificationForm.fxml"));

            AnchorPane studentAction = loader.load();

            Label lbl = (Label) studentAction.lookup("#msgLabel");
            if (lbl != null)
                lbl.setText(msgText.toString());

            studentAction.setId("NotificationForm");

            // Bind controller and main app
            NotificationFormController controller = loader.getController();
            controller.setStage(notification);
            controller.setMainApp(mainApp);

            notification.setScene(new Scene(studentAction));
            notification.initOwner(mainApp.getPrimaryStage());
            notification.initModality(Modality.APPLICATION_MODAL);
            notification.showAndWait();

            //tableStudents.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setStyleBackgroundColor(Node node, String color) {
        node.setStyle("-fx-background-color: " + color);
    }

    public static String getMapFieldName(String fieldName) {
        TreeMap<String, String> mapFields = Config.getFieldMapName();
        String correctName = mapFields.get(fieldName);

        return (correctName == null ? fieldName : correctName);
    }

    public static Boolean isFieldValueMustBeNonEmpty(String fieldName) {
        TreeMap<String, String> fields = Config.getFieldNonEmpty();

        return (fields.get(fieldName) == null ? false : true);
    }

    public static Boolean isFieldVisible(String fieldName) {
        TreeMap<String, String> fields = Config.getVisibleFields();

        return (fields.get(fieldName) == null ? false : true);
    }

    public static Boolean isFormListFieldVisible(String fieldName) {
        TreeMap<String, String> fields = Config.getVisibleFormListFields();

        return (fields.get(fieldName) == null ? false : true);
    }

    public static Boolean isFormListGroupFieldVisible(String fieldName) {
        TreeMap<String, Boolean> fields = Config.getVisibleFormListFields4Groups();

        return (fields.get(fieldName) == null ? false : true);
    }

    public static Boolean isFormListGroupFieldRef(String fieldName) {
        TreeMap<String, Boolean> fields = Config.getVisibleFormListFields4Groups();

        return (fields.get(fieldName) == null ? false : fields.get(fieldName));
    }

    public static Boolean isLoadFromExcelButtonHandled(Class clazz) {
        if (clazz.equals(Student.class)
            || clazz.equals(Teacher.class))
            return true;
        return false;
    }

    public static Boolean isRefField(String fieldName) {
        TreeMap<String, String> fields = Config.getRefFields();

        return (fields.get(fieldName) == null ? false : true);
    }

    public static String getLastSplitString(String[] splitArray) {

        Integer arraySize = splitArray.length;
        String lastString = "";
        if (arraySize >= 1)
            lastString = splitArray[arraySize - 1];

        return lastString;

    }

    public static Integer getIndexByStudent(Student entity, ObservableList<Student> dataList) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (Student ent : dataList) {
            if (ent.getId() == entityId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static Integer getIndexByTeacher(Teacher entity, ObservableList<Teacher> dataList) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (Teacher ent : dataList) {
            if (ent.getId() == entityId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static Integer getIndexByGroupType(GroupType entity, ObservableList<GroupType> dataList) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (GroupType ent : dataList) {
            if (ent.getId() == entityId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static Integer getIndexByGroupDOO(GroupDOO entity, ObservableList<GroupDOO> dataList) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (GroupDOO ent : dataList) {
            if (ent.getId() == entityId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static Integer getIndexByLesson(Lesson entity, ObservableList<Lesson> dataList) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (Lesson ent : dataList) {
            if (ent.getId() == entityId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static Integer getIndexByPosition(Position entity, ObservableList<Position> dataList) {
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

    public static Integer getIndexByRegime(Regime entity, ObservableList<Regime> dataList) {
        Integer index = 0;
        Integer entityId = entity.getId();
        for (Regime ent : dataList) {
            if (ent.getId() == entityId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static boolean isSimplePropertieType(Class clazz) {

        return (clazz.equals(StringProperty.class)
                || clazz.equals(IntegerProperty.class));

    }
}
