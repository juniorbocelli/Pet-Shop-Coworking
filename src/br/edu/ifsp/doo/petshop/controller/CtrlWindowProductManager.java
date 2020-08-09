package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowProduct;
import javafx.event.ActionEvent;

public class CtrlWindowProductManager {
    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewProduct(ActionEvent actionEvent) {
        WindowProduct windowProduct = new WindowProduct();
        windowProduct.startModal();
    }
}
