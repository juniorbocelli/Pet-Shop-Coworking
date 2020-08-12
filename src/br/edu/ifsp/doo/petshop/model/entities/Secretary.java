package br.edu.ifsp.doo.petshop.model.entities;

public class Secretary extends User {
    private boolean isSuperUser = true;

    public Secretary(String cpf, String name, String password, String email, String address) {
        super(cpf, name, password, email, address);
    }

    public Secretary(String cpf, String name, String email, String phone, String cell, String address) {
        super(cpf, name, email, phone, cell, address);
    }
}
