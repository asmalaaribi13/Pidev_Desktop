package service;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Services;
//
//import Entite.Categorie;
//import Entite.Produit;
//import Utils.DataSource;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Brahim
// */
//public abstract class ServiceCategorie implements IService<Categorie>{
//    Connection con=DataSource.getInstance().getConnection();
//    
//    private Statement ste;
//    private List<Categorie> c;
//
//    public ServiceCategorie {
//        
//        try {
//            ste=con.createStatement();
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
//    }
//    
//    
//    
//    
//    
//    public void ajouterC(Categorie c)  {
//        try {
//            String req = "INSERT INTO `categorie` ( `NomCategorie`) VALUES "
//                    + "( '"+c.getNomCategorie()+"');";
//
//            
//            ste.executeUpdate(req);
//            
//            } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
//    
//    
//    
//    public void ajouterPST(Categorie c)  {
//        
//        try {
//    String req = "INSERT INTO `categorie` ( `NomProduit`) VALUES ( ?);";
//
//     PreparedStatement pre=con.prepareStatement(req);
//        
//     
//     pre.setString(1,c.getNomCategorie() );
//     
//     pre.executeUpdate();
//     
//     
//     } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
//    
//    
//    
//    
//    public void supprimec(Categorie c) {
//        try {
//            String req = "DELETE FROM categorie WHERE IdCategorie=?";
//            PreparedStatement pst = con.prepareStatement(req);
//            pst.setInt(1, c.getIdCategorie());
//            pst.executeUpdate();
//            System.out.println("Categorie supprimé !");
//
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
//    
//    
//    
//    public List<Categorie> readAll() {
//          String requete="select * from categorie";
//        List<Categorie> list=new ArrayList<>();
//        
//        try {
//            Statement st=con.createStatement();
//            ResultSet rs=st.executeQuery(requete);
//            while(rs.next()){
//                Categorie c=new Categorie(
//                        rs.getInt("idCategorie"),
//                        rs.getString("NomCategorie"),
//                        
//                list.add(c);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
//    
//}
//
//
