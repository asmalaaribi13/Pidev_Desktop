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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.UserService;

public class CreerController implements Initializable{

    @FXML
    private TextField email;

    @FXML
    private ImageView image;

    @FXML
    private TextField nom;

    @FXML
    private TextField numtel;

    @FXML
    private TextField prenom;

    @FXML
    private PasswordField pwd;

    @FXML
    private ChoiceBox<String> typeUser;
     private String []type={"Admin","Client"};
         String path="";
     
       public void initialize(URL url, ResourceBundle rb) {
        typeUser.getItems().addAll(type);
    } 

    @FXML
    void creer(ActionEvent event) {
        Image photo=image.getImage();  ///image Done
        //    typeUser.getItems().addAll(type);
        //String selectedType=type;
        // Vérifier que tous les champs sont remplis
    if (nom.getText().isEmpty() || prenom.getText().isEmpty() || email.getText().isEmpty() || pwd.getText().isEmpty()) {
    // Afficher un message d'erreur
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText("Veuillez remplir tous les champs obligatoires.");
    alert.showAndWait();
    return;
}

if (!email.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
    // Afficher un message d'erreur
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText("Veuillez saisir une adresse e-mail valide.");
    alert.showAndWait();
    return;
}
UserService userService = new UserService();
 if (userService.isEmailUsed(email.getText())) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("L'adresse email est déjà utilisée par un autre utilisateur.");
        alert.showAndWait();
        return;
    }
// Validation du numéro de téléphone
     int num =0;
    try {
        num = Integer.parseInt(numtel.getText());
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le numéro de téléphone doit être un entier !");
        alert.showAndWait();
        return;
    }
    
    String numStr = Integer.toString(num);
    if (numStr.length() != 8 ) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le numéro de téléphone doit  contenir 8 chiffres !");
        alert.showAndWait();
        return;
        
    }
    
      
        User u=new User(nom.getText(),
                prenom.getText(),
                pwd.getText(),
                email.getText(),
                Integer.parseInt(numStr),
                typeUser.getValue(), 
                path);
        UserService US=new UserService();
      
          
        US.insert(u);
//        
//        
//        //MSG SUCCESS
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur ajouté avec succès !");
        alert.showAndWait();
        
//        //END MSG SUCCESS
//        
//        //Refrech
//          
    nom.setText("");
    prenom.setText("");
    pwd.setText("");
    email.setText("");
    numtel.setText("");
    typeUser.getSelectionModel().clearSelection();
    image.setImage(null);
        
//        

    }

    @FXML
    void uploadImage(ActionEvent event) {
                 FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
    // load the selected image into the image view
   path=selectedFile.getAbsolutePath();
    Image imageA = new Image(selectedFile.toURI().toString());
    image.setImage(imageA);
    }

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
                Logger.getLogger(CreerController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

}
