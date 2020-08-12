package br.edu.ifsp.doo.petshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class CtrlWindowBilling {
    @FXML ComboBox<String> cbxVeterinary;

    @FXML DatePicker txtInitialDate;
    @FXML DatePicker txtFinalDate;

    @FXML Button btnLast30Days;
    @FXML Button btnCurrentMonth;

    @FXML Label lblTotal;

    @FXML TableView tblBilling;

    @FXML TableColumn clnDate;
    @FXML TableColumn clnClient;
    @FXML TableColumn clnAnimal;
    @FXML TableColumn clnValue;

    @FXML
    private void initialize() {
        last30days();
    }

    public void changeVeterinary(ActionEvent actionEvent) {
    }

    public void changeFinalDate(ActionEvent actionEvent) {
    }

    public void changeInitialDate(ActionEvent actionEvent) {
    }

    public void currentMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);
        txtFinalDate.setValue(currentDate);
        txtInitialDate.setValue(firstDayOfMonth);
    }

    public void last30days(ActionEvent actionEvent) {
        last30days();
    }

    public void last30days() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDay = currentDate.minusDays(30);
        txtFinalDate.setValue(currentDate);
        txtInitialDate.setValue(firstDay);
    }
}
