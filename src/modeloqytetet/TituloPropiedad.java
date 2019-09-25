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
public class TituloPropiedad {
    private String nombre;
    private boolean hipotecada;
    private int precioCompra;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private int numHoteles;
    private int numCasas;
    private Jugador propietario;

    public TituloPropiedad(String nombre, int precioCompra, int alquilerBase, float factorRevalorizacion, int hipotecaBase, int precioEdificar) {
        this.nombre = nombre;
        this.hipotecada = false;
        this.precioCompra = precioCompra;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        this.numHoteles = 0;
        this.numCasas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isHipotecada() {
        return hipotecada;
    }

    public int getPrecioCompra() {
        return precioCompra;
    }

    public int getAlquilerBase() {
        return alquilerBase;
    }

    public float getFactorRevalorizacion() {
        return factorRevalorizacion;
    }

    public int getHipotecaBase() {
        return hipotecaBase;
    }

    public int getPrecioEdificar() {
        return precioEdificar;
    }

    public int getNumHoteles() {
        return numHoteles;
    }

    public int getNumCasas() {
        return numCasas;
    }
    
    public Jugador getPropietario(){
        return propietario;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }
    
    public void setPropietario(Jugador propietario){
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" + "nombre=" + nombre + ", hipotecada=" + hipotecada + ", precioCompra=" + precioCompra + ", alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", precioEdificar=" + precioEdificar + ", numHoteles=" + numHoteles + ", numCasas=" + numCasas + '}';
    }
    
    int calcularCosteCancelarHipoteca(){
        return (int)(calcularCosteHipotecar()*1.1);
    }
    
    int calcularCosteHipotecar(){
        return (int) (hipotecaBase + numCasas * 0.5 * hipotecaBase + numHoteles * hipotecaBase);
    }
    
    int calcularImporteAlquiler(){
        return (int) (alquilerBase + ( numCasas * 0.5 + numHoteles * 2 ));
    }
    
    int calcularPrecioVenta(){
        return precioCompra + (int) ((numCasas + numHoteles) * precioEdificar * factorRevalorizacion);
    }
    
    void cancelarHipoteca(){
        setHipotecada(false);
    }
    
    void cobrarAlquiler(int coste){
        
    }
    
    void edificarCasa(){
        numCasas=numCasas+1;
    }
    
    void edificarHotel(){
        numHoteles=numHoteles+1;
        numCasas=numCasas-4;
    }
    
    int hipotecar(){
        setHipotecada(true);
        return calcularCosteHipotecar();
    }
    
    int pagarAlquiler(){
        int costeAlquiler = calcularImporteAlquiler();
        propietario.modificarSaldo(-costeAlquiler);
        return costeAlquiler;
    }
    
    boolean propietarioEncarcelado(){
        return propietario.isEncarcelado();
    }
    
    boolean tengoPropietario(){
        return propietario != null;
    }
}
