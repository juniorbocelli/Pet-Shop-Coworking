package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.main.Main;
import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.view.loaders.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class CtrlWindowSecretaryDashboard {
    @FXML DatePicker txtDate;

    @FXML Button btnToday;
    @FXML Button btnManageVets;
    @FXML Button btnManageClients;
    @FXML Button btnManageAnimals;
    @FXML Button btnManageProducts;
    @FXML Button btnBilling;

    @FXML ComboBox<String> cbxVeterinary;

    @FXML Label lblUserName;
    @FXML Label lblCurrentDate;
    @FXML Label lblCurrentTime;

    @FXML TableView tblSchedule;

    @FXML TableColumn clnStartTime;
    @FXML TableColumn clnEndTime;
    @FXML TableColumn clnClient;
    @FXML TableColumn clnAnimal;
    @FXML TableColumn clnStatus;

    private Secretary secretary;

    public CtrlWindowSecretaryDashboard() {
        secretary = (Secretary)Main.getInstance().getLoggedUser();
    }

    @FXML
    private void initialize() {
        lblUserName.setText(secretary.getName());
        goToToday();
    }

    public void showClientsList(ActionEvent actionEvent) {
        WindowClientManager windowClientManager = new WindowClientManager();
        windowClientManager.startModal();
    }

    public void showVetsList(ActionEvent actionEvent) {
        WindowVeterinaryManager windowVeterinaryManager = new WindowVeterinaryManager();
        windowVeterinaryManager.startModal();
    }

    public void showProductsList(ActionEvent actionEvent) {
        WindowProductManager windowProductManager = new WindowProductManager();
        windowProductManager.startModal();
    }

    public void showAnimalsList(ActionEvent actionEvent) {
        WindowAnimalManager windowAnimalManager = new WindowAnimalManager();
        windowAnimalManager.startModal();
    }

    public void showBilling(ActionEvent actionEvent) {
        WindowBilling windowBilling = new WindowBilling();
        windowBilling.startModal();
    }

    public void showUserData(MouseEvent mouseEvent) {
        WindowSecretary windowSecretary = new WindowSecretary();
        windowSecretary.startModal("Nome da Secretária");
    }

    public void changeDate(ActionEvent actionEvent) {
    }

    public void goToToday(ActionEvent actionEvent) {
        goToToday();
    }

    public void goToToday() {
        LocalDate currentDate = LocalDate.now();
        txtDate.setValue(currentDate);
    }

    public void goToVeterinary(ActionEvent actionEvent) {
    }
}
