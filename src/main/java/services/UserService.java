package services;

import entities.User;
import utils.MyDatabase;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService {
    private Connection con;

    public UserService() {
        con = MyDatabase.getInstance().getCon();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO User (id, nom, email, password, prenom, typemaladie, specialite, groupesanguin, statuteligibilite, token, brochure) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(user + "#####################");
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, user.getId());
            pre.setString(2, user.getNom());
            pre.setString(3, user.getEmail());
            pre.setString(4, hashPassword(user.getPassword()));
            pre.setString(5, user.getPrenom());
            pre.setString(6, user.getTypemaladie());
            pre.setString(7, user.getSpecialite());
            pre.setString(8, user.getGroupesanguin());
            pre.setString(9, user.getStatuteligibilite());
            pre.setString(10, user.getToken());
            pre.setString(11, user.getBrochure());


            pre.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String requete = "DELETE FROM User WHERE id_user = ?";
        try (PreparedStatement ps = con.prepareStatement(requete)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void ajouter(Object o) throws SQLException {

    }

    @Override
    public void supprimer(Object o) throws SQLException {

    }

    @Override
    public void modifier(Object o) throws SQLException {

    }

    public void resetPassword(String email, String password) {
        String req = "UPDATE User SET password = ? WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(req)) {
            ps.setString(1, hashPassword(password));
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Password updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(User user) throws SQLException {
        String req = "UPDATE User SET nom = ?, email = ?, password = ?, prenom = ?, typemaladie = ?, specialite = ?, groupesanguin = ?, statuteligibilite = ?, token = ?, brochure = ? WHERE id = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, user.getNom());
            pre.setString(2, user.getEmail());
            pre.setString(3, hashPassword(user.getPassword()));
            pre.setString(4, user.getPrenom());
            pre.setString(5, user.getTypemaladie());
            pre.setString(6, user.getSpecialite());
            pre.setString(7, user.getGroupesanguin());
            pre.setString(8, user.getStatuteligibilite());
            pre.setString(9, user.getToken());
            pre.setString(10, user.getBrochure());
            pre.setInt(11, user.getId());
            pre.executeUpdate();
            System.out.println("User updated successfully!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public List<User> afficher() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (PreparedStatement ste = con.prepareStatement(sql);
             ResultSet rs = ste.executeQuery()) {
            while (rs.next()) {
                User user = new User(1, 25, "john_doe", "john@example.com", "password123", "avatar.jpg", "ROLE_USER", "token");
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPrenom(rs.getString("prenom"));
                user.setTypemaladie(rs.getString("typemaladie"));
                user.setSpecialite(rs.getString("specialite"));
                user.setGroupesanguin(rs.getString("groupesanguin"));
                user.setStatuteligibilite(rs.getString("statuteligibilite"));
                user.setToken(rs.getString("token"));
                user.setBrochure(rs.getString("brochure"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    public boolean checkEmailExists(String email) {
        boolean result = false;
        String req = "SELECT * FROM User WHERE email = ?";
        try (PreparedStatement st = con.prepareStatement(req)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            result = rs.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
