package services;

import entities.User;
import utils.MyDatabase;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService {
    private Connection con;
    private static UserService instance;



    public UserService() {
        con = MyDatabase.getInstance().getCon();

    }
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO User (id, nom, email, password, prenom, typemaladie, specialite, groupesanguin, statuteligibilite, token, brochure, roles) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            pre.setString(12, user.getRoles()); // Ajout du rôle

            pre.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }



    @Override
    public void supprimer(int id) throws SQLException {
        try {
            // Delete related records from reset_password_request table
            String resetPasswordRequestQuery = "DELETE FROM reset_password_request WHERE user_id = ?";
            try (PreparedStatement resetPasswordRequestStatement = con.prepareStatement(resetPasswordRequestQuery)) {
                resetPasswordRequestStatement.setInt(1, id);
                resetPasswordRequestStatement.executeUpdate();
            }

            // Now, delete the user from the User table
            String userDeleteQuery = "DELETE FROM User WHERE id = ?";
            try (PreparedStatement userDeleteStatement = con.prepareStatement(userDeleteQuery)) {
                userDeleteStatement.setInt(1, id);
                userDeleteStatement.executeUpdate();
            }
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

    public User login(String mail, String password) throws SQLException {
        String req = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, mail);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String encodedPassword = rs.getString("password");
            boolean passwordMatch = BCrypt.checkpw(password, encodedPassword);
            if (passwordMatch) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String prenom = rs.getString("prenom");
                String status = rs.getString("statuteligibilite");
                String reset_token = rs.getString("token");
                String roles = rs.getString("roles");
                String isbanned = rs.getString("status");
                User user = new User( id,  nom,  email,  roles,  prenom,  status,  reset_token,isbanned);
                return user;
            } else {
                // Login failed, return null
                return null;
            }
        } else {
            // Login failed, return null
            return null;
        }
    }

    public User getUserByEmail(String mail) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, mail);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            int id = result.getInt("id");
            String email = result.getString("email");
            String roles = result.getString("roles");
            String nom = result.getString("nom");
            String prenom = result.getString("prenom");
            String password = result.getString("password");
            String typemaladie = result.getString("typemaladie");
            String specialite = result.getString("specialite");
            String groupesanguin = result.getString("groupesanguin");
            String statuteligibilite = result.getString("statuteligibilite");
            String resetToken = result.getString("token");
            String brochure = result.getString("brochure");


             user = new User( id,  nom,  email,  password,  prenom,  typemaladie,  specialite,  groupesanguin,  statuteligibilite,  resetToken,  brochure,  roles);
        }

        return user;
    }
    public boolean checkUsernameExists(String email) throws SQLException {
        String query = "SELECT id FROM user WHERE email = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        boolean exists = resultSet.next();
        resultSet.close();
        statement.close();
        return exists;
    }

    public List<User> fetch() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user WHERE email NOT LIKE '%admin%'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setRoles(rs.getString("roles"));
            u.setEmail(rs.getString("email"));
            u.setStatus(rs.getString("status"));
            users.add(u);
        }
        return users;
    }

    public void ResetPaswword(String email, String password) {
        try {

            String req = "UPDATE user SET password = ? WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(req);

            ps.setString(1, password);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("Password updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void SetStatus (User u) {
  try {
      String sql = "UPDATE user SET status = ? WHERE id = ?";
      PreparedStatement statement = con.prepareStatement(sql);
      statement.setString(1, u.getStatus());
      statement.setInt(2, u.getId());
      int rowsUpdated = statement.executeUpdate();
      System.out.println("Rows updated: " + rowsUpdated);
  } catch (SQLException ex) {
      ex.printStackTrace();
  }
    }
}
