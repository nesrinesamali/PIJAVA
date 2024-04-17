package services;

import models.Rendezvous;
import models.Rendezvous;
import models.Reponse;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseService implements IService<Reponse>{

    private Connection connection;

    public ReponseService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Reponse reponse) throws SQLException {
        String sql = "insert into reponse (date,description)"+
                "values('"+reponse.getDate()+"',"+reponse.getDescription()+")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void update(Reponse reponse) throws SQLException {
        String sql = "update rendezvous set date=?, description = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDate(1, reponse.getDate());
        ps.setString(2, reponse.getDescription());

        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from reponse where id=" + id;
        Statement statment = connection.createStatement();
        statment.executeUpdate(sql);

    }

    @Override
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
}
