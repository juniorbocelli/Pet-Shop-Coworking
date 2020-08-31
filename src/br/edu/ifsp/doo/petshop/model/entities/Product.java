package br.edu.ifsp.doo.petshop.model.entities;

import br.edu.ifsp.doo.petshop.model.utils.MaskApply;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private Double price;
    private boolean active = true;

    public Product() {

    }

    public Product(String name, Double price, boolean active) {
        this(-1, name, price, active);
    }

    public Product(Integer id, String name, Double price, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    @Override
    public String toString() {
        return name + "\t\t\t\t R$ " + getMaskedPrice();
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
        if (name.length() < 10)
            throw new IllegalArgumentException("O nome deve ter no mínimo 10 caracteres!");

        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public String getMaskedPrice() {
        return MaskApply.maskMoney(price);
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Preço inválido!");

        String decimalFormated = String.format("%.2f", price);

        this.price = Double.parseDouble(decimalFormated.replace(",", "."));
    }

    public void setPrice(String price) {
        Double priceDouble;

        try {
            priceDouble = Double.parseDouble(price.replace(",", "."));
        } catch (Exception e) {
            throw new IllegalArgumentException("O valor do preço é inválido!");
        }

        this.setPrice(priceDouble);

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean matchesSearchString(String substring) {
        String nameLowerCase = getName().toLowerCase();
        String priceToStringLowerCase = getPrice().toString().toLowerCase().replace(".", ",");
        String subStringLowerCase = substring.toLowerCase();

        boolean isContainedInName = nameLowerCase.contains(subStringLowerCase);
        boolean isContainedInPrice = priceToStringLowerCase.contains(subStringLowerCase);

        return isContainedInName || isContainedInPrice;
    }

    public boolean matchesSearchByInactive(boolean inactive) {
        return !this.active || this.active != inactive;
    }
}
