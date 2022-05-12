package Labo.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import Labo.DB.DatabaseConnection;
import Labo.classes.Materiels;

import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

public class ControllerMateriel implements EventHandler<ActionEvent>, Initializable{
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
    private Button materielAjouter;
    
    @FXML
    private TextField materielModule;
  
    @FXML
    private TextField materielNom;
  
    @FXML
    private TextField materielNombre;
  
    @FXML
    private TextField materielReference;

    @FXML
    private TableView<Materiels> materielsTable;

    @FXML
    private TableColumn<Materiels, String> nameTable;

    @FXML
    private TableColumn<Materiels, Integer> numbreTable;

    @FXML
    private TableColumn<Materiels, String> referenceTable;

    @FXML
    private TableColumn<Materiels, String> statusTable;

    @FXML
    protected void page(ActionEvent event, String ui) throws IOException {
        root = FXMLLoader.load(getClass().getResource(ui));
        stage = (Stage)((Node) (event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    public void addMateriel(){
        String nom = materielNom.getText();
        String reference = materielReference.getText();
        String module = materielModule.getText();
        int quantity = Integer.parseInt(materielNombre.getText());
        
        String query = "INSERT INTO `materiels`.`materiel` (`Nom`, `Reference`, `Quantite`, `Module`, `Status`) VALUES ('"+nom+"', '"+reference+"', '"+quantity+"', '"+module+"', 'disponible');";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            Statement stmt = cnx.createStatement();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
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

    public ObservableList<Materiels> getMaterielList() {

        Connection cnx = DatabaseConnection.getConnection();
        ObservableList<Materiels> list = FXCollections.observableArrayList();

        String stmt = "SELECT * FROM materiels.materiel";

        try{
            PreparedStatement pstmt = cnx.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(new Materiels(rs.getString("Nom"), rs.getString("Reference"), Integer.parseInt(rs.getString("Quantite")), rs.getString("Module"), rs.getString("Status")));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        
        return list;
    }
    
    ObservableList<Materiels> listmat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection cnx = DatabaseConnection.getConnection();



        nameTable.setCellValueFactory(new PropertyValueFactory<Materiels, String>("Nom"));
        referenceTable.setCellValueFactory(new PropertyValueFactory<Materiels, String>("Reference"));
        numbreTable.setCellValueFactory(new PropertyValueFactory<Materiels, Integer>("Quantite"));
        statusTable.setCellValueFactory(new PropertyValueFactory<Materiels, String>("Status"));
    
        listmat = getMaterielList();
    }
}
