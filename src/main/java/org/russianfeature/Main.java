package org.russianfeature;

import com.utils.Config;
import com.utils.EnumAction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import org.russianfeature.controllers.*;
import org.russianfeature.controllers.dictionary.*;
import org.russianfeature.model.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane root;
    private AnchorPane currentWorkPlace;
    private ConfigurableApplicationContext context;
    private FXMLLoader loader = new FXMLLoader();;

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        builder.headless(false);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));

        Config.setScreenProperty();

        //FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/MainForm.fxml"));
        loader.setControllerFactory(context::getBean);
        root = (AnchorPane) loader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //test();

        this.primaryStage = primaryStage;

        //initRoot();
        initRootSpringBoot();

        showMainWorkPlace();

    }


    public void test() {
/*

        GroupDOOManager gManager = new GroupDOOManager();
        GroupDOO myGroup = new GroupDOO();
        myGroup.setGroupName("flower");
        myGroup.setCreateDate("01.01.2019");
        myGroup.setLastEditDate("01.01.2019");
        myGroup.setEditUserId(1);
        gManager.save(myGroup);
*/
/*
        GroupDOOManager gManager1 = new GroupDOOManager();
        GroupDOO myGroup1 = gManager1.findById(1);
        gManager1.delete(myGroup1);
        int t = 0;


        StudentManager stManager = new StudentManager();
        Student student = new Student();
        student.setFirstName("yudin");
        student.setSecondName("maksim");
        student.setLastName("vladimirovich");
        student.setBirthDate("22.11.1986");
        student.setCreateDate("11.11.2019");
        student.setGroup(myGroup1);
        stManager.save(student);
        int y = 0;
*/

        /*CartManager cManger = new CartManager();
        Cart cart  =  cManger.test(29);
        //Cart cart  = cManger.findByIdCurrentSessionQuery(29);
        //Cart cart  = cManger.findByIdOpenSession(29);
        //Cart cart  = cManger.findById(29);
        Set<CartDetail> cartDetail = cManger.getCartDetail(cart);
        int i;
        //Cart cart = new Cart();
        //cart.setCartName("my test cart");
        //cManger.save(cart);

*//*
        CartDetailManager cdManager = new CartDetailManager();
        CartDetail cartDetailRecord = new CartDetail();
        cartDetailRecord.setProductName("apple");
        cartDetailRecord.setQty(10);
        cartDetailRecord.setCart(cart);

        //cart.getCartDetails().add(cartDetailRecord);

        cdManager.save(cartDetailRecord);
*//*
        CartManager cManger1 = new CartManager();


        //HibernateUtil.beginTransaction();
        Cart cart1 = cManger1.findByIdOpenSession(cart.getCartId());
        //Set<CartDetail> detail = cart1.getCartDetails();
        //HibernateUtil.commitTransaction();

        //Cart gt = null;
        String sql = "select s from Cart s join fetch s.cartDetails where s.cartId = :cartId";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("cartId", 29);
        Cart gt = (Cart) query.uniqueResult();
        //gt = findOne(query);


        int a = 1;
       *//*
        GroupDOOManager gManager = new GroupDOOManager();
        GroupDOO group = new GroupDOO();

        group.setCreateUser(user);
        group.setGroupName("grp 1");
        group.setCreateDate("21.11.2019");
        group.setLastEditDate("21.11.2019");
        group.setCreateUser(user);

        //user.getGroupDOOSet().add(group);
        gManager.save(group);
*//*
        //HibernateUtil.commitTransaction();
*/



    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

    public void initRoot() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/MainForm.fxml"));
            root = (AnchorPane) loader.load();

            primaryStage.setTitle("Time control BEST");
            primaryStage.setScene(new Scene(root, Config.MAIN_FORM_WIDTH, Config.MAIN_FORM_HEIGHT));
            primaryStage.setMaximized(true);
            primaryStage.show();

            MainFormController controller = loader.getController();
            controller.setMainApp(this);

        }catch (Exception e) {
            System.out.printf(e.toString());
        }
    }

    public void initRootSpringBoot() {
        try {

//            SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
//            context = builder.run(getParameters().getRaw().toArray(new String[0]));
//
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("/fxml/MainForm.fxml"));
//            loader.setControllerFactory(context::getBean);
//            root = (AnchorPane) loader.load();

            primaryStage.setTitle("Time control BEST");
            primaryStage.setScene(new Scene(root, Config.MAIN_FORM_WIDTH, Config.MAIN_FORM_HEIGHT));
            primaryStage.setMaximized(true);
            primaryStage.show();

            MainFormController controller = loader.getController();
            controller.setMainApp(this);

        }catch (Exception e) {
            System.out.printf(e.toString());
        }
    }

    public void showMainWorkPlace() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/MainWorkPlace.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            //if (currentWorkPlace != null && currentWorkPlace.getId().equals(workPlace.getId()))
            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("mainWorkPlace");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            MainWorkPlaceController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPlaceProperty();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showSettingsWorkPlace() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/SettingsForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("settingsWorkPlace");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            SettingsFormController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPlaceProperty();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryWorkPlace() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/DictionaryForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryWorkPlace");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            DictionaryFormController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPlaceProperty();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showGroupsWorkPlace() {

        removeWorkPlace("dictionaryGroupDOO");

        showDictionaryMain(GroupDOO.class);

        Node lbl = getRoot().lookup("#programCaption");
        if (lbl != null)
            ((Label) lbl).setText("Группы ДОО");

    }

    public void showSheduleWorkPlace() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/SheduleForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("sheduleWorkPlace");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            SheduleFormController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPlaceProperty();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showManualWorkPlace() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/ManualForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("manualWorkPlace");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            ManualFormController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPlaceProperty();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showReportsWorkPlace() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/ReportsForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("reportsWorkPlace");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            ReportsFormController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPlaceProperty();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    // Show dictionaries list form
    public void showDictionaryStudentsMain() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/StudentListControlForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryStudentsMain");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            StudentMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setPlaceProperty();
            //controller.setTypeParameterClass(clazz);
            controller.initForm();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryGroupMain() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/GroupListControlForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryGroupMain");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            GroupMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setPlaceProperty();
            //controller.setTypeParameterClass(clazz);
            controller.initForm();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryTeacherMain() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/TeacherListControlForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryTeacherMain");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            TeacherMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setPlaceProperty();
            //controller.setTypeParameterClass(clazz);
            controller.initForm();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryLessonMain() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/LessonListControlForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryLessonMain");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            LessonMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setPlaceProperty();
            //controller.setTypeParameterClass(clazz);
            controller.initForm();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryGroupTypeMain() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/GroupTypeListControlForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryLessonMain");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            GroupTypeMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setPlaceProperty();
            //controller.setTypeParameterClass(clazz);
            controller.initForm();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryPositionMain() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/PositionListControlForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryPositionMain");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            PositionMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setPlaceProperty();
            //controller.setTypeParameterClass(clazz);
            controller.initForm();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryRegimeMain() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/RegimeListControlForm.fxml"));
            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryPositionMain");

            currentWorkPlace = workPlace;

            // Bind controller and main app
            RegimeMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setPlaceProperty();
            //controller.setTypeParameterClass(clazz);
            controller.initForm();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }



    public void showDictionaryMain(Class clazz) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/_kDictionaryListControlForm.fxml"));

            AnchorPane workPlace = (AnchorPane) loader.load();

            if (isValidToShowWorkPlace(workPlace))
                return;

            root.getChildren().add(workPlace);
            workPlace.setId("dictionaryMain");

            currentWorkPlace = workPlace;

            //DictionaryListFormController<Teacher> controller = new DictionaryListFormController<>();
            _DictionaryMainListFormController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setTypeParameterClass(Teacher.class);
            controller.setTypeParameterClass(clazz);
            controller.initForm();
            //controller.setPlaceProperty();

            // Bind controller and main app


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void showDictionaryStudentsAction(EnumAction action) {

        try {

            Stage studentDialogAction = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/dictionary/_StudentEditForm.fxml"));
            AnchorPane studentAction = loader.load();

            studentAction.setId("dictionaryStudentsFormAction");

            // Bind controller and main app
            _StudentEditFormController controller = loader.getController();
            controller.setMainApp(this);
            controller.setStage(studentDialogAction);
            controller.setAction(action);

            studentDialogAction.setScene(new Scene(studentAction));
            studentDialogAction.initOwner(primaryStage);
            studentDialogAction.initModality(Modality.APPLICATION_MODAL);
            studentDialogAction.showAndWait();


        } catch (IOException e) {
            System.out.printf(e.toString());
        }

    }

    public void removeWorkPlace(String NewPlaceId) {
        String currentWorkPalceId = this.getCurrentWorkPlace().getId();
        if (!currentWorkPalceId.equals(NewPlaceId)) {
            AnchorPane delPanel = (AnchorPane) this.getRoot().lookup("#" + currentWorkPalceId);
            this.getRoot().getChildren().remove(delPanel);

            System.gc();
        }
    }

    boolean isValidToShowWorkPlace(AnchorPane workPlace) {
        return (currentWorkPlace != null && currentWorkPlace.getId().equals(workPlace.getId()));
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public AnchorPane getCurrentWorkPlace() {
        return currentWorkPlace;
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void setCurrentWorkPlace(AnchorPane mainWorkPlace) {
        this.currentWorkPlace = mainWorkPlace;
    }
}
