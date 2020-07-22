package br.edu.ifsp.doo.petshop.model.entities;

import java.util.Map;
import java.util.TreeMap;

public class Veterinary extends User {
    private boolean active = true;

    // Lista de Consultas
    private Map<TimeLapse, ClinicalConsultation> schedule = new TreeMap<>();

    public Veterinary(String cpf, String password, String email, String address) {
        super(cpf, password, email, address);
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