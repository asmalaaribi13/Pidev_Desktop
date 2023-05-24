/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author ihebc
 */


public class Panier {
    private int id_client;
    private int id_product;

    public Panier(int id_client, int id_product, double prix) {
        this.id_client = id_client;
        this.id_product = id_product;
        this.prix=prix;
    }
    private int quantite;
    private String nomp;
    private double prix;
    private String nomc;
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
     public Panier() {
      
    }

    public Panier(int id_client, int id_product, int quantite, String nomp, double prix) {
        this.id_client = id_client;
        this.id_product = id_product;
        this.quantite = quantite;
        this.nomp = nomp;
        this.prix = prix;
    }

    public Panier(int id_product, int quantite, String nomp, double prix, String nomc, String image) {
        this.id_product = id_product;
        this.quantite = quantite;
        this.nomp = nomp;
        this.prix = prix;
        this.nomc = nomc;
        this.image = image;
    }

    public Panier(int id_client, int id_product, String nomp, double prix) {
        this.id_client = id_client;
        this.id_product = id_product;
        this.nomp = nomp;
        this.prix = prix;
    }

    public String getNomc() {
        return nomc;
    }

    public void setNomc(String nomc) {
        this.nomc = nomc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    private String image;

    public Panier( int id_product, int quantite, String nomp, double prix,String nomc) {
        this.id_product = id_product;
        this.quantite = quantite;
        this.nomp = nomp;
        this.prix = prix;
        this.nomc=nomc;
    }

    public String getNomp() {
        return nomp;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

   

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Panier( int id_client, int id_product,int quantite) {
        this.id_client = id_client;
        this.id_product = id_product;
         this.quantite = quantite;

    }

    @Override
    public String toString() {
        return "Panier{" + " id_client=" + id_client + ", id_product=" + id_product + ", quantite=" + quantite + '}';
    }

  

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
    
    
}
