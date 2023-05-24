/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Brahim
 */
public class Categorie {
    private int IdCategorie;
    private String NomCategorie;
    



public Categorie(String NomCategorie) {
    
        this.NomCategorie = NomCategorie;
          
    }

    public Categorie(int IdCategorie , String NomCategorie) {
        
        this.IdCategorie = IdCategorie;
        this.NomCategorie = NomCategorie;
    }

   
    
    
    public int getIdCategorie() {
        return IdCategorie;
    }

    public void setIdCategorie(int IdCategorie) {
        this.IdCategorie = IdCategorie;
    }
    
    public String getNomCategorie() {
        return NomCategorie;
    }

    public void setNomCategorie(String NomCategorie) {
        this.NomCategorie = NomCategorie;
    }
    
    
    @Override
    public String toString() {
        return "Categorie{" + "IdCategorie=" + IdCategorie + ", NomCategorie=" + NomCategorie  + '}';
    }
}