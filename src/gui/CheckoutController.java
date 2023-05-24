/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author ihebc
 */

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import entity.Commande;
import entity.Panier;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Date.from;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import service.CommandeService;
import service.PanierService;
import service.mailling;

/**
 * FXML Controller class
 *
 * @author ihebc
 */
public class CheckoutController implements Initializable {
    private static final String STRIPE_SECRET_KEY = "sk_test_51MfB6OFpYt8L2cT9P1pZRw9cJVQCUgAsXRM44c9mnnzfTz9g1WNO3rB0LTPFHB6Qz5h1w2iW6ok1PyxzBwMcP3bF00uoY9CCHW";

    @FXML
    private Button btnMenus;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private TextField nomC;
    @FXML
    private TextField prix;
    @FXML
    private TextField nomP;
AnchorPane bord = new AnchorPane();
    @FXML
    private Button Checkout;
    @FXML
    private Button payer;
    @FXML
    private TextField numcarte;
    @FXML
    private TextField expirmois;
    @FXML
    private TextField expyear;
    @FXML
    private TextField cvc;
    @FXML
    private Label tot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          PanierService s = new PanierService();
        List<Panier> pers = new ArrayList<Panier>();
        pers = s.getPanier(MarcheController.userConnected.getIdUser());
     
       
       
        for (Panier p : pers) {
            System.out.println(p.getTotal());
//            prix.setText(String.valueOf(p.getTotal()));
            nomC.setText(p.getNomc());
            System.out.println("testCheckout");
            nomP.setText(p.getNomp());
            
            tot.setText(String.valueOf(s.sommeProduit(MarcheController.userConnected.getIdUser())));

             
        }
    }    

    @FXML
    private void handleClicks(ActionEvent event) {
    }

  

    @FXML
    private void payer(ActionEvent event) {
        try {
            Stripe.apiKey = STRIPE_SECRET_KEY;
            // Create a customer object for the user who is paying
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email", "iheb.iheb@esprit.tn");
            Customer payer = Customer.create(customerParams);
            
            Map<String, Object> retrieveParams = new HashMap<String, Object>();
		List<String> expandList = new ArrayList<String>();
		expandList.add("sources");
		retrieveParams.put("expand", expandList);
		Customer customer = Customer.retrieve(payer.getId(), retrieveParams, null); //add customer id here : it will start with cus_
		
		Map<String, Object> cardParam = new HashMap<String, Object>(); //add card details
		cardParam.put("number", numcarte.getText());
		cardParam.put("exp_month",expirmois.getText() );
		cardParam.put("exp_year", expyear.getText());
		cardParam.put("cvc", cvc.getText());

		Map<String, Object> tokenParam = new HashMap<String, Object>();
		tokenParam.put("card", cardParam);

		Token token = Token.create(tokenParam); // create a token

		Map<String, Object> source = new HashMap<String, Object>();
		source.put("source", token.getId()); //add token as source

		Card card = (Card)customer.getSources().create(source); // add the customer details to which card is need to link
		String cardDetails = card.toJson();
		System.out.println("Card Details : " + cardDetails);
		customer = Customer.retrieve(payer.getId());//change the customer id or use to get customer by id.
		System.out.println("After adding card, customer details : " + customer);
                
                
                
//        PaymentMethod paymentMethod = PaymentMethod.create(cardParam);
                
                
                
                
                
                
                
                
            System.out.println(customer.getId());       
 //Use the payment method to make a charge
    Map<String, Object> chargeParams = new HashMap<String, Object>();
    chargeParams.put("amount", 1000);
    chargeParams.put("currency", "usd");
    //chargeParams.put("description", "Example charge");
     //chargeParams.put("source", token.getId());
    chargeParams.put("customer", customer.getId());
            Charge charge = Charge.create(chargeParams);
            Alert alert = new Alert(AlertType.CONFIRMATION);

alert.setTitle("PAIEMENT");
alert.setHeaderText("Voulez vous payer ?");
alert.setContentText("Paiement effectuée");

            
            System.out.println("Payment successful!");
            
alert.showAndWait();
        mailling.mailling
        ("servicecommande6@gmail.com","rgisdiryftmsdhnv","tunmix.application@gmail.com","nouvelle commande", "nous avons reçu une nouvelle commande veuillez la consultez le plus vite possible");

            
CommandeService c =new CommandeService();
      Commande co=new Commande(1,1,Double.parseDouble(tot.getText()));
      c.ajouterC(co);
       

        } catch (StripeException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    private void Checkout(ActionEvent event) {
    }

    
}
