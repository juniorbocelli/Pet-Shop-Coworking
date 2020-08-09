package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryConsultation;
import javafx.event.ActionEvent;

public class CtrlWindowVeterinary {
    public void addNewConsultantion(ActionEvent actionEvent) {
        WindowVeterinaryConsultation windowVeterinaryConsultation = new WindowVeterinaryConsultation();
        windowVeterinaryConsultation.startModal();
    }

    public void saveVeterinary(ActionEvent actionEvent) {
    }

    public void closeVeterinary(ActionEvent actionEvent) {
    }
}
