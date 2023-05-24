/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author firas
 */
public class KeyValuePair {
   private final int key;
   private final String value;

   
   public KeyValuePair(int key, String value) {
   this.key = key;
   this.value = value;
   }

  public int getKey()   {    return key;    }
  public String getValue(){
      return value;
  }

  public String toString() {    return value;  }
  

}
