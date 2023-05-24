/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class AdminController implements Initializable {

    @FXML
    private ChoiceBox<String> typeUser;
    private String []type={"Admin","Client"};
    @FXML
    private TextField prenom;
    @FXML
    private TextField numtel;
    @FXML
    private TextField email;
    @FXML
    private TextField nom;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> Iduser;
    @FXML
    private TableColumn<User, String> nomU;
    @FXML
    private TableColumn<User, String> prenomU;
    @FXML
    private TableColumn<User, Integer> numtelU;
    @FXML
    private TableColumn<User, String> emailU;
    @FXML
    private TableColumn<User, String> image;
    @FXML
    private PasswordField pwd;
    @FXML
    private ImageView imageU;
    String path="";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeUser.getItems().addAll(type);
        //typeUser.setOnAction(this::getType);
        
    
        
        
        
        
    } 
//    public void getType(ActionEvent event){
//        selectedType =typeUser.getValue();
//        
//    }
    
    
    @FXML
    private void ajouterimage(ActionEvent event) {
        
       
        
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
    // load the selected image into the image view
    path=selectedFile.getAbsolutePath();
    Image image = new Image(selectedFile.toURI().toString());
    imageU.setImage(image);
    }

      
        
    }

    @FXML
    private void ajoute(ActionEvent event) {
        Image photo=imageU.getImage();  ///image Done
        
                String nomValue = nom.getText();
                String prenomValue = prenom.getText();
                String pwdValue = pwd.getText();
                String emailValue = email.getText();
                int numtelValue = Integer.parseInt(numtel.getText());
                String typeUserValue = typeUser.getValue();
                String imagePath = path;
        
                 // Validate email input
    if (emailValue.isEmpty() || !emailValue.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez saisir une adresse email valide.");
        alert.showAndWait();
        return;
    }
     // Check if email is already used by another user
    UserService userService = new UserService();
    if (userService.isEmailUsed(emailValue)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("L'adresse email est déjà utilisée par un autre utilisateur.");
        alert.showAndWait();
        return;
    }
    
    // Create new user object
    User u = new User(nomValue, prenomValue, pwdValue, emailValue, numtelValue, typeUserValue, imagePath);
    
    // Insert new user into the database
    userService.insert(u);
    
    // Show success message
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Utilisateur ajouté avec succès !");
    alert.showAndWait();
    
    // Clear input fields
    nom.setText("");
    prenom.setText("");
    pwd.setText("");
    email.setText("");
    numtel.setText("");
    typeUser.getSelectionModel().clearSelection();
    imageU.setImage(null);
        
        
        
        
        
        
        
        
        
//        
//        
//        
//        User u=new User(nom.getText(),
//                prenom.getText(),
//                pwd.getText(),
//                email.getText(),
//                Integer.parseInt(numtel.getText()),
//                typeUser.getValue(), 
//                path);
//        UserService US=new UserService();
//        US.insert(u);
//        
//        
//        //MSG SUCCESS
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Confirmation");
//        alert.setHeaderText(null);
//        alert.setContentText("Utilisateur ajouté avec succès !");
//        alert.showAndWait();
//        
//        //END MSG SUCCESS
//        
//        //Refrech
//          
//    nom.setText("");
//    prenom.setText("");
//    pwd.setText("");
//    email.setText("");
//    numtel.setText("");
//    typeUser.getSelectionModel().clearSelection();
//    imageU.setImage(null);
//        
        
    }
@FXML
    void VoirUser(ActionEvent event) {
         User selectedUser = table.getSelectionModel().getSelectedItem();
    
    if (selectedUser != null) {
      
        nom.setText(selectedUser.getNomUser());
        prenom.setText(selectedUser.getPrenomUser());
        pwd.setText(selectedUser.getPwd());
        email.setText(selectedUser.getEmail());
        numtel.setText(String.valueOf(selectedUser.getNumTel()));
        typeUser.setValue(selectedUser.getTypeUser());
       
         Image image = new Image(new File(selectedUser.getPhoto()).toURI().toString());
            imageU.setImage(image);
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un utilisateur à modifier.");
        alert.showAndWait();
    }

    }
    @FXML
    private void modifier(ActionEvent event) {
     User selectedUser = table.getSelectionModel().getSelectedItem();
    if (selectedUser != null) {
        String nomUser = nom.getText();
        String prenomUser = prenom.getText();
        String password = pwd.getText();
        String emailUser = email.getText();
        String typeString = typeUser.getValue();
        String photo = imageU.getImage().toString();
        int numTel = 0;
        try {
            numTel = Integer.parseInt(numtel.getText());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de téléphone doit être un entier.");
            alert.showAndWait();
            return;
        }
        if (nomUser.isEmpty() || prenomUser.isEmpty() || emailUser.isEmpty() || typeString == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return;
        }
        
         
        selectedUser.setNomUser(nomUser);
        selectedUser.setPrenomUser(prenomUser);
        selectedUser.setPwd(password);
        selectedUser.setEmail(emailUser);
        selectedUser.setNumTel(numTel);
        selectedUser.setTypeUser(typeString);
       // selectedUser.setPhoto(photo);

        UserService userService = new UserService();
        userService.update(selectedUser);

        // Afficher un message de confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur modifié avec succès !");
        alert.showAndWait();

        // Réinitialiser les champs
        nom.setText("");
        prenom.setText("");
        pwd.setText("");
        email.setText("");
        numtel.setText("");
        typeUser.getSelectionModel().clearSelection();
        imageU.setImage(null);
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un utilisateur à modifier.");
        alert.showAndWait();
    }
    
    
    
    

    
    
        
    }

    @FXML
    private void Supprimer(ActionEvent event) {
         if (table.getSelectionModel().getSelectedItem() != null) {
        // Récupérer les données de l'événement sélectionné
        User selectedUser = table.getSelectionModel().getSelectedItem();
        UserService es = new UserService();
        es.delete(selectedUser);
        
          }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succés");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur supprimé avec succès !");
        alert.showAndWait();
        
        Afficher(event);
        
    }

    @FXML
    private void Afficher(ActionEvent event) {
        UserService US =new UserService();
        
        List<User> users=US.readAll();
        
          ObservableList<User> userData = FXCollections.observableArrayList(users);
         table.setItems(userData);
         
         
        Iduser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        nomU.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        prenomU.setCellValueFactory(new PropertyValueFactory<>("prenomUser"));
        numtelU.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        emailU.setCellValueFactory(new PropertyValueFactory<>("email"));
        image.setCellValueFactory(new PropertyValueFactory<>("photo"));

//       table.setOnMouseClicked(User -> {
//        // Get the selected event
//        User selectedEvent = table.getSelectionModel().getSelectedItem();
//        if (selectedEvent != null) {
//            // Update the ImageView with the event's photo
//            Image image = new Image(new File(selectedEvent.getPhoto()).toURI().toString());
//            imageU.setImage(image);
//        }
//    });
         
        
    }
@FXML
    void retour(ActionEvent event) {
        try {
                Parent page1 = FXMLLoader.load(getClass().getResource("user.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                  


     
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    
    
}