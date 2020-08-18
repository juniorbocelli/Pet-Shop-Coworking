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

    private boolean temporaryRegistration;

    // Lista de Animais do Cliente
    private ArrayList<Animal> animals = new ArrayList<>();

    public Client(String cpf, String name, String email, String phone, String cell, String address, boolean temporaryRegistration) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cell = cell;
        this.address = address;
        this.temporaryRegistration = temporaryRegistration;
    }

    public Client() {

    }

    // Construtor para caso de agendamento de Cliente sem cadastro
    public Client(String cpf, String name, String cell, boolean temporaryRegistration) {
        this.cpf = cpf;
        this.name = name;
        this.phone = cell;
        this.temporaryRegistration = temporaryRegistration;
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

    public boolean isTemporaryRegistration() {
        return temporaryRegistration;
    }

    public void setTemporaryRegistration(boolean temporaryRegistration) {
        this.temporaryRegistration = temporaryRegistration;
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

    public boolean matchesSearchString(String substring){
        String cpf = getCpf().toLowerCase();
        String nameLowerCase = getName().toLowerCase();
        String emailLowerCase = getEmail().toLowerCase();
        String subStringLowerCase = substring.toLowerCase().replace(".", "").replace("-", "");

        boolean isContainedInCpf = cpf.contains(subStringLowerCase);
        boolean isContainedInName = nameLowerCase.contains(subStringLowerCase);
        boolean isContainedInEmail = emailLowerCase.contains(subStringLowerCase);

        return isContainedInCpf || isContainedInName || isContainedInEmail;
    }

    public boolean matchesTemporaryRegistration(boolean temporaryRegistration){
        return !this.temporaryRegistration || this.temporaryRegistration != temporaryRegistration;
    }
}
