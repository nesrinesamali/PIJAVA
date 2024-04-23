package models;

import java.sql.Date;

public class Rendezvous {
    private int id,reponse_id;
    private User user;
    private String nompatient;

    private String nommedecin;
    private Date date;
    private String heure;
    private boolean etat;


    public Rendezvous() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReponse_id() {
        return reponse_id;
    }

    public void setReponse_id(Integer reponse_id) {
        this.reponse_id = reponse_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNompatient() {
        return nompatient;
    }

    public void setNompatient(String nompatient) {
        this.nompatient = nompatient;
    }

    public String getNommedecin() {
        return nommedecin;
    }

    public void setNommedecin(String nommedecin) {
        this.nommedecin = nommedecin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Rendezvous{" +
                "id=" + id +
                ", reponse_id=" + reponse_id +
                ", user=" + user +
                ", nompatient='" + nompatient + '\'' +
                ", nommedecin='" + nommedecin + '\'' +
                ", date=" + date +
                ", heure='" + heure + '\'' +
                ", etat=" + etat +
                '}';
    }

    public Rendezvous(int id, int reponse_id, String nompatient, String nommedecin, Date date, String heure, boolean etat, User user) {
        this.id = id;
        this.reponse_id = reponse_id;
        this.user = user;
        this.nompatient = nompatient;
        this.nommedecin = nommedecin;
        this.date = date;
        this.heure = heure;
        this.etat = etat;
    }
}
