/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ajose
 */
public class PruebaQytetet {

    /**
     * @param args the command line arguments
     */
    
    static Qytetet juego = Qytetet.getInstance();
    private static final Scanner in = new Scanner (System.in);
    private static final Scanner in2 = new Scanner (System.in);
    private static ArrayList<String> jugadores = new ArrayList<>();
    
    private static ArrayList<Sorpresa> getMayorQueCero(){
      
        ArrayList<Sorpresa> mazo = juego.getMazo();
        ArrayList<Sorpresa> mayoresQueCero = new ArrayList<>();
        for (Sorpresa s: mazo){
            if (s.getValor() > 0){
                mayoresQueCero.add(s);
            }
        }
        
        return mayoresQueCero;
    }  
    
    private static ArrayList<Sorpresa> getIrACasilla(){
        ArrayList<Sorpresa> mazo = juego.getMazo();
        ArrayList<Sorpresa> tipoIrACasilla = new ArrayList<>();
        
        for (Sorpresa s: mazo){
            if (s.getTipo() == TipoSorpresa.IRACASILLA){
                tipoIrACasilla.add(s);
            }
        }
        return tipoIrACasilla;
    }

    
    private static ArrayList<Sorpresa> getTipoEspecifico(TipoSorpresa tipo){
        ArrayList<Sorpresa> mazo = juego.getMazo();
        ArrayList<Sorpresa> tipoespec = new ArrayList<>();
        
        for (Sorpresa s: mazo){
            if (s.getTipo() == tipo){
                tipoespec.add(s);
            }
        }
        return tipoespec;
    }
    
    private static ArrayList<String> getNombreJugadores(){
        System.out.print("Numero Jugadores: ");
        int num = in.nextInt();
        for(int i=0;i<num;i++){
            System.out.print("Jugador " + (i+1) + ": ");
            jugadores.add(in2.nextLine());
        }
        return jugadores;
    }
    
    public static void main(String[] args) {
       
       juego.inicializarJuego(getNombreJugadores());
       ArrayList<Sorpresa> mazo = juego.getMazo();
       
       System.out.print(juego.getJugadorActual() + "\n");
       juego.siguienteJugador();
       System.out.print(juego.getJugadorActual() + "\n\n");
        
       System.out.print("-------------MENU PRUEBAS--------------\n");
       System.out.print("Elegir prueba:\n");
       System.out.print("1. Mover\n");
       System.out.print("2. Asignar propiedad a jugador y que otro pague alquiler al pasar\n");
       System.out.print("3. Sorpresas\n");
       System.out.print("4. Hipotecar, cancelar hipoteca, vender y edificar\n");
       System.out.print("5. Salir de la carcel\n");
       System.out.print("6. Ranking\n\n");
       
       int num = in.nextInt();
       switch(num){           
           case 1:
               juego.jugar();
               System.out.print("El jugador a jugado y se ha movido a la casilla " + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.mover(3);
               System.out.print("El jugador a jugado y se ha movido a la casilla " + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.mover(5);
               System.out.print("El jugador a jugado y se ha movido a la casilla " + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.getJugadorActual().comprarTituloPropiedad();
               for(TituloPropiedad prop: juego.getJugadorActual().getPropiedades()){
                    System.out.print("El jugador ha comprado la casilla actual:  " + prop.toString() + "\n");
               }
               System.out.print(juego.getJugadorActual() + "\n");
               juego.mover(7);
               System.out.print("El jugador a jugado y se ha movido a la casilla " + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.mover(10);
               System.out.print("El jugador a jugado y se ha movido a la casilla " + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               System.out.print("El jugador ha entrado a la carcel: " + juego.getJugadorActual().isEncarcelado() + "\n");
               juego.mover(13);
               System.out.print("El jugador a jugado y se ha movido a la casilla " + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               System.out.print("El jugador ha entrado a la carcel: " + juego.getJugadorActual().isEncarcelado() + "\n");
               break;
           case 2:
               System.out.print(juego.getJugadorActual() + "\n");
               juego.mover(4);
               juego.comprarTituloPropiedad();
               for(TituloPropiedad prop: juego.getJugadorActual().getPropiedades()){
                    System.out.print("El jugador ha comprado la casilla actual:  " + prop.toString() + "\n");
               }
               System.out.print(juego.getJugadorActual() + "\n");
               juego.siguienteJugador();
               System.out.print(juego.getJugadorActual() + "\n");
               juego.mover(4);
               System.out.print(juego.getJugadorActual() + "\n");
               break;
           case 3:
               System.out.print(juego.getJugadorActual() + "\n");
               juego.mover(3);
               juego.aplicarSorpresa();
               System.out.print(juego.getJugadorActual() + "\n");
               juego.mover(3);
               juego.aplicarSorpresa();
               System.out.print(juego.getJugadorActual().getCasillaActual() + "\n");
               juego.mover(3);
               juego.mover(16);
               juego.comprarTituloPropiedad();
               juego.edificarCasa(16);
               juego.aplicarSorpresa();
               System.out.print(juego.getJugadorActual() + "\n");
               juego.mover(3);
               System.out.print(juego.getJugadores().get(0) + "\n");
               juego.aplicarSorpresa();
               System.out.print(juego.getJugadorActual() + "\n");
               System.out.print(juego.getJugadores().get(0) + "\n");
               juego.mover(3);
               juego.aplicarSorpresa();
               juego.mover(13);
               System.out.print(juego.getJugadorActual().isEncarcelado() + "\n");
               break;
           case 4:
               juego.mover(16);
               System.out.print(juego.getJugadorActual() + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.comprarTituloPropiedad();
               System.out.print(juego.getJugadorActual() + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.edificarCasa(16);
               juego.edificarHotel(16);
               System.out.print(juego.getJugadorActual() + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.hipotecarPropiedad(16);
               System.out.print(juego.getJugadorActual() + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.cancelarHipoteca(16);
               System.out.print(juego.getJugadorActual() + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               juego.venderPropiedad(16);
               System.out.print(juego.getJugadorActual() + juego.getJugadorActual().getCasillaActual().toString() + "\n");
               break;
           case 5:
               juego.mover(13);
               System.out.print(juego.getJugadorActual().isEncarcelado() + "\n");
               juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
               System.out.print(juego.getJugadorActual().isEncarcelado() + "\n");
               juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
               System.out.print(juego.getJugadorActual().isEncarcelado() + "\n");
               juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
               System.out.print(juego.getJugadorActual().isEncarcelado() + "\n");
               juego.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
               System.out.print(juego.getJugadorActual().isEncarcelado() + "\n");
               break;
           case 6:
               juego.obtenerRanking();
               for(int i=0; i<20; i++){
                    System.out.print("Turno " + i + ":\n");
                    juego.jugar();
                    if(juego.getEstado() == EstadoJuego.JA_PUEDECOMPRAROGESTIONAR)
                        juego.comprarTituloPropiedad();
                    System.out.print(juego.getJugadores() + "\n");
                    juego.siguienteJugador();
                  }
               juego.obtenerRanking();
                for(Jugador i: juego.getJugadores())
                    System.out.print(i);
               break;
           default:
                                               
       }
            
    }
    
}
