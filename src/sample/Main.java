package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.mysql.MySqlConnector;

import java.io.IOException;
import java.sql.SQLException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {

        MySqlConnector connector = new MySqlConnector();
        connector.connect();
        connector.creatingDB();
        connector.disconnect();

        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));
        primaryStage.setTitle("Personal Managment System");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}