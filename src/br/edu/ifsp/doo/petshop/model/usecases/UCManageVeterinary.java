package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOAnimal;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinaryRecord;

import java.time.LocalDate;
import java.util.List;

public class UCManageVeterinary {
    private DAOVeterinary daoVeterinary;
    private UCManageConsultation ucManageConsultationPersistence;
    private UCManageAnimal ucAnimal;

    public UCManageVeterinary(DAOVeterinary daoVeterinary) {
        this.daoVeterinary = daoVeterinary;
    }

    public UCManageVeterinary(DAOVeterinary daoVeterinary,
                              DAOConsultation daoConsultation,
                              DAOAnimal daoAnimal) {
        this.daoVeterinary = daoVeterinary;

        this.ucManageConsultationPersistence = new UCManageConsultation(daoConsultation);
        this.ucAnimal = new UCManageAnimal(daoAnimal);
    }

    public void saveOrUpdate(Veterinary veterinary) {
        daoVeterinary.saveOrUpdate(veterinary);
    }

    public Veterinary select(String cpf) {
        return daoVeterinary.select(cpf).get();
    }

    public List<Veterinary> selectAll() {
        return daoVeterinary.selectAll();
    }

    public List<Consultation> selectConsultationsList(Veterinary veterinary, LocalDate localDate) {
        DAOVeterinaryRecord daoVeterinaryRecord = new DAOVeterinaryRecord();
        return daoVeterinaryRecord.selectConsultationsList(veterinary, localDate);
    }

    public List<Consultation> getConsultationsList(Veterinary veterinary) {
        return daoVeterinary.selectConsultationsList(veterinary);
    }
}
