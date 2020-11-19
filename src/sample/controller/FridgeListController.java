package sample.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.model.Product;
import sample.model.ShoppingList;
import sample.mysql.MySqlConnector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FridgeListController {

    @FXML
    private TextField frdigeNameField;

    @FXML
    private TextField frdigeQuantityField;

    @FXML
    private Button frdigeAddButton;

    @FXML
    private Button frdigeRemoveButton;

    @FXML
    private Label fridgeLabel;

    @FXML
    private Button listAddButton;

    @FXML
    private Button listRemoveButton;

    @FXML
    private Label listLabel;

    @FXML
    private Label frdigeFridgeLabel;

    @FXML
    private ListView<String> frdigeFridgeList;

    @FXML
    private Label frdigeShopListLabel;

    @FXML
    private ListView<String[]> frdigeShopList;

    @FXML
    private ImageView fridgeImageBack;

    private ObservableList<String> products;
    private ObservableList<String[]> shoppingList;

    private MySqlConnector connector;

    @FXML
    void initialize() throws IOException, SQLException, ClassNotFoundException {

        connector = new MySqlConnector();
        connector.connect();
        products = FXCollections.observableArrayList();
        //ResultSet resultSet = connector.getProductsByUser(MainPageController.userId);

        String resultSet1 = connector.getProductName(MainPageController.userId);
        products.addAll(resultSet1);
        frdigeFridgeList.setItems(products);

        String[] result = connector.getProducts(MainPageController.userId);
        shoppingList.addAll(result);
        frdigeShopList.setItems(result);

//        shoppingList = FXCollections.observableArrayList();
//        ResultSet resultSet = connector.getShoppingByUser(MainPageController.userId);
//        while (resultSet.next()) {
//            ShoppingList shopping = new ShoppingList();
//            shopping.setName(resultSet.getString("name"));
//            shoppingList.addAll(shopping);
//        }
//        frdigeShopList.setItems(shoppingList);


//        while (resultSet.next()) {
//            Product product = new Product();
//            //product.setProduct_id(resultSet.getInt("product_id"));
//            product.setName(resultSet.getString("name"));
//            //product.setQuantity(resultSet.getString("quantity"));
//
//            products.addAll(product);
//        }
//
//        frdigeFridgeList.setItems(products);



        fridgeImageBack.setOnMouseClicked(event -> {
            try {
                showMainPage(fridgeImageBack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showMainPage(ImageView image) throws IOException {
        image.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/mainPage.fxml"));

        loader.load();

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
