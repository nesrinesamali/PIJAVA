package org.example.service;
import org.example.models.prescription;
import org.example.utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class prescriptionService {

    Connection cnx = Connexion.getInstance().getCnx();



    public void add(prescription p) {
        try {

            String req = "INSERT INTO `prescription`( `dossier_medical_id`, `user_id`, `patient`, `doctor`, `created_at`, `medications`, `instructions`, `pharmacy`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getIdmed());
            ps.setInt(   2, p.getUserid());
            ps.setString(3, p.getPatient());
            ps.setString(   4, p.getDoctor());
            ps.setDate(5, p.getCreatedat());
            ps.setString(6, p.getMedication());
            ps.setString(7, p.getInstruction());
            ps.setString(8, p.getPharmacy());





            ps.executeUpdate();
            System.out.println("prescription Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }




    public ObservableList<prescription> fetch() {
        ObservableList<prescription> prescription = FXCollections.observableArrayList();
        try {

            String req = "SELECT * FROM prescription";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                prescription p = new prescription();
                p.setId(rs.getInt(1));
                p.setIdmed(rs.getInt(2));
                p.setUserid(rs.getInt(3));
                p.setPatient(rs.getString(4));
                p.setDoctor(rs.getString(5));
                p.setCreatedat(rs.getDate(6));
                p.setMedication(rs.getString(7));
                p.setInstruction(rs.getString(8));
                p.setPharmacy(rs.getString(9));


                prescription.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return prescription;
    }




    public void delete(int p) {
        try {
            String req ="DELETE FROM `prescription`  WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p);
            ps.executeUpdate();
            System.out.println("prescription Deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<prescription> rechercheprescription(int id) {
        List<prescription> prescription = new ArrayList<>();
        try {

            String req = "SELECT * FROM prescription WHERE id LIKE CONCAT(?, '%')";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                prescription p = new prescription();
                p.setId(rs.getInt(1));
                p.setIdmed(rs.getInt(2));
                p.setUserid(rs.getInt(3));
                p.setPatient(rs.getString(4));
                p.setDoctor(rs.getString(5));
                p.setCreatedat(rs.getDate(6));
                p.setMedication(rs.getString(7));
                p.setInstruction(rs.getString(8));
                p.setPharmacy(rs.getString(9));


                prescription.add(p);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return prescription;
    }


    public void Edit(int e,String patient,String doctor, Date date, String medic, String instru,String phar) {
        try {
            String req = "UPDATE `prescription` SET `patient`=?,`doctor`=?,`created_at`=?,`medications`=?, `instructions`=?, `pharmacy`=? WHERE `id`=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            // Set values for each parameter
            ps.setString(1, patient);
            ps.setString(2, doctor);
            ps.setDate(3, date);
            ps.setString(4, medic);
            ps.setString(5, instru);
            ps.setString(6, phar);


            ps.setInt(7, e);

            // Execute the update
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("prescription updated successfully!");
            } else {
                System.out.println("Failed to update prescription. No matching record found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
