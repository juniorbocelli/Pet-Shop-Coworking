package br.edu.ifsp.doo.petshop.view.loaders;

import br.edu.ifsp.doo.petshop.controller.CtrlWindowVeterinary;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowVeterinary {
    public void startModal(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLVeterinary.fxml").openStream());

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Cadastrar Novo Veterinário");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 600));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startModal(Veterinary veterinary, String name) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLVeterinary.fxml").openStream());

            CtrlWindowVeterinary ctrlWindowVeterinary = loader.getController();

            if (veterinary != null) {
                ctrlWindowVeterinary.setEntityToView(veterinary);
            }

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Editar Veterinário - " + name);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 600));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
