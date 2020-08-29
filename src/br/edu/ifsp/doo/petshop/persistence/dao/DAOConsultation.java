package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOConsultation extends AbstractTemplateSqlDAO<Consultation, Integer> {
    @Override
    protected String createSaveSql() {
        return "INSERT INTO consultation (id_animal, cpf_veterinary, start_time, end_time, price, annotations, paid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String createUpdateSql() {
        return "UPDATE consultation SET id_animal = ?, cpf_veterinary = ?, start_time = ?, end_time = ?, price = ?, annotations = ?, paid = ? " +
                "WHERE id = ?";
    }

    @Override
    protected String createDeleteSql() {
        return "DELETE FROM consultation WHERE id = ?";
    }

    @Override
    protected String createSelectSql() {
        return "SELECT * FROM consultation WHERE id = ?";
    }

    @Override
    protected String createSelectAllSql() {
        return "SELECT * FROM consultation";
    }

    @Override
    protected String createSelectBySql(String field) {
        return "SELECT * FROM consultation WHERE " + field + " = ?";
    }

    @Override
    protected void setEntityToPreparedStatement(@NotNull Consultation entity, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, entity.getAnimal().getId());
        stmt.setString(2, entity.getVeterinary().getCpf());
        stmt.setString(3, entity.getTimeLapse().getStartTime().toString());
        stmt.setString(4, entity.getTimeLapse().getEndTime().toString());
        stmt.setDouble(5, entity.getPrice());
        stmt.setString(6, entity.getAnnotations());
        stmt.setInt(7, entity.isPaid() == true ? 1 : 0);
        if (entity.getId() != -1)
            stmt.setInt(8, entity.getId());
    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull Integer key, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, key);
    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {
        if(filter instanceof  String)
            stmt.setString(1, filter.toString());
        else if(filter instanceof Integer)
            stmt.setInt(1, (Integer)filter);
        else
            throw new SQLException("O tipo do filtro fornecido não é suportado pela consulta.");
    }

    @Override
    protected Consultation getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        /*
        Consultation entity = new Consultation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("gender"),
                rs.getInt("birth_year"),
                rs.getString("general_annotations"),
                rs.getInt("active") == 1 ? true:false);

         */
        return null;
    }

    @Override
    protected Integer getEntityKey(@NotNull Consultation entity) {
        return entity.getId();
    }
}
