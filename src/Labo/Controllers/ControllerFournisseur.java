package Labo.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.naming.spi.InitialContextFactory;
import javax.swing.JOptionPane;

import com.mysql.cj.xdevapi.Statement;

import Labo.DB.DatabaseConnection;
import Labo.classes.Fournisseur;
import javafx.beans.Observable;
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
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControllerFournisseur implements EventHandler<ActionEvent>, Initializable{
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
    private TextField fournisseurCIN;

    @FXML
    private TextField fournisseurEmail;

    @FXML
    private TextField fournisseurNomComplet;

    @FXML
    private TextField fournisseurNumero;
    
    @FXML
    private TableView<Fournisseur> fourniesseurTable;

    @FXML
    private TableColumn<Fournisseur, String> cinTable;

    @FXML
    private TableColumn<Fournisseur, String> emailTable;

    @FXML
    private TableColumn<Fournisseur, String> nomCompletTable;

    @FXML
    private TableColumn<Fournisseur, Integer> telTable;

    MouseEvent event;

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

    @FXML
    public int getSelectedRow(){
        int index = fourniesseurTable.getSelectionModel().getFocusedIndex();

        if(index <= -1)
            return 0;

        fournisseurNomComplet.setText(nomCompletTable.getCellData(index));
        fournisseurCIN.setText(cinTable.getCellData(index));
        fournisseurEmail.setText(emailTable.getCellData(index));
        fournisseurNumero.setText(telTable.getCellData(index).toString());

        return index;
    }

    public int getSpecificItem(String nom){
        ObservableList<Fournisseur> list = getFournisseurList();

        for (Fournisseur fournisseur : list) {
            if(fournisseur.getNomComplet().equals(nom))
                return fournisseur.getId();
        }

        return 0;
    }

    public void addFournisseur(){
        String nom = fournisseurNomComplet.getText();
        String cin = fournisseurCIN.getText();
        String email = fournisseurEmail.getText();
        int tel = Integer.parseInt(fournisseurNumero.getText());
        
        String query = "INSERT INTO `materiels`.`fournisseur` (`NomComplet`, `Email`, `CIN`, `Numero`) VALUES ('"+nom+"', '"+email+"', '"+cin+"', '"+tel+"');";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            java.sql.Statement stmt = cnx.createStatement();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Fournisseur Ajouter");
            clearTextFields();
        }catch(Exception e){
            e.printStackTrace();
        }

        updateTable();
    }

    public ObservableList<Fournisseur> getFournisseurList(){

        Connection cnx = DatabaseConnection.getConnection();
        ObservableList<Fournisseur> list = FXCollections.observableArrayList();

        String stmt = "SELECT * FROM materiels.fournisseur";

        try{
            PreparedStatement pstmt = cnx.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(new Fournisseur(rs.getInt("Id"),rs.getString("NomComplet"), rs.getString("Email"), rs.getString("CIN"), rs.getInt("Numero")));
                fourniesseurTable.setItems(list);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        
        return list;
    }

    public void modifieElement(){
        String nom = fournisseurNomComplet.getText();
        String email = fournisseurEmail.getText();
        String cin = fournisseurCIN.getText();
        int tel = Integer.parseInt(fournisseurNumero.getText());

        int index = getSelectedRow();

        int id = getSpecificItem(nomCompletTable.getCellData(index).toString());

        String query = "UPDATE `materiels`.`fournisseur` SET `NomComplet` = '"+nom+"', `Email` = '"+email+"', `CIN` = '"+cin+"', `Numero` = '"+tel+"' WHERE (`Id` = '"+id+"');";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            java.sql.Statement stmt = cnx.createStatement();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.execute();
            JOptionPane.showMessageDialog(null, "Materiel modifier");
            clearTextFields();
        }catch(Exception e){
            e.printStackTrace();
        }

        updateTable();
    }

    public void clearTextFields(){
        fournisseurCIN.setText("");
        fournisseurNomComplet.setText("");
        fournisseurNumero.setText("");
        fournisseurEmail.setText("");
    }

    public void deleteElement(){
        int index = getSelectedRow();

        String nom = nomCompletTable.getCellData(index).toString();

        int id = getSpecificItem(nom);

        String query = "DELETE FROM `materiels`.`fournisseur` WHERE (`Id` = '"+id+"');";

        try{
            Connection cnx = DatabaseConnection.getConnection();
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.execute();
            JOptionPane.showMessageDialog(null, "Materiel Supprimer");
            clearTextFields();
        }catch(Exception e){
            e.printStackTrace();
        }

        updateTable();
    }

    public void updateTable(){
        getFournisseurList();

        nomCompletTable.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        emailTable.setCellValueFactory(new PropertyValueFactory<>("email"));
        cinTable.setCellValueFactory(new PropertyValueFactory<>("cin"));
        telTable.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        updateTable();
    }
}