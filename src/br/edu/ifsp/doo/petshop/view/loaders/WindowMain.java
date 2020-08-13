package br.edu.ifsp.doo.petshop.view.loaders;

import br.edu.ifsp.doo.petshop.controller.CtrlWindowLogin;
import br.edu.ifsp.doo.petshop.model.entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WindowMain extends Application {

    private Stage stage;
    private User loggedUser;

    private CtrlWindowLogin ctrlWindowLogin;

    private static WindowMain instance;

    public WindowMain() {
        instance = this;
    }

    public static WindowMain getInstance() {
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    @Override public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(User loggedUser) {
        this.loggedUser = loggedUser;

        if (loggedUser != null) {
            if (loggedUser.isSuperUser())
                gotoSecretaryProfile();
            else
                gotoVeterinaryProfile();
            return true;
        } else {
            return false;
        }
    }

    public void userLogout(){
        loggedUser = null;
        gotoLogin();
    }

    private void gotoSecretaryProfile() {
        try {
            replaceSceneContent("/br/edu/ifsp/doo/petshop/view/fxml/FXMLSecretaryDashboard.fxml", "Pet Shop Coworking - Dashboard", 800.0, 600.0);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void gotoVeterinaryProfile() {
        try {
            replaceSceneContent("/br/edu/ifsp/doo/petshop/view/fxml/FXMLVeterinaryDashboard.fxml", "Pet Shop Coworking - Dashboard", 800.0, 600.0);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void gotoLogin() {
        try {
            ctrlWindowLogin = replaceSceneContent("/br/edu/ifsp/doo/petshop/view/fxml/FXMLLogin.fxml", "Pet Shop Coworking - Login", 600.0, 400.0).getController();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private FXMLLoader replaceSceneContent(String fxml, String title, Double width, Double height) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent page = (Parent) fxmlLoader.load(WindowMain.class.getResource(fxml));

        stage.setTitle(title);
        stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, width, height);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return fxmlLoader;
    }
}