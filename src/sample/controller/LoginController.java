package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database.DbHandler;
import sample.animations.Shaker;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private int userId;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField loginPassword;

    @FXML
    private Button loginSignupBtn;

    @FXML
    private TextField loginUserName;

    private DbHandler dbHandler;


    @FXML
    void initialize(){

        //instantiate the DbHandler class
        dbHandler = new DbHandler();

        //when login button is clicked
        loginBtn.setOnAction(event -> {
            //get username and password input
            String loginText = loginUserName.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);

            ResultSet userRow =  dbHandler.getUser(user);
            // return user from the database
            int counter = 0;
            try{
                while(userRow.next()) {
                    counter++;
                    String name = userRow.getString("firstname");
                    userId = userRow.getInt("userid");
                    System.out.println("Welcome: " + name);
                }
                if(counter == 1) {
                    // switch to the add Item screen once successful login
                    showAddItemScreen();
                } else {
                    // add a little animation to indicate unsuccessful login
                    Shaker userNameShaker = new Shaker(loginUserName);
                    Shaker pwdShaker = new Shaker(loginPassword);
                    userNameShaker.shake();
                    pwdShaker.shake();
                }
            } catch(SQLException e){
                e.printStackTrace();
            }

        });


        //when sign up button is clicked
        loginSignupBtn.setOnAction(event -> {

            //take users to signup screen
            loginSignupBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));
            try{
                loader.load();
            } catch(IOException e){
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });

    }


    //once logged in, show new screen to add item
    private void showAddItemScreen(){
        //take users to signup screen
        loginSignupBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/addItem.fxml"));
        try{
            loader.load();
        } catch(IOException e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        //this will obtain the controller of addItemController and set user id using setUserID method
        AddItemController addItemController = loader.getController();
        addItemController.setUserId(userId);

        stage.showAndWait();
    }


}
