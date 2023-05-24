/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FrontClientController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Parent fxml;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @FXML
    private ImageView imageUser;

    @FXML
    void AccueilEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccueilEvent.fxml"));
            fxml = loader.load();
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
            AccueilEventController AccueilEventController = loader.getController();
            AccueilEventController.setUser(UserConnected);
        } catch (IOException ex) {
            Logger.getLogger(FrontClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void apropos(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Apropode-nous.fxml"));
            fxml = loader.load();
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);

        } catch (IOException ex) {
            Logger.getLogger(FrontClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void AccueilProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Marche.fxml"));
            fxml = loader.load();
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
            MarcheController MarcheController = loader.getController();
            MarcheController.setUser(UserConnected);
        } catch (IOException ex) {
            Logger.getLogger(FrontClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    User UserConnected;

    int setUser(User UserConnected) {
        this.UserConnected = UserConnected;
        Image imageP = new Image(new File(UserConnected.getPhoto()).toURI().toString());
        imageUser.setImage(imageP);
        return UserConnected.getIdUser();
    }

    @FXML
    void Profil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GererProfil.fxml"));
            fxml = loader.load();
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
            GererProfilController GererProfilController = loader.getController();
            GererProfilController.setUser(UserConnected);
        } catch (IOException ex) {
            Logger.getLogger(FrontClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void panier(ActionEvent event) throws IOException {

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("panier.fxml"));
//        Parent root = (Parent) loader.load();
//        PanierController PanierController = loader.getController();
//        PanierController.setUser(UserConnected);
//
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("panier.fxml"));
            fxml = loader.load();
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
            PanierController PanierController = loader.getController();
            PanierController.setUser(UserConnected);
        } catch (IOException ex) {
            Logger.getLogger(FrontClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void AjouterReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutReclamation.fxml"));
            fxml = loader.load();
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
            AjoutReclamationController ajoutReclamationController = loader.getController();
            ajoutReclamationController.setUser(UserConnected);
        } catch (IOException ex) {
            Logger.getLogger(FrontClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void mesReclamations(ActionEvent event) {
        try {
            if (UserConnected == null) {
                System.out.println("UserConnected is null");
            } else {
                System.out.println("UserConnected: " + UserConnected.toString());
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mesReclamations.fxml"));
            Parent fxml = loader.load();
            MesReclamationsController mesReclamationsController = loader.getController();
            mesReclamationsController.setUser(UserConnected);
            Stage stage = new Stage();
            Scene scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
