/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author ajose
 */
public class Dado {
    private static final Dado instance = new Dado();
    private int valor;
    
    private Dado(){
        tirar();
    }
    public static Dado getInstance(){
        return instance;
    }
    
   int tirar(){
        valor= 1+(int)(Math.random()*(6));
        return valor;
   }
    
   public int getValor(){
       return valor;
   }

    @Override
    public String toString() {
        return "Dado{" + "valor=" + valor + '}';
    }
}
