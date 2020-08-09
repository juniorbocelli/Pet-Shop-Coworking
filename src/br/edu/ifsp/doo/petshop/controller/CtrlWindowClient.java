package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import javafx.event.ActionEvent;

public class CtrlWindowClient {
    public void addNewanimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal();
    }

    public void saveClient(ActionEvent actionEvent) {
    }

    public void closeClientWindow(ActionEvent actionEvent) {
    }
}
