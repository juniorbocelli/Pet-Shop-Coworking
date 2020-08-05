package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimalManager;
import br.edu.ifsp.doo.petshop.view.loaders.WindowClientManager;
import br.edu.ifsp.doo.petshop.view.loaders.WindowProductManager;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryManager;
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

    public void showUserData(MouseEvent mouseEvent) {
    }

    public void goToDate(ActionEvent actionEvent) {
    }

    public void goToToday(ActionEvent actionEvent) {
    }

    public void goToVeterinary(ActionEvent actionEvent) {
    }
}
