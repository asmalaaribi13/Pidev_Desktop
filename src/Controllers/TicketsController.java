/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.KeyValuePair;
import entity.ticket;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.ticketService;

/**
 * FXML Controller class
 *
 * @author firas
 */
public class TicketsController implements Initializable {

    @FXML
    private TableView<ticket> table_ticket;
    @FXML
    private TableColumn<ticket, String> numTicket;
    @FXML
    private TableColumn<ticket, String> evenement;
    @FXML
    private TableColumn<ticket, Integer> nbMaxT;
    @FXML
    private TableColumn<ticket, Integer> nbTDemande;
    @FXML
    private TableColumn<ticket, String> prixF;
     @FXML
    private TableColumn<ticket, String> offre;
    @FXML
    private TableColumn<ticket, String> editcol;
    @FXML
    private TextField event_field;
    @FXML
    private Button ticket_search_button;
    @FXML
    private Button add_button;
    
    
    ticketService ticket_service=new ticketService();
    ObservableList<ticket> obsreservationlist=FXCollections.observableArrayList();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        get_tickets();
        loadData();
        
        // TODO
    }    

    @FXML
    private void serach_ticket(ActionEvent event) { 
        get_ticket_by_event_name(event_field.getText());
        loadData();

    }

    @FXML
    private void clear_search_fields(ActionEvent event) {
        event_field.setText(null); 
        get_tickets();
        loadData();
    }

    @FXML
    private void add_ticket(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/AjouterTicket.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root); 
                Stage stage = new Stage();
                stage.setScene(scene); 
                AjouterTicketController ajouter_ticket_controller=loader.getController();
                ajouter_ticket_controller.setTickets_controller(this);
                stage.show();
        }catch (IOException ex) {
            Logger.getLogger(AjouterTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void get_tickets(){ 
        obsreservationlist.clear();
        table_ticket.getItems().clear();
        ticket_service.getAll().forEach((ticket)->{
            obsreservationlist.add(ticket);
        });
        System.out.println("table");        
    }
    public void get_ticket_by_event_name(String event_name){ 
        obsreservationlist.clear();
        table_ticket.getItems().clear();
        ticket ticket=ticket_service.get_ticket_by_eventName(event_name); 
        obsreservationlist.add(ticket);
    }
    
    
    public void loadData(){
        table_ticket.setItems(obsreservationlist);
        System.out.println("table items "+table_ticket.getItems());
        //clear_search_fields();
        numTicket.setCellValueFactory(data->{
             String  type= "ticket - "+ data.getValue().getNumTicket();
             ObservableValue<String> obs=new SimpleObjectProperty<>(type);
             return obs;
         });
         evenement.setCellValueFactory(data->{
             String  event_nom= new ticketService().get_event_by_id(data.getValue().getId_event()).getNomEvent();
             ObservableValue<String> obs=new SimpleObjectProperty<>(event_nom);
             return obs;
         });
         
         nbMaxT.setCellValueFactory(data->{
             int  type= data.getValue().getNbMaxT();
             ObservableValue<Integer> obs=new SimpleObjectProperty<>(type);
             return obs;
         });
        nbTDemande.setCellValueFactory(data->{
             int  type= data.getValue().getNbTDemande();
             ObservableValue<Integer> obs=new SimpleObjectProperty<>(type);
             return obs;
         });
        prixF.setCellValueFactory(data->{
             String prix= data.getValue().getPrixF()+" DT" ;
             ObservableValue<String> obs=new SimpleObjectProperty<>(prix);
             return obs;
         });
        
        
        
        TicketsController tickets_controller=this;
        Callback<TableColumn<ticket, String>, TableCell<ticket, String>> cellFoctoryOffre;
        cellFoctoryOffre = (TableColumn<ticket, String> param) -> {
            // make cell containing buttons
            final TableCell<ticket, String> cell = new TableCell<ticket, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        
                        ticket ticket=(ticket) this.getTableRow().getItem();
                        Button button = new Button();
                        if(ticket!=null){
                            
                            if(ticket.getNumTicketO()==null){
                                setText("Aucune");
                            }else{ 
                                button.setId("details_button");
                                button.setText("Voir offre");
                                //Button fucntions 
                                button.setOnMouseClicked((event) -> {
                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/ModifierOffre.fxml"));
                                                    Parent root = loader.load();
                                                    Scene scene = new Scene(root); 
                                                    Stage stage = new Stage();
                                                    stage.setScene(scene);

                                                    ModifierOffreController modifier_offre_controller=loader.getController(); 
                                                    modifier_offre_controller.setTickets_controller(tickets_controller);
                                                    modifier_offre_controller.setTicket(ticket); 
                                                    modifier_offre_controller.initialize_modify_offre_form();
                                                    stage.show();
                                    } catch (IOException ex) {
                                           // Logger.getLogger(AjouterRéservationChambreController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                });
                                HBox managebtn = new HBox(button);
                                managebtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(button,new Insets(2, 2, 0, 0));
                                setGraphic(managebtn);
                                
                            }
                        }
                    }
                }

            };
           
            return cell;
        };
        
        
        
        Callback<TableColumn<ticket, String>, TableCell<ticket, String>> cellFoctoryAction;
        cellFoctoryAction = (TableColumn<ticket, String> param) -> {
            // make cell containing buttons
            final TableCell<ticket, String> cell = new TableCell<ticket, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        
                        ticket ticket=(ticket) this.getTableRow().getItem();
                        HBox managebtn = new HBox();
                        Button button = new Button(); 
                        Button  button_ajouter_offre=new Button(); 
                        if(ticket!=null){ 
                                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                                deleteIcon.setStyle(
                                    " -fx-cursor: hand ;"
                                            + "-glyph-size:24px;"
                                            + "-fx-fill:#ff1744;"
                                            + "-fx-border-insets: 5px;"
                                            + "-fx-padding: 10px;"
                                 );
                                //Button fucntions  
                                deleteIcon.setOnMouseClicked((event) -> {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Supprimer une ticket");
                                    alert.setHeaderText("Vous voulez vraiment effectuer la supression ?");
                                    Optional<ButtonType> option = alert.showAndWait();
                                    if (option.get() == ButtonType.OK) { 
                                        ticket_service.delete(ticket);
                                        tickets_controller.get_tickets();
                                        tickets_controller.loadData();
                                        
                                    }
                                });
                                FontAwesomeIconView printIcon = new FontAwesomeIconView(FontAwesomeIcon.PRINT);
                                printIcon.setStyle(
                                    " -fx-cursor: hand ;"
                                            + "-glyph-size:24px;"
                                            + "-fx-fill:blue;"
                                            + "-fx-border-insets: 5px;"
                                            + "-fx-padding: 10px;"
                                 );
                                printIcon.setOnMouseClicked((event) -> {
                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/Ticket.fxml"));
                                        Parent root = loader.load();
                                        Scene scene = new Scene(root);
                                        Stage stage = new Stage();
                                        stage.setScene(scene);
                                        TicketController ticket_controller=loader.getController();
                                        ticket_controller.setTickets_controller(tickets_controller);
                                        ticket_controller.setTicket(ticket);
                                        ticket_controller.initialize_ticket();
                                        stage.show();
                                    } catch (IOException ex) {
                                        Logger.getLogger(TicketsController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                });
                                
                                button.setId("details_button");
                                button.setText("Modifier");
                                //Button fucntions 
                                button.setOnMouseClicked((event) -> {
                                    try {
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/ModifierTicket.fxml"));
                                                Parent root = loader.load();
                                                Scene scene = new Scene(root); 
                                                Stage stage = new Stage();
                                                stage.setScene(scene);
                                                 ModifierTicketController modifier_ticket_controller=loader.getController(); 
                                                 modifier_ticket_controller.setTickets_controller(tickets_controller);
                                                 modifier_ticket_controller.setTicket(ticket); 
                                                 modifier_ticket_controller.initialize_modify_ticket_form();
                                                 stage.show();
                                    } catch (IOException ex) {
                                           // Logger.getLogger(AjouterRéservationChambreController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                });
                                
                                if(ticket.getNumTicketO()==null){ 
                                    button_ajouter_offre.setId("details_button");
                                    button_ajouter_offre.setText("Ajouter offre");
                                    //Button fucntions 
                                    button_ajouter_offre.setOnMouseClicked((event) -> {
                                        try {
                                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/AjouterOffre.fxml"));
                                                    Parent root = loader.load();
                                                    Scene scene = new Scene(root); 
                                                    Stage stage = new Stage();
                                                    stage.setScene(scene);

                                                    AjouterOffreController ajouter_offre_controller=loader.getController(); 
                                                    ajouter_offre_controller.setTickets_controller(tickets_controller);
                                                    ajouter_offre_controller.setTicket(ticket);
                                                    stage.show();
                                            loader.load();
                                        } catch (IOException ex) {
                                               // Logger.getLogger(AjouterRéservationChambreController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    });
                                    managebtn.getChildren().addAll(button,button_ajouter_offre,deleteIcon,printIcon);
                                }else{ 
                                    managebtn.getChildren().addAll(button,deleteIcon,printIcon);
                                }
                             
                                
                                
                                
                                
                                                               
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(button,new Insets(4, 2, 4, 4));
                            setGraphic(managebtn);
                            setText(null);
                        }
                    }
                }

            };
           
            return cell;
        };
        offre.setCellFactory(cellFoctoryOffre);
        editcol.setCellFactory(cellFoctoryAction);
        table_ticket.setItems(obsreservationlist); 
    }
    
}
