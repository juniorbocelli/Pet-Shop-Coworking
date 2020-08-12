package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CtrlWindowVeterinaryManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkInactive;

    @FXML Button btnAddVeterinary;

    @FXML TableView tblVeterinary;

    @FXML TableColumn clnCpf;
    @FXML TableColumn clnName;
    @FXML TableColumn clnEmail;
    @FXML TableColumn clnPhone;
    @FXML TableColumn clnCell;

    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewVeterinary(ActionEvent actionEvent) {
        WindowVeterinary windowVeterinary = new WindowVeterinary();
        windowVeterinary.startModal();
    }
}
