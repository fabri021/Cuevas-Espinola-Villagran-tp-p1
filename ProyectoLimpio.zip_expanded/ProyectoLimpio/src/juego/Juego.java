package juego;


import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	Planta roseBlade;
	Tanque tung;
	Regalos gift;
	private Entorno entorno;
	private Campo[][] tablero;
    private int filas = 5;
    private int columnas = 10;
    private double anchoCasilla = 102;
    private double altoCasilla = 102;
    private double margenX = 51;
    private double margenY = 151;
    private Regalos[] regalosPorFila;
    //llamamos la clase planta
    
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		roseBlade = new Planta(100.0, 100.0);
		tung = new Tanque(150.0, 150.0);
		gift = new Regalos(100.0, 100.0, 50.0, 50.0);	
		
		this.entorno = new Entorno(this, "Proyecto para TP", 916, 610);
	
		
		
		
		
		inicializarTablero();
		inicializarRegalosPorFila();
		
		
		
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
	 private void inicializarRegalosPorFila() {
		    regalosPorFila = new Regalos[filas];
		    for (int i = 0; i < filas; i++) {
		        Campo c = tablero[i][0]; // primera columna de cada fila
		        double xCentro = c.getX() + c.getAncho() / 40;
		        double yCentro = c.getY() + c.getAlto() / 40;
		        regalosPorFila[i] = new Regalos(xCentro, yCentro, 50.0, 50.0);
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
		
		//roseblade dibujada
		roseBlade.dibujar(entorno);
		if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && seleccionada(roseBlade)) {
			roseBlade.seleccionada = true;
			arrastrar();
		}else {
			roseBlade.seleccionada = false;
		}
		
		tung.dibujar(entorno);
		if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && seleccionada(tung)) {
			tung.seleccionada = true;
			arrastrarT();
		}else {
			tung.seleccionada = false;}
		
		for (int i = 0; i < filas; i++) {
		    regalosPorFila[i].dibujar(entorno);
		}

		
	}
		
		
		
	     
		
	
	private void arrastrar() {
		roseBlade.x = entorno.mouseX();
		roseBlade.y = entorno.mouseY();
	}
	
	private void arrastrarT() {
		tung.x = entorno.mouseX();
		tung.y = entorno.mouseY();
	}
	
	private boolean seleccionada(Planta p) {
		double cursorX = entorno.mouseX();
		double cursorY = entorno.mouseY();
		return cursorX > p.getBordeIzq() && cursorX < p.getBordeDer() && cursorY > p.getBordeSup() && cursorY < p.getBordeInf();
	}
	
	private boolean seleccionada(Tanque t) {
		double cursorX = entorno.mouseX();
		double cursorY = entorno.mouseY();
		return cursorX > t.getBordeIzq() && cursorX < t.getBordeDer() && cursorY > t.getBordeSup() && cursorY < t.getBordeInf();
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
