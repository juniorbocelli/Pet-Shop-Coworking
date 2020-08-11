package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimalManager;
import br.edu.ifsp.doo.petshop.view.loaders.WindowBilling;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinary;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryRecords;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class CtrlWindowVeterinaryDashboard {

    public void showUserData(MouseEvent mouseEvent) {
        WindowVeterinary windowVeterinary = new WindowVeterinary();
        windowVeterinary.startModal("Nome do Veterinário");
    }

    public void goToDate(ActionEvent actionEvent) {
    }

    public void goToToday(ActionEvent actionEvent) {
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
