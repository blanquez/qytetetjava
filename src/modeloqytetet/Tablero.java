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
public class Tablero {
    private ArrayList<Casilla> casillas;
    private Casilla carcel;

    public Tablero() {
        inicializar();
    }

    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    public Casilla getCarcel() {
        return carcel;
    }

    @Override
    public String toString() {
        String res = "Tablero{\n";
        
        for(int i=0;i<casillas.size();i++){
            res += casillas.get(i).toString() + "\n";
        }
        res += "}";
        
        return res;
    }
    
    private void inicializar(){
        casillas = new ArrayList<>();
        carcel = new OtraCasilla(10, 0, TipoCasilla.CARCEL);
                
        casillas.add(new OtraCasilla(0, 0, TipoCasilla.SALIDA));
        casillas.add(new Calle(1, new TituloPropiedad("El Faro", 500, 50, 0.1f, 150, 250)));
        casillas.add(new Calle(2, new TituloPropiedad("Distrito Medico", 550, 55, 0f, 200, 300)));
        casillas.add(new OtraCasilla(3, 0, TipoCasilla.SORPRESA));
        casillas.add(new Calle(4, new TituloPropiedad("Neptunes Bounty", 600, 60, -0.5f, 250, 350)));
        casillas.add(new Calle(5, new TituloPropiedad("Fountain Futuristics", 650, 65, 0.05f, 300, 400)));
        casillas.add(new Calle(6, new TituloPropiedad("Arcadia", 700, 70, 0.15f, 350, 450)));
        casillas.add(new OtraCasilla(7, 150, TipoCasilla.IMPUESTO));
        casillas.add(new Calle(8, new TituloPropiedad("Feria Agricola", 750, 75, 0.2f, 400, 500)));
        casillas.add(new OtraCasilla(9, 0, TipoCasilla.SORPRESA));
        casillas.add(carcel);
        casillas.add(new Calle(11, new TituloPropiedad("Fort Frolic", 800, 80, 0.1f, 500, 550)));
        casillas.add(new Calle(12, new TituloPropiedad("Hephaestus", 850, 85, -0.1f, 600, 600)));
        casillas.add(new OtraCasilla(13, 0, TipoCasilla.JUEZ));
        casillas.add(new Calle(14, new TituloPropiedad("Control Central", 900, 90, -0.2f, 700, 650)));
        casillas.add(new OtraCasilla(15, 0, TipoCasilla.PARKING));
        casillas.add(new Calle(16, new TituloPropiedad("Olympus Heights", 950, 95, 0f, 800, 700)));
        casillas.add(new Calle(17, new TituloPropiedad("Plaza Apollo", 975, 97, 0.2f, 900, 725)));
        casillas.add(new OtraCasilla(18, 0, TipoCasilla.SORPRESA));
        casillas.add(new Calle(19, new TituloPropiedad("Point Prometheus", 1000, 100, 0.15f, 1000, 750)));
        
    }
    
    boolean esCasillaCarcel(int numeroCasilla){
        return carcel.getNumeroCasilla() == numeroCasilla;
    }
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento){
        return casillas.get((casilla.getNumeroCasilla()+desplazamiento)%casillas.size());
    }
    
    Casilla obtenerCasillaNumero(int numeroCasilla){
        return casillas.get(numeroCasilla);
    }
}
