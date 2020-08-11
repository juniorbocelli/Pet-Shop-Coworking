package br.edu.ifsp.doo.petshop.view.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowProduct {
    public void startModal(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLProduct.fxml").openStream());

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Cadastrar Novo Produto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 100));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startModal(String name) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLProduct.fxml").openStream());

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Editar Produto - " + name);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 100));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
