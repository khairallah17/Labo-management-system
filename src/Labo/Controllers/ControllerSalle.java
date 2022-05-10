package Labo.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.TableModel;

import com.mysql.cj.xdevapi.Statement;

import Labo.DB.DatabaseConnection;
import Labo.Materiels.Materiels;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControllerSalle implements EventHandler<ActionEvent>, Initializable{
    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    
    @FXML
    private Button buttonFournisseur; 

    @FXML 
    private Button buttonSalle; 

    @FXML
    private Button buttonMateriel; 

    @FXML
    private Button buttonEtudiant; 

    @FXML 
    private Button buttonquitter;

    @FXML
    private DatePicker dateReservation;

    @FXML
    private TextField nombresReservation;

    @FXML
    private TextField nomreservation;

    @FXML
    private TableView<TableModel> materielsTable;

    @FXML
    private TableColumn<TableModel, String> nameTable;

    @FXML
    private TableColumn<TableModel, Integer> numbreTable;

    @FXML
    private TableColumn<TableModel, String> referenceTable;

    @FXML
    private TableColumn<TableModel, String> statusTable;

    @FXML
    protected void page(ActionEvent event, String ui) throws IOException {
        root = FXMLLoader.load(getClass().getResource(ui));
        stage = (Stage)((Node) (event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == buttonEtudiant){
            try {
                page(event, "../UI/Etudiant.fxml");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if(event.getSource() == buttonSalle){
            try {
                page(event, "../UI/Salle.fxml");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if(event.getSource() == buttonMateriel){
            try {
                page(event, "../UI/Materiel.fxml");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if(event.getSource() == buttonFournisseur){
            try {
                page(event, "../UI/Fournisseur.fxml");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if(event.getSource() == buttonquitter){
            Stage stage = (Stage) buttonquitter.getScene().getWindow();
            stage.close();
        }
    }

    public void addReservation() {
        String nom = nomreservation.getText();
        int nombre = Integer.parseInt(nombresReservation.getText());
        LocalDate date = dateReservation.getValue();
        
        String query = "INSERT INTO `materiels`.`reservationsalle` (`Nom`, `Nombresetudiant`, `Date`) VALUES ('"+nom+"', '"+nombre+"', '"+date+"');";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            java.sql.Statement stmt = cnx.createStatement();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<Materiels> getMaterielList() {

        Connection cnx = DatabaseConnection.getConnection();
        ObservableList<Materiels> list = FXCollections.observableArrayList();

        String stmt = "SELECT * FROM materiels.materiel";

        try{
            PreparedStatement pstmt = cnx.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();

            Materiels mat;

            while(rs.next()){
            //     System.out.println(rs.getString("Nom"));
                // System.out.println(rs.getString("Nom"));
                mat = new Materiels(rs.getString("Nom"), rs.getString("Reference"), Integer.parseInt(rs.getString("Quantite")), rs.getString("Module"), rs.getString("Status"));
                break;
                // list.add(new Materiels(rs.getString("Nom"), rs.getString("Reference"), Integer.parseInt(rs.getString("Quantite")), rs.getString("Module"), rs.getString("Status")));
            }


            // mat.getInfo();
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        // ObservableList<Materiels> list = 
        getMaterielList();

        // materielsTable.setItems(list);
    }
}
