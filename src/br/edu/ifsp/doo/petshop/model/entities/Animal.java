package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

public class Animal {
    public enum AnimalTypes {CACHORRO, GATO};
    public enum Diseases {CARDIOPATA, RENAL, PULMONAR, ALÉRGICO};
    public enum Genders {MACHO, FÊMEA};

    private int id;
    private String name;
    private int birthYear;
    private boolean active = true;
    private AnimalTypes animalType;
    private ArrayList<Diseases> diseases = new ArrayList<>();
    private Genders gender;

    private Veterinary preferredVeterinarian;
    private Client owner;
    private VeterinaryRecord veterinaryRecord;

    public Animal() {

    }

    public Animal(String name, int birthYear, AnimalTypes animalType, ArrayList<Diseases> diseases, Genders gender, Veterinary preferredVeterinarian) {
        this.name = name;
        this.birthYear = birthYear;
        this.animalType = animalType;
        this.diseases = diseases;
        this.gender = gender;
        this.preferredVeterinarian = preferredVeterinarian;
    }

    // Usado pelo DAO para criar entidade
    public Animal(int id, String name, String animalType, String gender, int birthYear, boolean active) {
        this.id = id;
        this.name = name;
        this.animalType = AnimalTypes.valueOf(animalType);
        this.gender = Genders.valueOf(gender);
        this.birthYear = birthYear;
        this.active = active;
    }

    public Animal(String name, AnimalTypes animalType, Veterinary preferredVeterinarian) {
        this.name = name;
        this.animalType = animalType;
        this.preferredVeterinarian = preferredVeterinarian;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 6)
            throw new IllegalArgumentException("O nome deve ter no mínimo 6 caracteres!");
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        if (birthYear < 2000 || birthYear > Calendar.getInstance().YEAR)
            throw new IllegalArgumentException("Ano de nascimento inválido!");
        this.birthYear = birthYear;
    }

    public int getAge() {
        return birthYear - Calendar.getInstance().YEAR;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AnimalTypes getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalTypes animalType) {
        this.animalType = animalType;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public Veterinary getPreferredVeterinarian() {
        return preferredVeterinarian;
    }

    public void setPreferredVeterinarian(Veterinary preferredVeterinarian) {
        this.preferredVeterinarian = preferredVeterinarian;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public VeterinaryRecord getVeterinaryRecord() {
        return veterinaryRecord;
    }

    public void setVeterinaryRecord(VeterinaryRecord veterinaryRecord) {
        this.veterinaryRecord = veterinaryRecord;
    }

    public ArrayList<Diseases> getDiseases() {
        return diseases;
    }

    public void addDiaese(Diseases diaese){
        if (!diseases.contains(diaese))
            diseases.add(diaese);
    }

    public void removeDiaese(Diseases diaese){
        if (diseases.contains(diaese))
            diseases.remove(diaese);
    }
}
