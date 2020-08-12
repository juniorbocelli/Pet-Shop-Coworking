package br.edu.ifsp.doo.petshop.view.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowClient {
    public void startModal(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLClient.fxml").openStream());

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Cadastrar Novo Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 600));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startModal(String name){
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLClient.fxml").openStream());

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Editar Cliente - " + name);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 600));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
