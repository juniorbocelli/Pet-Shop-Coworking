package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import br.edu.ifsp.doo.petshop.persistence.utils.ConnectionFactory;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSecretary extends AbstractTemplateSqlDAO<Secretary, String> {
    @Override
    protected String createSaveSql() {
        return "INSERT INTO secretary (name, email, phone, cell, address, password, cpf) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String createUpdateSql() {
        return "UPDATE secretary SET name = ?, email = ?, phone = ?, cell = ?, address = ?, password = ? "
                + "WHERE cpf = ?";
    }

    @Override
    protected String createDeleteSql() {
        return "DELETE FROM secretary WHERE cpf = ?";
    }

    @Override
    protected String createSelectSql() {
        return "SELECT * FROM secretary WHERE cpf = ?";
    }

    @Override
    protected String createSelectAllSql() {
        return "SELECT * FROM secretary";
    }

    @Override
    protected String createSelectBySql(String field) {
        return "SELECT * FROM secretary WHERE "+ field +" = ?";
    }

    @Override
    protected void setEntityToPreparedStatement(@NotNull Secretary entity, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, entity.getName());
        stmt.setString(2, entity.getEmail());
        stmt.setString(3, entity.getPhone());
        stmt.setString(4, entity.getCell());
        stmt.setString(5, entity.getAddress());
        stmt.setString(6, entity.getPassword());
        stmt.setString(7, entity.getCpf());
    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull String key, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, key);
    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected Secretary getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        Secretary secretary = new Secretary(
                rs.getString("cpf"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("cell"),
                rs.getString("address"),
                rs.getString("password"));
        return secretary;
    }

    @Override
    protected String getEntityKey(@NotNull Secretary entity) {
        return entity.getCpf();
    }


    public Secretary getFromLogin(String login, String password) {
        String sql = "SELECT * FROM secretary WHERE cpf = ? AND password = ?";

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

    public boolean existSecretary(){
        List<Secretary> secretaries = new ArrayList<>();
        secretaries = selectAll();

        if (secretaries.size() > 0)
            return true;

        return false;
    }
}
