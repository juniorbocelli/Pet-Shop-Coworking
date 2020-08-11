package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class CtrlWindowSecretaryDashboard {
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

    public void goToDate(ActionEvent actionEvent) {
    }

    public void goToToday(ActionEvent actionEvent) {
    }

    public void goToVeterinary(ActionEvent actionEvent) {
    }
}
