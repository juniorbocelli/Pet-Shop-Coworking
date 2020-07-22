package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class Animal {
    public enum AnimalTypes {CACHORRO, GATO};
    public enum Diseases {CARDIOPATA, RENAL, PULMONAR, ALÉRGICO};
    public enum Genders {MACHO, FÊMEA};

    private String name;
    private int birthYear;
    private boolean active;
    private AnimalTypes animalType;
    private ArrayList<Diseases> diseases;
    private Genders gender;

    private Veterinary preferredVeterinarian;


}
