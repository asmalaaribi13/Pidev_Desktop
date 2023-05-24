/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Asma Laaribi
 */
public class Type {

    private int id;
    private String nom_type;

    public Type() {
    }

    public Type(int id, String nom_type) {
        this.id = id;
        this.nom_type = nom_type;
    }

    public Type(String nom_type) {
        this.nom_type = nom_type;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_type() {
        return nom_type;
    }

    public void setNomType(String nom_type) {
        this.nom_type = nom_type;
    }

    
}
