package br.edu.ifsp.doo.petshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class CtrlWindowSecretary {
    @FXML TextField txtName;
    @FXML TextField txtCpf;
    @FXML TextField txtEmail;
    @FXML TextField txtPhone;
    @FXML TextField txtCell;

    @FXML PasswordField txtPassword;
    @FXML PasswordField txtConfirmPassword;

    @FXML TextArea txaAddress;

    @FXML Button btnSaveSecretary;
    @FXML Button btnCloseSecretary;

    public void saveSecretary(ActionEvent actionEvent) {
    }

    public void closeSecretary(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseSecretary.getScene().getWindow();
        stage.close();
    }
}
