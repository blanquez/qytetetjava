/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;
import java.util.ArrayList;

/**
 *
 * @author ajose
 */
public class Jugador implements Comparable{
//public class Jugador {
    private boolean encarcelado;
    private String nombre;
    private int saldo;
    private Sorpresa cartaLibertad;
    private Casilla casillaActual;
    private ArrayList<TituloPropiedad> propiedades;
    
    public Jugador(String nomb){
        encarcelado=false;
        nombre=nomb;
        saldo=7500;
        propiedades = new ArrayList<TituloPropiedad>();
        cartaLibertad = null;
        casillaActual = null;
    }
    
    protected Jugador(Jugador otroJugador){
        encarcelado=otroJugador.encarcelado;
        nombre=otroJugador.nombre;
        saldo=otroJugador.saldo;
        propiedades=otroJugador.propiedades;
        cartaLibertad=otroJugador.cartaLibertad;
        casillaActual=otroJugador.casillaActual;
    }
    
    protected Especulador convertirme(int fian){
        Especulador nuevo= new Especulador(this, fian);
        return nuevo;
    }
    
    protected boolean deboIrACarcel(){
        return !tengoCartaLibertad();
    }
    
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        return titulo.getNumCasas()<4;
    }
    
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        return (titulo.getNumCasas()==4 && titulo.getNumHoteles()<4);
    }

    boolean isEncarcelado() {
        return encarcelado;
    }

    String getNombre() {
        return nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    Sorpresa getCartaLibertad() {
        return cartaLibertad;
    }

    Casilla getCasillaActual() {
        return casillaActual;
    }

    ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }
    
    void setSaldo(int saldo){
        this.saldo = saldo;
    }

    void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    void setCartaLibertad(Sorpresa cartaLibertad) {
        this.cartaLibertad = cartaLibertad;
    }

    void setCasillaActual(Casilla casillaActual) {
        this.casillaActual = casillaActual;
    }

    @Override
    public String toString() {
        String mensaje = "Jugador{" + "nombre=" + nombre + ", saldo=" + saldo + ", capital=" + obtenerCapital() + ", propiedades=";
        for(TituloPropiedad titulo: propiedades){
            mensaje = mensaje + titulo.toString();
        }
        return mensaje;
    }
    
    @Override
    public int compareTo(Object otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - obtenerCapital();
    }
    
    
    boolean cancelarHipoteca(TituloPropiedad titulo){
        boolean cancelar = saldo > titulo.calcularCosteCancelarHipoteca();
        if(cancelar){
            modificarSaldo(-titulo.calcularCosteCancelarHipoteca());
            titulo.cancelarHipoteca();
        }
        return cancelar;
    }
    
    boolean comprarTituloPropiedad(){
        int costeCompra = casillaActual.getCoste();
        if (costeCompra < saldo){
            casillaActual.asignarPropietario(this);
            propiedades.add(casillaActual.getTitulo());
            modificarSaldo(-costeCompra);
            return true;
        }
        return false;
    }
    
    int cuantasCasasHotelesTengo(){
        int contador=0;
        for(TituloPropiedad titulo: propiedades)
            contador = contador + titulo.getNumCasas() + titulo.getNumHoteles();
        return contador;
    }
    
    boolean deboPagarAlquiler(){
        TituloPropiedad titulo = casillaActual.getTitulo();
        return(!esDeMiPropiedad(titulo) && titulo.tengoPropietario() && !titulo.propietarioEncarcelado() && !titulo.isHipotecada());
    }
    
    Sorpresa devolverCartaLibertad(){
        Sorpresa objeto=getCartaLibertad();
        setCartaLibertad(null);
        return objeto;
    }
    
    boolean edificarCasa(TituloPropiedad titulo){
        boolean edificada=false;
        if(puedoEdificarCasa(titulo)){
            int costeEdificarCasa=titulo.getPrecioEdificar();
            if(tengoSaldo(costeEdificarCasa)){
                titulo.edificarCasa();
                modificarSaldo(-costeEdificarCasa);
                edificada=true;
            }
        }
        return edificada;
    }
    
    boolean edificarHotel(TituloPropiedad titulo){
        boolean edificada=false;
        if(puedoEdificarHotel(titulo)){
            int costeEdificarHotel=titulo.getPrecioEdificar();
            if(tengoSaldo(costeEdificarHotel)){
                titulo.edificarHotel();
                modificarSaldo(-costeEdificarHotel);
                edificada=true;
            }
        }
        return edificada;
    }
    
    private void eliminarDeMisPropiedades(TituloPropiedad titulo){
        propiedades.remove(titulo);
        titulo.setPropietario(null);
    }
    
    private boolean esDeMiPropiedad(TituloPropiedad titulo){
        boolean condicion=false;
        for(TituloPropiedad tit: propiedades){
            if(tit==titulo)
                condicion=true;
        }
        return condicion;
    }
    
    boolean estoyEnCalleLibre(){
        return !casillaActual.tengoPropietario();
    }
    
    boolean hipotecarPropiedad(TituloPropiedad titulo){
        modificarSaldo(titulo.hipotecar());
        return titulo.isHipotecada();
    }
 
    void irACarcel(Casilla casilla){
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    
    void modificarSaldo(int cantidad){
        saldo=saldo+cantidad;
    }
    
    int obtenerCapital(){
        int capital=0;
        capital=capital+getSaldo();
        for(TituloPropiedad titulo: propiedades){
            capital=capital+titulo.getPrecioCompra()+(titulo.getNumCasas()+titulo.getNumHoteles())*titulo.getPrecioEdificar();
        }
        return capital;
    }
    
    ArrayList<TituloPropiedad> obtenerPropiedades(boolean hipotecada){
        ArrayList<TituloPropiedad> prop = new ArrayList<>();
        for(TituloPropiedad tit: propiedades){
            if(tit.isHipotecada() == hipotecada)
                prop.add(tit);
        }
        return prop;
    }
    
    void pagarAlquiler(){
        int costeAlquiler = casillaActual.pagarAlquiler();
        modificarSaldo(-costeAlquiler);
    }
    
    protected void pagarImpuesto(){
        saldo=saldo-casillaActual.getCoste();
    }
    
    void pagarLibertad(int cantidad){
        if(tengoSaldo(cantidad)){
            setEncarcelado(false);
            modificarSaldo(-cantidad);
        }
    }
    
    boolean tengoCartaLibertad(){
        return cartaLibertad != null;
    }
    
    protected boolean tengoSaldo(int cantidad){
        return saldo>cantidad;
    }
    
    void venderPropiedad(Casilla casilla){
        TituloPropiedad titulo = casilla.getTitulo();
        eliminarDeMisPropiedades(titulo);
        modificarSaldo(titulo.calcularPrecioVenta());
    }
}