package services;

import models.Rendezvous;
import models.Rendezvous;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezvousService implements IService<Rendezvous>{

    private Connection connection;

    public RendezvousService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Rendezvous rendezvous) throws SQLException {
        String sql = "insert into rendezvous (heure,nommedecin,nompatient,date,etat,reponse_id)"+
                "values('"+rendezvous.getHeure()+"','"+rendezvous.getNommedecin()+"'" +
                ","+rendezvous.getNompatient()+rendezvous.getDate()+ ","+rendezvous.getEtat()+"','"+rendezvous.getReponse_id()+")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void update(Rendezvous rendezvous) throws SQLException {
        String sql = "update rendezvous set heure = ?,date=?, nommedecin = ?, nompatient,date=?,etat=?,reponse_id = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, rendezvous.getNommedecin());
        ps.setString(2, rendezvous.getNompatient());
        ps.setString(2, rendezvous.getHeure());
        ps.setDate(2, rendezvous.getDate());

        ps.setInt(3,rendezvous.getReponse_id());
        ps.setInt(4,rendezvous.getId());
        ps.executeUpdate();
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
            r.setEtat(rs.getInt("etat"));
            r.setHeure(rs.getString("heure"));

            rendezvous.add(r);
        }
        return rendezvous;
    }
}
