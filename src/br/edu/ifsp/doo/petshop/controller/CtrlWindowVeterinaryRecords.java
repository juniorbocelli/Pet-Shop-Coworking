package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import br.edu.ifsp.doo.petshop.view.loaders.WindowConsultation;
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

    private Animal animal;

    public void openAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal(animal, animal.getName());
    }

    public void addConsultation(ActionEvent actionEvent) {
        WindowConsultation windowConsultation = new WindowConsultation();
        windowConsultation.startModal();
    }

    public void saveVeterinaryRecord(ActionEvent actionEvent) {
    }

    public void closeVeterinaryRecord(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseVeterinaryRecord.getScene().getWindow();
        stage.close();
    }
}
