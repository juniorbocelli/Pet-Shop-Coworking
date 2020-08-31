package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOConsultation;

import java.util.List;

public class UCManageVeterinaryRecord {
    private UCManageConsultation ucManageConsultationPersistence;

    public UCManageVeterinaryRecord(DAOConsultation daoConsultation) {
        ucManageConsultationPersistence = new UCManageConsultation(daoConsultation);
    }

    public List<Client> getClientsList() {
        return ucManageConsultationPersistence.getClientsList();
    }

    public List<Consultation> getConsultationsList() {
        return ucManageConsultationPersistence.selectAll();
    }
}
