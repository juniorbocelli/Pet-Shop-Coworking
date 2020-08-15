package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;

public class UCAnimal {
    DAOAnimal daoAnimal;
    public UCAnimal(DAOAnimal daoAnimal) {
        this.daoAnimal = daoAnimal;
    }
}
