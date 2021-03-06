package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animations.Shaker;
import sample.model.User;
import sample.mysql.MySqlConnector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private int userId;

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginSignUp;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        MySqlConnector conn = new MySqlConnector();
        conn.connect();
        conn.creatingDB();
        conn.disconnect();

        loginButton.setOnAction(event -> {

            String loginText = loginUsername.getText().trim();
            String passwordText = loginPassword.getText().trim();

            User user = new User();
            user.setLogin(loginText);
            user.setPassword(passwordText);

            MySqlConnector connector = new MySqlConnector();
            int counter = 0;

            try {
                connector.connect();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            try {
                ResultSet userRow = connector.loginInUser(user);
                while ((userRow != null) && userRow.next()) {
                    counter++;
                    userId = userRow.getInt("user_id");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (counter == 1) {
                showMainPage();
            } else {
                Shaker shakerLogin = new Shaker(loginUsername);
                Shaker shakerPassword = new Shaker(loginPassword);
                shakerLogin.shake();
                shakerPassword.shake();
            }
        });

        loginSignUp.setOnAction(event -> {

            loginSignUp.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Personal To-Do Machine");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

    private void showMainPage(){
        loginSignUp.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/mainPage.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Personal To-Do Machine");
        stage.setScene(new Scene(root));

        MainPageController mainPageController = loader.getController();
        mainPageController.setUserId(userId);

        stage.showAndWait();
    }
}
