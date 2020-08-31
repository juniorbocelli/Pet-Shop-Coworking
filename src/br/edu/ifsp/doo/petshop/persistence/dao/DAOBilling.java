package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DAOBilling extends AbstractTemplateSqlDAO<Consultation, Integer> {
    @Override
    protected String createSaveSql() {
        return null;
    }

    @Override
    protected String createUpdateSql() {
        return null;
    }

    @Override
    protected String createDeleteSql() {
        return null;
    }

    @Override
    protected String createSelectSql() {
        return null;
    }

    @Override
    protected String createSelectAllSql() {
        return null;
    }

    @Override
    protected String createSelectBySql(String field) {
        return null;
    }

    @Override
    protected void setEntityToPreparedStatement(@NotNull Consultation entity, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull Integer key, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected Consultation getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected Integer getEntityKey(@NotNull Consultation entity) {
        return null;
    }

    public List<Consultation> selectConsultationsList(Veterinary veterinary, LocalDate initialDate, LocalDate finalDate) {
        DAOVeterinaryRecord daoVeterinaryRecord = new DAOVeterinaryRecord();
        return daoVeterinaryRecord.selectConsultationsList(veterinary, initialDate, finalDate);
    }
}
