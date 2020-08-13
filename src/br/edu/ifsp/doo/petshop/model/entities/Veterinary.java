package br.edu.ifsp.doo.petshop.model.entities;

import java.util.Map;
import java.util.TreeMap;

public class Veterinary extends User {
    private boolean active = true;
    private boolean isSuperUser = false;

    // Lista de Consultas
    private Map<TimeLapse, ClinicalConsultation> schedule = new TreeMap<>();

    public Veterinary(String cpf, String name, String email, String phone, String cell, String address) {
        super(cpf, name, email, phone, cell, address);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Método que adiciona Consultas a Agenda
    public boolean addSchedule(ClinicalConsultation clinicalConsultation){
        return true;
    }

    private boolean isFreeTimeLapse(TimeLapse timeLapse){
        return true;
    }
}