package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Secretary;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import br.edu.ifsp.doo.petshop.persistence.utils.ConnectionFactory;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOSecretary extends AbstractTemplateSqlDAO<Secretary, String> {
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
    protected void setEntityToPreparedStatement(@NotNull Secretary entity, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull String key, @NotNull PreparedStatement stmt) throws SQLException {

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
                rs.getString("address"));
        return secretary;
    }

    @Override
    protected String getEntityKey(@NotNull Secretary entity) {
        return entity.getCpf();
    }

    public ResultSet hasFromLogin(String login, String password) {
        String sql = "SELECT * FROM secretary WHERE cpf = ? AND password = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, login.replace(".", "").replace("-", ""));
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return  rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
