package br.edu.ifsp.doo.petshop.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;

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
    public Animal(int id, String name, String animalType, String gender, int birthYear, String generalAnnotations, boolean active) {
        this.id = id;
        this.name = name;
        this.animalType = AnimalTypes.valueOf(animalType.toUpperCase());
        this.gender = Genders.valueOf(gender.toUpperCase());
        this.birthYear = birthYear;
        this.active = active;
        this.veterinaryRecord = new VeterinaryRecord(generalAnnotations);
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
        if (birthYear < 2000 || birthYear > LocalDate.now().getYear())
            throw new IllegalArgumentException("Ano de nascimento inválido!");
        this.birthYear = birthYear;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthYear;
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

    public void setAnimalType(String animalType) {
        try {
            this.animalType = AnimalTypes.valueOf(animalType.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Tipo de Animal inválido!");
        }
    }


    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public void setGender(String gender) {
        try {
            this.gender = Genders.valueOf(gender.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Sexo de Animal inválido!");
        }
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

    public void addDisease(Diseases disease){
        if (!diseases.contains(disease))
            diseases.add(disease);
    }

    public void clearDisease(){
        diseases.clear();
    }

    public boolean matchesSearchString(String substring){
        String nameLowerCase = getName().toLowerCase();
        String ownerLowerCase = getOwner().getName().toLowerCase();
        String subStringLowerCase = substring.toLowerCase().replace(".", "").replace("-", "");

        boolean isContainedInName = nameLowerCase.contains(subStringLowerCase);
        boolean isContainedInOwner = ownerLowerCase.contains(subStringLowerCase);

        return isContainedInName || isContainedInOwner;
    }

    public boolean matchesSearchByInactive(boolean inactive){
        return !this.active || this.active != inactive;
    }
}
