package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class Client {
    private String cpf;
    private String name;
    private String phone;
    private String cell;
    private String email;
    private String address;

    // Lista de Animais do Cliente
    private ArrayList<Animal> animals = new ArrayList<>();

    public Client(String cpf, String name, String phone, String address) {
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Construtor para caso de agendamento de Cliente sem cadastro
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

    // Método que adiciona Animal
    public void addAnimal(Animal animal){

    }

    // Método que lista Animais ativos
    public ArrayList<Animal> listActiveAnimals(){
        return this.animals;
    }

    // Método que lista todos os Animais
    public ArrayList<Animal> listAnimals(){
        return this.animals;
    }
}
