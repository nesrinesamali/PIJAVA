package test;

import models.Rendezvous;
import models.User;
import services.RendezvousService;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        RendezvousService rs = new RendezvousService();
       User user= rs.getUserById(2);
        System.out.println(user);
        try {
            // Utilisation de la classe Date.valueOf pour convertir la chaîne de date en objet Date
            rs.create(new Rendezvous(1,2, "nounou", "ness",Date.valueOf("2001-04-11"), "12:12", false,user));
            //System.out.println(rs.read());
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }

}
