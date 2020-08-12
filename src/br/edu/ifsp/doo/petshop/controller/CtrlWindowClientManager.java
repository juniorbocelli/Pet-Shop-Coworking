package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CtrlWindowClientManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkInactive;

    @FXML Button btnAddNewClient;

    @FXML TableView tblClient;

    @FXML TableColumn clnCpf;
    @FXML TableColumn clnName;
    @FXML TableColumn clnEmail;
    @FXML TableColumn clnPhone;
    @FXML TableColumn clnCell;

    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewClient(ActionEvent actionEvent) {
        WindowClient windowClient = new WindowClient();
        windowClient.startModal();
    }
}
