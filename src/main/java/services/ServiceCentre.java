package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.CentreDon;
import models.Dons;
import utils.MyDatabase;

public class ServiceCentre implements CRUD<CentreDon> {
    private final Connection connection;

    public ServiceCentre() {
        connection = MyDatabase.getInstance().getConnection();
    }



    @Override
    public void insertOne(CentreDon centreDon) throws SQLException {
        String req = "INSERT INTO `Centre_don`(`nom_centre`,`date_ouverture`,`datefermeture`, `gouvernorat`, `adresse`, `email`, `numero`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, centreDon.getNom());
        ps.setObject(2, centreDon.getHeureouv());
        ps.setObject(3, centreDon.getHeureferm());
        ps.setObject(4, centreDon.getGouvernorat());
        ps.setObject(5, centreDon.getLieu());
        ps.setObject(6, centreDon.getEmail());
        ps.setObject(7, centreDon.getNum());
        ps.executeUpdate();
        System.out.println("Centre Added !");
    }

    @Override
    public void updateOne(CentreDon centreDon) throws SQLException {
        String req = "UPDATE Centre_don SET nom_centre=?, date_ouverture=?, datefermeture=?, gouvernorat=?, adresse=?, email=?, numero=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, centreDon.getNom());
        ps.setObject(2, centreDon.getHeureouv());
        ps.setObject(3, centreDon.getHeureferm());
        ps.setObject(4, centreDon.getGouvernorat());
        ps.setObject(5, centreDon.getLieu());
        ps.setObject(6, centreDon.getEmail());
        ps.setObject(7, centreDon.getNum());
        ps.setInt(8,centreDon.getId());
        System.out.println(("---------------"));
        ps.executeUpdate();
        System.out.println("Centre mis à jour !");
    }



    @Override
    public void deleteOne(int id) throws SQLException {

    }


    public boolean deleteOne(CentreDon centreDon) throws SQLException {
        String req = "DELETE FROM `Centre_don` WHERE `id`=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, centreDon.getId()); // Assuming you have an id field in your Donation class
        ps.executeUpdate();
        System.out.println("Centre Deleted !");

        return false;
    }


    @Override
    public List<CentreDon> selectAll() throws SQLException {
        List<CentreDon> centreDonsList = new ArrayList<>();

        String req = "SELECT * FROM `Centre_don`"; // Modifier le nom de la table ici
        Statement st = connection.createStatement();

        ResultSet rs;
        rs = st.executeQuery(req);

        while (rs.next()){
            CentreDon centreDon = new CentreDon();

            centreDon.setId(rs.getInt(("id")));
            centreDon.setNom(rs.getString("nom_centre")); // Modifier le nom de la colonne si nécessaire
            centreDon.setHeureouv(rs.getString("date_ouverture"));
            centreDon.setHeureferm(rs.getString("datefermeture"));
            centreDon.setGouvernorat(rs.getString("gouvernorat"));
            centreDon.setLieu(rs.getString("adresse"));
            centreDon.setEmail(rs.getString("email"));
            centreDon.setNum(rs.getInt("numero"));

            centreDonsList.add(centreDon);
        }
        return centreDonsList;
    }

    @Override
    public boolean deleteOne(Dons don) throws SQLException {

        return false;
    }
    public List<Dons> selectAllDonsWithCentreDetails() throws SQLException {
        List<Dons> donsList = new ArrayList<>();

        // Requête SQL pour récupérer les dons avec les détails du centre de don associé
        String req = "SELECT d.*, c.* FROM Dons d JOIN Centre_don c ON d.centre_don_id = c.id\n";

        // Utilisation d'une PreparedStatement pour exécuter la requête
        try (PreparedStatement ps = connection.prepareStatement(req);
             ResultSet rs = ps.executeQuery()) {

            // Parcours des résultats de la requête
            while (rs.next()) {
                // Création d'un nouvel objet Dons
                Dons don = new Dons();

                // Récupération des données du don
                don.setId(rs.getInt("id"));
                don.setCin(rs.getString("cin"));
                don.setTypeDeDon(rs.getString("typededon"));
                don.setGenre(rs.getString("genre"));
                don.setDatePro(rs.getString("date_don"));
                don.setDateDer(rs.getString("datedernierdon"));
                don.setGroupeSanguin(rs.getString("groupe_sanguin"));
                don.setEtatMarital(rs.getString("etatmarital"));

                // Création d'un nouvel objet CentreDon
                CentreDon centreDon = new CentreDon();

                // Récupération des données du centre de don
                centreDon.setId(rs.getInt("id_centre"));
                centreDon.setNom(rs.getString("nom_centre"));
                // Récupérez d'autres champs du centre de don de la même manière

                // Associez le centre de don au don
                don.setCentreDon(centreDon);

                // Ajoutez le don à la liste
                donsList.add(don);
            }
        }

        return donsList;
    }


}
