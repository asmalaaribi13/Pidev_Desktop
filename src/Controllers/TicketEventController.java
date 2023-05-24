/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.User;
import entity.event;
import entity.reservation;
import entity.ticket;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.ResService;
import service.ticketService;

/**
 * FXML Controller class
 *
 * @author alaaz
 */
public class TicketEventController implements Initializable {

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
    private Label event_name_label;
    
    private event event;
    private User userconnected;

    ticketService ticket_service=new ticketService();
    ObservableList<ticket> obsreservationlist=FXCollections.observableArrayList(); 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    
    
    public void get_ticket( int id_event){ 
        obsreservationlist.clear();
        table_ticket.getItems().clear();
        obsreservationlist.add(ticket_service.get_ticket_by_eventId(id_event));
        loadData();
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
        
        
        
        TicketEventController tickets_controller=this;
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
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/VoirOffre.fxml"));
                                                    Parent root = loader.load();
                                                    Scene scene = new Scene(root); 
                                                    Stage stage = new Stage();
                                                    stage.setScene(scene);
                                                    VoirOffreController voir_offre_controller=loader.getController(); 
                                                    voir_offre_controller.setTicketo(ticket.getNumTicketO());
                                                    voir_offre_controller.initializa_data();
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
                        if(ticket!=null){  
                            if (ticket.getNbMaxT()!=ticket.getNbTDemande()){ 
                                System.out.println("im here");
                                button.setId("details_button");
                                button.setText("Réserver");
                                //Button fucntions 
                                button.setOnMouseClicked((event) -> {
                                    //reservation logic 
                                    reservation r = new reservation();
                                    //User u=new User(1, "test");
                                    // System.out.println("Res :"+userConnected);
                                    r.setIdUser(userconnected);
                                    r.setIdEvent(getEvent());
                                    ResService RS = new ResService();
                                    ticket_service.update_number_demanded_tickets(ticket);
                                    RS.insert(r);
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Félicitation");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Reservation ajouté avec succès !");   
                                    alert.showAndWait();
                                    get_ticket(getEvent().getIdEvent());
                                    
                                });
                                managebtn.setStyle("-fx-alignment:center");
                                managebtn.getChildren().add(button);
                                HBox.setMargin(button,new Insets(4, 2, 4, 4));
                                setGraphic(managebtn);
                            }else{
                                 setText("Pas de tickets");
                            }                           
                            
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
    
    public void initialize_data(){ 
        get_ticket(event.getIdEvent());
        event_name_label.setText(event.getNomEvent());
        
    }

    public event getEvent() {
        return event;
    }

    public void setEvent(event event) {
        this.event = event;
    }

    public User getUserconnected() {
        return userconnected;
    }

    public void setUserconnected(User userconnected) {
        this.userconnected = userconnected;
    }
    
    
}
