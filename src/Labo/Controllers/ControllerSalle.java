package Labo.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import com.mysql.cj.xdevapi.Statement;

import Labo.DB.DatabaseConnection;
import Labo.classes.Salle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Salle, Date> salleDate;

    @FXML
    private TableColumn<Salle, String> salleNom;

    @FXML
    private TableColumn<Salle, Integer> salleNombre;

    @FXML
    private TableView<Salle> salleTable;

    MouseEvent event;

    ObservableList<Salle> list = FXCollections.observableArrayList();

    @FXML
    protected void page(ActionEvent event, String ui) throws IOException {
        root = FXMLLoader.load(getClass().getResource(ui));
        stage = (Stage)((Node) (event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public int getSelectedRow(){
        int index = salleTable.getSelectionModel().getFocusedIndex();

        if (index <= -1)
            return 0;
        
        nomreservation.setText(salleNom.getCellData(index));
        nombresReservation.setText(salleNombre.getCellData(index).toString());
        dateReservation.setValue(salleDate.getCellData(index).toLocalDate());

        return index;
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

    public int getSpecificItem(String nom){
        ObservableList<Salle> list = getSalleList();

        for (Salle salle : list) {
            if(salle.getNom().equals(nom))
                return salle.getId();
        }

        return 0;
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
            JOptionPane.showMessageDialog(null, "salle reserver");
        }catch(Exception e){
            e.printStackTrace();
        }

        updateTable();
    }

    public ObservableList<Salle> getSalleList() {

        list.clear();

        Connection cnx = DatabaseConnection.getConnection();

        String stmt = "SELECT * FROM materiels.reservationsalle";

        try{
            PreparedStatement pstmt = cnx.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(new Salle(rs.getInt("id"), rs.getString("Nom"), rs.getInt("Nombresetudiant"), rs.getDate("Date")));
                salleTable.setItems(list);
            }
            pstmt.close();
            rs.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public void deleteElement(){
        int index = getSelectedRow();

        String nom = salleNom.getCellData(index).toString();

        int id = getSpecificItem(nom);

        String query = "DELETE FROM `materiels`.`reservationsalle` WHERE (`Id` = '"+id+"');";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.execute();
            JOptionPane.showMessageDialog(null, "salle Supprimer");
        }catch(Exception e){
            e.printStackTrace();
        }

       
        updateTable();
    }

    public void modifieElement(){
        String nom = nomreservation.getText();
        LocalDate date = dateReservation.getValue();
        int qt = Integer.parseInt(nombresReservation.getText());

        int index = getSelectedRow();

        int id = getSpecificItem(salleNom.getCellData(index).toString());

        String query = "UPDATE `materiels`.`reservationsalle` SET `Nom` = '"+nom+"', `Date` = '"+date+"', `Nombresetudiant` = '"+qt+"' WHERE (`Id` = "+id+");";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            java.sql.Statement stmt = cnx.createStatement();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.execute();
            JOptionPane.showMessageDialog(null, "Materiel modifier");
        }catch(Exception e){
            e.printStackTrace();
        }

        updateTable();
    }

    public void updateTable(){
        getSalleList();

        salleNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        salleNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEtudiant"));
        salleDate.setCellValueFactory(new PropertyValueFactory<>("reservation"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();
    }
}
