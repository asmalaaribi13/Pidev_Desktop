/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.UserService;
/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class GererProfilController implements Initializable {

    /**
     * Initializes the controller class.
     */
        @FXML
    private TextField nom;
        
    User UserConnected;
      @FXML
    private TextField email;

    @FXML
    private ImageView image;

    

    @FXML
    private TextField numtel;
       @FXML
    private TextField id;


    @FXML
    void btn_changer_email(ActionEvent event) {
         String nouveauMail = email.getText();
        UserService US=new UserService();
         US.ChangerMail(nouveauMail, setUser(UserConnected));
          System.out.println("mail modifier");
         email.setText(nouveauMail);
         nom.setText(UserConnected.getNomUser());
         email.setText(UserConnected.getEmail());
numtel.setText(String.valueOf(UserConnected.getNumTel()));
Image imageP = new Image(new File(UserConnected.getPhoto()).toURI().toString());
    image.setImage(imageP);
    }

    @FXML
    void btn_changer_image(ActionEvent event) {
         // Ouvrir une boîte de dialogue pour sélectionner une nouvelle image
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Sélectionner une nouvelle image");
    File selectedFile = fileChooser.showOpenDialog(null);
    
    if (selectedFile != null) {
        // Traiter l'image
        Image newImage = new Image(selectedFile.toURI().toString());
        UserService US = new UserService();
        US.ChangerPhoto(selectedFile.getAbsolutePath(), setUser(UserConnected));
        
        // Afficher la nouvelle image
        image.setImage(newImage);
    }
    }

    @FXML
    void btn_changer_nom(ActionEvent event) {
         String nouveauNom = nom.getText();
         System.out.println("nouve"+nouveauNom);
         UserService US=new UserService();
         US.ChangerNom(nouveauNom, setUser(UserConnected));
         System.out.println("nom modifier");
         nom.setText(nouveauNom);
        //  nom.setText(UserConnected.getNomUser());
         email.setText(UserConnected.getEmail());
numtel.setText(String.valueOf(UserConnected.getNumTel()));
Image imageP = new Image(new File(UserConnected.getPhoto()).toURI().toString());
    image.setImage(imageP);
    }

    @FXML
    void btn_changer_num(ActionEvent event) {
        String nouveauTEL = numtel.getText();
        UserService US=new UserService();
         US.ChangerNumTel(nouveauTEL, setUser(UserConnected));
          System.out.println("numTel modifier");
         numtel.setText(nouveauTEL);
          nom.setText(UserConnected.getNomUser());
         email.setText(UserConnected.getEmail());
//numtel.setText(String.valueOf(UserConnected.getNumTel()));
Image imageP = new Image(new File(UserConnected.getPhoto()).toURI().toString());
    image.setImage(imageP);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test");
    }  
     int setUser(User userConnected) {
    this.UserConnected = userConnected; // mettre à jour l'attribut UserConnected
    System.out.println("utilisateu connecter :" + UserConnected);
    
    id.setText(String.valueOf(UserConnected.getIdUser()));
    nom.setText(UserConnected.getNomUser());
    email.setText(UserConnected.getEmail());
    numtel.setText(String.valueOf(UserConnected.getNumTel()));
    Image imageP = new Image(new File(UserConnected.getPhoto()).toURI().toString());
    image.setImage(imageP);

    return UserConnected.getIdUser();
}
     
     
}
