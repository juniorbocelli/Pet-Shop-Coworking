package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class VeterinaryRecord {
    private String generalAnnotations;
    private ArrayList<Consultation> consultations = new ArrayList<>();

    public VeterinaryRecord(String generalAnnotations) {
        this.generalAnnotations = generalAnnotations;
    }

    public String getGeneralAnnotations() {
        return generalAnnotations;
    }

    public void setGeneralAnnotations(String generalAnnotations) {
        this.generalAnnotations = generalAnnotations;
    }

    public ArrayList<Consultation> getClinicalConsultations() {
        return consultations;
    }

    public void setClinicalConsultations(ArrayList<Consultation> consultations) {
        this.consultations = consultations;
    }
}
