package br.edu.ifsp.doo.petshop.view.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowProductManager {
    public void startModal(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane pane = loader.load(getClass().getResource("/br/edu/ifsp/doo/petshop/view/fxml/FXMLProductManager.fxml").openStream());

            Stage stage = new Stage();

            stage.getIcons().add(new Image("/br/edu/ifsp/doo/petshop/view/image/logo.png"));
            stage.setTitle("Lista de Produtos e Procedimentos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane, 800, 600));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
