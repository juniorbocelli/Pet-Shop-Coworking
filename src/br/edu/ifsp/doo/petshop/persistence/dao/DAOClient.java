package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOClient extends AbstractTemplateSqlDAO<Client, String> {
    @Override
    protected String createSaveSql() {
        return "INSERT INTO client (name, email, phone, cell, address, is_temporary_registration, cpf) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String createUpdateSql() {
        return "UPDATE client SET name = ?, email = ?, phone = ?, cell = ?, address = ?, is_temporary_registration = ? "
                + "WHERE cpf = ?";
    }

    @Override
    protected String createDeleteSql() {
        return "DELETE FROM client WHERE cpf = ?";
    }

    @Override
    protected String createSelectSql() {
        return "SELECT * FROM client WHERE cpf = ?";
    }

    @Override
    protected String createSelectAllSql() {
        return "SELECT * FROM client";
    }

    @Override
    protected String createSelectBySql(String field) {
        return "SELECT * FROM client WHERE "+ field +" = ?";
    }

    @Override
    protected void setEntityToPreparedStatement(@NotNull Client entity, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, entity.getName());
        stmt.setString(2, entity.getEmail());
        stmt.setString(3, entity.getPhone());
        stmt.setString(4, entity.getCell());
        stmt.setString(5, entity.getAddress());
        stmt.setInt(7, entity.isTemporaryRegistration() == true ? 1 : 0);
        stmt.setString(8, entity.getCpf());
    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull String key, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, key);
    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected Client getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        Client client = new Client(
                rs.getString("cpf"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("cell"),
                rs.getString("address"),
                rs.getInt("is_temporary_registration") == 1 ? true : false);
        return client;
    }

    @Override
    protected String getEntityKey(@NotNull Client entity) {
        return entity.getCpf();
    }
}
