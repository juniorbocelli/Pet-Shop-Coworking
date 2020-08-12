package br.edu.ifsp.doo.petshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CtrlWindowProduct {
    @FXML TextField txtName;
    @FXML TextField txtPrice;

    @FXML Button btnSaveProduct;
    @FXML Button btnCloseProduct;

    public void saveProduct(ActionEvent actionEvent) {
    }

    public void closeProduct(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseProduct.getScene().getWindow();
        stage.close();
    }
}
