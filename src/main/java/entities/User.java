package entities;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int id;
    private String nom, email, roles, password, prenom, typemaladie, specialite, groupesanguin, statuteligibilite, token, brochure;

    public User(int i, int i1, String johnDoe, String mail, String password123, String image, String roleUser, String token) {

    }
    public User( String nom, String email, String password, String prenom, String brochure, String roles) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.prenom = prenom;
        this.brochure = brochure;
        this.roles = roles;
    }
    

    public User(int id, String nom, String email, String password, String prenom, String typemaladie, String specialite, String groupesanguin, String statuteligibilite, String token, String brochure, String roles) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.prenom = prenom;
        this.typemaladie = typemaladie;
        this.specialite = specialite;
        this.groupesanguin = groupesanguin;
        this.statuteligibilite = statuteligibilite;
        this.token = token;
        this.brochure = brochure;
        this.roles = roles;
    }


    public User(int id, String nom, String email, String roles, String prenom, String statuteligibilite, String token) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.roles = roles;
        this.prenom = prenom;
        this.statuteligibilite = statuteligibilite;
        this.token = token;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public String getToken() {
        return token;
    }

    public String getBrochure() {
        return brochure;
    }

    public void setBrochure(String brochure) {
        this.brochure = brochure;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatuteligibilite() {
        return statuteligibilite;
    }

    public void setStatuteligibilite(String statuteligibilite) {
        this.statuteligibilite = statuteligibilite;
    }

    public String getGroupesanguin() {
        return groupesanguin;
    }

    public void setGroupesanguin(String groupesanguin) {
        this.groupesanguin = groupesanguin;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTypemaladie() {
        return typemaladie;
    }

    public void setTypemaladie(String typemaladie) {
        this.typemaladie = typemaladie;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password); // Hashing the password before setting it
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Hashing the password
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // Setter and getter for age
    public void setAge(int age) {
        // Add code to set the age property if needed
    }

    public int getAge() {
        // Add code to get the age property if needed
        return 0; // Replace with actual implementation
    }

    // Setter and getter for user id
    public void setId_user(int id_user) {
        // Add code to set the user id property if needed
    }

    public int getId_user() {
        // Add code to get the user id property if needed
        return 0; // Replace with actual implementation
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", prenom='" + prenom + '\'' +
                ", typemaladie='" + typemaladie + '\'' +
                ", specialite='" + specialite + '\'' +
                ", groupesanguin='" + groupesanguin + '\'' +
                ", statuteligibilite='" + statuteligibilite + '\'' +
                ", token='" + token + '\'' +
                ", brochure='" + brochure + '\'' +
                '}';
    }
}
