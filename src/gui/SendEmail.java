package gui;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    public void sendEmailToAdministrator() throws MessagingException {

        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "test.java9999@gmail.com"; // mail réservé pour l'application
        //Your gmail password
        String password = "wzpqvsyuhvorypup";

        //Create a session with account credentials
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(myAccountEmail, password);
                    }
                }
        );
        String recepient = "sassi.ali@esprit.tn"; //c'est notre administrateur  
        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("REC URGENT !");
            String htmlCode = "<h2><b>Une Nouvelle Réclamation est créee ! </b></h2>" +
                    "" +
                    "<img src= " + "https://thumbs.dreamstime.com/b/bouton-de-r%C3%A9clamation-69565221.jpg >";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            System.out.println("Exception");
        }
        return null;
    }
}
