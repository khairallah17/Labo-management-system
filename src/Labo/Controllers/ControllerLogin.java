package Labo.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControllerLogin{
   @FXML
    Button connectionbutton;

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    @FXML
    protected void initialise(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../UI/Materiel.fxml"));
        stage = (Stage)((Node) (event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
