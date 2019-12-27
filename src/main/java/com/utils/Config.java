package com.utils;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.*;
import java.util.List;

@Component
public class Config {

    public static int MAIN_FORM_WIDTH = 1200;
    public static int MAIN_FORM_HEIGHT = 1000;
    public static int TOP_BOTTOM_PANE_HEIGHT = 80;
    public static int BTN_MENU_HEIGHT = 80;
    public static int BTN_MENU_WIDTH = 200;
    public static int BTN_MENU_FONT_SIZE = 20;
    public static int BTN_MENU_IMAGE_SIZE = 20;
    public final static int SEPARATOR_BTN_WIDTH = 15;
    public static int WORK_PLACE_LEFT_ANCOR_WIDTH = (BTN_MENU_WIDTH + 20);
    public static int MAIN_WP_IMG_WIDTH = 130;
    public static int MAIN_WP_IMG_HEIGHT = 100;
    public static int MAIN_WP_GRIDPANE_WIDTH = 100;
    public static int MAIN_WP_GRIDPANE_HEIGHT = 100;
    public static int PROGRAM_CAPTION_FONT_SIZE = 20;
    public static int PROGRAM_CAPTION_HEIGHT = 20;
    public static int MAIN_WP_GRIDPANE_LABEL_HEIGHT = 100;

    public static final String GROUP_NAME = "ДОО 1";
    public static final String PATH_TO_ICONS = "/org/russianfeature/assests/images/icons/";
    public static final int minHeightWorkPlaceMenu = 200;

    public static final String DB_FULL_PATH = "src/sample/db/TimeControl.sqlite";
    public static int DICTIONARY_MENU_WIDTH = 180;
    public static int DICTIONARY_MENU_HEIGHT = 180;
    public static int DICTIONARY_MENU_FONT_SIZE = 20;

    private static TreeMap<String, String> fieldMap = new TreeMap<>();
    private static TreeMap<String, String> fieldNonEmpty = new TreeMap<>();
    private static TreeMap<String, String> visibleFields = new TreeMap<>();
    private static TreeMap<String, String> visibleFormListFields = new TreeMap<>();
    private static TreeMap<String, Boolean> visibleFormListFields4Groups = new TreeMap<>();
    private static TreeMap<String, String> refFields = new TreeMap<>();
    private static List<String> dictionaryBtnName = new ArrayList<>();

    //@Autowired
    //private static Toolkit toolkit = Toolkit.getDefaultToolkit();

    static {
        // DB table fields mapping name
        fieldMap.put("firstName", "Фамилия");
        fieldMap.put("secondName", "Имя");
        fieldMap.put("lastName", "Отчество");
        fieldMap.put("birthDate", "Дата рождения");
        fieldMap.put("createDate", "Дата создания");
        fieldMap.put("comment", "Комментарий");
        fieldMap.put("startWorkDate", "Дата начала работы");
        fieldMap.put("endWorkDate", "Дата окончания работы");
        fieldMap.put("changeDate", "Дата изменения");
        fieldMap.put("groupKindName", "Вид группы");
        fieldMap.put("groupTypeName", "Тип группы");
        fieldMap.put("groupName", "Название группы");
        fieldMap.put("lastEditDate", "Дата редактирования");
        fieldMap.put("name", "Название");
        fieldMap.put("groupDOO", "Группа ДОО");
        fieldMap.put("groupName4Visual", "Название группы");
        fieldMap.put("groupType", "Тип группы");

        // DB table fields non empty
        fieldNonEmpty.put("firstName", "firstName");
        fieldNonEmpty.put("secondName", "secondName");
        fieldNonEmpty.put("lastName", "lastName");
        fieldNonEmpty.put("birthDate", "birthDate");
        fieldNonEmpty.put("groupKindName", "groupKindName");
        fieldNonEmpty.put("groupTypeName", "groupTypeName");
        fieldNonEmpty.put("name", "name");

        // DB table visible fields on form
        visibleFields.put("firstName", "firstName");
        visibleFields.put("secondName", "secondName");
        visibleFields.put("lastName", "lastName");
        visibleFields.put("birthDate", "birthDate");
        visibleFields.put("comment", "comment");
        visibleFields.put("startWorkDate", "startWorkDate");
        visibleFields.put("endWorkDate", "endWorkDate");
        visibleFields.put("groupKindName", "groupKindName");
        visibleFields.put("groupTypeName", "groupTypeName");
        visibleFields.put("name", "name");
        visibleFields.put("groupDOO", "group");
        visibleFields.put("groupName", "groupName");
        visibleFields.put("groupType", "groupType");

        // DB table visible fields in TableView
        visibleFormListFields.put("firstName", "firstName");
        visibleFormListFields.put("secondName", "secondName");
        visibleFormListFields.put("lastName", "lastName");
        visibleFormListFields.put("birthDate", "birthDate");
        visibleFormListFields.put("comment", "comment");
        visibleFormListFields.put("startWorkDate", "startWorkDate");
        visibleFormListFields.put("endWorkDate", "endWorkDate");
        visibleFormListFields.put("groupKindName", "groupKindName");
        visibleFormListFields.put("groupTypeName", "groupTypeName");
        visibleFormListFields.put("name", "name");
        visibleFormListFields.put("groupName4Visual", "groupName");
        visibleFormListFields.put("groupName", "groupName");

        visibleFormListFields4Groups.put("groupName", false);
        visibleFormListFields4Groups.put("comment", false);
        visibleFormListFields4Groups.put("groupTypeName", true);

        refFields.put("groupDOO", "groupDOO");
        refFields.put("groupType", "groupType");
    }

    public static void setScreenProperty() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Dimension screenSize = toolkit.getScreenSize();

        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        MAIN_FORM_WIDTH = (int) width;
        MAIN_FORM_HEIGHT = (int) height;
        BTN_MENU_WIDTH = 160;
        BTN_MENU_HEIGHT = 50;
        BTN_MENU_FONT_SIZE = 12;
        TOP_BOTTOM_PANE_HEIGHT = 40;
        MAIN_WP_GRIDPANE_WIDTH = 120;
        MAIN_WP_GRIDPANE_HEIGHT = 164;
        PROGRAM_CAPTION_FONT_SIZE = 20;
        PROGRAM_CAPTION_HEIGHT = 20;

        if (width <= 1366) {
            //MAIN_FORM_WIDTH = 800;
            //MAIN_FORM_HEIGHT = 600;
            //BTN_MENU_WIDTH = 120;
            //BTN_MENU_HEIGHT = 50;
            //BTN_MENU_FONT_SIZE = 12;
            BTN_MENU_IMAGE_SIZE = 20;
            MAIN_WP_IMG_WIDTH = 70;
            MAIN_WP_IMG_HEIGHT = 50;
            //MAIN_WP_GRIDPANE_WIDTH = 120;
            //MAIN_WP_GRIDPANE_HEIGHT = 164;
            //TOP_BOTTOM_PANE_HEIGHT = 40;
            //PROGRAM_CAPTION_FONT_SIZE = 20;
            //PROGRAM_CAPTION_HEIGHT = 20;
            MAIN_WP_GRIDPANE_LABEL_HEIGHT = 35;
            DICTIONARY_MENU_WIDTH = 140;
            DICTIONARY_MENU_HEIGHT = 140;
            DICTIONARY_MENU_FONT_SIZE = 14;
        } else {
            //MAIN_FORM_WIDTH = 1200;
            //MAIN_FORM_HEIGHT = 1000;
            //BTN_MENU_WIDTH = 200;
            //BTN_MENU_HEIGHT = 80;
            //BTN_MENU_FONT_SIZE = 20;
            BTN_MENU_IMAGE_SIZE = 30;
            MAIN_WP_IMG_WIDTH = 130;
            MAIN_WP_IMG_HEIGHT = 100;
            //MAIN_WP_GRIDPANE_WIDTH = 140;
            //MAIN_WP_GRIDPANE_HEIGHT = 200;
            //TOP_BOTTOM_PANE_HEIGHT = 80;
            //PROGRAM_CAPTION_FONT_SIZE = 30;
            //PROGRAM_CAPTION_HEIGHT = 30;
            MAIN_WP_GRIDPANE_LABEL_HEIGHT = 20;
            DICTIONARY_MENU_WIDTH = 180;
            DICTIONARY_MENU_HEIGHT = 180;
            DICTIONARY_MENU_FONT_SIZE = 20;
        }
        WORK_PLACE_LEFT_ANCOR_WIDTH = (BTN_MENU_WIDTH + 20);
    }

    public static TreeMap<String, String> getMenuBtnMap() {
        TreeMap<String, String> menuBtns = new TreeMap<String, String>();

        menuBtns.put("1.main", "leftMenuMain");
        menuBtns.put("2.settings", "leftMenuSettings");
        menuBtns.put("3.dictionary", "leftMenuDictionary");
        menuBtns.put("4.group", "leftMenuGroup");
        menuBtns.put("5.shedule", "leftMenuShedule");
        menuBtns.put("6.manual", "leftMenuManual");
        menuBtns.put("7.reports", "leftMenuReports");

        return menuBtns;
    }

    public static TreeMap<String, String> getDictionaryMap() {

        TreeMap<String, String> dic = new TreeMap<String, String>();

        dic.put("1.student", "Воспитанники");
        dic.put("2.teacher", "Педагоги");
        dic.put("3.lesson", "Занятия");
        dic.put("4.group", "Группы");

        return dic;
    }

    public static TreeMap<String, String> getSettingsMap() {

        TreeMap<String, String> dic = new TreeMap<String, String>();

        dic.put("1.sanpin", "СанПин");
        dic.put("2.crosslessons", "Пересечения занятий");

        return dic;
    }

    public static TreeMap<String, String> getGroupsMap() {

        TreeMap<String, String> dic = new TreeMap<String, String>();

        dic.put("1.group", "Группы");
        dic.put("2.groupLessons", "Групповые занятия");
        dic.put("3.personalLessons", "Индивидуальные занятия");

        return dic;
    }

    public static TreeMap<String, String> getReportsMap() {

        TreeMap<String, String> dic = new TreeMap<String, String>();

        dic.put("1.report1", "Отчет по группам");
        dic.put("2.report2", "Отчет по воспитанникам");

        return dic;
    }

    public static TreeMap<String, String> getSheduleMap() {

        TreeMap<String, String> dic = new TreeMap<String, String>();

        dic.put("1.shedule", "Рассписание занятий");

        return dic;
    }

    public static List<String> getMainWorkPlaceImages() {
        List<String> imgList = new ArrayList<>();

        imgList.add("imgMenuReports");
        imgList.add("imgMenuShedule");
        imgList.add("imgMenuGroup");
        imgList.add("imgMenuDictionary");
        imgList.add("imgMenuSettings");

        return imgList;
    }

    public static List<String> getMainWorkPlaceBlocks() {
        List<String> imgList = new ArrayList<>();

        imgList.add("reportsBlock");
        imgList.add("sheduleBlock");
        imgList.add("groupsBlock");
        imgList.add("dictionaryBlock");
        imgList.add("settingsBlock");

        return imgList;
    }

    public static List<String> getDictionaryBtnName() {
        dictionaryBtnName.add("btnGroups");
        dictionaryBtnName.add("btnStudents");
        dictionaryBtnName.add("btnTeachers");
        dictionaryBtnName.add("btnLessons");
        dictionaryBtnName.add("btnGroupType");
        dictionaryBtnName.add("btnRegime");
        dictionaryBtnName.add("btnPosition");

        return dictionaryBtnName;
    }

    public static TreeMap<String, String> getFieldMapName() {
        return fieldMap;
    }

    public static TreeMap<String, String> getFieldNonEmpty() {
        return fieldNonEmpty;
    }

    public static TreeMap<String, String> getVisibleFields() { return visibleFields; }

    public static TreeMap<String, String> getVisibleFormListFields() { return visibleFormListFields; }

    public static TreeMap<String, Boolean> getVisibleFormListFields4Groups() { return visibleFormListFields4Groups; }

    public static TreeMap<String, String> getRefFields() { return refFields; }
}
