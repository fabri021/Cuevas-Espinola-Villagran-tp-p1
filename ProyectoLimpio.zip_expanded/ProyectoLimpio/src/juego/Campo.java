package juego;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;

public class Campo {
    private double x, y;     // posición del centro de la casilla
    private double ancho, alto;
    private boolean ocupada;
    private boolean bloqueadoRegalo; //bloquear las casillas de los regalos
    Image c;
    Image hud;
    Image frase;

    public Campo(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.ocupada = false;
        c = Herramientas.cargarImagen("tablero.png");
        hud= Herramientas.cargarImagen("hud.png");
        frase= Herramientas.cargarImagen("HUDPALABRA.png");
        
    }

    public void dibujar(Entorno t) {
    	
    	t.dibujarImagen(c, 500, 351, 0, 1);    	
    	t.dibujarImagen(hud, 500, 50, 0,1);
    	t.dibujarImagen(frase, 500, 50, 0,0.4);
    }
    
   
    public boolean estaOcupada() {
        return ocupada || bloqueadoRegalo;
    }

    public void ocupar() { ocupada = true; }
    public void liberar() { ocupada = false; }

    public void bloquearRegalo() { bloqueadoRegalo = true; }
    public void desbloquearRegalo() { bloqueadoRegalo = false; }

    // getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }
}

