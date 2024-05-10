package models;

import jakarta.persistence.OneToMany;

public class User {
    private int id ;
    private String nom;
    private String prenom;
    private String typemaladie;
    private String specialite;
    private String groupesanguin;
    private int brochure;
    private boolean is_verified;
    private boolean is_banned;
    private String password;
    private String roles;
    private String email;
    @OneToMany
    private Rendezvous rendezvous;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTypemaladie() {
        return typemaladie;
    }

    public void setTypemaladie(String typemaladie) {
        this.typemaladie = typemaladie;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getGroupeSanguin() {
        return groupesanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupesanguin = groupeSanguin;
    }

    public int getBrochure() {
        return brochure;
    }

    public void setBrochure(int brochure) {
        this.brochure = brochure;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", typemaladie='" + typemaladie + '\'' +
                ", specialite='" + specialite + '\'' +
                ", groupeSanguin='" + groupesanguin + '\'' +
                ", brochure=" + brochure +
                ", is_verified=" + is_verified +
                ", is_banned=" + is_banned +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User(int id, String nom, String prenom, String typemaladie, String specialite, String groupeSanguin, int brochure, boolean is_verified, boolean is_banned, String password, String roles, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.typemaladie = typemaladie;
        this.specialite = specialite;
        this.groupesanguin = groupeSanguin;
        this.brochure = brochure;
        this.is_verified = is_verified;
        this.is_banned = is_banned;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }
}
