package br.edu.ifsp.doo.petshop.controller;

import br.edu.ifsp.doo.petshop.view.loaders.WindowAnimal;
import br.edu.ifsp.doo.petshop.view.loaders.WindowVeterinaryConsultation;
import javafx.event.ActionEvent;

public class CtrlWindowVeterinaryRecords {
    public void openAnimal(ActionEvent actionEvent) {
        WindowAnimal windowAnimal = new WindowAnimal();
        windowAnimal.startModal("Nome do Animal");
    }

    public void addConsultation(ActionEvent actionEvent) {
        WindowVeterinaryConsultation windowVeterinaryConsultation = new WindowVeterinaryConsultation();
        windowVeterinaryConsultation.startModal();
    }

    public void saveVeterinaryRecord(ActionEvent actionEvent) {
    }

    public void closeVeterinaryRecord(ActionEvent actionEvent) {
    }
}
