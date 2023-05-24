/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.User;
import entity.event;
import entity.ticket;
import entity.ticketo;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author firas
 */
public class Mail {
 

    private static String username = "benameur.firas@esprit.tn";
    private static String password = "whszmtdoublcqjot";

    public static void envoyer_add_ticket(event evenement) {
// Etape 1 : Création de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //Set TLS encryption enabled
        props.put("mail.smtp.host", "smtp.gmail.com");  //Set SMTP host
        props.put("mail.smtp.port", "587"); //Set smtp port
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("benameur.firas@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Benameurfiras44@gmail.com"));
            message.setSubject("Ajout d'un ticket à l'evenenement : "+evenement.getNomEvent());
            message.setText("L'ajout du ticket à l'évenement : "+evenement.getNomEvent()+" a été effectué avec  succés." );
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
     public static void envoyer_modify_ticket(event evenement,ticket ticket) {
// Etape 1 : Création de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //Set TLS encryption enabled
        props.put("mail.smtp.host", "smtp.gmail.com");  //Set SMTP host
        props.put("mail.smtp.port", "587"); //Set smtp port
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("benameur.firas@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Benameurfiras44@gmail.com"));
            message.setSubject("Modification d'un ticket de l'evenenement : "+evenement.getNomEvent());
            message.setText("Modification du ticket de l'évenement : "+evenement.getNomEvent()+" a été effectué avec  succés." );
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static void envoyer_add_offre(event evenement, ticketo offre ) {
// Etape 1 : Création de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //Set TLS encryption enabled
        props.put("mail.smtp.host", "smtp.gmail.com");  //Set SMTP host
        props.put("mail.smtp.port", "587"); //Set smtp port
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
// Etape 2 : Création de l'objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("benameur.firas@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Benameurfiras44@gmail.com"));
            message.setSubject("Ajout d'un offre au ticket d'evenement : "+evenement.getNomEvent());
            message.setText("L'offre : Logement :  "+offre.getLogement()+" ,Restauration : "+offre.getRestauration()+" ,prix : "+offre.getPrix() +" a été ajouté avec succés au ticket"
                    + "de l'évenement "+evenement.getNomEvent());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
     public static void envoyer_User(User user,String mdp) {
// Etape 1 : Création de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //Set TLS encryption enabled
        props.put("mail.smtp.host", "smtp.gmail.com");  //Set SMTP host
        props.put("mail.smtp.port", "587"); //Set smtp port
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
// Etape 2 : Création de l'objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("benameur.firas@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Benameurfiras44@gmail.com"));
            message.setSubject("Mot de passe changer : "+user.getNomUser());
            message.setText("cher client "+user.getNomUser()+"\n Votre nouveau mot de passe est  :  "+mdp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void envoyer_modify_offre(event evenement,ticketo offre ) {
// Etape 1 : Création de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //Set TLS encryption enabled
        props.put("mail.smtp.host", "smtp.gmail.com");  //Set SMTP host
        props.put("mail.smtp.port", "587"); //Set smtp port
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
// Etape 2 : Création de l'objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("benameur.firas@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Benameurfiras44@gmail.com"));
            message.setSubject("Modification du l'offre du ticket d'evenement : "+evenement.getNomEvent());
            message.setText(" La modfication de L'offre : Logement :  "+offre.getLogement()+" ,Restauration : "+offre.getRestauration()+" ,prix : "+offre.getPrix() +" sont affectées avec succés au ticket"
                    + "de l'évenement "+evenement.getNomEvent());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}