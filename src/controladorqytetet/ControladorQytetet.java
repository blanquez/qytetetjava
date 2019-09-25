/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorqytetet;
import java.util.ArrayList;
import modeloqytetet.EstadoJuego;
import modeloqytetet.MetodoSalirCarcel;
/**
 *
 * @author acer
 */
public class ControladorQytetet {
    private static final ControladorQytetet instance = new ControladorQytetet();
    public static ControladorQytetet getInstance(){
        return instance;
    }
    private ArrayList<String> nombreJugadores = new ArrayList<>();
    private modeloqytetet.Qytetet modelo=modeloqytetet.Qytetet.getInstance();
    
    public void setNombreJugadores(ArrayList<String> nombreJugadores){
        this.nombreJugadores = nombreJugadores;
    }
    
    public ArrayList<Integer> obtenerOperacionesJuegoValidas(){
        ArrayList<Integer> validas = new ArrayList<>();
        if(modelo.getJugadores().isEmpty())
            validas.add(OpcionMenu.INICIARJUEGO.ordinal());
        else{
            switch(modelo.getEstado()){
                case JA_ENCARCELADO:
                    validas.add(OpcionMenu.PASARTURNO.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    validas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    validas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case JA_ENCARCELADOCONOPCIONDELIBERTAD:
                    validas.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                    validas.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    validas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    validas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case JA_PREPARADO:
                    validas.add(OpcionMenu.JUGAR.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    validas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    validas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case ALGUNJUGADORENBANCARROTA:
                    validas.add(OpcionMenu.OBTENERRANKING.ordinal());
                    break;
                case JA_PUEDEGESTIONAR:
                    validas.add(OpcionMenu.PASARTURNO.ordinal());
                    validas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    validas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    validas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    validas.add(OpcionMenu.EDIFICARCASA.ordinal());
                    validas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    validas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    validas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case JA_PUEDECOMPRAROGESTIONAR:
                    validas.add(OpcionMenu.PASARTURNO.ordinal());
                    validas.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                    validas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    validas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    validas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    validas.add(OpcionMenu.EDIFICARCASA.ordinal());
                    validas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    validas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    validas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case JA_CONSORPRESA:
                    validas.add(OpcionMenu.APLICARSORPRESA.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    validas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    validas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    validas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
            }
        }
        return validas;
    }
    
    public boolean necesitaElegirCasilla(int opcionMenu){
        if(opcionMenu == OpcionMenu.HIPOTECARPROPIEDAD.ordinal())
            return true;
        else if(opcionMenu == OpcionMenu.CANCELARHIPOTECA.ordinal())
            return true;
        else if(opcionMenu == OpcionMenu.EDIFICARCASA.ordinal())
            return true;
        else if(opcionMenu == OpcionMenu.EDIFICARHOTEL.ordinal())
            return true;
        else if(opcionMenu == OpcionMenu.VENDERPROPIEDAD.ordinal())
            return true;
        else
            return false;
    }
    
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu){
        ArrayList<Integer> validas = new ArrayList<>();
        OpcionMenu opcion = OpcionMenu.values()[opcionMenu];
        switch(opcion){
            case HIPOTECARPROPIEDAD:
                validas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
                break;
            case CANCELARHIPOTECA:
                validas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
                break;
            case EDIFICARCASA:
                validas = modelo.obtenerPropiedadesJugador();
                break;
            case EDIFICARHOTEL:
                validas = modelo.obtenerPropiedadesJugador();
                break;
            case VENDERPROPIEDAD:
                validas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
                break;
        }
        return validas;
    }
    
    public String realizarOperacion(int opcionElegida, int casillaElegida){
        String mensaje = "";
        OpcionMenu opcion = OpcionMenu.values()[opcionElegida];
        switch(opcion){
            case INICIARJUEGO:
                modelo.inicializarJuego(nombreJugadores);
                mensaje = "\n----------COMIENZA EL JUEGO----------\nEmpieza jugando " + modelo.getJugadorActual() + "\n";
                break;
            case JUGAR:
                modelo.jugar();
                mensaje = "\nEn el dado te ha salido un " + modelo.getValorDado() + " por lo que has acabado en la casilla " + modelo.obtenerCasillaJugadorActual() + "\n";
                break;
            case APLICARSORPRESA:
                mensaje = "\nTe ha salido la siguiente carta sorpresa: " + modelo.getCartaActual() + "\n";
                modelo.aplicarSorpresa();
                break;
            case INTENTARSALIRCARCELPAGANDOLIBERTAD:
                boolean pagada = !modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
                if(pagada)
                    mensaje = "\nHas pagado 6000€ por tu libertad\n";
                else
                    mensaje = "\nNo tienes suficiente dinero para pagar tu libertad\n";
                break;
            case INTENTARSALIRCARCELTIRANDODADO:
                boolean libre = !modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
                if(libre)
                    mensaje = "\nHas conseguido salir de la cárcel\n";
                else
                    mensaje = "\nNo has tenido suerte, sigues en la cárcel\n";
                break;
            case COMPRARTITULOPROPIEDAD:
                boolean comprada = modelo.comprarTituloPropiedad();
                if(comprada)
                    mensaje = "\nLa casilla actual ahora es tuya\n";
                else
                    mensaje = "\nNo puedes comprar esta casilla\n";
                break;
            case HIPOTECARPROPIEDAD:
                modelo.hipotecarPropiedad(casillaElegida);
                mensaje = "\nSe ha hipotecado la casilla " + casillaElegida + "\n";
                break;
            case CANCELARHIPOTECA:
                modelo.cancelarHipoteca(casillaElegida);
                mensaje = "\nSe ha cancelado la hipoteca de la casilla " + casillaElegida + "\n";
                break;
            case EDIFICARCASA:
                boolean edificada = modelo.edificarCasa(casillaElegida);
                if(edificada)
                    mensaje = "\nSe ha edificado una casa en la casilla " + casillaElegida + "\n";
                else
                    mensaje = "\nNo se ha podido edificar la casa\n";
                break;
            case EDIFICARHOTEL:
                boolean edificado = modelo.edificarHotel(casillaElegida);
                if(edificado)
                    mensaje = "\nSe ha edificado un hotel en la casilla " + casillaElegida + "\n";
                else
                    mensaje = "\nNo se ha podido edificar el hotel\n";
                break;
            case VENDERPROPIEDAD:
                modelo.venderPropiedad(casillaElegida);
                mensaje = "\nHas vendido la casilla " + casillaElegida + "\n";
                break;
            case PASARTURNO:
                modelo.siguienteJugador();
                mensaje = "\nTurno del jugador " + modelo.getJugadorActual() + "\n";
                break;
            case OBTENERRANKING:
                modelo.obtenerRanking();
                int i;
                for(i=0; i<nombreJugadores.size(); i++){
                    mensaje = mensaje + modelo.getJugadores().get(i).toString() + "\n";
                }
                mensaje = mensaje + "\n";
                break;
            case TERMINARJUEGO:
                mensaje = "\n----------JUEGO FINALIZADO----------";
                break;
            case MOSTRARJUGADORACTUAL:
                mensaje = "\n" + modelo.getJugadorActual().toString() + "\n";
                break;
            case MOSTRARJUGADORES:
                for(i=0; i<nombreJugadores.size(); i++){
                    mensaje = mensaje + "\n" + modelo.getJugadores().get(i).toString() + "\n";
                }
                mensaje = mensaje + "\n";
                break;
            case MOSTRARTABLERO:
                mensaje = "\n" + modelo.getTablero().toString() + "\n";
                break;
        }
        return mensaje;
    }
}
