package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;

public class UCManageSecretary {
    private DAOSecretary daoSecretary;

    public UCManageSecretary(DAOSecretary daoSecretary) {
        this.daoSecretary = daoSecretary;
    }

    public void saveOrUpdate(Secretary secretary) {
        daoSecretary.saveOrUpdate(secretary);
    }
}
