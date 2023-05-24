/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import java.sql.Date;

/**
 *
 * @author Asma Laaribi
 */
public class Reclamation {
    private int id;
    private User user; 
    private Type type; 
    private String description;
    private String libelle;
    private Date date_reclamation;
    private String etat_reclamation;
    private String photo;
  

    public Reclamation() {
    }

   

    public Reclamation(User user, Type type, String description, Date date_reclamation, String etat_reclamation, String photo,String libelle) {
        this.user = user;
        this.type = type;
        this.description = description;
        this.date_reclamation = date_reclamation;
        this.etat_reclamation = etat_reclamation;
        this.photo = photo;
        this.libelle = libelle;
    }

    public Reclamation(int id, User user, Type type, String description, Date dateReclamation, String etatReclamation, String photo,String libelle) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.description = description;
        this.date_reclamation = dateReclamation;
        this.etat_reclamation = etatReclamation;
        this.photo = photo;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(Date date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getEtat_reclamation() {
        return etat_reclamation;
    }

    public void setEtat_reclamation(String etat_reclamation) {
        this.etat_reclamation = etat_reclamation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", user=" + user + ", type=" + type + ", description=" + description + ", libelle=" + libelle + ", date_reclamation=" + date_reclamation + ", etat_reclamation=" + etat_reclamation + ", photo=" + photo + '}';
    }

    
    

    
 

     
}
