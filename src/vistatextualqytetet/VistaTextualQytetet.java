/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistatextualqytetet;
import java.util.ArrayList;
import controladorqytetet.*;
import java.util.Scanner;
/**
 *
 * @author acer
 */
public class VistaTextualQytetet {
    private ControladorQytetet controlador = ControladorQytetet.getInstance();
    private static final Scanner in = new Scanner (System.in);
    private static final Scanner in2 = new Scanner (System.in);
    private static ArrayList<String> jugadores = new ArrayList<>();
    
    public ArrayList<String> obtenerNombreJugadores(){
        System.out.print("Numero Jugadores: ");
        int num = in.nextInt();
        for(int i=0;i<num;i++){
            System.out.print("Jugador " + (i+1) + ": ");
            jugadores.add(in2.nextLine());
        }
        System.out.print("\n");
        return jugadores;
    }
    
    public int elegirCasilla(int opcionMenu){
        ArrayList<Integer> casillasValidas=controlador.obtenerCasillasValidas(opcionMenu);
        if (casillasValidas.isEmpty()){
            return -1;
        }
        else{
            ArrayList<String> listaConvertida=new ArrayList<>();
            for(Integer i: casillasValidas){
                listaConvertida.add(String.valueOf(i));
            }
            
            System.out.println("Casillas validas:");
            
            for(String s: listaConvertida){
                System.out.println(s);
            }
            
            return Integer.parseInt(leerValorCorrecto(listaConvertida));
        }
    }
    
    public String leerValorCorrecto(ArrayList<String> valoresCorrectos){
        String valor="";
        Scanner in = new Scanner(System.in);
        System.out.print("Selecciona una opción: ");
        do{
            valor = in.nextLine();
            if(!valoresCorrectos.contains(valor))
                System.out.print("\nLa opcion seleccionada no es valida, selecciona otra: ");
        }while(!valoresCorrectos.contains(valor));
        return valor;
    }
    
    public int elegirOperacion(){
        ArrayList<Integer> operacionesValidas=controlador.obtenerOperacionesJuegoValidas();
        ArrayList<String> operacionesConvertidas=new ArrayList<>();
        
        for(Integer i: operacionesValidas){
            operacionesConvertidas.add(String.valueOf(i));
        }
        
        OpcionMenu[] opciones = OpcionMenu.values();
        
        for(Integer i: operacionesValidas){
            System.out.println(opciones[i] + " -> " + Integer.toString(i));
        }
        
        return Integer.parseInt(leerValorCorrecto(operacionesConvertidas));
    }
    
    public static void main(String args[]) {
        VistaTextualQytetet ui = new VistaTextualQytetet();
        ui.controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida, casillaElegida = 0;
        boolean necesitaElegirCasilla;
        String mensaje="";
        do {
            operacionElegida = ui.elegirOperacion();
            necesitaElegirCasilla = ui.controlador.necesitaElegirCasilla(operacionElegida);
            if (necesitaElegirCasilla)
                casillaElegida = ui.elegirCasilla(operacionElegida);
            if (!necesitaElegirCasilla || casillaElegida >= 0){
                mensaje = ui.controlador.realizarOperacion(operacionElegida,casillaElegida);
                System.out.println(mensaje);
            }
        } while (mensaje != "\n----------JUEGO FINALIZADO----------");
    }
}
