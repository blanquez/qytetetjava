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
public class Especulador extends Jugador {
    
    private int fianza;
    
    protected Especulador(Jugador jug, int fian){
        super(jug);
        fianza=fian;
    }
    
    @Override
    protected void pagarImpuesto(){
        super.setSaldo((super.getSaldo()-super.getCasillaActual().getCoste())/2);
    }
    
    @Override
    protected Especulador convertirme(int fian){
        return this;
    }
    
    @Override
    protected boolean deboIrACarcel(){
        return (super.deboIrACarcel() && !pagarFianza());
    }
    
    private boolean pagarFianza(){
        boolean condicion=false;
        if(super.getSaldo()>=fianza){
            super.setSaldo(super.getSaldo()-fianza);
            condicion=true;
        }
        return condicion;
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        return titulo.getNumCasas()<8;
    }
    
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        return (titulo.getNumCasas()>=4 && titulo.getNumHoteles()<8);
    }
    
    @Override
    public String toString() {
        return super.toString() + ", fianza=" + fianza;
    }
    
}
