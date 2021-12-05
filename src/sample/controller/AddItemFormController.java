package sample.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Database.DbHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    private int userId;
    private DbHandler dbHandler;

    @FXML
    private Label successLbl;

    @FXML
    private Button todosBtn;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button saveTaskBtn;

    @FXML
    private TextField taskField;

    @FXML
    void initialize() {
        dbHandler = new DbHandler();

        saveTaskBtn.setOnAction(event -> {
            Task task = new Task();

            Calendar calendar = Calendar.getInstance();

            //this records the time in milliseconds when the save-task button is clicked
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            //this will obtain text from the add task textfields
            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            if(!taskText.equals("") || !taskDescription.equals("")) {

                System.out.println("User Id " + AddItemController.userId);
                task.setUserId(AddItemController.userId);
                task.setDatecreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskText);

                //insert task into the task table in the database
                dbHandler.insertTask(task);

                //display to the user that task has been added successfully
                successLbl.setVisible(true);
                todosBtn.setVisible(true);
                int taskNumber = 0;
                try {
                    taskNumber = dbHandler.getAllTasks(AddItemController.userId);
                } catch (SQLException | ClassNotFoundException e) {
                   e.printStackTrace();
                }
                todosBtn.setText("My 2DO's: " + taskNumber);

                //clear the task and description fields
                taskField.setText("");
                descriptionField.setText("");

                //fade out the message that shows successful add
                FadeTransition labelTransition = new FadeTransition(Duration.millis(1000), successLbl);
                labelTransition.setFromValue(1f);
                labelTransition.setToValue(0f);
                labelTransition.setCycleCount(1);
                labelTransition.setAutoReverse(false);
                labelTransition.play();

                //what happens when the "My 2DO's" button is clicked
                todosBtn.setOnAction(event1 -> {
                    //send user to the list screen
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/list.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                });

            } else {
                System.out.println("Nothing added!");
            }


        });

    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println(this.userId);
    }



}
