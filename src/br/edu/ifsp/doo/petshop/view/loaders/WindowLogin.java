package br.edu.ifsp.doo.petshop.view.loaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WindowLogin extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLLogin.fxml"));
        primaryStage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
        primaryStage.setTitle("Pet Shop Coworking - Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
