package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOSecretary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinaryRecord;

import java.time.LocalDate;
import java.util.List;

public class UCManageSecretary {
    private DAOSecretary daoSecretary;
    private UCManageVeterinary ucManageVeterinaryPersistence;
    private UCManageVeterinaryRecord ucManageVeterinaryRecordPersistence;

    public UCManageSecretary(DAOSecretary daoSecretary) {
        this.daoSecretary = daoSecretary;
    }

    public UCManageSecretary(DAOSecretary daoSecretary, DAOVeterinary daoVeterinary, DAOVeterinaryRecord daoVeterinaryRecord) {
        this.daoSecretary = daoSecretary;
        ucManageVeterinaryPersistence = new UCManageVeterinary(daoVeterinary);
        ucManageVeterinaryRecordPersistence = new UCManageVeterinaryRecord(daoVeterinaryRecord);
    }

    public void saveOrUpdate(Secretary secretary) {
        daoSecretary.saveOrUpdate(secretary);
    }

    public List<Veterinary> getVeterinariesList() {
        return ucManageVeterinaryPersistence.selectAll();
    }

    public List<Consultation> selectConsultationsList(Veterinary veterinary, LocalDate localDate) {
        DAOVeterinaryRecord daoVeterinaryRecord = new DAOVeterinaryRecord();
        return daoVeterinaryRecord.selectConsultationsList(veterinary, localDate);
    }
}
