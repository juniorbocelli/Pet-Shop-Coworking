package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOClinicalConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;

import java.util.List;

public class UCManageVeterinary {
    private DAOVeterinary daoVeterinary;
    private UCManageClinicalConsultation ucManageClinicalConsultationPersistence;
    private UCManageAnimal ucAnimal;

    public UCManageVeterinary(DAOVeterinary daoVeterinary) {
        this(daoVeterinary, null, null);
    }

    public UCManageVeterinary(DAOVeterinary daoVeterinary,
                              DAOClinicalConsultation daoClinicalConsultation,
                              DAOAnimal daoAnimal) {
        this.daoVeterinary = daoVeterinary;

        this.ucManageClinicalConsultationPersistence = new UCManageClinicalConsultation(daoClinicalConsultation);
        this.ucAnimal = new UCManageAnimal(daoAnimal);
    }

    public void saveOrUpdate(Veterinary veterinary) {
        daoVeterinary.saveOrUpdate(veterinary);
    }

    public List<Veterinary> selectAll() {
        return daoVeterinary.selectAll();
    }
}
