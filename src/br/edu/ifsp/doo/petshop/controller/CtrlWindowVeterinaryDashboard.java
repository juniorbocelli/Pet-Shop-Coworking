package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimalManager;
import br.edu.ifsp.doo.petshop.view.loaders.WindowBilling;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryRecords;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class CtrlWindowVeterinaryDashboard {
    @FXML DatePicker txtDate;

    @FXML Button btnToday;
    @FXML Button btnManageVeterinaryRecords;
    @FXML Button btnManageVeterinaryConsultations;
    @FXML Button btnManageAnimals;
    @FXML Button btnBilling;

    @FXML Label lblCurrentTime;
    @FXML Label lblCurrentDate;
    @FXML Label lblUserName;

    private Veterinary veterinary;

    public CtrlWindowVeterinaryDashboard() {
        veterinary = (Veterinary) Main.getInstance().getLoggedUser();
    }

    @FXML
    private void initialize() {
        txtDate.setValue(LocalDate.now());
        lblUserName.setText(veterinary.getName());
    }

    public void showUserData(MouseEvent mouseEvent) {
        WindowVeterinary windowVeterinary = new WindowVeterinary();
        windowVeterinary.startModal(veterinary, veterinary.getName());
    }

    public void changeDate(ActionEvent actionEvent) {
    }

    public void goToToday(ActionEvent actionEvent) {
        txtDate.setValue(LocalDate.now());
    }

    public void manageVeterinaryConsultations(ActionEvent actionEvent) {
        WindowVeterinaryRecords windowVeterinaryRecords = new WindowVeterinaryRecords();
        windowVeterinaryRecords.startModal("Título do Prontuário");
    }

    public void manageVeterinaryRecords(ActionEvent actionEvent) {
        WindowVeterinaryRecords windowVeterinaryRecords = new WindowVeterinaryRecords();
        windowVeterinaryRecords.startModal("Prontuários");
    }

    public void showBilling(ActionEvent actionEvent) {
        WindowBilling windowBilling = new WindowBilling();
        windowBilling.startModal();
    }

    public void manageAnimals(ActionEvent actionEvent) {
        WindowAnimalManager windowAnimalManager = new WindowAnimalManager();
        windowAnimalManager.startModal();
    }
}
