package models;

public class Dons {
    private Integer id;
    private String genre, etatMarital, groupeSanguin, typeDeDon, dateDer, datePro;
    private String cin;
    //@ManyToOne
    //@JoinColumn(name = "centre_don_id") // Nom de la colonne dans la table Don faisant référence au CentreDon
    private CentreDon centreDon;

    public int getId_centre() {
        return id_centre;
    }

    public void setId_centre(int id_centre) {
        this.id_centre = id_centre;
    }

    private int id_centre;
    // Constructeur et autres méthodes existantes

    public CentreDon getCentreDon() {
        return centreDon;
    }

    public void setCentreDon(CentreDon centreDon) {
        this.centreDon = centreDon;
    }


    public Dons() {}

    public Dons(Integer id, String genre, String etatMarital, String typeDeDon, String dateDer, String datePro, String cin, String groupeSanguin) {
        this.id = id;
        this.genre = genre;
        this.etatMarital = etatMarital;
        this.typeDeDon = typeDeDon;
        this.dateDer = dateDer;
        this.datePro = datePro;
        this.cin = cin;
        this.groupeSanguin = groupeSanguin;
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEtatMarital() {
        return etatMarital;
    }

    public void setEtatMarital(String etatMarital) {
        this.etatMarital = etatMarital;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public String getTypeDeDon() {
        return typeDeDon;
    }

    public void setTypeDeDon(String typeDeDon) {
        this.typeDeDon = typeDeDon;
    }

    public String getDateDer() {
        return dateDer;
    }

    public void setDateDer(String dateDer) {
        this.dateDer = dateDer;
    }

    public String getDatePro() {
        return datePro;
    }

    public void setDatePro(String datePro) {
        this.datePro = datePro;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    @Override
    public String toString() {
        return "Dons{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", etatMarital='" + etatMarital + '\'' +
                ", groupeSanguin='" + groupeSanguin + '\'' +
                ", typeDeDon='" + typeDeDon + '\'' +
                ", dateDer='" + dateDer + '\'' +
                ", datePro='" + datePro + '\'' +
                ", cin='" + cin + '\'' +
                '}';
    }
}
