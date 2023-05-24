/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.pdfjet.PDF;
import entity.ticket;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.ticketService;

/**
 * FXML Controller class
 *
 * @author firas
 */
public class TicketController implements Initializable {

    @FXML
    private Label event_name_text;
    @FXML
    private Label price_text;

    ticket ticket; 
    ticketService ticket_service=new ticketService();
    private TicketsController tickets_controller;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setTickets_controller(TicketsController tickets_controller) {
        this.tickets_controller = tickets_controller;
    }

    public void setTicket(ticket ticket) {
        this.ticket = ticket;
    }
    
    public void initialize_ticket() { 
       event_name_text.setText(ticket_service.get_event_by_id(ticket.getId_event()).getNomEvent());
       if(ticket.getNumTicketO()==null){ 
           price_text.setText(ticket.getPrixF()+" DT");
       }else{ 
           price_text.setText(ticket.getNumTicketO().getPrix()+" DT");
       }
       Stage stage = (Stage) price_text.getScene().getWindow();
        try {
             PDF pdf = new PDF(new FileOutputStream("Ticket.pdf"));
            // Print the PDF file
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                if (printerJob.showPrintDialog(stage.getOwner()) && printerJob.printPage(stage.getScene().getRoot())) {
                    printerJob.endJob();
                }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

       
      
    }
    
}
