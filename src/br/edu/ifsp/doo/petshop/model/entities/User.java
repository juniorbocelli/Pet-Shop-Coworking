package br.edu.ifsp.doo.petshop.model.entities;

import br.edu.ifsp.doo.petshop.model.utils.CheckCpf;

public class User {
    private String cpf;
    private String password;
    private String name;
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
        if (!CheckCpf.isValid(cpf))
            throw new IllegalArgumentException("CPF inválido!");
        cpf = CheckCpf.unmask(cpf);
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 6)
            throw new IllegalArgumentException("A Senha deve ter no mínimo 6 caracteres!");
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 6)
            throw new IllegalArgumentException("O nome deve ter no mínimo 6 caracteres!");
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // Retira máscara
        phone = phone.replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace(" ", "");

        // Faz verificações
        if(phone.length() < 10 || !phone.chars().allMatch( Character::isDigit ))
            throw  new  IllegalArgumentException("Telefone inválido!");
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        // Retira máscara
        cell = cell.replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace(" ", "");

        // Faz verificações
        if(cell.length() < 11 || !cell.chars().allMatch( Character::isDigit ))
            throw  new  IllegalArgumentException("Celular inválido!");
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
