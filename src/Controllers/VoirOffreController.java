/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entity.ticketo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author alaaz
 */
public class VoirOffreController implements Initializable {

    @FXML
    private Label restaurant_label;
    @FXML
    private Label logement_label;
    @FXML
    private Label prix_label; 
    
    private ticketo ticketo; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public  void initializa_data(){ 
        restaurant_label.setText(ticketo.getRestauration());
        logement_label.setText(ticketo.getLogement());
        prix_label.setText(ticketo.getPrix()+" DT");
    }

    public ticketo getTicketo() {
        return ticketo;
    }

    public void setTicketo(ticketo ticketo) {
        this.ticketo = ticketo;
    }
    
}
