/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Brahim
 * @param <C>
 */
public interface ICategorie<C> {
    void ajouterc(C c) ;
    void supprimec(C c) ;
    List<C> readAll() ;
}
