package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.*;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageVeterinaryRecord;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOConsultation;
import br.edu.ifsp.doo.petshop.view.loaders.WindowConsultation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowVeterinaryRecords {
    @FXML ComboBox<Client> cbxClient;
    private StringConverter<Client> stringClientConverter;

    @FXML ComboBox<Animal> cbxAnimal;
    private StringConverter<Animal> stringAnimalConverter;

    @FXML Button btnAddConsultation;
    @FXML Button btnCloseVeterinaryRecord;

    @FXML TableView<Consultation> tblConsultations;

    @FXML TableColumn<Consultation, String> clnDate;
    @FXML TableColumn<Consultation, String> clnStartTime;
    @FXML TableColumn<Consultation, String>  clnEndTime;
    @FXML TableColumn<Consultation, String>  clnVeterinary;
    @FXML TableColumn<Consultation, String>  clnPrice;
    @FXML TableColumn<Consultation, String>  clnFinalized;

    @FXML TextArea txaGeneralAnnotations;

    private UCManageVeterinaryRecord ucManageVeterinaryRecord;

    private ObservableList<Client> clients;
    private ObservableList<Animal> animals;

    private ObservableList<Consultation> tableData;
    private List<Consultation> allConsultations;

    public CtrlWindowVeterinaryRecords() {
        ucManageVeterinaryRecord = new UCManageVeterinaryRecord(new DAOConsultation());
    }

    @FXML
    private void initialize() {
        loadClientsInComboBox();
    }

    public void addConsultation(ActionEvent actionEvent) {
        WindowConsultation windowConsultation = new WindowConsultation();
        windowConsultation.startModal();
    }

    public void closeStage() {
        Stage stage = (Stage) btnCloseVeterinaryRecord.getScene().getWindow();
        stage.close();
    }

    public void closeVeterinaryRecord(ActionEvent actionEvent) {
        closeStage();
    }

    public void changeClient(ActionEvent actionEvent) {
        loadAnimalsInComboBox();
    }

    private void loadClientsInComboBox() {
        clients = FXCollections.observableArrayList(ucManageVeterinaryRecord.getClientsList());
        cbxClient.setItems(clients);

        stringClientConverter = new StringConverter<Client>() {

            @Override
            public String toString(Client object) {
                return object.getName();
            }

            @Override
            public Client fromString(String cpf) {
                return clients.stream()
                        .filter(item -> item.getCpf().equals(cpf))
                        .collect(Collectors.toList()).get(0);
            }
        };

        cbxClient.setConverter(stringClientConverter);
    }

    private void loadAnimalsInComboBox() {
        animals = FXCollections.observableArrayList(cbxClient.getValue().listActiveAnimals());
        cbxAnimal.setItems(animals);

        stringAnimalConverter = new StringConverter<Animal>() {

            @Override
            public String toString(Animal object) {
                return object.getName();
            }

            @Override
            public Animal fromString(String id) {
                return animals.stream()
                        .filter(item -> item.getId() == Integer.parseInt(id))
                        .collect(Collectors.toList()).get(0);
            }
        };

        cbxAnimal.setConverter(stringAnimalConverter);
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblConsultations.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnDate.setCellValueFactory((param) -> {
            String dateTimeString = param.getValue().getTimeLapse().getStartTime().toString();
            String[] dateParts = dateTimeString.split("T")[0].split("-");
            return new SimpleStringProperty(dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0]);
        });
        clnStartTime.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMaskedPrice()));
        clnEndTime.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMaskedPrice()));
        clnVeterinary.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMaskedPrice()));
        clnPrice.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMaskedPrice()));
        clnFinalized.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMaskedPrice()));
    }

    private void loadDataAndShow() {
        if (allConsultations == null)
            allConsultations = new ArrayList<>();
        tableData.setAll(allConsultations);
    }
}
