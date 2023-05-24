package gui;

//import com.sun.mail.util.MailSSLSocketFactory;
//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import entity.User;
import entity.ticket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import service.UserService;
import utils.DataSource;
import java.util.Properties;
import javafx.scene.control.Alert.AlertType;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import service.ticketService;
import utils.Mail;

public class UserController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField pwd;
    private Connection connection;

    @FXML
    void Gotoacoount(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("creer.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        String e = email.getText();
        String mdp = pwd.getText();

        if (e == null || e.isEmpty() || mdp == null || mdp.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir l'email et le mot de passe.");
            alert.showAndWait();
            return;
        }

        UserService userService = new UserService();
        User utilisateur = userService.getUserByEmail(e);
        if (utilisateur == null || !mdp.equals(utilisateur.getPwd())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Email ou mot de passe incorrect.");
            alert.showAndWait();
            return;
        }

        if (utilisateur.getTypeUser().equals("Admin")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Bienvenue Admin");
            alert.setContentText("Vous êtes maintenant connecté!");
            alert.showAndWait();
//        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = (Parent) loader.load();
            MenuController MenuController = loader.getController();
            MenuController.setUser(utilisateur);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } else {      //cLIENT
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes maintenant connecté!");
            alert.showAndWait();

            // Récupération de l'utilisateur
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontClient.fxml"));
            Parent root = (Parent) loader.load();
            FrontClientController FrontClientController = loader.getController();
            FrontClientController.setUser(utilisateur);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void pwd(ActionEvent event) throws SQLException {

        //SMS
        UserService userService = new UserService();
        // Create a new text input dialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Phone Number");
        dialog.setHeaderText("Please enter your phone number:");
        dialog.setContentText("Phone number:");

        // Show the dialog and wait for the user's response
        Optional<String> result = dialog.showAndWait();

        // If the user entered a phone number, store it in a string
        if (result.isPresent()) {
            String phoneNumber = result.get();
            System.out.println("votre numTel est " + phoneNumber);
            if (!phoneNumber.isEmpty()) {
                String nouveauMotDePasse = genererMotDePasseAleatoire(8);
                System.out.println("le nouveaux mdp est : " + nouveauMotDePasse);
                User utilisateur = userService.readByNumtel(phoneNumber);
                //    System.out.println("utilis"+utilisateur.getIdUser()+"mdp"+nouveauMotDePasse);

                userService.setMotDePasse(utilisateur.getIdUser(), nouveauMotDePasse);
                SMS send = new SMS();
                //Mail.envoyer_modify_offre(new ticketService().get_event_by_id(ticket.getId_event()),offre);
                Mail.envoyer_User(utilisateur, nouveauMotDePasse);
                System.out.println("pho"+phoneNumber+"userName "+utilisateur.getNomUser()+"noubMdp"+nouveauMotDePasse);
                send.sendSMS(phoneNumber, utilisateur.getNomUser(), nouveauMotDePasse);
                System.out.println("mdp envoyer");

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Phone number cannot be empty.");
                alert.showAndWait();
            }

        }

        //email-----------------------------------------------
//    String e = email.getText();
//    if (e.isEmpty()) {
//        Alert alert = new Alert(AlertType.WARNING);
//        alert.setTitle("Attention");
//        alert.setHeaderText(null);
//        alert.setContentText("Veuillez saisir votre adresse email.");
//        alert.showAndWait();
//        System.out.println("email is not empty");
//        return;
//    }
//    UserService userService = new UserService();
//    User utilisateur = userService.readByEmail(e);
//    if (utilisateur == null) {
//        Alert alert = new Alert(AlertType.WARNING);
//        alert.setTitle("Attention");
//        alert.setHeaderText(null);
//        alert.setContentText("Cette adresse email n'existe pas.");
//        alert.showAndWait();
//        return;
//    }
//
//    String nouveauMotDePasse = genererMotDePasseAleatoire(8);
//        System.out.println("le nouveaux mdp est : "+nouveauMotDePasse);
//    userService.setMotDePasse(utilisateur.getIdUser(), nouveauMotDePasse);
//        System.out.println("le msg est remplacer");
//       // userService.toString();
//       
//    envoyerEmail(e, nouveauMotDePasse);
//        System.out.println("le mail est envoyer");
//    Alert alert = new Alert(AlertType.INFORMATION);
//    alert.setTitle("Succès");
//    alert.setHeaderText(null);
//    alert.setContentText("Un nouveau mot de passe a été envoyé à votre adresse email.");
//    alert.showAndWait();
    }

    //end
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String genererMotDePasseAleatoire(int longueur) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(longueur);
        for (int i = 0; i < longueur; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
//
//final String username = "tunmix697@gamil.com";
////final String password = "tunmix123";
//
//public void envoyerEmail(String e, String nouveauMotDePasse) throws MessagingException {
//        
//try {
//    
//    System.out.println("entrer dans envoyer email");
//    
//    // Recipient's email address
//    String to = "ibtihel.farhat@esprit.tn";
//    
//// Sender's email address
//String from = "sassiali12@outlook.com";
//
//// Sender's password
//String password = "tunmix123";
//
//// Host name of the SMTP server
//String host = "smtp.live.com";
//
//// Properties object for the mail session
//Properties properties = new Properties();
//
//// SMTP server properties
//properties.put("mail.smtp.host", host);
//properties.put("mail.smtp.port", "25");
//properties.put("mail.smtp.auth", "true");
//properties.put("mail.smtp.starttls.enable", "true");
//properties.put("mail.smtp.ssl.protocols", " TLSv1.0, TLSv1.3");
//System.setProperty("https.protocols", "TLSv1.1,TLSv1.2");
////props.put("mail.smtp.auth", "true");
////            props.put("mail.smtp.starttls.enable", "true");
//properties.put("mail.smtp.host", "smtp.gmail.com");
//MailSSLSocketFactory sf = new MailSSLSocketFactory();
//sf.setTrustAllHosts(true);
//// or
// sf.setTrustedHosts(new String[] { "my-server" });
//properties.put("mail.smtp.ssl.enable", "true");
//// also use following for additional safety
//properties.put("mail.smtp.ssl.checkserveridentity", "true");
//properties.put("mail.smtp.ssl.socketFactory", sf);
//
//
//
//properties.put("mail.smtp.ssl.ciphersuites", "TLS_AES_256_GCM_SHA384,TLS_AES_128_GCM_SHA256");
//
// Create a mail session with authentication
//Session session = Session.getInstance(properties,
//        new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        }
//);
//
//try {
//    // Create a default MimeMessage object
//    System.out.println("111111111");
//    MimeMessage message = new MimeMessage(session);
//    System.out.println("2222222222"+message.getSubject());
//    
//    // Set From: header field of the header
//    message.setFrom(new InternetAddress(username));
//    System.out.println("44444"+username);
//    
//    // Set To: header field of the header
//    message.addRecipient(Message.RecipientType.TO,
//            new InternetAddress(to));
//    System.out.println("55555"+to);
//    
//    // Set Subject: header field
//    message.setSubject("This is the Subject Line!");
//    System.out.println("666666"+message.getSubject());
//    
//    // Now set the actual message
//    message.setText("This is the message body");
//    System.out.println("777777"+message.getSubject());
//    
//    // Send message
//    
//    
//    Transport.send(message);
//    
//    
//    // Transport.send(message);
//    System.out.println("Sent message successfully....");
//} catch (MessagingException mex) {
//    mex.printStackTrace();
//  
//}
//
//} catch (GeneralSecurityException ex) {
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//  
//  
//  
//  
//}     

    // }
}
