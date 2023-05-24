/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author firas
 */
public class ticket {
     private int numTicket; 
     
    private int nbMaxT;
    private int nbTDemande;
     private int idRes;
    private float PrixF;
    private int id_event;
    private ticketo numTicketO;

    public ticket() {
    }

    public ticket(int numTicket, int nbMaxT, int nbTDemande, int idRes, float PrixF, int id_event, ticketo numTicketO) {
        this.numTicket = numTicket;
        this.nbMaxT = nbMaxT;
        this.nbTDemande = nbTDemande;
        this.idRes = idRes;
        this.PrixF = PrixF;
        this.id_event = id_event;
        this.numTicketO = numTicketO;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public int getNbMaxT() {
        return nbMaxT;
    }

    public void setNbMaxT(int nbMaxT) {
        this.nbMaxT = nbMaxT;
    }

    public int getNbTDemande() {
        return nbTDemande;
    }

    public void setNbTDemande(int nbTDemande) {
        this.nbTDemande = nbTDemande;
    }

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }

    public float getPrixF() {
        return PrixF;
    }

    public void setPrixF(float PrixF) {
        this.PrixF = PrixF;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public ticketo getNumTicketO() {
        return numTicketO;
    }

    public void setNumTicketO(ticketo numTicketO) {
        this.numTicketO = numTicketO;
    }

    @Override
    public String toString() {
        return "ticket{" + "numTicket=" + numTicket + ", nbMaxT=" + nbMaxT + ", nbTDemande=" + nbTDemande + ", idRes=" + idRes + ", PrixF=" + PrixF + ", id_event=" + id_event + ", numTicketO=" + numTicketO + '}';
    }

    

    
   
    

    
}
