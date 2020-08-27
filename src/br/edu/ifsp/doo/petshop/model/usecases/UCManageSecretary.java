package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;

import java.util.List;

public class UCManageSecretary {
    private DAOSecretary daoSecretary;
    private UCManageVeterinary ucManageVeterinaryPersistence;

    public UCManageSecretary(DAOSecretary daoSecretary) {
        this.daoSecretary = daoSecretary;
    }

    public UCManageSecretary(DAOSecretary daoSecretary, DAOVeterinary daoVeterinary) {
        this.daoSecretary = daoSecretary;
        this.ucManageVeterinaryPersistence = new UCManageVeterinary(daoVeterinary);
    }

    public void saveOrUpdate(Secretary secretary) {
        daoSecretary.saveOrUpdate(secretary);
    }

    public List<Veterinary> getVeterinariesList() {
        return ucManageVeterinaryPersistence.selectAll();
    }
}
