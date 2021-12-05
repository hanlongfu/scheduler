package sample.controller;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    public static int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addBtn;

    @FXML
    private Label noTaskLbl;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    void initialize() {

        //when add item Btn is clicked
        addBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{


            //add fade transition to the add button once clicked
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), addBtn);
            FadeTransition labelTransition = new FadeTransition(Duration.millis(1000), noTaskLbl);

            addBtn.relocate(0, 20);
            noTaskLbl.relocate(0, 85);
            addBtn.setOpacity(0);
            noTaskLbl.setOpacity(0);

            fadeTransition.setFromValue(1f);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();

            labelTransition.setFromValue(1f);
            labelTransition.setToValue(0f);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();

            try{
                AnchorPane formPane = FXMLLoader.load(getClass().getResource("/sample/view/addItemForm.fxml"));

                AddItemController.userId = getUserId();

//                AddItemFormController addItemFormController = new AddItemFormController();
//                addItemFormController.setUserId(getUserId());

                //put addItemForm inside addItem pane
                rootAnchorPane.getChildren().setAll(formPane);

            }catch(IOException e) {
                e.printStackTrace();
            }


        });

    }

    //this will set the user id
    public void setUserId (int userId){
        this.userId = userId;
        System.out.println("User Id is " + this.userId);
    }

    //this will return the user id
    public int getUserId(){
        return this.userId;
    }


}
