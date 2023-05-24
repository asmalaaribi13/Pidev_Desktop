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
public class ticketo {
    private int numTicketO ;
    private String  Logement ;
    private String  Restauration ;
    private float prix ;

    public ticketo() {
    }

    public ticketo(int numTicketO, String Logement, String Restauration, float prix) {
        this.numTicketO = numTicketO;
        this.Logement = Logement;
        this.Restauration = Restauration;
        this.prix = prix;
    }

    public int getNumTicketO() {
        return numTicketO;
    }

    public void setNumTicketO(int numTicketO) {
        this.numTicketO = numTicketO;
    }

    public String getLogement() {
        return Logement;
    }

    public void setLogement(String Logement) {
        this.Logement = Logement;
    }

    public String getRestauration() {
        return Restauration;
    }

    public void setRestauration(String Restauration) {
        this.Restauration = Restauration;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "ticketo{" + "numTicketO=" + numTicketO + ", Logement=" + Logement + ", Restauration=" + Restauration + ", prix=" + prix + '}';
    }

    

    
   
    
    

    
    
}
