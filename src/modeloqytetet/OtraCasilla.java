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
public class OtraCasilla extends Casilla {
    public OtraCasilla(int numeroCasilla, int coste, TipoCasilla tipo) {
        super(numeroCasilla, coste, tipo, null);
    }
    
    @Override
    protected TipoCasilla getTipo(){
        return super.getTipo();
    }
    
    @Override
    protected boolean soyEdificable(){
        return false;
    }
    
    @Override
    protected TituloPropiedad getTitulo(){
        return null;
    }
    
    @Override
    protected boolean tengoPropietario(){
        return false;
    }
    
    @Override
    protected void asignarPropietario(Jugador jugador){
        super.getTitulo().setPropietario(null);
    }
    
    @Override
    public int pagarAlquiler(){
        return 0;
    }
    @Override
    public String toString() {
        return "Casilla{" + "numeroCasilla=" + getNumeroCasilla() + ", coste=" + getCoste() + ", tipo=" + getTipo() + "}";
    }
}
