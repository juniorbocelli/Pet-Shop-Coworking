package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinary;
import javafx.event.ActionEvent;

public class CtrlWindowVeterinaryManager {
    public void search(ActionEvent actionEvent) {
    }

    public void filterByActive(ActionEvent actionEvent) {
    }

    public void addNewVeterinary(ActionEvent actionEvent) {
        WindowVeterinary windowVeterinary = new WindowVeterinary();
        windowVeterinary.startModal();
    }
}
