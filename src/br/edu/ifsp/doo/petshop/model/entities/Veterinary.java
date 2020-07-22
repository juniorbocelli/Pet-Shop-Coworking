package br.edu.ifsp.doo.petshop.model.entities;

public class Veterinary extends User {
    private boolean active = true;

    public Veterinary(String cpf, String password, String email, String address) {
        super(cpf, password, email, address);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}