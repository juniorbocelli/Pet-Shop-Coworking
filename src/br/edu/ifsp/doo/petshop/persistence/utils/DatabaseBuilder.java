package br.edu.ifsp.doo.petshop.persistence.utils;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public void buildDatabaseIfMissing(){
        if(!isDatabaseAvailable()){
            System.out.println("Database is missing. Building database: \n");
            buildTables();
        }
    }

    private boolean isDatabaseAvailable(){
        return Files.exists(Paths.get("database.db"));
    }

    private void buildTables() {
        try (Statement stmt = ConnectionFactory.createStatement()) {
            stmt.addBatch(createSecretaryTableSql());
            stmt.addBatch(createVeterinaryTableSql());
            stmt.addBatch(createClientTableSql());
            stmt.addBatch(createAnimalTableSql());
            stmt.addBatch(createDiseasesOfAnimalsTableSql());
            stmt.addBatch(createProductTableSql());
            stmt.addBatch(createConsultationTableSql());
            stmt.addBatch(createProductsOfConsultationsTableSql());
            stmt.executeBatch();

            System.out.println("Banco de dados criado com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String createSecretaryTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE secretary (\n");
        builder.append("cpf\tTEXT NOT NULL, \n");
        builder.append("name\tTEXT NOT NULL, \n");
        builder.append("email\tTEXT NOT NULL UNIQUE, \n");
        builder.append("phone\tTEXT NOT NULL, \n");
        builder.append("cell\tTEXT NOT NULL, \n");
        builder.append("address\tTEXT NOT NULL, \n");
        builder.append("password\tTEXT NOT NULL, \n");
        builder.append("is_super_user\tINTEGER NOT NULL DEFAULT 1, \n");
        builder.append("PRIMARY KEY(cpf) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createVeterinaryTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE veterinary (\n");
        builder.append("cpf\tTEXT NOT NULL, \n");
        builder.append("name\tTEXT NOT NULL, \n");
        builder.append("email\tTEXT NOT NULL UNIQUE, \n");
        builder.append("phone\tTEXT NOT NULL, \n");
        builder.append("cell\tTEXT NOT NULL, \n");
        builder.append("address\tTEXT NOT NULL, \n");
        builder.append("password\tTEXT NOT NULL, \n");
        builder.append("active\tINTEGER NOT NULL DEFAULT 1, \n");
        builder.append("is_super_user\tINTEGER NOT NULL DEFAULT 0, \n");
        builder.append("PRIMARY KEY(cpf) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createClientTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE  client (\n");
        builder.append("cpf\tTEXT NOT NULL, \n");
        builder.append("name\tTEXT NOT NULL, \n");
        builder.append("email\tTEXT UNIQUE, \n");
        builder.append("phone\tTEXT, \n");
        builder.append("cell\tTEXT NOT NULL, \n");
        builder.append("address\tTEXT, \n");
        builder.append("is_temporary_registration\tINTEGER NOT NULL DEFAULT 0, \n");
        builder.append("PRIMARY KEY(cpf) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createAnimalTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE  animal (\n");
        builder.append("id\tINTEGER NOT NULL, \n");
        builder.append("cpf_owner\tTEXT NOT NULL, \n");
        builder.append("cpf_official_veterinary\tTEXT NOT NULL, \n");
        builder.append("name\tTEXT NOT NULL, \n");
        builder.append("type\tTEXT NOT NULL, \n");
        builder.append("gender\tTEXT NOT NULL, \n");
        builder.append("birth_year\tINTEGER NOT NULL, \n");
        builder.append("general_annotations\tTEXT, \n");
        builder.append("active\tINTEGER NOT NULL DEFAULT 1, \n");
        builder.append("PRIMARY KEY(id AUTOINCREMENT), \n");
        builder.append("FOREIGN KEY(cpf_official_veterinary) REFERENCES veterinary(cpf), \n");
        builder.append("FOREIGN KEY(cpf_owner) REFERENCES client(cpf) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createDiseasesOfAnimalsTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE  diseases_of_animals (\n");
        builder.append("id_animal\tNUMERIC NOT NULL, \n");
        builder.append("name\tTEXT NOT NULL, \n");
        builder.append("PRIMARY KEY(id_animal,name) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createProductTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE  product (\n");
        builder.append("id\tINTEGER NOT NULL, \n");
        builder.append("name\tTEXT NOT NULL, \n");
        builder.append("price\tNUMERIC(6, 2) NOT NULL DEFAULT 0.00, \n");
        builder.append("active\tINTEGER NOT NULL DEFAULT 1, \n");
        builder.append("PRIMARY KEY(id AUTOINCREMENT) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createConsultationTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE  consultation (\n");
        builder.append("id\tINTEGER NOT NULL, \n");
        builder.append("id_animal\tINTEGER NOT NULL, \n");
        builder.append("cpf_veterinary\tTEXT NOT NULL, \n");
        builder.append("start_time\tTEXT NOT NULL, \n");
        builder.append("end_time\tTEXT, \n");
        builder.append("price\tNUMERIC(6, 2), \n");
        builder.append("annotations\tTEXT, \n");
        builder.append("paid\tINTEGER NOT NULL DEFAULT 0, \n");
        builder.append("PRIMARY KEY(id AUTOINCREMENT), \n");
        builder.append("FOREIGN KEY(paid) REFERENCES animal(id), \n");
        builder.append("FOREIGN KEY(cpf_veterinary) REFERENCES veterinary(cpf) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createProductsOfConsultationsTableSql(){
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE  products_of_consultations (\n");
        builder.append("id_consultation\tINTEGER NOT NULL, \n");
        builder.append("id_product\tINTEGER NOT NULL, \n");
        builder.append("price\tNUMERIC(6, 2) NOT NULL DEFAULT 0.00, \n");
        builder.append("PRIMARY KEY(id_consultation,id_product) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
}
