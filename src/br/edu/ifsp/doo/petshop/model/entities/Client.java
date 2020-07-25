package br.edu.ifsp.doo.petshop.model.entities;

import br.edu.ifsp.doo.petshop.model.utils.CheckCpf;

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
    public Client(String cpf, String name, String phone) {
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (!CheckCpf.isValid(cpf))
            throw new IllegalArgumentException("CPF inválido!");
        cpf = CheckCpf.unmask(cpf);
        this.cpf = cpf;
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

    // Método que adiciona Animal
    public void addAnimal(Animal animal) throws Exception{
        // Verifica se o Animal já pertence a lista
        if (this.animals.contains(animal))
            throw new Exception("Animal já cadastrado!");
        this.animals.add(animal);
    }

    // Método que lista Animais ativos
    public ArrayList<Animal> listAnimals(){
        ArrayList<Animal> activeAnimalsList = new ArrayList<>();

        for (Animal a : this.animals)
            if (a.isActive())
                activeAnimalsList.add(a);

        return activeAnimalsList;
    }

    // Método que lista todos os Animais
    public ArrayList<Animal> listAllAnimals(){
        ArrayList<Animal> animalsList = new ArrayList<>();

        for (Animal a : this.animals)
            if (a.isActive())
                animalsList.add(a);

        return animalsList;
    }
}
