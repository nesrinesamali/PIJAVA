package controllers;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import services.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @FXML
    private TableColumn<?, ?> emailLB;

    @FXML
    private TableColumn<?, ?> nomLB;

    @FXML
    private TableColumn<?, ?> prenomLB;

    @FXML
    private TableColumn<?, ?> roleLB;

    @FXML
    private TableView<User> tabUsrs;


    @FXML
    private TextField SearchField;
    UserService us = new UserService();
    private List<User> UserList;


    public void fetchUsers() throws SQLException {
        List<User> list = us.fetch();
        nomLB.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomLB.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailLB.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleLB.setCellValueFactory(new PropertyValueFactory<>("roles"));

        boolean deleteColumnExists = false;
        for (TableColumn column : tabUsrs.getColumns()) {
            if (column.getText().equals("Action")) {
                deleteColumnExists = true;
                break;
            }
        }

        if (!deleteColumnExists) {
            TableColumn<User, Void> deleteColumn = new TableColumn<>("Action");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<User, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            User u = getTableView().getItems().get(getIndex());
                            UserService ps = new UserService();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Product");
                            alert.setHeaderText("Are you sure you want to delete this Product?");
                            alert.setContentText("This action cannot be undone.");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    System.out.println(u);
                                    ps.supprimer(u.getId());

                                    refreshTable();
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {

                                alert.close();
                            }

                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            });

            tabUsrs.getColumns().add(deleteColumn);
        }
        ObservableList<User> observableList = FXCollections.observableArrayList(list);
        tabUsrs.setItems(observableList);

    }

    public void refreshTable() {
        try {
            UserList = new UserService().fetch();
            tabUsrs.setItems(FXCollections.observableArrayList(UserList));
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fetchUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        SearchField.setOnKeyReleased(event -> search());

    }



    @FXML
    void search() {
        String searchString = SearchField.getText().trim().toLowerCase();
        ObservableList<User> filteredList = FXCollections.observableArrayList();

        if (searchString.isEmpty()) {
            // If the search string is empty, fetch all users again
            try {
                fetchUsers();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            for (User user : tabUsrs.getItems()) {
                // Check if the user's name starts with the search string
                if (user.getNom().toLowerCase().startsWith(searchString)) {
                    filteredList.add(user);
                }
            }

            // Update the TableView with the filtered list of users
            tabUsrs.setItems(filteredList);
        }
    }
}
