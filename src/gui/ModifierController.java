/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.Produit;
import service.ServiceProduit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * FXML Controller class
 *
 * @author Brahim
 */
public class ModifierController implements Initializable {

    private Object PhotoP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
       @FXML
    private ImageView tPhotoP;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrix;

    @FXML
    private TextField txtQte;
     @FXML
    private TextField txtID;
    private Produit eventToUpdate;
    
    
    @FXML
    void modifier(ActionEvent event) throws IOException{
        
        
    String NomProduit = txtNom.getText();
    String DescProduit = txtDesc.getText();
    LocalDate DateProduit = txtDate.getValue();
    float PrixProduit = Float.parseFloat(txtPrix.getText());
    int Qte =  Integer.parseInt(txtQte.getText());
    String photo = eventToUpdate.getPhotoP();

//    // Contrôle de saisie
//    if (nomEvent.isEmpty() || localisationEvent.isEmpty() || dateDebutEvent == null || dateFinEvent == null || heureEvent.isEmpty() || descriptionEvent.isEmpty() || prixEvenement == 0.0) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Champ(s) vide(s)");
//        alert.setHeaderText(null);
//        alert.setContentText("Veuillez remplir tous les champs!");
//        alert.showAndWait();
//        return;
//    }

    // Mettre à jour l'objet event
    eventToUpdate.setNomProduit(NomProduit);
    eventToUpdate.setDescProduit(DescProduit);
    eventToUpdate.setDateProduit(DateProduit);
    eventToUpdate.setPrixProduit(PrixProduit);
    eventToUpdate.setQte(Qte);
    eventToUpdate.setPhotoP("aa");
    

    // Mettre à jour l'événement dans la base de données
    ServiceProduit ser = new ServiceProduit();
    ser.update(eventToUpdate);

    // Afficher une alerte pour signaler que la mise à jour est réussie
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Modifier");
    alert.setHeaderText(null);
    alert.setContentText("Modification avec succés!");
    alert.showAndWait();


    }

    void setEvent(Produit eventToUpdate) {
  this.eventToUpdate = eventToUpdate;
        // Afficher les données de l'événement à modifier dans le formulaire
        txtID.setText(String.valueOf(eventToUpdate.getIdProduit()));

        txtNom.setText(eventToUpdate.getNomProduit());
        txtDesc.setText(eventToUpdate.getDescProduit());
        txtDate.setValue(eventToUpdate.getDateProduit());
        txtPrix.setText(String.valueOf(eventToUpdate.getPrixProduit()));
        txtQte.setText(String.valueOf(eventToUpdate.getQte()));
        Image image = new Image(new File(eventToUpdate.getPhotoP()).toURI().toString());
        tPhotoP.setImage(image);

    }

    
}
