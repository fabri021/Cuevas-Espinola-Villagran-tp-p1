package juego;
import java.awt.Color;
import entorno.Entorno;

public class Campo {
    private double x, y;     // posición del centro de la casilla
    private double ancho, alto;
    private boolean ocupada;
    private boolean bloqueadoRegalo; //bloquear las casillas de los regalos

    public Campo(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.ocupada = false;
    }

    public void dibujar(Entorno e) {
        e.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
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

