package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.Database.DbHandler;
import sample.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpBtn;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpLocation;

    @FXML
    private TextField signUpPassword;

    @FXML
    private TextField signUpUserName;

    @FXML
    void initialize() {

        //when signup button is clicked
        signUpBtn.setOnAction(event -> {
            //insert a record into the database
            createUser();



        });

    }

    private void createUser(){
        DbHandler dbHandler = new DbHandler();

        String firstName = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpUserName.getText();
        String password = signUpPassword.getText();
        String location = signUpLocation.getText();
        String gender;
        if(signUpCheckBoxFemale.isSelected()) {
            gender = "Female";
        } else {
            gender = "Male";
        }

        User user = new User(firstName, lastName, userName, password, location, gender);

        try {
            dbHandler.signUpUser(user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}
