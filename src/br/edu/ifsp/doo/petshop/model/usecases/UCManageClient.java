package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.utils.DAO;

import java.util.List;
import java.util.Optional;

public class UCManageClient {
    DAO<Client, String> daoClient;
    UCManageAnimal ucManageAnimalPersistence;

    public UCManageClient(DAO<Client, String> daoClient) {
        this.daoClient = daoClient;
    }

    public UCManageClient(DAO<Client, String> daoClient, DAOAnimal daoAnimal) {
        this.daoClient = daoClient;
        ucManageAnimalPersistence = new UCManageAnimal(daoAnimal);
    }

    public void saveOrUpdate(Client client) {
        daoClient.saveOrUpdate(client);
    }

    public List<Client> selectAll() {
        return daoClient.selectAll();
    }

    public Optional<Client> select(String cpf) {
        return daoClient.select(cpf);
    }

    public List<Animal> selectAllWithOwnerAndVeterinary(Client owner) {
        return ucManageAnimalPersistence.selectAllWithOwnerAndVeterinary(owner);
    }
}
