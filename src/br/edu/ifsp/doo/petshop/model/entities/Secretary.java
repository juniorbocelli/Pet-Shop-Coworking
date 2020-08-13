package br.edu.ifsp.doo.petshop.model.entities;

public class Secretary extends User {
    private boolean isSuperUser = true;

    public Secretary() {
    }

    public Secretary(String cpf, String name, String email, String phone, String cell, String address) {
        super(cpf, name, email, phone, cell, address);
    }

    public Secretary(String cpf, String name, String email, String phone, String cell, String address, String password) {
        super(cpf, name, email, phone, cell, address, password);
    }

    @Override
    public boolean isSuperUser() {
        return isSuperUser;
    }
}
