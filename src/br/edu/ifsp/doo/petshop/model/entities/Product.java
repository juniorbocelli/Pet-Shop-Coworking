package br.edu.ifsp.doo.petshop.model.entities;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private Double price;
    private boolean active = true;
    private static int transientId;

    public Product() {

    }

    public Product(String name, Double price, boolean active) {
        this(getNextTransientID(), name, price, active);
    }

    public Product(int id, String name, Double price, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                name.equals(product.name) &&
                price == product.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", active=" + active +
                '}';
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPrice(String price) {

        try {
            this.price = Double.parseDouble(price.replace(",", "."));
        } catch (Exception e) {
            throw new IllegalArgumentException("O valor do preço é inválido!");
        }

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

    public static int getNextTransientID(){
        return --transientId;
    }
}
