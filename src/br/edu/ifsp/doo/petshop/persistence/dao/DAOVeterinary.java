package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import br.edu.ifsp.doo.petshop.persistence.utils.ConnectionFactory;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOVeterinary extends AbstractTemplateSqlDAO<Veterinary, String> {
    @Override
    protected String createSaveSql() {
        return "INSERT INTO veterinary (cpf, name, email, phone, cell, address, password, active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String createUpdateSql() {
        return "UPDATE veterinary SET cpf = ?, name = ?, email = ?, phone = ?, cell = ?, address = ?, password = ?, active = ? "
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
        stmt.setString(1, entity.getCpf());
        stmt.setString(2, entity.getName());
        stmt.setString(3, entity.getEmail());
        stmt.setString(4, entity.getPhone());
        stmt.setString(5, entity.getCell());
        stmt.setString(6, entity.getAddress());
        stmt.setString(7, entity.getPassword());
        stmt.setInt(8, entity.getActive() == true ? 1 : 0);
        stmt.setString(9, entity.getCpf());
    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull String key, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, key);
    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {

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
