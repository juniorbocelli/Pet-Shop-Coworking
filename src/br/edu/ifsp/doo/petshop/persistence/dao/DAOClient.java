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
    protected void setEntityToPreparedStatement(@NotNull Client entity, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull String key, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected Client getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected String getEntityKey(@NotNull Client entity) {
        return null;
    }
}
