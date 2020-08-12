package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CtrlWindowClient {
    @FXML TextField txtName;
    @FXML TextField txtCpf;
    @FXML TextField txtEmail;
    @FXML TextField txtPhone;
    @FXML TextField txtCelular;

    @FXML CheckBox chkActive;

    @FXML TextArea txaAddress;

    @FXML Button btnAddNewConsultant;
    @FXML Button btnSaveClient;
    @FXML Button btnCloseClient;

    @FXML TableView tblAnimal;

    @FXML TableColumn clnName;
    @FXML TableColumn clnType;
    @FXML TableColumn clsGender;
    @FXML TableColumn clnAge;
    @FXML TableColumn clnActive;

    public void addNewAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal();
    }

    public void saveClient(ActionEvent actionEvent) {
    }

    public void closeClient(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseClient.getScene().getWindow();
        stage.close();
    }
}
