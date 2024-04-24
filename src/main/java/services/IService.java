package services;

import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(User user) throws SQLException;

    void supprimer(int id) throws SQLException;

    void ajouter(T t) throws SQLException;
    void supprimer(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void resetPassword(String email, String password);

    void modifier(User user) throws SQLException;

    List<T> afficher() throws SQLException;
}
