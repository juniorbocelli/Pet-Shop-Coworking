package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class ClinicalConsultation {
    private String annotations;
    private double price = 0.00;
    private boolean paid = false;
    private TimeLapse timeLapse;
    private ArrayList<Product> products = new ArrayList<>();

    public ClinicalConsultation(TimeLapse timeLapse) {
        this.timeLapse = timeLapse;
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
