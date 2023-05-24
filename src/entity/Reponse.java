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
public class Reponse {
    private int id;
    private Reclamation reclamation_id; 
    private String message;
    private Date date_reponse;

    public Reponse() {
    }

    public Reponse(int id, Reclamation reclamation_id, String message, Date date_reponse) {
        this.id = id;
        this.reclamation_id = reclamation_id;
        this.message = message;
        this.date_reponse = date_reponse;
    }

    public Reponse(Reclamation idReclamation, String message, Date date_reponse) {
        this.reclamation_id = idReclamation;
        this.message = message;
        this.date_reponse= date_reponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reclamation getReclamation_id() {
        return reclamation_id;
    }

    public void setReclamation_id(Reclamation reclamation_id) {
        this.reclamation_id = reclamation_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(Date date_reponse) {
        this.date_reponse = date_reponse;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", reclamation_id=" + reclamation_id + ", message=" + message + ", date_reponse=" + date_reponse + '}';
    }

    
    
 
     
}
