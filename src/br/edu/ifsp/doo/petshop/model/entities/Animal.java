package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;
import java.util.Calendar;

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
