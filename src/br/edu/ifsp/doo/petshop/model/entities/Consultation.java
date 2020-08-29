package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class Consultation {
    private int id;
    private String annotations;
    private double price = 0.00;
    private boolean paid = false;
    private TimeLapse timeLapse;
    private ArrayList<Product> products = new ArrayList<>();

    private Animal animal;
    private Veterinary veterinary;

    public Consultation() {
    }

    public Consultation(Integer id, Animal animal, Veterinary veterinary, String start_time, String end_time, Double price, String annotations, boolean paid) {
        this.id = id;
        this.animal = animal;
        this.veterinary = veterinary;
        this.timeLapse = new TimeLapse();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Veterinary getVeterinary() {
        return veterinary;
    }

    public void setVeterinary(Veterinary veterinary) {
        this.veterinary = veterinary;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Preço inválido!");

        String decimalFormated = String.format("$.2f", price);

        this.price = Double.parseDouble(decimalFormated);
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public TimeLapse getTimeLapse() {
        return timeLapse;
    }

    public void setTimeLapse(TimeLapse timeLapse) {
        this.timeLapse = timeLapse;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public ArrayList<Product> getProducts(){
        return this.products;
    }
}
