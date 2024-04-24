package test;

import entities.User;

import services.UserService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Créer un objet UserService pour accéder aux opérations CRUD
        UserService userService = new UserService();

        // Créer un nouvel utilisateur
        User newUser = new User(1, 25, "john_doe", "john@example.com", "password123", "avatar.jpg", "ROLE_USER", "token");

        // Ajouter l'utilisateur à la base de données
        userService.ajouter(newUser);
        System.out.println("Utilisateur ajouté avec succès.");

        // Afficher tous les utilisateurs
        System.out.println("Liste des utilisateurs :");
        for (User user : userService.afficher()) {
            System.out.println(user);
        }

        // Modifier l'utilisateur
        newUser.setAge(30);
        userService.modifier(newUser);
        System.out.println("Utilisateur modifié avec succès.");

        // Afficher à nouveau tous les utilisateurs après modification
        System.out.println("Liste des utilisateurs après modification :");
        for (User user : userService.afficher()) {
            System.out.println(user);
        }

        // Supprimer l'utilisateur
        userService.supprimer(newUser.getId_user());
        System.out.println("Utilisateur supprimé avec succès.");

        // Afficher à nouveau tous les utilisateurs après suppression
        System.out.println("Liste des utilisateurs après suppression :");
        for (User user : userService.afficher()) {
            System.out.println(user);
        }
    }
}
