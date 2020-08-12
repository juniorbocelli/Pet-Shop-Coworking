package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryConsultation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CtrlWindowVeterinaryRecords {
    @FXML ComboBox<String> cbxAnimal;

    @FXML Button btnOpenAnimal;
    @FXML Button btnAddConsultation;
    @FXML Button btnSaveVeterinaryRecord;
    @FXML Button btnCloseVeterinaryRecord;

    @FXML TableView tblConsultations;

    @FXML TableColumn clnDate;
    @FXML TableColumn clnStartTime;
    @FXML TableColumn clnEndTime;
    @FXML TableColumn clnVeterinary;
    @FXML TableColumn clnPrice;
    @FXML TableColumn clnFinalized;

    @FXML TextArea txaGeneralAnnotations;

    public void openAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal("Nome do Animal");
    }

    public void addConsultation(ActionEvent actionEvent) {
        WindowVeterinaryConsultation windowVeterinaryConsultation = new WindowVeterinaryConsultation();
        windowVeterinaryConsultation.startModal();
    }

    public void saveVeterinaryRecord(ActionEvent actionEvent) {
    }

    public void closeVeterinaryRecord(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseVeterinaryRecord.getScene().getWindow();
        stage.close();
    }
}
