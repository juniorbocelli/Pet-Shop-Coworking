package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class VeterinaryRecord {
    private String generalAnnotations;
    private ArrayList<ClinicalConsultation> clinicalConsultations = new ArrayList<>();
}
