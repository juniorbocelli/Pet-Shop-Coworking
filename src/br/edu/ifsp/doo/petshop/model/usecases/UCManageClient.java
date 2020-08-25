package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;

import java.util.List;

public class UCManageClient {
    DAOClient daoClient;
    UCManageAnimal ucManageAnimalPersistence;

    public UCManageClient(DAOClient daoClient) {
        this.daoClient = daoClient;
    }

    /*
    public UCManageClient(DAOClient daoClient, DAOAnimal daoAnimal) {
        this.daoClient = daoClient;
        this.ucManageAnimalPersistence = new UCManageAnimal(daoAnimal);
    }
     */

    public void saveOrUpdate(Client client) {
        daoClient.saveOrUpdate(client);
    }

    public List<Client> selectAll() {
        return daoClient.selectAll();
    }
}
