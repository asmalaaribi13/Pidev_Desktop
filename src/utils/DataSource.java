/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class DataSource {
     private String url="jdbc:mysql://localhost:3306/tunmixfinal";
   private String login="root";
   private String pwd="root";
   private Connection cnx;
   private static DataSource instance;

    private DataSource() {
       try {
           cnx=DriverManager.getConnection(url, login, pwd);
           System.out.println("______Bienvenue______");
       } catch (SQLException ex) {
           Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    public static DataSource getInstance(){
        if(instance==null)
           instance=new DataSource();
        return instance;
    }
    public Connection getCnx() {
        return cnx;
    }
   
    
}
