package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class Animal {
    public enum AnimalTypes {CACHORRO, GATO};
    public enum Diseases {CARDIOPATA, RENAL, PULMONAR, ALÉRGICO};
    public enum Genders {MACHO, FÊMEA};

    private String name;
    private int birthYear;
    private boolean active = true;
    private AnimalTypes animalType;
    private ArrayList<Diseases> diseases = new ArrayList<>();
    private Genders gender;
    private Veterinary preferredVeterinarian;

    // Prontuário do animal
    private VeterinaryRecord veterinaryRecord;

    public Animal(String name, int birthYear, AnimalTypes animalType, ArrayList<Diseases> diseases, Genders gender, Veterinary preferredVeterinarian) {
        this.name = name;
        this.birthYear = birthYear;
        this.animalType = animalType;
        this.diseases = diseases;
        this.gender = gender;
        this.preferredVeterinarian = preferredVeterinarian;
    }

    public Animal(String name, AnimalTypes animalType, Veterinary preferredVeterinarian) {
        this.name = name;
        this.animalType = animalType;
        this.preferredVeterinarian = preferredVeterinarian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
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

    public ArrayList<Diseases> getDiseases() {
        return diseases;
    }

    public void addDiaese(Diseases diaese){

    }
}
