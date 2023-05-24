/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.ticket;
import entity.ticketo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Mail;
import utils.MyDB;

/**
 *
 * @author firas
 */
public class ticketoService implements IService<ticketo>{
    
    Connection cnx;

    public ticketoService() {
        cnx = MyDB.getInstance().getConnection();
    }
        
        

    public void insert(ticketo tk) {
        
        String req = "INSERT INTO ticketo (Logement,Restauration,Prix) VALUES(?,?,?)" ;
        PreparedStatement  pst;
        try {
            
            pst = cnx.prepareStatement(req);
            pst.setString(1, tk.getLogement());
            pst.setString(2, tk.getRestauration());
            pst.setFloat(3, tk.getPrix());
            pst.executeUpdate();
            System.out.println("offre avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void insert_tickto(ticketo tk,ticket tkt) {
        
        String req = "INSERT INTO ticketo (Logement,Restauration,Prix) VALUES(?,?,?)" ;
        String generatedColumns[] = { "numTicketO" };
        PreparedStatement  pst; 
        int id_ticketo=0;
        try {
            
            pst = cnx.prepareStatement(req,generatedColumns);
            pst.setString(1, tk.getLogement());
            pst.setString(2, tk.getRestauration());
            pst.setFloat(3, tk.getPrix());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                 id_ticketo= rs.getInt(1);
            }
            ticketService ticket_service=new ticketService();  
            ticket_service.update_ticketo_add(tkt, id_ticketo);
            Mail.envoyer_add_offre(ticket_service.get_event_by_id(tkt.getId_event()),tk);
            System.out.println("offre avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
       
        
    

    public void delete(ticketo t) {

        
              String requete="delete from ticketo where numTicketo = "+t.getNumTicketO();
        try {
            Statement st=cnx.createStatement();

            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ticketService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ticketo t) {
         
           try {
            String req =  "UPDATE ticketo SET Logement = '" + t.getLogement()+  "', Restauration = '" + t.getRestauration()+ "', prix = '" + t.getPrix() + "' WHERE ticketo.`NumTicketo` = " + t.getNumTicketO();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("ticketo updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<ticketo> getAll() {
        
        List<ticketo> ticketo_list = new ArrayList();
        PreparedStatement  pst;
        try {
            String req ="select * from ticketo ORDER BY numTicketO DESC";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) { 
                 ticketo tk=new ticketo(); 
                 tk.setNumTicketO(rs.getInt(1));
                 tk.setLogement(rs.getString(2));
                 tk.setRestauration(rs.getString(3));
                 tk.setPrix(rs.getFloat(4));
               
                 ticketo_list.add(tk);              
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return ticketo_list;
    }
    
    

   
    public ticketo get_ticket_by_id(int id) {
        PreparedStatement  pst;
        ticketo tk=new ticketo(); 
        try {
            
            String req ="select * from ticketo where numTicketO="+id+"";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 tk.setNumTicketO(rs.getInt(1));
                 tk.setLogement(rs.getString(2));
                 tk.setRestauration(rs.getString(3));
                 tk.setPrix(rs.getFloat(4));
            }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return tk;
    }

    @Override
    public void ajouter(ticketo p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprime(ticketo p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ticketo> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ticketo findById(int IdProduit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
