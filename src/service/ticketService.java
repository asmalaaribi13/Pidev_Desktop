/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import entity.event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.ticket;
import utils.Mail;
import utils.MyDB;

/**
 *
 * @author firas
 */
public class ticketService implements IService<ticket>{
    
   Connection cnx;

    public ticketService() {
        cnx = MyDB.getInstance().getConnection();
    }
    public void insert(ticket tk){
       System.out.println("ticket service "+tk);
        String req = "INSERT INTO ticket (nbMaxT,nbTDemande,PrixF,idEvent) VALUES(?,?,?,?)" ;
        PreparedStatement  pst;
        try {
            
            pst = cnx.prepareStatement(req);
            pst.setInt(1, tk.getNbMaxT());
            pst.setInt(2, tk.getNbTDemande());
            pst.setFloat(3, tk.getPrixF());
            pst.setInt(4, tk.getId_event());
            pst.executeUpdate();
            System.out.println("ticket avec succés");
            Mail.envoyer_add_ticket(get_event_by_id(tk.getId_event()));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void delete(ticket t) {
            String requete="delete from ticket where NumTicket = "+t.getNumTicket();
        try {
            Statement st=cnx.createStatement();

            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ticketService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
    public void update(ticket t) {
           try {
            String req =  "UPDATE ticket SET NbMaxT = '" + t.getNbMaxT()+ "', prixF = '" + t.getPrixF() + "' WHERE ticket.`NumTicket` = " + t.getNumTicket();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            Mail.envoyer_modify_ticket(get_event_by_id(t.getId_event()),t);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void update_number_demanded_tickets(ticket t) {
           try {
            String req =  "UPDATE ticket SET nbTDemande  = nbTDemande + '" + 1 + "' WHERE ticket.`NumTicket` = " + t.getNumTicket();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
  
    public void update_ticketo_add(ticket t,int id_ticketo) {
           try {
            String req =  "UPDATE ticket SET numTicketO = '" +id_ticketo + "' WHERE ticket.`NumTicket` = " + t.getNumTicket();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("ticket updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public List<ticket> getAll() {
        
        List<ticket> tickets_list = new ArrayList();
        PreparedStatement  pst;
        try {
            String req ="select * from ticket ORDER BY numTicket DESC";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) { 
                 ticket tk=new ticket(); 
                 tk.setNumTicket(rs.getInt(1));
                 tk.setNbMaxT(rs.getInt(2));
                 tk.setNbTDemande(rs.getInt(3));
                 tk.setIdRes(rs.getInt(4));
                 tk.setPrixF(rs.getFloat(5)); 
                 tk.setId_event(rs.getInt(6));   
                 if(rs.getInt(7)!=0){ 
                      ticketoService ticketo_service=new  ticketoService();
                      tk.setNumTicketO(ticketo_service.get_ticket_by_id(rs.getInt(7)));
                 }
                
                 tickets_list.add(tk);              
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return tickets_list;
    }


    
    public ticket get_ticket_by_id(int id) {
        PreparedStatement  pst;
        ticket tk=new ticket(); 
        try {
            
            String req ="select * from ticket where numTicket="+id+"";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 tk.setNumTicket(rs.getInt(1));
                 tk.setNbMaxT(rs.getInt(2));
                 tk.setNbTDemande(rs.getInt(3));
                 tk.setIdRes(rs.getInt(4));
                 tk.setPrixF(rs.getFloat(5));
                 tk.setId_event(rs.getInt(6));
                 if(rs.getInt(7)!=0){ 
                      ticketoService ticketo_service=new  ticketoService();
                      tk.setNumTicketO(ticketo_service.get_ticket_by_id(rs.getInt(7)));
                 }
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return tk;
    }
     public ticket get_ticket_by_eventId(int id) {
        PreparedStatement  pst;
        ticket tk=new ticket(); 
        try {
            
            String req ="select * from ticket where idEvent="+id+"";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 tk.setNumTicket(rs.getInt(1));
                 tk.setNbMaxT(rs.getInt(2));
                 tk.setNbTDemande(rs.getInt(3));
                 tk.setIdRes(rs.getInt(4));
                 tk.setPrixF(rs.getFloat(5));
                 tk.setId_event(rs.getInt(6));
                 if(rs.getInt(7)!=0){ 
                      ticketoService ticketo_service=new  ticketoService();
                      tk.setNumTicketO(ticketo_service.get_ticket_by_id(rs.getInt(7)));
                 }
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return tk;
    }
    
    public ticket get_ticket_by_eventName(String event_name) {
        PreparedStatement  pst;
        ticket tk=new ticket(); 
        try {
            event ev=get_event_by_name(event_name);  
            System.out.println("event searched " + ev.getNomEvent());
            if(ev!=null){
                String req ="select * from ticket where idEvent="+ev.getIdEvent()+"";
                pst = cnx.prepareStatement(req);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                     tk.setNumTicket(rs.getInt(1));
                     tk.setNbMaxT(rs.getInt(2));
                     tk.setNbTDemande(rs.getInt(3));
                     tk.setIdRes(rs.getInt(4));
                     tk.setPrixF(rs.getFloat(5));
                     tk.setId_event(rs.getInt(6));
                     if(rs.getInt(7)!=0){ 
                      ticketoService ticketo_service=new  ticketoService();
                      tk.setNumTicketO(ticketo_service.get_ticket_by_id(rs.getInt(7)));
                 }
                 }
            }else return null; 
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("ticket searched "+ tk);
        return tk;
    }
    
    
    public List<event> get_events_with_no_ticket() {
        
        List<event> events_list = new ArrayList();
        PreparedStatement  pst;
        try {
            String req ="SELECT * FROM event WHERE IdEvent NOT IN (SELECT DISTINCT idEvent FROM ticket);";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) { 
                 event ev=new event(); 
                 ev.setIdEvent(rs.getInt(1));
                 ev.setNomEvent(rs.getString(2));
                 events_list.add(ev);              
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return events_list;
    }
    
    public event get_event_by_name(String name) {
        PreparedStatement  pst;
        event ev=new event(); 
        try {
            
            String req ="select * from event where NomEvent LIKE('%"+name+"%')";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 ev.setIdEvent(rs.getInt(1)); 
                 ev.setNomEvent(rs.getString(2));
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return ev;
    } 
    public event get_event_by_id(int id) {
        PreparedStatement  pst;
        event ev=new event(); 
        try {
            
            String req ="select * from event where idEvent="+id+"";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 ev.setIdEvent(rs.getInt(1)); 
                 ev.setNomEvent(rs.getString(2));
                
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return ev;
    }
    
    public event get_events(int id) {
        PreparedStatement  pst;
        event ev=new event(); 
        try {
            
            String req ="select * from event";
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 ev.setIdEvent(rs.getInt(1)); 
                 ev.setNomEvent(rs.getString(2));
                
             }           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return ev;
    }

    @Override
    public void ajouter(ticket p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprime(ticket p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ticket> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ticket findById(int IdProduit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
