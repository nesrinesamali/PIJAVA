package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.logincontroller;
import javafx.scene.chart.XYChart;
import models.CentreDon;
import models.Dons;
import utils.MyDatabase;

public class ServiceDon implements CRUD<Dons> {
    private final Connection connection;

    public ServiceDon() {
        connection = MyDatabase.getInstance().getCon();
    }

    public static boolean saveDon(Dons don) {
        return false;
    }

    ///////INSERT DONATION////////


    @Override
    public void insertOne(Dons don) throws SQLException {
        String req = "INSERT INTO `Dons`(`cin`,`genre`,`date_don`, `datedernierdon`, `groupe_sanguin`, `typededon`, `etatmarital`,`centre_don_id`,`user_id`) VALUES (?,?,?,?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, don.getCin());
        ps.setObject(2, don.getGenre());
        ps.setObject(3, don.getDatePro());
        ps.setObject(4, don.getDateDer());
        ps.setObject(5, don.getGroupeSanguin());
        ps.setObject(6, don.getTypeDeDon());
        ps.setObject(7, don.getEtatMarital());
        ps.setObject(9, logincontroller.user.getId());

        // ps.setObject(8, don.getId_User());
        // Vérifiez si l'objet CentreDon associé à Dons est null
        if (don.getCentreDon() != null) {
            ps.setObject(8, don.getCentreDon().getId());
        } else {
            ps.setNull(8, Types.INTEGER); // Si null, définissez le centre_don_id sur NULL dans la base de données
        }

        ps.executeUpdate();


        System.out.println("Donation Added !");
    }

    @Override
    public void updateOne(CentreDon don) throws SQLException {

    }
///////UPDATE DONATION////////


    public void updateOne(Dons don) throws SQLException {
        String req = "UPDATE `Dons` SET `cin`=?, `genre`=?, `date_don`=?, `datedernierdon`=?, `groupe_sanguin`=?, `typededon`=?, `etatmarital`=?,`centre_don_id`=? =? WHERE `id`=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, don.getCin());
        ps.setString(2, don.getGenre());
        ps.setString(3, don.getDatePro());
        ps.setString(4, don.getDateDer());
        ps.setString(5, don.getGroupeSanguin());
        ps.setString(6, don.getTypeDeDon());
        ps.setString(7, don.getEtatMarital());
    // Assuming you have an id field in your don class
        if (don.getCentreDon() != null) {
            ps.setObject(8, don.getCentreDon().getId());
        } else {
            ps.setNull(8, Types.INTEGER); // Si null, définissez le centre_don_id sur NULL dans la base de données
        }
        ps.executeUpdate();
        System.out.println("Donation Updated !");
    }

    @Override
    public void deleteOne(int id) throws SQLException {


    }


    ///////DELETE DONATION////////


    @Override
    public boolean deleteOne(Dons don) throws SQLException {
        String req = "DELETE FROM `Dons` WHERE `id`=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, don.getId()); // Assuming you have an id field in your Donation class
        ps.executeUpdate();
        System.out.println("Donation Deleted !");
        return false;
    }
    @Override
    public List<Dons> selectAll() throws SQLException {
        List<Dons> donsList = new ArrayList<>();

        String req = "SELECT d.*, c.* FROM Dons d JOIN Centre_don c ON d.centre_don_id = c.id";
        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Dons don = new Dons();

            don.setId(rs.getInt(("id")));
            don.setCin(rs.getString("cin"));
            don.setTypeDeDon(rs.getString("typededon"));
            don.setGenre(rs.getString("genre"));
            don.setDatePro(rs.getString("date_don"));
            don.setDateDer(rs.getString("datedernierdon"));
            don.setGroupeSanguin(rs.getString("groupe_sanguin"));
            don.setEtatMarital(rs.getString("etatmarital"));
            don.setId_centre(rs.getInt("centre_don_id"));
            don.setId_User(rs.getInt("user_id"));
            // Récupérez l'ID du centre de don à partir de la colonne 'centreDonCol'
            // Assurez-vous d'utiliser le nom correct de la colonne dans votre ResultSet


            // Vous pouvez ajouter les autres champs de votre table 'Centre_don' ici si nécessaire

            // Ajoutez le don à la liste
            donsList.add(don);
        }

        return donsList;
    }
    public Map<String, Integer> countDonsByGroupeSanguin() throws SQLException {
        Map<String, Integer> countMap = new HashMap<>();
        String query = "SELECT groupe_sanguin, COUNT(*) AS count FROM Dons GROUP BY groupe_sanguin";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String groupeSanguin = resultSet.getString("groupe_sanguin");
                int count = resultSet.getInt("count");
                countMap.put(groupeSanguin, count);
            }
        }

        return countMap;
    }
    public List<XYChart.Data<String, Integer>> getBarChartData() throws SQLException {
        List<XYChart.Data<String, Integer>> barChartData = new ArrayList<>();

        String query = "SELECT groupe_sanguin, COUNT(*) AS count FROM Dons GROUP BY groupe_sanguin";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String groupeSanguin = resultSet.getString("groupe_sanguin");
                int count = resultSet.getInt("count");
                // Créer un nouvel objet XYChart.Data avec les données du groupe sanguin et le nombre de dons
                XYChart.Data<String, Integer> data = new XYChart.Data<>(groupeSanguin, count);
                // Ajouter les données à la liste
                barChartData.add(data);
            }
        }

        return barChartData;
    }

    public List<Dons> readByUserId(int id) throws SQLException {
        String sql = "SELECT * FROM Dons WHERE id=?";
        List<Dons> dons = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, logincontroller.user.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Dons don = new Dons();
                don.setId(rs.getInt(("id")));
                don.setCin(rs.getString("cin"));
                don.setTypeDeDon(rs.getString("typededon"));
                don.setGenre(rs.getString("genre"));
                don.setDatePro(rs.getString("date_don"));
                don.setDateDer(rs.getString("datedernierdon"));
                don.setGroupeSanguin(rs.getString("groupe_sanguin"));
                don.setEtatMarital(rs.getString("etatmarital"));
                don.setId_centre(rs.getInt("centre_don_id"));
                don.setId_User(rs.getInt("user_id"));
                dons.add(don);
            }
        }

        return dons;
    }
}
