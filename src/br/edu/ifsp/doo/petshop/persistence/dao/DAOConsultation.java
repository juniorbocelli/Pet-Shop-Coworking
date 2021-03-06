package br.edu.ifsp.doo.petshop.persistence.dao;

import br.edu.ifsp.doo.petshop.model.entities.*;
import br.edu.ifsp.doo.petshop.persistence.utils.AbstractTemplateSqlDAO;
import br.edu.ifsp.doo.petshop.persistence.utils.ConnectionFactory;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        DAOVeterinary daoVeterinary = new DAOVeterinary();
        DAOAnimal daoAnimal = new DAOAnimal();
        Consultation entity = new Consultation(
                rs.getInt("id"),
                rs.getString("start_time"),
                rs.getString("end_time"),
                rs.getDouble("price"),
                rs.getString("annotations"),
                rs.getInt("paid") == 1 ? true:false);

        return entity;
    }

    @Override
    protected Integer getEntityKey(@NotNull Consultation entity) {
        return entity.getId();
    }


    @Override
    protected void loadNestedEntitiesHook(List<Consultation> entities) throws SQLException{
        entities.forEach((x) -> {
            selectAndBindVeterinary(x);
            selectAndBindAnimal(x);
            selectAndBindAnimalOwner(x);
        });
    }


    public List<Client> getClientsList() {
        DAOClient daoClient = new DAOClient();
        List<Client> clientList = daoClient.selectAll();

        return clientList;
    }

    public List<Veterinary> getVeterinariesList() {
        DAOVeterinary daoVeterinary = new DAOVeterinary();
        List<Veterinary> veterinaryList = daoVeterinary.selectAll();

        return veterinaryList;
    }

    public List<Consultation> getConsultationsList() {
        List<Consultation> consultationList = selectAll();

        return consultationList;
    }

    public List<Product> selectConsultationProducts(Consultation consultation) {
        DAOProduct daoProduct = new DAOProduct();
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products_of_consultations WHERE id_consultation = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, consultation.getId());
            ResultSet rs  = stmt.executeQuery();

            while (rs.next()) {
                productList.add(daoProduct.select(rs.getInt("id_product")).get());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public void insertProduct(Consultation consultation, Product product) {
        String sql = "INSERT INTO products_of_consultations (id_consultation, id_product, price) VALUES (?, ?, ?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, consultation.getId());
            stmt.setInt(2, product.getId());
            stmt.setDouble(3, product.getPrice());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(Consultation consultation, Product product) {
        String sql = "DELETE FROM products_of_consultations WHERE id_consultation = ? AND id_product = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, consultation.getId());
            stmt.setInt(2, product.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String selectVeterinaryKey(Consultation consultation) {
        String sql = "SELECT cpf_veterinary FROM consultation WHERE id = ?";
        String key = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, consultation.getId());
            ResultSet rs  = stmt.executeQuery();

            while (rs.next()) {
                key = rs.getString("cpf_veterinary");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void selectAndBindVeterinary(Consultation consultation) {
        DAOVeterinary daoVeterinary = new DAOVeterinary();
        consultation.setVeterinary(daoVeterinary.select(selectVeterinaryKey(consultation)).get());
    }

    private Integer selectAnimalKey(Consultation consultation) {
        String sql = "SELECT id_animal FROM consultation WHERE id = ?";
        Integer key = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, consultation.getId());
            ResultSet rs  = stmt.executeQuery();

            while (rs.next()) {
                key = rs.getInt("id_animal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void selectAndBindAnimal(Consultation consultation) {
        DAOAnimal daoAnimal = new DAOAnimal();
        consultation.setAnimal(daoAnimal.select(selectAnimalKey(consultation)).get());
    }



    private String selectAnimalOwnerKey(Animal animal) {
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

    public void selectAndBindAnimalOwner(Consultation consultation) {
        DAOClient daoClient = new DAOClient();
        consultation.getAnimal().setOwner(daoClient.select(selectAnimalOwnerKey(consultation.getAnimal())).get());
    }
}
