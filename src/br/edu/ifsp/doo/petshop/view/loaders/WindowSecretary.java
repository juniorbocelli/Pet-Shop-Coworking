package br.edu.ifsp.doo.petshop.view.loaders;

import br.edu.ifsp.doo.petshop.controller.CtrlWindowSecretary;
import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowSecretary extends Application {

    public void startModal(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLSecretary.fxml").openStream());

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Cadastrar Secretária");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 600));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startModal(Secretary secretary, String name) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLSecretary.fxml").openStream());

            CtrlWindowSecretary ctrlWindowSecretary = loader.getController();

            Stage stage = new Stage();

            if(secretary != null) {
                ctrlWindowSecretary.setEntityToView(secretary);
            }

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Editar Secretária - " + name);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 300));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLSecretary.fxml"));

        primaryStage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
        primaryStage.setTitle("Cadastro de Super Usuário - Secretária");
        primaryStage.setScene(new Scene(root, 800, 300));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        if (args.length > 0)
            if (args[0] == "true")
                WindowLogin.main(args);
    }
}
