package br.edu.ifsp.doo.petshop.model.entities;

import java.util.ArrayList;

public class ClinicalConsultation {
    private String annotations;
    private double price = 0.00;
    private boolean paid = false;
    private TimeLapse timeLapse;
    private ArrayList<Product> products = new ArrayList<>();
}
