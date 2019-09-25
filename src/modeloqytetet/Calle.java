/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author acer
 */
public class Calle extends Casilla{
    public Calle(int numeroCasilla, TituloPropiedad titulo) {
        super(numeroCasilla,titulo.getPrecioCompra(),TipoCasilla.CALLE,titulo);
    }
    
    public void asignarPropietario(Jugador jugador){
        super.getTitulo().setPropietario(jugador);
    }
    
    @Override
    protected TipoCasilla getTipo(){
        return TipoCasilla.CALLE;
    }
    
    @Override
    protected TituloPropiedad getTitulo(){
        return super.getTitulo();
    }
    
    @Override
    public int pagarAlquiler(){
        return super.getTitulo().pagarAlquiler();
    }
    
    @Override
    protected void setTitulo(TituloPropiedad titulo){
        super.setTitulo(titulo);
    }
    
    @Override
    protected boolean soyEdificable(){
        return true;
    }
    @Override
    public boolean tengoPropietario(){
        return super.getTitulo().tengoPropietario();
    }
    
    @Override
    public String toString() {
        return "Calle{" + getTitulo() + ": numeroCasilla=" + getNumeroCasilla() + ", coste=" + getCoste() + "}";
    }
}
