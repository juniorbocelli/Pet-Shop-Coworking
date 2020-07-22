package br.edu.ifsp.doo.petshop.model.entities;

import br.edu.ifsp.doo.petshop.utils.CheckCpf;

public class User {
    private String cpf;
    private String name;
    private String password;
    private String phone;
    private String cell;
    private String email;
    private String address;

    public User(String cpf, String name, String password, String email, String address) {
        this.cpf = cpf;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws Exception {
        if (CheckCpf.isValid(cpf)) {
            this.cpf = cpf;
        } else {
            throw new Exception("CPF inválido!");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
