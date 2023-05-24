/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entity.KeyValuePair;
import entity.ticket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import org.controlsfx.control.Notifications;
import service.ticketService;

/**
 * FXML Controller class
 *
 * @author firas
 */
public class ModifierTicketController implements Initializable {

    @FXML
    private Button add_button;
    @FXML
    private Spinner<Integer> nb_ticket_field;
    @FXML
    private TextField price_field;
    @FXML
    private TextField event_field;
    
    ticket ticket; 
    ticketService ticket_service=new ticketService();
    private BooleanProperty form_valid= new SimpleBooleanProperty(true);
    private boolean event_check=false;
    private TicketsController tickets_controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //nb_ticket_field.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, ticket.getNbMaxT()));
        //price_field.setText(ticket.getPrixF()+"");
        //format_price_field_to_positive_float(price_field); 
        //event_field.setText(ticket_service.get_event_by_id(ticket.getId_event()).getNomEvent());
        
    }    
    
    @FXML
    private void modify_ticket(ActionEvent event) {
        ticket tk=new ticket();
        
        tk.setNumTicket(ticket.getNumTicket());
        tk.setId_event(ticket.getId_event());
        tk.setNbMaxT(nb_ticket_field.getValue());
        tk.setNbTDemande(0); 
        tk.setPrixF(Float.parseFloat(price_field.getText()));
        System.out.println("nbtt prec"+ticket.getNbMaxT());
        System.out.println("nbtt present"+tk.getNbMaxT());
        if(tk.getNbMaxT()==ticket.getNbMaxT() && tk.getPrixF()==ticket.getPrixF()){ 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Modification ticket");
            alert.setHeaderText(null);
            alert.setContentText("Rien a modifier");
        }else{ 
            ticket_service.update(tk);
            tickets_controller.get_tickets();
            tickets_controller.loadData();
            Notifications.create()
                        .title("Modification d'un ticket")
                        .text("le ticket de l'evennement "+event_field.getText()+" a été modifé avec succés");
            Stage stage = (Stage) add_button.getScene().getWindow();
            stage.close();
        }
        
        
    }

    
    public void format_price_field_to_positive_float(TextField field){ 
        FloatStringConverter floatConverter = new FloatStringConverter() {
            @Override
            public Float fromString(String value) {
                // Throw a NumberFormatException if the input string contains non-numeric characters
                if (value != null && !value.matches("\\d*\\.?\\d+")) {
                    throw new NumberFormatException("Input string is not a valid float number");
                }
                return super.fromString(value);
            }
        };
        
        // Create a TextFormatter that uses the custom FloatStringConverter and a custom UnaryOperator that filters out negative numbers and enforces a minimum value of 1
        TextFormatter<Float> positiveFloatTextFormatter = new TextFormatter<>(floatConverter, 1.0f, change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }

            try {
                float value = Float.parseFloat(change.getControlNewText());
                if (value >= 1) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Allow the change to be rejected by returning the original change
            }

            return null;
        });

        // Set the TextFormatter on the TextField
        field.setTextFormatter(positiveFloatTextFormatter);
    }
    private boolean check_form(){ 
        if (event_check==true )
            return true;
        return false;
     }
    
     public void setTickets_controller(TicketsController tickets_controller) {
        this.tickets_controller = tickets_controller;
    }

    public void setTicket(ticket ticket) {
        this.ticket = ticket;
    }
    
    public void initialize_modify_ticket_form() { 
        event_field.setDisable(true);
        nb_ticket_field.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, ticket.getNbMaxT()));
        price_field.setText(ticket.getPrixF()+"");
        format_price_field_to_positive_float(price_field); 
        event_field.setText(ticket_service.get_event_by_id(ticket.getId_event()).getNomEvent());
    }

    
    
}
