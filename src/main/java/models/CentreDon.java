package models;

import java.util.ArrayList;
import java.util.List;

public class CentreDon {
    private Integer id;
    private String heureouv, heureferm, nom, lieu;
    private Integer num;
    private String gouvernorat; // Ajout de l'attribut gouvernorat
    private String email; // Ajout de l'attribut email
    private List<Dons> donsList;

    public CentreDon() {}

    public CentreDon(Integer id, String nom, String heureferm, String heureouv, String lieu, Integer num ,String gouvernorat,String email) {
        this.id = id;
        this.nom = nom;
        this.heureferm = heureferm;
        this.heureouv = heureouv;
        this.lieu = lieu;
        this.gouvernorat = gouvernorat;
        this.email = email;
        this.num = num;

    }

    // Autres méthodes existantes...

    // Getters et Setters pour latitude et longitude

    // Getters
    public Integer getId() {
        return id;
    }

    public String getHeureouv() {
        return heureouv;
    }

    public String getHeureferm() {
        return heureferm;
    }



    public String getLieu() {
        return lieu;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public String getEmail() {
        return email;
    }

    public Integer getNum() {
        return num;
    }
    public String getNom() {
        return nom;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setHeureouv(String heureouv) {
        this.heureouv = heureouv;
    }

    public void setHeureferm(String heureferm) {
        this.heureferm = heureferm;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return nom;
    }



}
