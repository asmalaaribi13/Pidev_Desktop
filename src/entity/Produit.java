/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Brahim
 */
public class Produit {
    private int IdProduit;
    private String NomProduit;
    private String DescProduit;
    private LocalDate DateProduit;
    private float PrixProduit ;
    private int Qte;
    private String PhotoP;
    private String NomCategorie;

    public Produit(int IdProduit, String NomProduit) {
        this.IdProduit = IdProduit;
        this.NomProduit = NomProduit;
    }

    public Produit(LocalDate DateProduit, String NomProduit, String DescProduit, int Qte, float PrixProduit, String NomCategorie, String PhotoP) {
     
        this(Qte, NomProduit, DescProduit, DateProduit, PrixProduit, PhotoP);
          
    }

    public Produit(int Qte, String NomProduit, String DescProduit, LocalDate DateProduit, float PrixProduit, String PhotoP) {
        this.NomProduit = NomProduit;
        this.DescProduit = DescProduit;
        this.DateProduit = DateProduit;
        this.PrixProduit = PrixProduit;
        this.Qte = Qte;
        this.PhotoP = PhotoP;
        this.NomCategorie = NomCategorie;
    }

    public Produit(int IdProduit, String NomProduit, String DescProduit, LocalDate DateProduit, float PrixProduit, int Qte, String PhotoP) {
        this.IdProduit = IdProduit;
        this.NomProduit = NomProduit;
        this.DescProduit = DescProduit;
        this.DateProduit = DateProduit;
        this.PrixProduit = PrixProduit;
        this.Qte = Qte;
        this.PhotoP = PhotoP;
    }

    public Produit(int IdProduit , String NomProduit, String DescProduit, LocalDate DateProduit, float PrixProduit,int Qte, String PhotoP,String NomCategorie) {
        
        this.IdProduit = IdProduit;
        this.NomProduit = NomProduit;
        this.DescProduit = DescProduit;
        this.DateProduit = DateProduit;
        this.PrixProduit = PrixProduit;
        this.Qte = Qte;
        this.PhotoP = PhotoP;
        this.NomCategorie = NomCategorie;
    }

    public Produit() {
    }

    public int getIdProduit() {
        return IdProduit;
    }

    public void setIdProduit(int IdProduit) {
        this.IdProduit = IdProduit;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public void setNomProduit(String NomProduit) {
        this.NomProduit = NomProduit;
    }

    public String getDescProduit() {
        return DescProduit;
    }

    public void setDescProduit(String DescProduit) {
        this.DescProduit = DescProduit;
    }

    public LocalDate getDateProduit() {
        return DateProduit;
    }

    public void setDateProduit(LocalDate DateProduit) {
        this.DateProduit = DateProduit;
    }

    public float getPrixProduit() {
        return PrixProduit;
    }

    public void setPrixProduit(float PrixProduit) {
        this.PrixProduit = PrixProduit;
    }

    public int getQte() {
        return Qte;
    }

    public void setQte(int Qte) {
        this.Qte = Qte;
    }

    public String getPhotoP() {
        return PhotoP;
    }

    public void setPhotoP(String PhotoP) {
        this.PhotoP = PhotoP;
    }
    
    public String getNomCategorie() {
        return NomCategorie;
    }

    public void setNomCategorie(String NomCategorie) {
        this.NomCategorie = NomCategorie;
    }

    @Override
   
    public String toString() {
        return NomProduit;
    }

    
    
    
}
