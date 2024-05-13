package org.example.service;
import org.example.models.Medrecord;
import org.example.utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedrecordService {
    Connection cnx = Connexion.getInstance().getCnx();
    public void add(Medrecord p) {
        try {
            String req = "INSERT INTO `dossier_medical`( `user_id`, `patient`, `date`, `diagnosis`, `test`) VALUES(?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getUserid());
            ps.setString(   2, p.getPatient());
            ps.setDate(3, p.getDate());
            ps.setString(   4, p.getDiagnosis());
            ps.setString(5, p.getTest());





            ps.executeUpdate();
            System.out.println("Medrecord Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }




    public ObservableList<Medrecord> fetch() {
        ObservableList<Medrecord> Medrecord = FXCollections.observableArrayList();
        try {

            String req = "SELECT * FROM dossier_medical";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Medrecord p = new Medrecord();
                p.setId(rs.getInt(1));
                p.setUserid(rs.getInt(2));
                p.setPatient(rs.getString(3));
                p.setDate(rs.getDate(4));
                p.setDiagnosis(rs.getString(5));
                p.setTest(rs.getString(6));


                Medrecord.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Medrecord;
    }




    public void delete(int p) {
        try {
            String req ="DELETE FROM `dossier_medical`  WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p);
            ps.executeUpdate();
            System.out.println("Medrecord Deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<Medrecord> rechercheMedrecord(int id) {
        List<Medrecord> Medrecord = new ArrayList<>();
        try {

            String req = "SELECT * FROM dossier_medical WHERE id LIKE CONCAT(?, '%')";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Medrecord p = new Medrecord();
                p.setId(rs.getInt(1));
                p.setUserid(rs.getInt(2));
                p.setPatient(rs.getString(3));
                p.setDate(rs.getDate(4));
                p.setDiagnosis(rs.getString(5));
                p.setTest(rs.getString(6));
                Medrecord.add(p);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Medrecord;
    }


    public void Edit(int e,String patient, Date date, String diagnosis, String test) {
        try {
            String req = "UPDATE `dossier_medical` SET `patient`=?, `date`=?, `diagnosis`=?, `test`=? WHERE `id`=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            // Set values for each parameter
            ps.setString(1, patient);
            ps.setDate(2, date);
            ps.setString(3, diagnosis);
            ps.setString(4, test);

            ps.setInt(5, e);

            // Execute the update
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Medrecord updated successfully!");
            } else {
                System.out.println("Failed to update Medrecord. No matching record found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
