package Labo.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Labo.DB.DatabaseConnection;
import Labo.classes.Materiels;
import Labo.classes.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class ControllerEtudiant implements EventHandler<ActionEvent>, Initializable{
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
    private TextField carteEtudiant;

    @FXML
    private DatePicker debutEtudiant;
    
    @FXML
    private DatePicker finEtudiant;

    @FXML
    private ComboBox materielEtudiant;

    @FXML
    private TextField nomEtudiant;

    @FXML
    private TableView<Reservation> studentTable;

    @FXML
    private TableColumn<Materiels, String> nomStudent;

    @FXML
    private TableColumn<Materiels, String> studentCard;

    @FXML
    private TableColumn<Materiels, Date> studentEnd;

    @FXML
    private TableColumn<Materiels, String> studentMaterial;

    @FXML
    private TableColumn<Materiels, Date> studentStart;

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

    public void reservationEtudiant() {
        String nom = nomEtudiant.getText();
        String carte = carteEtudiant.getText();
        Object materiel = materielEtudiant.getValue();
        LocalDate datedebut = debutEtudiant.getValue();
        LocalDate datefin = finEtudiant.getValue();
        
        String query = "INSERT INTO `materiels`.`reservationmateriel` (`Nom`, `CarteEtudiant`, `NomMateriel`, `DateDebut`, `DateFin`) VALUES ('"+nom+"', '"+carte+"', '"+materiel+"', '"+datedebut+"', '"+datefin+"');";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            java.sql.Statement stmt = cnx.createStatement();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void fetchMateriels(){
        ObservableList<String> list = FXCollections.observableArrayList();
        
        Connection cnx = DatabaseConnection.getConnection();
        String stmt = "SELECT Nom FROM materiels.materiel";

        try{
            PreparedStatement pstmt = cnx.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(rs.getString("Nom"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        materielEtudiant.setItems(list);
        
    }

    public void fillTable(){
        Connection cnx = DatabaseConnection.getConnection();
        ObservableList<Reservation> list = FXCollections.observableArrayList();

        String stmt = "SELECT * FROM materiels.reservationmateriel";

        try{
            PreparedStatement pstmt = cnx.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(new Reservation(rs.getString("Nom"), rs.getString("CarteEtudiant"), rs.getString("NomMateriel"), rs.getDate("DateDebut"), rs.getDate("DateFin")));
                studentTable.setItems(list);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub 

        fetchMateriels();
        fillTable();

        nomStudent.setCellValueFactory(new PropertyValueFactory<>("nom"));
        studentCard.setCellValueFactory(new PropertyValueFactory<>("cin"));
        studentMaterial.setCellValueFactory(new PropertyValueFactory<>("materiel"));
        studentStart.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        studentEnd.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
    }

}
