package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.Animal;
import br.edu.ifsp.doo.petshop.model.entities.Client;
import br.edu.ifsp.doo.petshop.model.entities.Veterinary;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import br.edu.ifsp.doo.petshop.persistence.utils.ConnectionFactory;
import br.edu.ifsp.doo.petshop.persistence.utils.DAO;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOAnimal extends AbstractTemplateSqlDAO<Animal, Integer> {

    @Override
    protected String createSaveSql() {
        return "INSERT INTO animal (cpf_owner, cpf_official_veterinary, name, type, gender, birth_year, general_annotations, active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String createUpdateSql() {
        return "UPDATE animal SET cpf_owner = ?, cpf_official_veterinary = ?, name = ?, type = ?, gender = ?, birth_year = ?, " +
                "general_annotations = ?, active = ? WHERE id = ?";
    }

    @Override
    protected String createDeleteSql() {
        return "DELETE FROM animal WHERE id = ?";
    }

    @Override
    protected String createSelectSql() {
        return "SELECT * FROM animal WHERE id = ?";
    }

    @Override
    protected String createSelectAllSql() {
        return "SELECT * FROM animal";
    }

    @Override
    protected String createSelectBySql(String field) {
        return "SELECT * FROM animal WHERE " + field + " = ?";
    }

    @Override
    protected void setEntityToPreparedStatement(@NotNull Animal entity, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setString(1, entity.getOwner().getCpf());
        stmt.setString(2, entity.getPreferredVeterinarian().getCpf());
        stmt.setString(3, entity.getName());
        stmt.setString(4, entity.getAnimalType().name());
        stmt.setString(5, entity.getGender().name());
        stmt.setInt(6, entity.getBirthYear());
        stmt.setString(7, entity.getVeterinaryRecord().getGeneralAnnotations());
        stmt.setInt(8, entity.isActive() == true ? 1 : 0);
        if (entity.getId() != -1)
            stmt.setInt(9, entity.getId());
    }

    @Override
    protected void setKeyToPreparedStatement(@NotNull Integer key, @NotNull PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, key);
    }

    @Override
    protected void setFilterToPreparedStatement(@NotNull Object filter, @NotNull PreparedStatement stmt) throws SQLException {

    }

    @Override
    protected Animal getEntityFromResultSet(@NotNull ResultSet rs) throws SQLException {
        Animal entity = new Animal(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("gender"),
                rs.getInt("birth_year"),
                rs.getString("general_annotations"),
                rs.getInt("active") == 1 ? true:false);
        return entity;
    }

    @Override
    protected Integer getEntityKey(@NotNull Animal entity) {
        return entity.getId();
    }

    private String selectOwnerKey(Animal animal) {
        String sql = "SELECT cpf_owner FROM animal WHERE id = ?";
        String key = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, animal.getId());
            ResultSet rs  = stmt.executeQuery();

            while (rs.next()) {
                key = rs.getString("cpf_owner");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    private String selectOfficialVeterinaryKey(Animal animal) {
        String sql = "SELECT cpf_official_veterinary FROM animal WHERE id = ?";
        String key = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, animal.getId());
            ResultSet rs  = stmt.executeQuery();

            while (rs.next()) {
                key = rs.getString("cpf_official_veterinary");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void selectAndBindOwner(Animal animal) {
        DAOClient daoClient = new DAOClient();
        animal.setOwner(daoClient.select(selectOwnerKey(animal)).get());
    }

    public void selectAndBindVeterinary(Animal animal) {
        DAOVeterinary daoVeterinary = new DAOVeterinary();
        animal.setPreferredVeterinarian(daoVeterinary.select(selectOfficialVeterinaryKey(animal)).get());
    }
}
