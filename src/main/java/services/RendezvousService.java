package services;

import models.Rendezvous;
import models.Rendezvous;
import models.User;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RendezvousService implements IService<Rendezvous>{


    private Connection connection;

    public RendezvousService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Rendezvous rendezvous) throws SQLException {
        String sql = "INSERT INTO rendezvous (reponse_id, nompatient, nommedecin, date, heure, etat, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pijava", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values for parameters
            pstmt.setNull(1, java.sql.Types.VARCHAR);
            pstmt.setString(2, rendezvous.getNompatient());
            pstmt.setString(3, rendezvous.getNommedecin());
            pstmt.setDate(4, rendezvous.getDate());
            pstmt.setString(5, rendezvous.getHeure());
            pstmt.setBoolean(6, rendezvous.getEtat());
           // pstmt.setInt(7, rendezvous.getUser().getId());


            User user = rendezvous.getUser();
            if (user != null) {
                pstmt.setInt(7, user.getId());
            } else {
                // Handle the case where the User object is null
                // For example, you can set user_id to NULL or throw an exception
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            // Execute the statement
            pstmt.executeUpdate();
            // Execute the statement
            //pstmt.executeUpdate();
        }

    }



    @Override
    public void update(Rendezvous rd) throws SQLException {
        String query = "UPDATE rendezvous SET nompatient=?, nommedecin=?, date=?, heure=?,etat=?  WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, rd.getNompatient());
            preparedStatement.setString(2, rd.getNommedecin());
            preparedStatement.setDate(3, rd.getDate());
            preparedStatement.setString(4, rd.getHeure());
            preparedStatement.setBoolean(5,rd.getEtat());
            preparedStatement.setInt(6,rd.getId());
            preparedStatement.executeUpdate();
        }
    }


    public void updateResponseId(Rendezvous rd) throws SQLException {
        String query = "UPDATE rendezvous SET reponse_id=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, rd.getReponse_id());
            preparedStatement.setInt(2, rd.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from rendezvous where id=" + id;
        Statement statment = connection.createStatement();
        statment.executeUpdate(sql);

    }


    @Override
    public List<Rendezvous> read() throws SQLException {
        String sql = "select * from rendezvous";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Rendezvous> rendezvous = new ArrayList<>();
        while (rs.next()){
            Rendezvous r = new Rendezvous();
            r.setId(rs.getInt("id"));
            r.setDate(rs.getDate("date"));
            r.setNompatient(rs.getString("nompatient"));
            r.setNommedecin(rs.getString("nommedecin"));
            r.setEtat(rs.getBoolean("etat"));
            r.setHeure(rs.getString("heure"));
            System.out.println(r.getEtat());
            rendezvous.add(r);
        }
        return rendezvous;
    }


    public User getUserById(int userId) {
        String selectQuery = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = MyDatabase.getInstance().getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                }

                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String typemaladie = resultSet.getString("typemaladie");
                    String specialite= resultSet.getString("specialite");
                    String groupesanguin = resultSet.getString("groupesanguin");
                    int brochure = resultSet.getInt("brochure");
                    Boolean is_banned=resultSet.getBoolean("is_banned");
                    Boolean is_verified=resultSet.getBoolean("is_verified");
                    String roles = resultSet.getString("roles");
                    String email = resultSet.getString("email");
                    int id=resultSet.getInt("id");
                    String password= resultSet.getString("password");

                    return new User(id,nom,prenom,typemaladie,specialite,groupesanguin,brochure,is_verified,is_banned,password,roles,email);
                }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


    }

    public List<Rendezvous> getRendezvousByDate(LocalDate date) {
        List<Rendezvous> rendezvousForDate = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous WHERE date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, Date.valueOf(date));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Rendezvous rendezvous = new Rendezvous();
                    rendezvous.setId(resultSet.getInt("id"));
                    rendezvous.setNompatient(resultSet.getString("nompatient"));
                    rendezvous.setNommedecin(resultSet.getString("nommedecin"));
                    rendezvous.setDate(resultSet.getDate("date"));
                    rendezvous.setHeure(resultSet.getString("heure"));
                    rendezvous.setEtat(resultSet.getBoolean("etat"));
                    rendezvousForDate.add(rendezvous);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
        return rendezvousForDate;
    }


}

