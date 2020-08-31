package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Product;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;

import java.util.List;

public class DAOVeterinaryRecord {

    protected void loadNestedEntitiesHook(List<Consultation> entities) {
        entities.forEach((x) -> {
            selectAndBindVeterinary(x);
            selectAndBindAnimal(x);
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
        loadNestedEntitiesHook(consultationList);
        return consultationList;
    }

    public List<Consultation> selectConsultationsList(Animal animal) {
        System.out.println(animal);
        DAOConsultation daoConsultation = new DAOConsultation();
        List<Consultation> consultationList = daoConsultation.selectBy("id_animal", animal.getId());
        loadNestedEntitiesHook(consultationList);
        return consultationList;
    }
}
