package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowClient;
import javafx.event.ActionEvent;

public class CtrlWindowClientManager {
    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewClient(ActionEvent actionEvent) {
        WindowClient windowClient = new WindowClient();
        windowClient.startModal();
    }
}
