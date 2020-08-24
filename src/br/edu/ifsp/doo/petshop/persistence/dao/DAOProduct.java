package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Product;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOProduct extends AbstractTemplateSqlDAO<Product, Integer> {
    @Override
    protected String createSaveSql() {
        return "INSERT INTO product (name, price, active) " +
                "VALUES (?, ?, ?)";
    }

    @Override
    protected String createUpdateSql() {
        return "UPDATE product SET name = ?, price = ?, active = ? WHERE id = ?";
    }

    @Override
    protected String createDeleteSql() {
        return "DELETE FROM product WHERE id = ?";
    }

    @Override
    protected String createSelectSql() {
        return "SELECT * FROM product WHERE id = ?";
    }

    @Override
    protected String createSelectAllSql() {
        return "SELECT * FROM product";
    }

    @Override
    protected String createSelectBySql(String field) {
        return "SELECT * FROM product WHERE " + field + " = ?";
    }

    @Override
    protected void setEntityToPreparedStatement(@NotNull Product entity, @NotNull PreparedStatement stmt) throws SQLException {
        System.out.println(entity);
        stmt.setString(1, entity.getName());
        stmt.setDouble(2, entity.getPrice());
        stmt.setInt(3, entity.isActive() == true ? 1 : 0);
        if (entity.getId() != -1)
            stmt.setInt(4, entity.getId());
    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull Integer key, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, key);
    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, filter.toString());
    }

    @Override
    protected Product getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        Product entity = new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("active") == 1 ? true:false);
        return entity;
    }

    @Override
    protected Integer getEntityKey(@NotNull Product entity) {
        return entity.getId();
    }
}
