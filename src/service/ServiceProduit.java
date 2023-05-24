/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Produit;
import utils.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Brahim
 */
public class ServiceProduit implements IService<Produit>{

    public static List<Produit> readIdNom() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
 
    Connection con=DataSource.getInstance().getCnx();
    
    private Statement ste;
    private List<Produit> p;

    public ServiceProduit() {
        
        try {
            ste=con.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    @Override
    public void ajouter(Produit p)  {
        try {
            String req = "INSERT INTO `produit` ( `NomProduit`, `DescProduit`, `DateProduit`, `PrixProduit`, `Qte`, `PhotoP` , `IdCategorie`) VALUES "
                    + "( '"+p.getNomProduit()+"', '"+p.getDescProduit()+"', '"+p.getDateProduit()+"','"+p.getPrixProduit()+"','"+p.getQte()+"','"+p.getPhotoP()+"', (select IdCategorie from categorie where NomCategorie='"+p.getNomCategorie()+"'));";

            
            ste.executeUpdate(req);
            
            } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void ajouterPST(Produit p)  {
        
        try {
    String req = "INSERT INTO `produit` ( `NomProduit`, `DescProduit`, `DateProduit`, `PrixProduit`, `Qte`, `PhotoP`,`IdCategorie`) VALUES ( ?,?,?,?,?,?,?);";

     PreparedStatement pre=con.prepareStatement(req);
        
     
     pre.setString(1,p.getNomProduit() );
     pre.setString(2, p.getDescProduit());
     pre.setDate(2, java.sql.Date.valueOf(p.getDateProduit()));
     pre.setFloat(4, p.getPrixProduit());
     pre.setInt(5, p.getQte());
//     FileInputStream fis = new FileInputStream(p.getPhotoP());
//      byte[] imageData = new byte[fis.available()];
//      fis.read(imageData);
//      fis.close();
//            
//
//     pre.setBytes(6, imageData);
     pre.setString(6, p.getPhotoP());
     pre.setString(7, p.getNomCategorie());
     pre.executeUpdate();
     
     
     } catch (SQLException ex) {
            System.err.println(ex.getMessage());
       }
//           catch (FileNotFoundException ex) {
//             Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
//         } catch (IOException ex) {
//             Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
//         }
    }

    @Override
    public void update(Produit p)  {
        try {
            String req = "UPDATE produit SET NomProduit=?,DescProduit=?,DateProduit=?,PrixProduit=?,Qte=?,PhotoP=?  WHERE IdProduit=?";
            PreparedStatement pst = con.prepareStatement(req);
            pst.setString(1, p.getNomProduit());
            pst.setString(2, p.getDescProduit());
            pst.setDate(3, java.sql.Date.valueOf(p.getDateProduit()));
            pst.setFloat(4, p.getPrixProduit());
            pst.setInt(5, p.getQte());
            pst.setString(6, p.getPhotoP());
            pst.setInt(7, p.getIdProduit());

            pst.executeUpdate();
            System.out.println("Produit_id " + p.getIdProduit()+":" + " modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     *
     * @param p
     */
    @Override
    public void supprime(Produit p) {
        try {
            String req = "DELETE FROM produit WHERE IdProduit=?";
            PreparedStatement pst = con.prepareStatement(req);
            pst.setInt(1, p.getIdProduit());
            pst.executeUpdate();
            System.out.println("Article supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
   

 @Override
    public List<Produit> readAll() {
          String requete="select  produit.*, NomCategorie from produit join categorie on categorie.IdCategorie = produit.IdCategorie";
        List<Produit> list=new ArrayList<>();
        
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                Produit p=new Produit(
                        rs.getInt("idProduit"),
                        rs.getString("NomProduit"),
                        rs.getString("DescProduit"),
                        rs.getDate("DateProduit").toLocalDate(),
                        rs.getFloat("PrixProduit"),
                        rs.getInt("Qte"),
                        rs.getString("PhotoP"),
                        rs.getString("NomCategorie"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
//    public void recherche_nom (String NomProduit){    
//    PreparedStatement  pst;
//        Produit tk=new ticket(); 
//        try {
//            event ev=get_event_by_name(event_name);  
//            System.out.println("event searched " + ev.getNomEvent());
//            if(ev!=null){
//                String req ="select * from ticket where idEvent="+ev.getIdEvent()+"";
//                pst = cnx.prepareStatement(req);
//                ResultSet rs = pst.executeQuery();
//                while (rs.next()) {
//                     tk.setNumTicket(rs.getInt(1));
//                     tk.setNbMaxT(rs.getInt(2));
//                     tk.setNbTDemande(rs.getInt(3));
//                     tk.setIdRes(rs.getInt(4));
//                     tk.setPrixF(rs.getFloat(5));
//                     tk.setId_event(rs.getInt(6));
//                 }
//            }else return null; 
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//        System.out.println("ticket searched "+ tk);
//        return tk;
//    }

    
    
    
    
    
    
    
    @Override
    public Produit findById(int IdProduit) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    


    public boolean AjouterProduit(Produit p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(Produit selectedEvent) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public ObservableList readAllProduitsIds() {
        String requete = "select * from produit";
        ObservableList<Produit> produitList = FXCollections.observableArrayList();
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                int id = resultSet.getInt("IdProduit");
                String nom = resultSet.getString("NomProduit");
                
                 Produit pdt = new Produit(id,nom);
                produitList.add(pdt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produitList;
    }

    public Produit retournerProduit(int idProduit) throws SQLException {
        Produit produit = new Produit();
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM produit where `IdProduit` = " + idProduit);
        while (res.next()) {
            int id = res.getInt("idProduit");
            String nomProduit = res.getString("nomProduit");
            LocalDate dateProduit = res.getDate("DateProduit").toLocalDate();
            float prixProduit = res.getFloat("prixProduit");
            int qte = res.getInt("qte");
            String photoP = res.getString("photoP");
            produit.setIdProduit(id);
            produit.setNomProduit(nomProduit);
            produit.setPrixProduit(prixProduit);
            produit.setQte(qte);
            produit.setPhotoP(photoP);
            produit.setDateProduit(dateProduit);
            System.out.println("produit"+produit.toString());
        }
        
        return produit;
    }
   
    
}
