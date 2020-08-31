package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Product;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
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
}
