package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import javafx.event.ActionEvent;

public class CtrlWindowAnimalManager {
    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal();
    }

    public void filterByOwner(ActionEvent actionEvent) {
    }
}
