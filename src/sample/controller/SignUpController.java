package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.mysql.MySqlConnector;

import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpSurname;

    @FXML
    private TextField signUpLogin;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private Button signUp;

    void initialize(){

        MySqlConnector connector = new MySqlConnector();

        signUp.setOnAction(event -> {
            System.out.println("klik");
            try {
                connector.signUpUser(signUpName.getText(), signUpSurname.getText(),
                        signUpLogin.getText(), signUpPassword.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


    }

}
