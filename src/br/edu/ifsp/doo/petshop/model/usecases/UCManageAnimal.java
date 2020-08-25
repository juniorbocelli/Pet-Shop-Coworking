package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;

import java.util.List;

public class UCManageAnimal {
    DAOAnimal daoAnimal;
    UCManageClient ucManageClientPersistence;
    UCManageVeterinary ucManageVeterinaryPersistence;

    public UCManageAnimal(DAOAnimal daoAnimal) {
        this.daoAnimal = daoAnimal;
    }

    public UCManageAnimal(DAOAnimal daoAnimal, DAOClient daoClient, DAOVeterinary daoVeterinary) {
        this.daoAnimal = daoAnimal;

        this.ucManageClientPersistence = new UCManageClient(daoClient);
        this.ucManageVeterinaryPersistence = new UCManageVeterinary(daoVeterinary);
    }

    public void saveOrUpdate(Animal animal) {
        daoAnimal.saveOrUpdate(animal);
    }

    public List<Client> getClientsList() {
        return ucManageClientPersistence.selectAll();
    }

    public List<Veterinary> getVeterinariesList() {
        return ucManageVeterinaryPersistence.selectAll();
    }
}
