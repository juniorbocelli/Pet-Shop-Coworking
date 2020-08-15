package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.persistence.dao.DAOClient;

public class UCClient {
    DAOClient daoClient;

    public UCClient(DAOClient daoClient) {
        this.daoClient = daoClient;
    }
}
