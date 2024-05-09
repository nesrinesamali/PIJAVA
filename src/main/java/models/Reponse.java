package models;

import jakarta.persistence.OneToOne;

import java.sql.Date;

public class Reponse {
    private int id;
    private Date date;
    private String description;
    @OneToOne
    private Rendezvous rendezvous;
    public Reponse() {
    }
    public Reponse(int id, Date date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", rendezvous=" + rendezvous +
                '}';
    }
}
