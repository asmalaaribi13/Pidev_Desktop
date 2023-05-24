/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author sassi
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SMS {

    public SMS() {
    }
    
        
    public static final String ACCOUNT_SID = "AC80adfc62dd07137031c732ccb10687ea";
    public static final String AUTH_TOKEN = "4b35d2a7be2819cb9e4a336343e52a96";

//      public static void main(String[] args) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        Message message = Message.creator(
//                new PhoneNumber("+21699640992"),
//                new PhoneNumber("+12766006394"),
//                "Hi there")
//            .create();
//
//        System.out.println(message.getSid());
//    }
     public void sendSMS(String phoneNumber,String nom,String mdp) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+216"+phoneNumber),
                new PhoneNumber("+12766006394"), 
                "Bonjour Mme/Mr- '"+nom+"'\n Suite a votre demande \n votre nouveau mdp est :"+mdp).create();

        System.out.println(message.getSid());
    }
    
    
    
}
