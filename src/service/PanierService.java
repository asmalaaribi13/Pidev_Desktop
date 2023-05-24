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

import entity.Commande;
import entity.Panier;
import entity.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyConnection;


public class PanierService implements InterfaceService<Panier> {

    Connection connection = MyConnection.getInstance().getConnection();

    public List<Panier> afficher() {
        List<Panier> pers = new ArrayList<Panier>();
        try {
            Statement st = connection.createStatement();

            String req = "SELECT * FROM `panier`";

            ResultSet result = st.executeQuery(req);

            while (result.next()) {
                Panier resultc = new Panier(result.getInt(1), result.getInt(2), result.getInt(3));
                pers.add(resultc);
            }

        } catch (SQLException ex) {
            System.out.println("leeeee");
        }
        return pers;
    }

      public List<Panier> getPanier(int id_client) {

        List<Panier> pers = new ArrayList<Panier>();
        try {
            Statement st = connection.createStatement();

            String req = "SELECT p.id_client, p.id_product,p.quantite, c.NomUser, pr.PrixProduit, pr.NomProduit "
                    + "FROM panier p "
                    + "JOIN user c ON p.id_client = c.IdUser "
                    + "JOIN produit pr ON p.id_product = pr.IdProduit "
                    + "WHERE p.id_client = '" + id_client + "' ";

            ResultSet result = st.executeQuery(req);

            while (result.next()) {
                Panier p=new Panier(result.getInt(2),result.getInt(3),result.getString(6),result.getInt(5),result.getString(4));
                
               pers.add(p);
                

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pers;
    }       
    //totalePanier
    public Double sommeProduit(int id_client) {
        Double somme = 0.0;
        List<Panier> pers = new ArrayList<Panier>();
        try {
            Statement st = connection.createStatement();

            String req = "SELECT  p.id_product,p.quantite, pr.PrixProduit, pr.NomProduit "
                    + "FROM panier p "
                    + "JOIN user c ON p.id_client = c.IdUser "
                    + "JOIN produit pr ON p.id_product= pr.IdProduit "
                    + "WHERE p.id_client = '" + id_client + "' ";

            ResultSet result = st.executeQuery(req);

            while (result.next()) {

                somme += result.getInt(2) * result.getInt(3);

            }
            String qry="UPDATE panier SET totale = '"+somme+"' WHERE id_client = '" + id_client + "'";
            st.executeUpdate(qry);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return somme;

    }

    @Override
    public void ajouter(Panier p) {
        try {
            
            int idp=0;
            int quantite=0;
            String req=" SELECT id_product , quantite FROM `panier` WHERE id_client='" +p.getId_client()+"'  ";
              Statement ste = connection.createStatement();
            ste.executeQuery(req);
             ResultSet result = ste.executeQuery(req);

            while (result.next()) {
                idp=result.getInt(1);
                quantite=result.getInt(2);
            }
            if(idp!=p.getId_product())
            {
             String qry = "INSERT INTO panier ( id_product,id_client, quantite) VALUES ('" + p.getId_product() + "', '" + p.getId_client() + "', '" + p.getQuantite() + "')";
            Statement st = connection.createStatement();
            st.executeUpdate(qry);
            System.out.println("n");
            }
            else{
               quantite=quantite+p.getQuantite();
             String updateReq = "UPDATE panier SET quantite = '"+quantite+"' WHERE id_client = '" + p.getId_client() + "' AND id_product= '"+p.getId_product()+"'" ;
              ste.executeUpdate(updateReq);
            System.out.println("m ");
                sommeProduit(p.getId_client());
                
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Panier c, Panier c1) {
        Statement ste;
        try {
            ste = connection.createStatement();
            String qry = "UPDATE `panier` SET `quantite`='" + c.getQuantite() + "',`id_product`='"+c.getId_product()+"' WHERE id_client= '" + c1.getId_client() + "' ";
            ste.executeUpdate(qry);
            System.out.println("m ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
   
    
    public void supprimerP(int id_client,int IdProduit ) {
        Statement ste;
        try {
            ste = connection.createStatement();
            String qry = "DELETE FROM `panier` WHERE id_client='" + id_client + "' AND id_product = '"+IdProduit+"'";
            ste.executeUpdate(qry);
            System.out.println("m ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

   
  public void decrementQuantite(int id_client , int IdProduit)
  {
      try {
        // Check if the product exists in the cart
        String selectReq = "SELECT quantite FROM panier WHERE id_client = ? AND id_product = ?";
        PreparedStatement selectPs = connection.prepareStatement(selectReq);
        selectPs.setInt(1, id_client);
        selectPs.setInt(2, IdProduit);
        ResultSet rs = selectPs.executeQuery();
 
        if (rs.next()) {
            int quantite = rs.getInt("quantite");
            if (quantite > 1) {
                // Product exists and quantity is more than 1, decrement the quantity
                String updateReq = "UPDATE panier SET quantite = ? WHERE id_client = ? AND id_product = ?";
                PreparedStatement updatePs = connection.prepareStatement(updateReq);
                updatePs.setInt(1, quantite-1);
                updatePs.setInt(2, id_client);
                updatePs.setInt(3, IdProduit);
                updatePs.executeUpdate();
                System.out.println("Product quantity decremented in the cart!");
            } else {
                // Product exists but quantity is 1, remove the product from the cart
                String deleteReq = "DELETE FROM panier WHERE id_client = ? AND id_product = ?";
                PreparedStatement deletePs = connection.prepareStatement(deleteReq);
                deletePs.setInt(1, id_client);
                deletePs.setInt(2, IdProduit);
                deletePs.executeUpdate();
                System.out.println("Product removed from the cart!");
            }
        } else {
            // Product does not exist in the cart
            System.out.println("Product not found in the cart!");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
 
   public void incrementQuantite(int id_client,int IdProduit)
  {
       try {
        // Check if the product exists in the cart
        String selectReq = "SELECT quantite FROM panier WHERE id_client = ? AND id_product = ?";
        PreparedStatement selectPs = connection.prepareStatement(selectReq);
        selectPs.setInt(1, id_client);
        selectPs.setInt(2, IdProduit);
        ResultSet rs = selectPs.executeQuery();
 
        if (rs.next()) {
            // Product exists in the cart, increment the quantity
            int quantite = rs.getInt("quantite");
            String updateReq = "UPDATE panier SET quantite = ? WHERE id_client = ? AND id_product = ?";
            PreparedStatement updatePs = connection.prepareStatement(updateReq);
            updatePs.setInt(1, quantite + 1);
            updatePs.setInt(2, id_client);
            updatePs.setInt(3, IdProduit);
            updatePs.executeUpdate();
            System.out.println("Product quantity incremented in the cart!");
        } else {
            // Product does not exist in the cart, add the product with quantity 1
            String insertReq = "INSERT INTO panier (id_client, IdProduit, quantite) VALUES (?, ?, 1)";
            PreparedStatement insertPs = connection.prepareStatement(insertReq);
            insertPs.setInt(1,id_client);
            insertPs.setInt(2, IdProduit);
            insertPs.executeUpdate();
            System.out.println("Product added to the cart!");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
  }
   

    @Override
    public void supprimer(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
