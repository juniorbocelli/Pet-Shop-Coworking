package br.edu.ifsp.doo.petshop.model.entities;

public class Product {
    private String name;
    private boolean active = true;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 10)
            throw new IllegalArgumentException("O nome deve ter no mínimo 10 caracteres!");

        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
