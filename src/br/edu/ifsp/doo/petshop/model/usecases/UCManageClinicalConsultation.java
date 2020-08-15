package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.ClinicalConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClinicalConsultation;
import br.edu.ifsp.doo.petshop.persistence.utils.DAO;

public class UCManageClinicalConsultation {
    DAOClinicalConsultation daoClinicalConsultation;
    public UCManageClinicalConsultation(DAOClinicalConsultation daoClinicalConsultation) {
        this.daoClinicalConsultation = daoClinicalConsultation;
    }
}
