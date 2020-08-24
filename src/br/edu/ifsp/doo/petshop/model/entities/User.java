package br.edu.ifsp.doo.petshop.model.entities;

import br.edu.ifsp.doo.petshop.model.utils.CheckCpf;
import br.edu.ifsp.doo.petshop.model.utils.EncryptString;
import br.edu.ifsp.doo.petshop.model.utils.MaskApply;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class User {
    private String cpf;
    private String password;
    private String name;
    private String phone;
    private String cell;
    private String email;
    private String address;

    private boolean isSuperUser;

    public User() {

    }

    public User(String cpf, String name, String email, String phone, String cell, String address) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cell = cell;
        this.address = address;
    }

    public User(String cpf, String name, String email, String phone, String cell, String address, String password) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cell = cell;
        this.address = address;
        this.password = password;
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

    public String getMaskedCpf() {
        return MaskApply.maskCfp(this.cpf);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (password != null) {
            if (password.length() < 6)
                throw new IllegalArgumentException("A Senha deve ter no mínimo 6 caracteres!");
            // Para evitar sobrescrever a senha correta gravando um hash de um outro hash
            if(password != this.password)
                this.password = EncryptString.encryptPassword(password);
        }
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

    public String getMaskedPhone() {
        return MaskApply.maskPhone(this.phone);
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
        if(cell.length() < 11 || !cell.chars().allMatch( Character::isDigit )){
            throw  new  IllegalArgumentException("Celular inválido!");
        }
        this.cell = cell;
    }

    public String getMaskedCell() {
        return MaskApply.maskCell(this.cell);
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

    public boolean isSuperUser() {
        return isSuperUser;
    }
}
