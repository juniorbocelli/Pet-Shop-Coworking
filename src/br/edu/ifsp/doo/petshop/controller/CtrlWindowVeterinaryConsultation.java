package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowProductManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CtrlWindowVeterinaryConsultation {
    @FXML ComboBox<String> cbxClient;
    @FXML ComboBox<String> cbxAnimal;
    @FXML ComboBox<String> cbxVeterinary;

    @FXML DatePicker txtDate;

    @FXML TextField txtInitialTime;
    @FXML TextField txtEndTime;
    @FXML TextField txtPrice;

    @FXML Button btnModifyStartTime;
    @FXML Button btnAddProduct;
    @FXML Button btnFinalizeConsultant;
    @FXML Button btnSaveVeterinaryConsultant;
    @FXML Button btnCloseVeterinaryConsultant;

    @FXML TextArea txaAnnotations;

    @FXML TableView tblProducts;

    @FXML TableColumn clnName;
    @FXML TableColumn clnPrice;

    public void changeClient(ActionEvent actionEvent) {
    }

    public void changeAnimal(ActionEvent actionEvent) {
    }

    public void changeVeterinary(ActionEvent actionEvent) {
    }

    public void modifyStartTime(ActionEvent actionEvent) {
    }

    public void addProduct(ActionEvent actionEvent) {
        // TODO: Criar nova tela para selecionar ou excluir produtos
        WindowProductManager windowProductManager = new WindowProductManager();
        windowProductManager.startModal();
    }

    public void finalizeConsultant(ActionEvent actionEvent) {
    }

    public void saveVeterinaryConsultant(ActionEvent actionEvent) {
    }

    public void closeVeterinaryConsultant(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseVeterinaryConsultant.getScene().getWindow();
        stage.close();
    }
}
