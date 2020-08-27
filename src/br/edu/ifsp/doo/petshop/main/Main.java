package br.edu.ifsp.doo.petshop.main;

import br.edu.ifsp.doo.petshop.model.entities.User;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import br.edu.ifsp.doo.petshop.persistence.utils.DatabaseBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    private User loggedUser;

    private static Main instance;

    public boolean isFirstExecution = false;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
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

            // Constrói banco caso não exista
            DatabaseBuilder dbBuilder = new DatabaseBuilder();
            dbBuilder.buildDatabaseIfMissing();

            // Cadastro inicial de secretária ou tela de login
            DAOSecretary daoSecretary = new DAOSecretary();
            if (daoSecretary.existSecretary())
                gotoLogin();
            else {
                isFirstExecution = true;
                gotoSecretaryRegistration();
            }

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

    private  void gotoSecretaryRegistration() {
        try {
            replaceSceneContent("/br/edu/ifsp/doo/petshop/view/fxml/FXMLSecretary.fxml", "Cadastro de Super Usuário - Secretária", 800.0, 300.0);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
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

    public void gotoLogin() {
        try {
            replaceSceneContent("/br/edu/ifsp/doo/petshop/view/fxml/FXMLLogin.fxml", "Pet Shop Coworking - Login", 600.0, 400.0);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private Parent replaceSceneContent(String fxml, String title, Double width, Double height) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent page = (Parent) fxmlLoader.load(Main.class.getResource(fxml));

        stage.setTitle(title);
        stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));

        // Centraliza a scene na tela
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene(page, width, height);
        stage.setScene(scene);

        stage.sizeToScene();
        return page;
    }
}