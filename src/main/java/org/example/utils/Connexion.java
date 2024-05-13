package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    //DB PARAM
    static final String URL ="jdbc:mysql://localhost:3306/pidevsymfonyvita";
    static final String USER ="root";
    static final String PASSWORD ="";

    //var
    private Connection cnx;
    //1
    static Connexion instance;

    //const
    //2
    private Connexion(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public Connection getCnx() {
        return cnx;
    }

    //3
    public static Connexion getInstance() {
        if(instance == null)
            instance = new Connexion();

        return instance;
    }



}