package br.edu.ifsp.doo.petshop.model.entities;

import java.util.Map;
import java.util.TreeMap;

public class Veterinary extends User {
    private boolean active = true;
    private boolean isSuperUser = false;

    // Lista de Consultas
    private Map<TimeLapse, Consultation> schedule = new TreeMap<>();

    public Veterinary() {
    }

    public Veterinary(String cpf, String name, String email, String phone, String cell, String address, Boolean active, String password) {
        super(cpf, name, email, phone, cell, address, password);
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Método que adiciona Consultas a Agenda
    public void addSchedule(Consultation consultation){
        schedule.put(consultation.getTimeLapse(), consultation);
    }

    private boolean isFreeTimeLapse(TimeLapse timeLapse){
        return true;
    }

    public boolean matchesSearchString(String substring){
        String cpf = getCpf().toLowerCase();
        String nameLowerCase = getName().toLowerCase();
        String emailLowerCase = getEmail().toLowerCase();
        String subStringLowerCase = substring.toLowerCase().replace(".", "").replace("-", "");

        boolean isContainedInCpf = cpf.contains(subStringLowerCase);
        boolean isContainedInName = nameLowerCase.contains(subStringLowerCase);
        boolean isContainedInEmail = emailLowerCase.contains(subStringLowerCase);

        return isContainedInCpf || isContainedInName || isContainedInEmail;
    }

    public boolean matchesSearchByInactive(boolean inactive){
        return !this.active || this.active != inactive;
    }
}