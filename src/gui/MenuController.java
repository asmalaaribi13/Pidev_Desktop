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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MenuController implements Initializable {



  

    /**
     * Initializes the controller class.
     */
    private Parent fxml;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imag;
    
    
    User UserConnected;

        @FXML
    void Events(ActionEvent event) {
        try {
            fxml=FXMLLoader.load(getClass().getResource("ReadEvent.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     int setUser(User UserConnected) {
        
         this.UserConnected=UserConnected;
          Image imageP = new Image(new File(UserConnected.getPhoto()).toURI().toString());
        imag.setImage(imageP);
        return UserConnected.getIdUser();
    }

    @FXML
    void Tickets(ActionEvent event) {
//          try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/Tickets.fxml"));
//                                                    Parent root = loader.load();
//                                                    Scene scene = new Scene(root); 
//                                                    Stage stage = new Stage();
//                                                    stage.setScene(scene);
//                                                    stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
//        }
          try {
            fxml=FXMLLoader.load(getClass().getResource("/Vues/Tickets.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Users(ActionEvent event) {
          try {
            fxml=FXMLLoader.load(getClass().getResource("admin.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void paiement(ActionEvent event) {
          try {
            fxml=FXMLLoader.load(getClass().getResource("Commande.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void products(ActionEvent event) {
           try {
            fxml=FXMLLoader.load(getClass().getResource("Insert.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void reclamations(ActionEvent event) throws IOException {
         try {
            fxml=FXMLLoader.load(getClass().getResource("EspaceAdmin.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
//          Stage stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("EspaceAdmin.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

    }
     @FXML
    void profil(ActionEvent event) throws IOException{
//      //  int profilUser=setUser(UserConnected);
//         System.out.println("menu admin connecter "+UserConnected);
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("GererProfil.fxml"));
//    Parent root = (Parent) loader.load();     
//   GererProfilController GererProfilController = loader.getController(); 
//    GererProfilController.setUser(UserConnected);
//    
//    Scene scene = new Scene(root);
//    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//    stage.setScene(scene);
//    stage.show();
    
    
    try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GererProfil.fxml"));
            fxml = loader.load();
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
            GererProfilController GererProfilController = loader.getController();
            GererProfilController.setUser(UserConnected);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
      @FXML
    void Reservations(ActionEvent event) {
             try {
            fxml=FXMLLoader.load(getClass().getResource("ReservationCRUD.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
