package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.model.Task;
import sample.mysql.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class ListController {

    @FXML
    private Label listLabel;

    @FXML
    private ImageView listRefreshButton;

    @FXML
    private ListView<Task> listListView;

    @FXML
    private TextField listTaskName;

    @FXML
    private TextField listTaskDesc;

    @FXML
    private Button listSaveButton;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;

    private MySqlConnector mySqlConnector;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        mySqlConnector = new MySqlConnector();
        tasks = FXCollections.observableArrayList();
        mySqlConnector.connect();
        ResultSet resultSet = mySqlConnector.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("task_id"));
            task.setTaskName(resultSet.getString("task_name"));
            task.setTaskDesc(resultSet.getString("task_desc"));
            task.setDatecreated(resultSet.getTimestamp("task_date"));

            tasks.addAll(task);
        }

        listListView.setItems(tasks);
        listListView.setCellFactory(CellController -> new CellController());

        listSaveButton.setOnAction(event -> {
            try {
                addNewTask();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        listRefreshButton.setOnMouseClicked(event -> {
            try {
                refreshTasks();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void refreshTasks() throws SQLException, ClassNotFoundException {
        refreshedTasks = FXCollections.observableArrayList();
        mySqlConnector.connect();

        ResultSet resultSet = mySqlConnector.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("task_id"));
            task.setTaskName(resultSet.getString("task_name"));
            task.setTaskDesc(resultSet.getString("task_desc"));
            task.setDatecreated(resultSet.getTimestamp("task_date"));

            refreshedTasks.addAll(task);
        }

        listListView.setItems(refreshedTasks);
        listListView.setCellFactory(CellController -> new CellController());
        // test

    }

    public void addNewTask() throws SQLException, ClassNotFoundException {
        String taskName = listTaskName.getText().trim();
        String taskDesc = listTaskDesc.getText().trim();

        Task task = new Task();

        Calendar calendar = Calendar.getInstance();

        java.sql.Timestamp timestamp =
                new java.sql.Timestamp(calendar.getTimeInMillis());

        mySqlConnector.connect();

        task.setTaskName(taskName);
        task.setTaskDesc(taskDesc);
        task.setUserId(AddItemController.userId);
        task.setDatecreated(timestamp);

        mySqlConnector.insertTask(task);

        listTaskName.setText("");
        listTaskDesc.setText("");

        initialize();
    }
}