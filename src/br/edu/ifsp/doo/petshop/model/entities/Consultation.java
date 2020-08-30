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

    public Consultation(Integer id, String startTime, String endTime, Double price, String annotations, boolean paid) {
        this.id = id;

        this.timeLapse = new TimeLapse();
        String[] initialDateTimeParts = startTime.split("T");
        String[] initialDateParts = initialDateTimeParts[0].split("-");
        String[] initialTimeParts = initialDateTimeParts[1].split(":");
        this.timeLapse.setEndTime(Integer.parseInt(initialDateParts[0]), Integer.parseInt(initialDateParts[1]), Integer.parseInt(initialDateParts[2]), Integer.parseInt(initialTimeParts[0]), Integer.parseInt(initialTimeParts[1]));

        String[] finalDateTimeParts = endTime.split("T");
        String[] finalDateParts = finalDateTimeParts[0].split("-");
        String[] finalTimeParts = finalDateTimeParts[1].split(":");
        this.timeLapse.setEndTime(Integer.parseInt(finalDateParts[0]), Integer.parseInt(finalDateParts[1]), Integer.parseInt(finalDateParts[2]), Integer.parseInt(finalTimeParts[0]), Integer.parseInt(finalTimeParts[1]));

        this.price = price;
        this.annotations = annotations;
        this.paid = paid;
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
