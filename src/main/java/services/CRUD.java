package services;
import models.CentreDon;
import models.Dons;

import java.sql.SQLException;
import java.util.List;


public interface CRUD<T> {
   

    void insertOne(T t) throws SQLException;
    void updateOne(CentreDon don) throws SQLException;
    void deleteOne(int id) throws SQLException; // Change parameter to int
      List<T> selectAll() throws SQLException;

    boolean deleteOne(Dons don) throws SQLException;
}