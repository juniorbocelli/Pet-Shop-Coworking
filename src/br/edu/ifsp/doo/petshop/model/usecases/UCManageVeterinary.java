package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClinicalConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;

public class UCManageVeterinary {
    private DAOVeterinary daoVeterinary;
    private UCManageClinicalConsultation ucManageClinicalConsultationPersistence;
    private UCAnimal ucAnimal;

    public UCManageVeterinary(DAOVeterinary daoVeterinary) {
        this(daoVeterinary, null, null);
    }

    public UCManageVeterinary(DAOVeterinary daoVeterinary,
                              DAOClinicalConsultation daoClinicalConsultation,
                              DAOAnimal daoAnimal) {
        this.daoVeterinary = daoVeterinary;

        this.ucManageClinicalConsultationPersistence = new UCManageClinicalConsultation(daoClinicalConsultation);
        this.ucAnimal = new UCAnimal(daoAnimal);
    }

    public void saveOrUpdate(Veterinary veterinary) {
        daoVeterinary.saveOrUpdate(veterinary);
    }
}
