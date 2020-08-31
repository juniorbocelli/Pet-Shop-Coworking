package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOVeterinaryRecord {

    protected void loadNestedEntitiesHook(List<Consultation> entities) {
        entities.forEach((x) -> {
            selectAndBindProducts(x);
        });
    }

    private void selectAndBindVeterinary(Consultation consultation) {
        DAOVeterinary daoVeterinary = new DAOVeterinary();
        Veterinary veterinary = daoVeterinary.select(consultation.getVeterinary().getCpf()).get();
        consultation.setVeterinary(veterinary);
    }

    private void selectAndBindAnimal(Consultation consultation) {
        DAOAnimal daoAnimal = new DAOAnimal();
        Animal animal = daoAnimal.select(consultation.getAnimal().getId()).get();
        daoAnimal.selectAndBindOwner(animal);
        consultation.setAnimal(animal);
    }

    private void selectAndBindProducts(Consultation consultation) {
        DAOConsultation daoConsultation = new DAOConsultation();
        List<Product> productList = daoConsultation.selectConsultationProducts(consultation);
    }

    public List<Consultation> selectConsultationsList() {
        DAOConsultation daoConsultation = new DAOConsultation();
        List<Consultation> consultationList = daoConsultation.selectAll();
        return consultationList;
    }

    public List<Consultation> selectConsultationsList(Animal animal) {
        DAOConsultation daoConsultation = new DAOConsultation();
        List<Consultation> consultationList = daoConsultation.selectBy("id_animal", animal.getId());
        return consultationList;
    }

    public List<Consultation> selectConsultationsList(Veterinary veterinary, LocalDate localDate) {
        DAOConsultation daoConsultation = new DAOConsultation();
        List<Consultation> consultationList;
        if (veterinary != null)
            consultationList = daoConsultation.selectBy("cpf_veterinary", veterinary.getCpf());
        else
            consultationList = daoConsultation.selectAll();

        return filterConsultationsByDay(consultationList, localDate);
    }

    private List<Consultation> filterConsultationsByDay(List<Consultation> consultationList, LocalDate localDate){
        List<Consultation> listOfConsultations = new ArrayList<>();
        consultationList.forEach((c) ->{
            if (c.getTimeLapse().getStartTime().toLocalDate().compareTo(localDate) == 0)
                listOfConsultations.add(c);
        });

        return listOfConsultations;
    }

    public List<Consultation> selectConsultationsList(Veterinary veterinary, LocalDate initialDate, LocalDate finalDate) {
        DAOConsultation daoConsultation = new DAOConsultation();
        List<Consultation> consultationList;
        if (veterinary != null)
            consultationList = daoConsultation.selectBy("cpf_veterinary", veterinary.getCpf());
        else
            consultationList = daoConsultation.selectAll();

        return filterConsultationsByDay(consultationList, initialDate, finalDate);
    }

    private List<Consultation> filterConsultationsByDay(List<Consultation> consultationList, LocalDate initialDate, LocalDate finalDate){
        List<Consultation> listOfConsultations = new ArrayList<>();
        consultationList.forEach((c) ->{
            if (c.getTimeLapse().getStartTime().toLocalDate().isAfter(initialDate) && c.getTimeLapse().getStartTime().toLocalDate().isAfter(initialDate) && c.isPaid())
                listOfConsultations.add(c);
        });

        return listOfConsultations;
    }
}
