package sample.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import sample.Database.DbHandler;
import sample.model.Task;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ListController {

    @FXML private TextField listDescriptionField;
    @FXML private Button listSaveTaskBtn;
    @FXML private TextField listTaskField;
    @FXML private ListView<Task> taskList;
    @FXML private ImageView listRefreshBtn;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;
    private DbHandler dbHandler;

    @FXML
    void initialize() throws SQLException {
        tasks = FXCollections.observableArrayList();

        dbHandler = new DbHandler();
        ResultSet resultSet = dbHandler.getTasksById(AddItemController.userId);

        while(resultSet.next()) {

            Task task = new Task();
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            task.setTaskId(resultSet.getInt("taskid"));
            tasks.add(task);
        }
        taskList.setItems(tasks);
        //lambda expression to create a customized cell for each row
        taskList.setCellFactory(CellController -> new CellController());

        //add task through button on the left of the list view
        listSaveTaskBtn.setOnAction(event -> {
            try {
                addNewTask();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //refresh button is clicked
        listRefreshBtn.setOnMouseClicked(event->{
            try {
                refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


    }

    //when refresh button is clicked
    public void refreshList() throws SQLException{
        refreshedTasks = FXCollections.observableArrayList();

        dbHandler = new DbHandler();
        ResultSet resultSet = dbHandler.getTasksById(AddItemController.userId);

        while(resultSet.next()) {

            Task task = new Task();
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            task.setTaskId(resultSet.getInt("taskid"));

            refreshedTasks.addAll(task);

        }
        taskList.setItems(tasks);
        //lambda expression to create a customized cell for each row
        taskList.setCellFactory(CellController -> new CellController());
    }


    public void addNewTask() throws SQLException {
        if(!listTaskField.getText().equals("") || !listDescriptionField.getText().equals("")) {

            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            Task myNewTask = new Task();
            myNewTask.setUserId(AddItemController.userId);
            myNewTask.setTask(listTaskField.getText().trim());
            myNewTask.setDescription(listDescriptionField.getText().trim());
            myNewTask.setDatecreated(timestamp);

            dbHandler.insertTask(myNewTask);

            //clear the task and description fields
            listTaskField.setText("");
            listDescriptionField.setText("");

            //this will refresh the list view (in addition to the database)
            initialize();
        }

    }

}


