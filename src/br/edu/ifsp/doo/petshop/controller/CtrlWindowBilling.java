package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.model.usecases.UCManageBilling;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOBilling;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinaryRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CtrlWindowBilling {
    @FXML ComboBox<Veterinary> cbxVeterinary;
    private StringConverter<Veterinary> stringVeterinaryConverter;

    @FXML DatePicker txtInitialDate;
    @FXML DatePicker txtFinalDate;

    @FXML Button btnLast30Days;
    @FXML Button btnCurrentMonth;

    @FXML Label lblTotal;

    @FXML TableView<Consultation> tblBilling;

    @FXML TableColumn<Consultation, String> clnDate;
    @FXML TableColumn<Consultation, String> clnClient;
    @FXML TableColumn<Consultation, String> clnAnimal;
    @FXML TableColumn<Consultation, String> clnValue;

    private ObservableList<Veterinary> veterinaries;

    private UCManageBilling ucManageBilling;

    private ObservableList<Consultation> tableData;
    private List<Consultation> allConsultations;

    public CtrlWindowBilling() {
        ucManageBilling = new UCManageBilling(new DAOBilling(), new DAOVeterinary(), new DAOVeterinaryRecord());
    }

    @FXML
    private void initialize() {
        loadVeterinariesInComboBox();
        bindTableViewToItemsList();
        bindColumnsToValueSources();

        last30days();

        loadDataAndShow();
    }

    public void changeVeterinary(ActionEvent actionEvent) {
        loadDataAndShow();
    }

    public void changeFinalDate(ActionEvent actionEvent) {
        loadDataAndShow();
    }

    public void changeInitialDate(ActionEvent actionEvent) {
        loadDataAndShow();
    }

    public void currentMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);
        txtFinalDate.setValue(currentDate);
        txtInitialDate.setValue(firstDayOfMonth);

        loadDataAndShow();
    }

    public void last30days(ActionEvent actionEvent) {
        last30days();

        loadDataAndShow();
    }

    public void last30days() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDay = currentDate.minusDays(30);
        txtFinalDate.setValue(currentDate);
        txtInitialDate.setValue(firstDay);
    }

    private Veterinary getVeterinaryFromView() {
        return (Veterinary) cbxVeterinary.getValue();
    }

    private LocalDate getInitialDateFromView() {
        return txtInitialDate.getValue();
    }
    private LocalDate getFinalDateFromView() {
        return txtFinalDate.getValue();
    }

    private void loadVeterinariesInComboBox() {
        veterinaries = FXCollections.observableArrayList(ucManageBilling.getVeterinariesList());
        veterinaries.add(0, null);
        cbxVeterinary.setItems(veterinaries);

        stringVeterinaryConverter = new StringConverter<Veterinary>() {

            @Override
            public String toString(Veterinary object) {
                return object.getName();
            }

            @Override
            public Veterinary fromString(String cpf) {
                return veterinaries.stream()
                        .filter(item -> item.getCpf().equals(cpf))
                        .collect(Collectors.toList()).get(0);
            }
        };

        cbxVeterinary.setConverter(stringVeterinaryConverter);
    }

    public void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tblBilling.setItems(tableData);
    }

    public void bindColumnsToValueSources() {
        clnDate.setCellValueFactory((param) -> {
            String dateTimeString = param.getValue().getTimeLapse().getStartTime().toString();
            String[] dateParts = dateTimeString.split("T")[0].split("-");
            return new SimpleStringProperty(dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0]);
        });
        clnClient.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getAnimal().getOwner().getName()));
        clnAnimal.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getAnimal().getName()));
        clnValue.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMaskedPrice() + ""));
    }

    private void loadDataAndShow() {
        loadTableDataFromDatabase();
    }

    private void loadTableDataFromDatabase() {
        allConsultations = ucManageBilling.selectConsultationsList(getVeterinaryFromView(), getInitialDateFromView(), getFinalDateFromView());
        tableData.setAll(allConsultations);

        lblTotal.setText(sumValue());
    }

    private String sumValue() {
        List<Double> totalValueList = new ArrayList<Double>();
        double totalValue = 0.00;
        allConsultations.forEach((c) ->{
            totalValueList.add(c.getPrice());
        });

        for (double value : totalValueList) {
            totalValue += value;
        }
        return (totalValue + "").replace(".", ",");
    }
}
