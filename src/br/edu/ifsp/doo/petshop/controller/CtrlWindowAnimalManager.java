package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CtrlWindowAnimalManager {
    @FXML TextField txtSearch;

    @FXML CheckBox chkInactive;

    @FXML ComboBox<String> cbxOwner;

    @FXML Button btnAddVeterinary;

    @FXML TableView tblAnimal;

    @FXML TableColumn clnName;
    @FXML TableColumn clnOwner;
    @FXML TableColumn clnType;
    @FXML TableColumn clnGender;
    @FXML TableColumn clnAge;

    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal();
    }

    public void filterByOwner(ActionEvent actionEvent) {
    }
}
