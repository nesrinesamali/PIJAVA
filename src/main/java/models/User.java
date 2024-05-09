package models;

public class User {

        private String nom;
        private String email;
        private String password;
        private String prenom;
        private String brochure;
        private String roles;
        private int id;

        private boolean isBanned;

    public User(String nom, String email, String password, String prenom, String brochure, String roles, int id, boolean isBanned) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.prenom = prenom;
        this.brochure = brochure;
        this.roles = roles;
        this.id = id;
        this.isBanned = isBanned;
    }


    public User(String nom, String email, String password, String prenom, String brochure, String roles, boolean isBanned) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.prenom = prenom;
        this.brochure = brochure;
        this.roles = roles;
        this.isBanned = isBanned;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public User() {
        }

        public User(String nom, String email, String password, String prenom, String brochure, String roles) {
            this.nom = nom;
            this.email = email;
            this.password = password;
            this.prenom = prenom;
            this.brochure = brochure;
            this.roles = roles;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getBrochure() {
            return brochure;
        }

        public void setBrochure(String brochure) {
            this.brochure = brochure;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    @Override
    public String toString() {
        return "User{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", prenom='" + prenom + '\'' +
                ", brochure='" + brochure + '\'' +
                ", roles='" + roles + '\'' +
                ", id=" + id +
                ", isBanned=" + isBanned +
                '}';
    }
}


