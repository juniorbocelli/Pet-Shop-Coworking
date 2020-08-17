package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;

public class UCManageAnimal {
    DAOAnimal daoAnimal;
    public UCManageAnimal(DAOAnimal daoAnimal) {
        this.daoAnimal = daoAnimal;
    }
}
