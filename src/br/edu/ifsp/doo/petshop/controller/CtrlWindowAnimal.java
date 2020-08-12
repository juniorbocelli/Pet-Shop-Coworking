package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryRecords;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CtrlWindowAnimal {
    @FXML TextField txtName;

    @FXML Spinner<Integer> txtBirthYear;

    @FXML CheckBox chkActive;

    @FXML RadioButton rdoMale;
    @FXML RadioButton rdoFemale;
    @FXML RadioButton rdoDog;
    @FXML RadioButton rdoCat;

    @FXML ComboBox cbxVeterinary;
    @FXML ComboBox cbxOwner;

    @FXML CheckBox chkCardiopath;
    @FXML CheckBox chkRenal;
    @FXML CheckBox chkPulmonary;
    @FXML CheckBox chkAllergic;

    @FXML Button btnVeterinaryRecord;
    @FXML Button btnSaveAnimal;
    @FXML Button btnCloseAnimal;

    @FXML TextArea txaGeneralAnnotations;

    private Animal animal;
    private  Client client;

    @FXML
    private void initialize() {
        populaSexo();
    }

    public void openVeterinaryRecord(ActionEvent actionEvent) {
        WindowVeterinaryRecords windowVeterinaryRecords = new WindowVeterinaryRecords();
        windowVeterinaryRecords.startModal("Nome do Animal");
    }

    public void saveAnimal(ActionEvent actionEvent) {
    }

    public void closeAnimal(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseAnimal.getScene().getWindow();
        stage.close();
    }

    public void populaSexo() {
        ObservableList<Animal.Genders> options = FXCollections.observableArrayList(Animal.Genders.values());
        cbxOwner.setItems(options);
    }
}
