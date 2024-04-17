package test;

import models.Rendezvous;
import services.RendezvousService;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        RendezvousService rs = new RendezvousService();
        try {
            // Utilisation de la classe Date.valueOf pour convertir la chaîne de date en objet Date
            rs.create(new Rendezvous(1,2,"nesrine", "nounou", Date.valueOf("2001-04-11"), "12:12", 1));
            System.out.println(rs.read());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
