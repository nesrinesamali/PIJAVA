package org.example;
import models.Dons;
import services.ServiceDon;
import utils.MyDatabase;

import java.sql.SQLException;

import entities.User;
import services.UserService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Création d'une instance de UserService pour tester ses fonctions
        UserService userService = new UserService();}

    public static void testAjouter(UserService userService) throws SQLException {
        // Création d'un nouvel utilisateur pour tester l'ajout
        User newUser = new User(1, "john@example.com", "", "john", "doe", "typemaladie", "specialite", "groupesanguin", "statuteligibilite", "token", "brochure", "ROLE_USER");

        // Ajout de l'utilisateur
        userService.ajouter(newUser);

        // Vérification de l'ajout en affichant tous les utilisateurs
        System.out.println("Utilisateurs après ajout :");
        userService.afficher().forEach(System.out::println);
    }

    public static void testAfficher(UserService userService) {
        // Affichage de tous les utilisateurs
        System.out.println("Liste des utilisateurs :");
        userService.afficher().forEach(System.out::println);
    }

    public static void testModifier(UserService userService) throws SQLException {
        // Modification de l'utilisateur - vous pouvez ajouter une logique pour obtenir un utilisateur spécifique à modifier
        // Ici, je vais modifier le premier utilisateur de la liste retournée par userService.afficher()
        User userToModify = userService.afficher().get(0);
        userToModify.setNom("john_modified");
        userToModify.setEmail("modified_email@example.com");

        // Modification de l'utilisateur dans la base de données
        userService.modifier(userToModify);

        // Vérification de la modification en affichant tous les utilisateurs
        System.out.println("Utilisateurs après modification :");
        userService.afficher().forEach(System.out::println);
    }

    public static void testSupprimer(UserService userService) throws SQLException {
        // Suppression de l'utilisateur ayant l'identifiant 1
        userService.supprimer(1);

        // Vérification de la suppression en affichant tous les utilisateurs
        System.out.println("Utilisateurs après suppression :");
        userService.afficher().forEach(System.out::println);
    }
}
