/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entity.ticket;
import entity.ticketo;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import org.controlsfx.control.Notifications;
import service.ticketService;
import service.ticketoService;
import utils.Mail;

/**
 * FXML Controller class
 *
 * @author firas
 */
public class ModifierOffreController implements Initializable {

   @FXML
    private Button add_button;
    @FXML
    private ChoiceBox<String> resto_choice_field;
    @FXML
    private TextField price_field;
    @FXML
    private ChoiceBox<String> hotel_choice_field;

    ticket ticket; 
    ticketoService ticketo_service=new ticketoService();
    private boolean resto,hotel=false;
    private BooleanProperty form_valid= new SimpleBooleanProperty(true);
    ObservableList<String> restoList = FXCollections.observableArrayList("Aucun","Lorenzia", "Golf la marsa", "Dar zarrouk");
    ObservableList<String> hotelList = FXCollections.observableArrayList("Aucun","El mouradi", "El hana", "Radisson bleu");
    private TicketsController tickets_controller;
    @FXML
    private Button delete_button1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        resto_choice_field.setItems(restoList);
        hotel_choice_field.setItems(hotelList);
        format_price_field_to_positive_float(price_field);
        resto_choice_field.setOnAction((event) -> {
           if(resto_choice_field.getValue()=="Aucun"){
                resto=false;
           }else{
                resto=true;
           }
           form_valid.set(!check_form());
        });
        hotel_choice_field.setOnAction((event) -> {
           if(hotel_choice_field.getValue()=="Aucun"){
                hotel=false;
           }else{
                hotel=true;
           }
           form_valid.set(!check_form());
        });
        
        add_button.disableProperty().bind(form_valid);
        // TODO
    }    

    @FXML
    private void modify_offre(ActionEvent event) {
        ticketo offre=new ticketo();
        offre.setNumTicketO(ticket.getNumTicketO().getNumTicketO());
        offre.setLogement(hotel_choice_field.getValue()); 
        offre.setRestauration(resto_choice_field.getValue());
        offre.setPrix(Float.parseFloat(price_field.getText())); 
        
        if(ticket.getNumTicketO().getLogement()==offre.getLogement() && ticket.getNumTicketO().getRestauration()==offre.getRestauration() 
                && ticket.getNumTicketO().getPrix()==offre.getPrix()){ 
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification offre");
                    alert.setHeaderText(null);
                    alert.setContentText("Rien a modifier");
        }else{  
            
            ticketo_service.update(offre);
            tickets_controller.get_tickets();
            tickets_controller.loadData();
            Notifications.create()
                        .title("Modification d'un offre")
                        .text("l'offre a été modifé avec succés");
            Stage stage = (Stage) add_button.getScene().getWindow();
            stage.close();
            Mail.envoyer_modify_offre(new ticketService().get_event_by_id(ticket.getId_event()),offre);
        }
    }
    
    @FXML
    private void delete_offre(ActionEvent event) { 
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Supprimer l'offre");
                                    alert.setHeaderText("Vous voulez vraiment effectuer la supression ?");
                                    Optional<ButtonType> option = alert.showAndWait();
                                    if (option.get() == ButtonType.OK) { 
                                        ticketo_service.delete(ticket.getNumTicketO());
                                        tickets_controller.get_tickets();
                                        tickets_controller.loadData();
                                        Notifications.create()
                                                    .title("Supression d'un offre")
                                                    .text("l'offre a été modifé avec succés");
                                        Stage stage = (Stage) delete_button1.getScene().getWindow();
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
        if (resto==true && hotel==true)
            return true;
        return false;
     }
     
      public void setTickets_controller(TicketsController tickets_controller) {
        this.tickets_controller = tickets_controller;
    }

    public ticket getTicket() {
        return ticket;
    }

    public void setTicket(ticket ticket) {
        this.ticket = ticket;
    }
    
    public void initialize_modify_offre_form() { 
        resto_choice_field.setValue(ticket.getNumTicketO().getRestauration());
        hotel_choice_field.setValue(ticket.getNumTicketO().getLogement());
         format_price_field_to_positive_float(price_field); 
        price_field.setText(ticket.getNumTicketO().getPrix()+"");
        
       
        
    }

    
    
}
