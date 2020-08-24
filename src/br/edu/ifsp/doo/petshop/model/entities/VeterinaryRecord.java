package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class VeterinaryRecord {
    private String generalAnnotations;
    private ArrayList<ClinicalConsultation> clinicalConsultations = new ArrayList<>();

    public String getGeneralAnnotations() {
        return generalAnnotations;
    }

    public void setGeneralAnnotations(String generalAnnotations) {
        this.generalAnnotations = generalAnnotations;
    }

    public ArrayList<ClinicalConsultation> getClinicalConsultations() {
        return clinicalConsultations;
    }

    public void setClinicalConsultations(ArrayList<ClinicalConsultation> clinicalConsultations) {
        this.clinicalConsultations = clinicalConsultations;
    }
}
