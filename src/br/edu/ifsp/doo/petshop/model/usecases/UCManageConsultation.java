package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.*;
import br.edu.ifsp.doo.petshop.persistence.dao.*;

import java.util.List;

public class UCManageConsultation {
    private DAOConsultation daoConsultation;
    private UCManageVeterinary ucManageVeterinaryPersistence;
    private UCManageClient ucManageClient;
    private UCManageAnimal ucManageAnimalPersistence;
    private UCManageProduct ucManageProduct;

    public UCManageConsultation(DAOConsultation daoConsultation) {
        this.daoConsultation = daoConsultation;
    }

    public UCManageConsultation(DAOConsultation daoConsultation, DAOVeterinary daoVeterinary, DAOClient daoClient, DAOAnimal daoAnimal, DAOProduct daoProduct) {
        this.daoConsultation = daoConsultation;
        ucManageVeterinaryPersistence = new UCManageVeterinary(daoVeterinary);
        ucManageClient = new  UCManageClient(daoClient);
        ucManageAnimalPersistence = new UCManageAnimal(daoAnimal);
        ucManageProduct = new UCManageProduct(daoProduct);
    }

    public void saveOrUpdate(Consultation consultation) {
        daoConsultation.saveOrUpdate(consultation);
    }

    public Integer saveOrUpdateWithReturnId(Consultation consultation) {
        return daoConsultation.saveOrUpdateWithReturnId(consultation);
    }

    public List<Consultation> selectAll() {
        return daoConsultation.selectAll();
    }

    public List<Client> getClientsList() {
        return daoConsultation.getClientsList();
    }

    public List<Veterinary> getVeterinariesList() {
        return daoConsultation.getVeterinariesList();
    }

    public List<Product> getProductsList() {
        return ucManageProduct.selectAllActives();
    }

    public void updateProductsList(Consultation consultation) {
        List<Product> productsToUpdate = consultation.getProducts();
        List<Product> productsInDatabase = selectConsultationProducts(consultation);

        deleteRemovedProducts(consultation, productsToUpdate, productsInDatabase);
        insertNewProducts(consultation, productsToUpdate, productsInDatabase);
    }

    public List<Product> selectConsultationProducts(Consultation consultation){
        List<Product> products = daoConsultation.selectConsultationProducts(consultation);
        return products;
    }

    private void deleteRemovedProducts(Consultation consultation, List<Product> productsToUpdate, List<Product> productsInDatabase) {
        productsInDatabase.forEach(c-> {
            if(!productsToUpdate.contains(c))
                daoConsultation.removeProduct(consultation, c);
        });
    }

    private void insertNewProducts(Consultation consultation, List<Product> productsToUpdate, List<Product> productsInDatabase) {
        productsToUpdate.forEach(c-> {
            if(!productsInDatabase.contains(c))
                daoConsultation.insertProduct(consultation, c);
        });
    }
}
