/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author ajose99bp
 */
public abstract class Casilla {
    private int numeroCasilla;
    private int coste;
    private TipoCasilla tipo;
    private TituloPropiedad titulo;

    public Casilla(int numeroCasilla, int coste, TipoCasilla tipo, TituloPropiedad titulo) {
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
        this.tipo = tipo;
        this.titulo = titulo;
    }

    public int getNumeroCasilla() {
        return numeroCasilla;
    }

    public int getCoste() {
        return coste;
    }

    protected TipoCasilla getTipo() {
        return tipo;
    }

    protected TituloPropiedad getTitulo() {
        return titulo;
    }
    
    public void setCoste(int coste){
        this.coste=coste;
    }
    
    protected void setTitulo(TituloPropiedad titulo){
        this.titulo=titulo;
    }
    
    protected boolean soyEdificable(){
        return tipo == TipoCasilla.CALLE;
    }

    @Override
    public String toString() {
        return "Casilla{" + "numeroCasilla=" + numeroCasilla + ", coste=" + coste + ", tipo=" + tipo + ", titulo=" + titulo + "}";
    }
    
   protected abstract boolean tengoPropietario();
   protected abstract void asignarPropietario(Jugador jugador);
   public abstract int pagarAlquiler();
}