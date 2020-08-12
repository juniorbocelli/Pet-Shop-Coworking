package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CtrlWindowProductManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkInactive;

    @FXML Button btnAddProduct;

    @FXML TableView tblProduct;

    @FXML TableColumn clnName;
    @FXML TableColumn clnPrice;

    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewProduct(ActionEvent actionEvent) {
        WindowProduct windowProduct = new WindowProduct();
        windowProduct.startModal();
    }
}
