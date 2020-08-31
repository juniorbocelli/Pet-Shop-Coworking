package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOConsultation;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinaryRecord;

import java.util.List;

public class UCManageVeterinaryRecord {
    private DAOVeterinaryRecord daoVeterinaryRecord;
    private UCManageConsultation ucManageConsultationPersistence;

    public UCManageVeterinaryRecord(DAOVeterinaryRecord daoVeterinaryRecord, DAOConsultation daoConsultation) {
        this.daoVeterinaryRecord = daoVeterinaryRecord;
        ucManageConsultationPersistence = new UCManageConsultation(daoConsultation);
    }

    public List<Client> getClientsList() {
        return ucManageConsultationPersistence.getClientsList();
    }

    public List<Consultation> getConsultationsList() {
        return daoVeterinaryRecord.selectConsultationsList();
    }

    public List<Consultation> getConsultationsList(Animal animal) {
        return daoVeterinaryRecord.selectConsultationsList(animal);
    }
}
