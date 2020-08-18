package br.edu.ifsp.doo.petshop.model.entities;

import br.edu.ifsp.doo.petshop.model.utils.AbstractFilterEntity;

public class Product implements AbstractFilterEntity {
    private String name;
    private Double price;
    private boolean active = true;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean matchesSearchString(String substring) {
        String nameLowerCase = getName().toLowerCase();
        String priceToStringLowerCase = getPrice().toString().toLowerCase().replace(".", ",");
        String subStringLowerCase = substring.toLowerCase();

        boolean isContainedInName = nameLowerCase.contains(subStringLowerCase);
        boolean isContainedInPrice = priceToStringLowerCase.contains(subStringLowerCase);

        return isContainedInName || isContainedInPrice;
    }

    @Override
    public boolean matchesSearchByInactive(boolean inactive) {
        return !this.active || this.active != inactive;
    }
}
