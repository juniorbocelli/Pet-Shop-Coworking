package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryConsultation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CtrlWindowVeterinary {
    @FXML TextField txtName;
    @FXML TextField txtCpf;
    @FXML TextField txtEmail;
    @FXML TextField txtPhone;
    @FXML TextField txtCell;

    @FXML CheckBox chkActive;

    @FXML PasswordField txtPassword;
    @FXML PasswordField txtConfirmPassword;

    @FXML TextArea txaAddress;

    @FXML Button btnAddNewConsultant;
    @FXML Button btnSaveVeterinary;
    @FXML Button btnCloseVeterinary;

    @FXML TableView tblSchedule;

    @FXML TableColumn clnDate;
    @FXML TableColumn clnStartTime;
    @FXML TableColumn clnEndTime;
    @FXML TableColumn clnClientName;
    @FXML TableColumn clnAnimalName;

    public void addNewConsultantion(ActionEvent actionEvent) {
        WindowVeterinaryConsultation windowVeterinaryConsultation = new WindowVeterinaryConsultation();
        windowVeterinaryConsultation.startModal();
    }

    public void saveVeterinary(ActionEvent actionEvent) {
    }

    public void closeVeterinary(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseVeterinary.getScene().getWindow();
        stage.close();
    }
}
