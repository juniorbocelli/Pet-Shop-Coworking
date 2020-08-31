package br.edu.ifsp.doo.petshop.model.usecases;

import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOBilling;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinary;
import br.edu.ifsp.doo.petshop.persistence.dao.DAOVeterinaryRecord;

import java.time.LocalDate;
import java.util.List;

public class UCManageBilling {
    private DAOBilling daoBilling;
    private UCManageVeterinary ucManageVeterinaryPersistence;
    private UCManageVeterinaryRecord ucManageVeterinaryRecordPersistence;

    public UCManageBilling(DAOBilling daoBilling, DAOVeterinary daoVeterinary, DAOVeterinaryRecord daoVeterinaryRecord) {
        this.daoBilling = daoBilling;
        ucManageVeterinaryPersistence = new UCManageVeterinary(daoVeterinary);
        ucManageVeterinaryRecordPersistence = new UCManageVeterinaryRecord(daoVeterinaryRecord);
    }

    public List<Veterinary> getVeterinariesList() {
        DAOVeterinary daoVeterinary = new DAOVeterinary();
        return daoVeterinary.selectAll();
    }

    public List<Consultation> selectConsultationsList(Veterinary veterinary, LocalDate initialDate, LocalDate finalDate) {
        return daoBilling.selectConsultationsList(veterinary, initialDate, finalDate);
    }
}
