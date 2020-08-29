package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.persistence.dao.DAOConsultation;

public class UCManageConsultation {
    DAOConsultation daoConsultation;
    public UCManageConsultation(DAOConsultation daoConsultation) {
        this.daoConsultation = daoConsultation;
    }
}
