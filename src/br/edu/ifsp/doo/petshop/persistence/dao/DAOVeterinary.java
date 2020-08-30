package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Consultation;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import br.edu.ifsp.doo.petshop.persistence.utils.ConnectionFactory;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DAOVeterinary extends AbstractTemplateSqlDAO<Veterinary, String> {
    @Override
    protected String createSaveSql() {
        return "INSERT INTO veterinary (name, email, phone, cell, address, password, active, cpf) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String createUpdateSql() {
        return "UPDATE veterinary SET name = ?, email = ?, phone = ?, cell = ?, address = ?, password = ?, active = ? "
                + "WHERE cpf = ?";
    }

    @Override
    protected String createDeleteSql() {
        return "DELETE FROM veterinary WHERE cpf = ?";
    }

    @Override
    protected String createSelectSql() {
        return "SELECT * FROM veterinary WHERE cpf = ?";
    }

    @Override
    protected String createSelectAllSql() {
        return "SELECT * FROM veterinary";
    }

    @Override
    protected String createSelectBySql(String field) {
        return "SELECT * FROM veterinary WHERE "+ field +" = ?";
    }

    @Override
    protected void setEntityToPreparedStatement(@NotNull Veterinary entity, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, entity.getName());
        stmt.setString(2, entity.getEmail());
        stmt.setString(3, entity.getPhone());
        stmt.setString(4, entity.getCell());
        stmt.setString(5, entity.getAddress());
        stmt.setString(6, entity.getPassword());
        stmt.setInt(7, entity.getActive() == true ? 1 : 0);
        stmt.setString(8, entity.getCpf());
    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull String key, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, key);
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
    protected void loadNestedEntitiesHook(List<Veterinary> entities) throws SQLException {
        entities.forEach((x) -> {
            selectAndBindConsultations(x);
        });
    }

    private void selectAndBindConsultations(Veterinary veterinary) {
        DAOConsultation daoConsultation = new DAOConsultation();
        List<Consultation> consultationList = daoConsultation.selectBy("cpf_veterinary", veterinary.getCpf());
        consultationList.forEach((c) -> {
            c.setVeterinary(veterinary);
            veterinary.addSchedule(c);
        });
    }

    @Override
    protected Veterinary getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        Veterinary veterinary = new Veterinary(
                rs.getString("cpf"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("cell"),
                rs.getString("address"),
                rs.getInt("active") == 1 ? true : false,
                rs.getString("password"));
        return veterinary;
    }

    @Override
    protected String getEntityKey(@NotNull Veterinary entity) {
        return entity.getCpf();
    }

    public Veterinary getFromLogin(String login, String password) {
        String sql = "SELECT * FROM veterinary WHERE cpf = ? AND password = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, login.replace(".", "").replace("-", ""));
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return getEntityFromResultSet(rs);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
