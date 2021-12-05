package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Database.DbHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.SQLException;

public class CellController extends ListCell<Task> {
    @FXML
    private Label dateLbl;

    @FXML
    private ImageView deleteBtn;

    @FXML
    private Label descriptionLbl;

    @FXML
    private ImageView iconImgView;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label taskLbl;

    private FXMLLoader fxmlLoder;

    private DbHandler dbHandler;


    @FXML
    void initialize() {
    }

    @Override
    public void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);

        if(empty || myTask == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoder == null) {
                fxmlLoder = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoder.setController(this);

                try {
                    fxmlLoder.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            taskLbl.setText(myTask.getTask());
            dateLbl.setText(myTask.getDatecreated().toString());
            descriptionLbl.setText(myTask.getDescription());


            //what happens when the delete button is clicked
            deleteBtn.setOnMouseClicked(event -> {

                //delete from the database
                dbHandler = new DbHandler();
                try {
                    dbHandler.deleteTask(AddItemController.userId, myTask.getTaskId());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                //delete from the list view
                getListView().getItems().remove(getItem());

            });



            setText(null);
            setGraphic(rootAnchorPane);

        }
    }

}
