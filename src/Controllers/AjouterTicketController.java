/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entity.KeyValuePair;
import entity.event;
import entity.ticket;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.NumberStringConverter;
//import org.apache.logging.log4j.core.util.KeyValuePair;
import org.controlsfx.control.Notifications;
import service.ticketService;

/**
 * FXML Controller class
 *
 * @author firas
 */
public class AjouterTicketController implements Initializable {

    @FXML
    private Button add_button;
    @FXML
    private ChoiceBox<KeyValuePair> event_choice_field;
    @FXML
    private Spinner<Integer> nb_ticket_field;
    @FXML
    private TextField price_field;
    
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
        nb_ticket_field.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        format_price_field_to_positive_float(price_field);
        get_events();
        event_choice_field.setOnAction((event) -> {
           if(event_choice_field.getValue()==null) {
                event_check=false;
           }else{
                event_check=true;
           }
           form_valid.set(!check_form());
        });
        add_button.disableProperty().bind(form_valid);

    }    

    @FXML
    private void add_ticket(ActionEvent event) { 
        ticket ticket=new ticket();
        System.out.println("kety :"+event_choice_field.getValue().getKey());
        ticket.setId_event(event_choice_field.getValue().getKey());
        ticket.setNbMaxT(nb_ticket_field.getValue());
        ticket.setNbTDemande(0); 
        ticket.setPrixF(Float.parseFloat(price_field.getText()));
        System.out.println("price :"+ticket);
        ticket_service.insert(ticket);
        tickets_controller.get_tickets();
        tickets_controller.loadData();
        Notifications.create()
                    .title("Enregistrement d'un ticket")
                    .text("le ticket de l'evennement "+event_choice_field.getValue().getValue()+" a été ajouté avec succés")
                    .showInformation();
        price_field.setText("1.0");
        event_choice_field.setValue(null);
        nb_ticket_field.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        get_events();
        
    }
    
    public void get_events(){ 
        event_choice_field.getItems().clear();
        ticket_service.get_events_with_no_ticket().forEach((event)->{
            event_choice_field.getItems().add(new KeyValuePair(event.getIdEvent(),event.getNomEvent())); 
        });
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
    

    
}
