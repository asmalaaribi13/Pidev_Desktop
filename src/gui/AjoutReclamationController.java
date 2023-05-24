/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.Reclamation;
import entity.Type;
import entity.User;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.swing.filechooser.FileSystemView;
import service.ReclamationService;
import service.TypeService;

public class AjoutReclamationController implements Initializable {

    private User userConnected;
    @FXML
    private TextField tf_libelle;
    @FXML
    private ComboBox<Type> cb_type;
    @FXML
    private TextArea tf_desc;
    @FXML
    private ImageView iv;
    TypeService typeService = new TypeService();
    @FXML
    private Text imagePath;
    public String Url="C:/wamp64/www/Pidev_Web/public/Pictures//";
    public void setUser(User user) {
        this.userConnected = user;
    }
    ReclamationService recService = new ReclamationService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initListDeroulante();
         
    }

    private void initListDeroulante() {

        cb_type.setItems(FXCollections.observableList(typeService.readTypes()));

    }

    @FXML
    private void uploadImage(ActionEvent event) {
        List<String> listExt = new ArrayList<>();
        listExt.add("*.jpg");
        listExt.add("*.jpeg");
        listExt.add("*.png");
        listExt.add("*.JPG");
        listExt.add("*.JPEG");
        listExt.add("*.PNG");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", listExt));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            imagePath.setText(file.getAbsolutePath());
            String imagePath = file.toURI().toString();
            Image image = new Image(imagePath);
            iv.setImage(image);
        }
    }

    public String uniqueFilename() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 40;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
    @FXML
    private void ajoutReclamation(ActionEvent event) throws IOException {
        if (tf_libelle.getText().isEmpty() || tf_libelle.getText().length() < 3
                || tf_desc.getText().isEmpty() || tf_desc.getText().length() < 3
                || cb_type.getValue() == null || imagePath.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs du formulaire correctement");
            alert.showAndWait();
        } else {
            System.out.println(imagePath.getText());
            String imgName = "";
            String imgSelceted = "";
            String typeImg = "";
            int picpath = imagePath.getText().lastIndexOf('.');
            if (picpath > 0) {
                
//                Path sourceFile = Paths.get(imgSelceted.toPath().toString());

                

                try {
                    imgName = uniqueFilename() + "." + imagePath.getText().substring(picpath + 1);
                    imgSelceted = imagePath.getText();
                    typeImg = URLConnection.guessContentTypeFromName(imgSelceted);
                    FileSystemView view = FileSystemView.getFileSystemView();
                    //File homeDirectory = view.getHomeDirectory();
                    Path targetFile = Paths.get(Url+imgName);

                    //Files.copy(Paths.get(imgSelceted), Paths.get(homeDirectory.getPath() + "/" + imgName));
                    Files.copy(Paths.get(imgSelceted), targetFile,StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            } else {
                System.out.println("error");
            }

            Reclamation rec = new Reclamation();
            rec.setLibelle(tf_libelle.getText());
            rec.setDescription(tf_desc.getText());
            rec.setUser(userConnected);
            rec.setEtat_reclamation("en cours");
            rec.setType(cb_type.getSelectionModel().getSelectedItem());
            rec.setPhoto("Pictures/"+imgName);
            rec.setDate_reclamation(new Date(System.currentTimeMillis()));

            if (recService.insertReclamation(rec)) {
                SendEmail s =new SendEmail();
                try{
                    s.sendEmailToAdministrator();
                }
                catch(Exception e){
                    
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Reclamation a été ajouté");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Reclamation n'a pas été ajouté");
                alert.showAndWait();
            }

        }

    }
}
