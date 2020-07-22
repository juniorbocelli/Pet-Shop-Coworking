package br.edu.ifsp.doo.petshop.model.entities;

public class Client {
    private String cpf;
    private String phone;
    private String cell;
    private String email;
    private String address;

    public Client(String cpf, String phone, String address) {
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
    }

    public Client(String cpf, String phone) {
        this.cpf = cpf;
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
