package services;

import entities.User;
import models.Reponse;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseService implements IService<Reponse>{

    private Connection connection;

    public ReponseService(){
        connection = MyDatabase.getInstance().getCon();
    }



    public void create(Reponse reponse) throws SQLException {
        String sql = "insert into reponse set date=?, description = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDate(1, reponse.getDate());
        ps.setString(2, reponse.getDescription());
        ps.executeUpdate();
    }


    public void update(Reponse reponse) throws SQLException {
        String sql = "update rendezvous set date=?, description = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDate(1, reponse.getDate());
        ps.setString(2, reponse.getDescription());

        ps.executeUpdate();
    }


    public void delete(int id) throws SQLException {
        String sql = "delete from reponse where id=" + id;
        Statement statment = connection.createStatement();
        statment.executeUpdate(sql);

    }


    public List<Reponse> read() throws SQLException {
        String sql = "select * from reponse";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Reponse> reponse = new ArrayList<>();
        while (rs.next()){
            Reponse r = new Reponse();
            r.setId(rs.getInt("id"));
            r.setDate(rs.getDate("date"));
            r.setDescription(rs.getString("dsecription"));


            reponse.add(r);
        }
        return reponse;
    }

    @Override
    public void ajouter(User user) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public void ajouter(Reponse reponse) throws SQLException {

    }

    @Override
    public void supprimer(Reponse reponse) throws SQLException {

    }

    @Override
    public void modifier(Reponse reponse) throws SQLException {

    }

    @Override
    public void resetPassword(String email, String password) {

    }

    @Override
    public void modifier(User user) throws SQLException {

    }

    @Override
    public List<Reponse> afficher() throws SQLException {
        return List.of();
    }
}
