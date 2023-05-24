/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author ihebc
 */
import utils.MyConnection;
import service.InterfaceService;
import entity.Commande;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




  public class CommandeService implements InterfaceService<Commande> {
 
        Connection conn = MyConnection.getInstance().getConnection();
      public List<Commande> afficher() {
List<Commande> pers = new ArrayList<Commande>();
return pers;
      }
       public List<Commande> getCommannde() {
           List<Commande> pers = new ArrayList<Commande>();
        try {
            Statement st = conn.createStatement();
 
            String req = "SELECT * FROM commande";
 
 
 
            ResultSet result = st.executeQuery(req);
 
            while (result.next()) {
                Commande resultc = new Commande(result.getInt(1),result.getInt(2),result.getInt(4), result.getDate(3),result.getDouble(5));
                pers.add(resultc);
            }
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pers;
         }
    
    @Override
    public void ajouter(Commande c) {
    }
 
         public List<Commande> ajouterC(Commande c) {
          List<Commande> commande = new ArrayList<Commande>();
        try {
            Statement st=conn.createStatement();
            String qry= " INSERT INTO `commande` ( `id_client`, `date` , etat,totale) VALUES ('"+c.getId_client()+"', NOW(),'"+c.getEtat()+"' ,'"+c.getTotal()+"') ";
            st.executeUpdate(qry);
            System.out.println("mrigel ye brooo ");
 
            //String req = "SELECT etat from commande WHERE id_client='"+c.getId_client()+"'";
              ResultSet result = st.executeQuery(qry);
            //System.out.println("payer lhamdouleh");
            while (result.next()) {
               Commande c1 =new Commande(result.getInt(1),result.getInt(3),result.getDate(2),result.getDouble(4));
               commande.add(c1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 
        return commande;
    }
 
    @Override
    public void modifier(Commande  c , Commande c1) {
       Statement ste;
        try {
            ste = conn.createStatement();
             String qry="UPDATE `commande` SET `prenom`=aa WHERE idc= 1 ";
        ste.executeUpdate(qry);
          System.out.println("mrigel ye lhob ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 
 
    }
    public void modifierEtat(int etat , int idc)
    {
         Statement ste;
        try {    
            ste = conn.createStatement();
String qry="UPDATE `commande` SET `etat`='"+etat+"' WHERE idc= '" + idc + "' ";
        ste.executeUpdate(qry);
          System.out.println("modifieretat");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 
    }
        public void payer(int id_client)
    {
         try {
            Statement st = conn.createStatement();
 
            String req = "SELECT etat from commande WHERE id_client='"+id_client+"'";
              ResultSet result = st.executeQuery(req);
            System.out.println("payer lhamdouleh");
            while (result.next()) {
                if(result.getInt(1)==1)
                {
                 PanierService ps=new PanierService();
                 ps.supprimer(id_client);
                }
            }
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 
    }

    
 
 
    @Override
    public void supprimer(int i) {
          Statement ste;
        try {
            ste = conn.createStatement();
            String qry = "DELETE FROM `commande` WHERE idc='" + i + "'";
            ste.executeUpdate(qry);
            System.out.println("mrigel ye lhob ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public List<Commande> chercherCommande(int idC)
    {          List<Commande> commande = new ArrayList<Commande>();
         Statement ste;
        try {
            ste = conn.createStatement();
            String qry = "SELECT * FROM `commande` WHERE idc='" + idC+ "'";
            ste.executeQuery(qry);
            System.out.println("mrigel ye lhob ");
              ResultSet result = ste.executeQuery(qry);
            //System.out.println("payer lhamdouleh");
            while (result.next()) {
               Commande c1 =new Commande(result.getInt(1),result.getInt(2),result.getInt(4),result.getDate(3),result.getDouble(5));
               commande.add(c1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return commande;
    }
    public  List<Commande>  trierC()
    {
      List<Commande> commande = new ArrayList<Commande>();
 
          try {
            Statement st = conn.createStatement();
 
            String req = "SELECT * FROM commande "
                    + " ORDER BY date ASC";
 
            ResultSet result = st.executeQuery(req);
              System.out.println("cbon");
            while (result.next()) {
               Commande c1 =new Commande(result.getInt(1),result.getInt(2),result.getInt(4),result.getDate(3),result.getDouble(5));
               commande.add(c1);
                System.out.println(c1);
            }
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                  return commande;
    }
 
 
 
}
