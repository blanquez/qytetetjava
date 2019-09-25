/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ajose
 */
public class Qytetet {
    private static final Qytetet instance = new Qytetet();
    public static Qytetet getInstance(){
        return instance;
    }
    
    public static int MAX_JUGADORES = 4;
    static int NUM_SORPRESAS = 10;
    public static int NUM_CASILLAS = 20;
    static int PRECIO_LIBERTAD = 6000;
    static int SALDO_SALIDA = 1000;
    private ArrayList<Sorpresa> mazo;
    private Tablero tablero;
    private Sorpresa cartaActual;
    private ArrayList<Jugador> jugadores;
    private Jugador jugadorActual;
    private Dado dado;
    private EstadoJuego estado;

    private Qytetet(){
        mazo = new ArrayList<>();
	jugadores = new ArrayList<>();
	jugadorActual = null;
	cartaActual = null;
        estado = null;
        dado = Dado.getInstance();
    }

    public ArrayList<Sorpresa> getMazo() {
        return mazo;
    }

    public void setEstado(EstadoJuego estado) {
        this.estado = estado;
    }

    public EstadoJuego getEstado() {
        return estado;
    }

    public Tablero getTablero() {
        return tablero;
    }
    
    public void inicializarTablero(){
        tablero = new Tablero();
    }
    
    void inicializarCartasSorpresa(){
        mazo.add(new Sorpresa("Te conviertes en Especulador con una fianza de 3000€", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("Te conviertes en Especulador con una fianza de 5000€", 5000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("Tus inversiones en bolsa han dado sus frutos, ganas 600€", 600, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Te multan con 300€ por circular por el carril bus", -300, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Necesitas un respiro, ve a dar un paso por Olympus Height", 16, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("La grua se ha llevado tu coche, recogelo en el parking", 15, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Te condenan por fraude en una antigua venta, vas a la carcel", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Cedes habitaciones para un evento local, cobras 30€ por edificio", 30, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Ha llegado la factura del mobiliario nuevo, pagas 30€ por edificio", -30, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Has cerrado una compra-venta rapida ganando 200€ de cada jugador", 200, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Anoche bebiste y perdiste una apuesta, debes pagar 150€ a cada jugador", -150, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Uno de tus contactos te debe un favor y paga tu fianza", 0, TipoSorpresa.SALIRCARCEL));
        Collections.shuffle(mazo);
    }
    
    void actuarSiEnCasillaEdificable(){
        if(jugadorActual.deboPagarAlquiler()){
            jugadorActual.pagarAlquiler();
            if(jugadorActual.getSaldo() <= 0)
                setEstado(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        }
        Casilla casilla=obtenerCasillaJugadorActual();
        if(estado != EstadoJuego.ALGUNJUGADORENBANCARROTA){
            if(casilla.tengoPropietario())
                setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
            else
                setEstado(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
        }
    }
    
    void actuarSiEnCasillaNoEdificable(){
        setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
        Casilla casillaActual=jugadorActual.getCasillaActual();
        if(casillaActual.getTipo()==TipoCasilla.IMPUESTO)
            jugadorActual.pagarImpuesto();
        else{
            if(casillaActual.getTipo()==TipoCasilla.JUEZ)
                encarcelarJugador();
            else if(casillaActual.getTipo()==TipoCasilla.SORPRESA){
                cartaActual=mazo.remove(0);
                setEstado(EstadoJuego.JA_CONSORPRESA);
            }
        }
    }
    
    public void aplicarSorpresa(){
        setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
        if(cartaActual.getTipo()==TipoSorpresa.SALIRCARCEL)
            jugadorActual.setCartaLibertad(cartaActual);
        else{
            mazo.add(cartaActual);
            if(cartaActual.getTipo()==TipoSorpresa.PAGARCOBRAR){
                jugadorActual.modificarSaldo(cartaActual.getValor());
                if(jugadorActual.getSaldo() <= 0)
                    setEstado(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
            else if(cartaActual.getTipo()==TipoSorpresa.IRACASILLA){
                int valor=cartaActual.getValor();
                boolean casillaCarcel=tablero.esCasillaCarcel(valor);
                if(casillaCarcel)
                    encarcelarJugador();
                else
                    mover(valor);
            }
            else if(cartaActual.getTipo()==TipoSorpresa.PORCASAHOTEL){
                int cantidad=cartaActual.getValor();
                int numeroTotal=jugadorActual.cuantasCasasHotelesTengo();
                jugadorActual.modificarSaldo(cantidad*numeroTotal);
                if(jugadorActual.getSaldo() <= 0)
                    setEstado(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
            else if(cartaActual.getTipo()==TipoSorpresa.PORJUGADOR){
                for(Jugador jug: jugadores){
                    if(jug != jugadorActual){
                        jug.modificarSaldo(-cartaActual.getValor());
                        if(jug.getSaldo() <= 0)
                            setEstado(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        jugadorActual.modificarSaldo(cartaActual.getValor());
                        if(jugadorActual.getSaldo() <= 0)
                            setEstado(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                    }
                }
            }
            else if(cartaActual.getTipo()==TipoSorpresa.CONVERTIRME){
                int i=jugadores.indexOf(jugadorActual);
                jugadorActual=jugadorActual.convertirme(cartaActual.getValor());
                jugadores.set(i, jugadorActual);
            }
        }
    }
    
    public boolean cancelarHipoteca(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        ArrayList<TituloPropiedad> prop = jugadorActual.obtenerPropiedades(true);
        boolean cancelar = false;
        for(TituloPropiedad p: prop){
            if(p == casilla.getTitulo())
                cancelar = true;
        }
        if (cancelar)
            cancelar = jugadorActual.cancelarHipoteca(casilla.getTitulo());
        setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
        return cancelar;
    }
  
    public boolean comprarTituloPropiedad(){
        boolean comprado = jugadorActual.comprarTituloPropiedad();
        if (comprado)
            setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
        return comprado;
    }
  
    public boolean edificarCasa(int numeroCasilla){
        boolean edificada = false;
        Casilla casilla=tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        edificada=jugadorActual.edificarCasa(titulo);
        if (edificada)
            setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
        return edificada;
    }
    
    public boolean edificarHotel(int numeroCasilla){
        boolean edificada = false;
        Casilla casilla=tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        edificada=jugadorActual.edificarHotel(titulo);
        if (edificada)
            setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
        return edificada;
    }
    
    private void encarcelarJugador(){
        if(jugadorActual.deboIrACarcel()){
            jugadorActual.irACarcel(tablero.getCarcel());
            setEstado(EstadoJuego.JA_ENCARCELADO);
        }
        else{
            mazo.add(jugadorActual.devolverCartaLibertad());
            setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }
    
    public Sorpresa getCartaActual(){
        return cartaActual;
    }
    
    Dado getDado(){
        return dado.getInstance();
    }
    
    public Jugador getJugadorActual(){
        return jugadorActual;
    }
    
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    
    public int getValorDado(){
        return dado.getValor();
    }
    
    public void hipotecarPropiedad(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        jugadorActual.hipotecarPropiedad(titulo);
        setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
    }
    
    public void inicializarJuego(ArrayList<String> nombres){
        inicializarJugadores(nombres);
        inicializarTablero();
        inicializarCartasSorpresa();
        salidaJugadores();
    }
    
    private void inicializarJugadores(ArrayList<String> nombres){
        for (String nom: nombres){
            jugadores.add(new Jugador(nom));
        }
    }
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo){
        if(metodo == MetodoSalirCarcel.TIRANDODADO){
            if(tirarDado()>=5)
                jugadorActual.setEncarcelado(false);
        }
        else if(metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
            jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
        }
        boolean encarcelado = jugadorActual.isEncarcelado();
        if (encarcelado)
            setEstado(EstadoJuego.JA_ENCARCELADO);
        else
            setEstado(EstadoJuego.JA_PREPARADO);
        return encarcelado;
    }
    
    public void jugar(){
        int tirada = tirarDado();
        Casilla casillaFinal = tablero.obtenerCasillaFinal(jugadorActual.getCasillaActual(), tirada);
        mover(casillaFinal.getNumeroCasilla());
    }
    
    void mover(int numCasillaDestino){
        Casilla casillaInicial = jugadorActual.getCasillaActual();
        Casilla casillaFinal = tablero.obtenerCasillaNumero(numCasillaDestino);
        jugadorActual.setCasillaActual(casillaFinal);
        if(numCasillaDestino < casillaInicial.getNumeroCasilla())
            jugadorActual.modificarSaldo(SALDO_SALIDA);
        if(casillaFinal.soyEdificable())
            actuarSiEnCasillaEdificable();
        else
            actuarSiEnCasillaNoEdificable();
    }
    
    public Casilla obtenerCasillaJugadorActual(){
        return jugadorActual.getCasillaActual();
    }
    
    public ArrayList<Casilla> obtenerCasillasTablero(){
        return tablero.getCasillas();
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugador(){
        ArrayList<TituloPropiedad> propiedades = new ArrayList<>();
        propiedades = jugadorActual.getPropiedades();
        ArrayList<Integer> prop = new ArrayList<>();
        for(Casilla p: tablero.getCasillas()){
            if(p.getTitulo() != null){
                for(TituloPropiedad t: propiedades){
                    if(p.getTitulo() == t)
                        prop.add(p.getNumeroCasilla());
                }
            }
        }
        return prop;
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca){
        ArrayList<TituloPropiedad> propiedades = new ArrayList<>();
        propiedades = jugadorActual.obtenerPropiedades(estadoHipoteca);
        ArrayList<Integer> prop = new ArrayList<>();
        for(Casilla p: tablero.getCasillas()){
            if(p.getTitulo() != null){
                for(TituloPropiedad t: propiedades){
                    if(p.getTitulo() == t)
                        prop.add(p.getNumeroCasilla());
                }
            }
        }
        return prop;
    }
    
    public void obtenerRanking(){
        Collections.sort(jugadores);
    }
    
    public int obtenerSaldoJugadorActual(){
        return jugadorActual.getSaldo();
    }
    
    private void salidaJugadores(){
        int aleatorio = (int)(Math.random()*(jugadores.size()));
        for(Jugador jug: jugadores)
            jug.setCasillaActual(tablero.getCasillas().get(0));
        jugadorActual=jugadores.get(aleatorio);
        estado=EstadoJuego.JA_PREPARADO;
    }
    
    private void setCartaActual(Sorpresa cartaActual){
        this.cartaActual = cartaActual;
    }
    
    public void siguienteJugador(){
        int indice=0;
        for(int i=0;i<jugadores.size();i++){
            if(jugadores.get(i)==jugadorActual)
                indice=i;
        }
        
        if(indice==jugadores.size()-1)
            jugadorActual=jugadores.get(0);
        else
            jugadorActual=jugadores.get(indice+1);
        
        if(jugadorActual.isEncarcelado())
            estado=EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD;
        else
            estado=EstadoJuego.JA_PREPARADO;
    }
    
    int tirarDado(){
        return dado.tirar();
    }
    
    public void venderPropiedad(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        jugadorActual.venderPropiedad(casilla);
        setEstado(EstadoJuego.JA_PUEDEGESTIONAR);
    }
}
