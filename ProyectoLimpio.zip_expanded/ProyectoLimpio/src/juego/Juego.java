package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Campo[][] tablero;
    private int filas = 5;
    private int columnas = 10;
    private double anchoCasilla = 80;
    private double altoCasilla = 80;
    private double margenX = 40;
    private double margenY = 200;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		inicializarTablero();
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}
	 private void inicializarTablero() {
	        tablero = new Campo[filas][columnas];
	        for (int i = 0; i < filas; i++) {
	            for (int j = 0; j < columnas; j++) {
	                double x = margenX + j * anchoCasilla;
	                double y = margenY + i * altoCasilla;
	                tablero[i][j] = new Campo(x, y, anchoCasilla -2 , altoCasilla -2 );
	            }
	        }
	    }

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		 entorno.colorFondo(new Color(0, 120, 0)); // Césped
	        dibujarTablero();
		// Procesamiento de un instante de tiempo
		// ...
	     
		
	}
	private void dibujarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j].dibujar(entorno);
            }
        }
    }
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
