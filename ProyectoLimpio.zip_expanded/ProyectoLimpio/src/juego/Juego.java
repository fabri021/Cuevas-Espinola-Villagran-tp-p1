package juego;


import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	Planta icon;
	Tanque iconT;
	Regalos gift;
	private Planta[] RoseBlade;
	private Tanque[] Tung;
	private int contadorTicks = 0;
	private int roseI = 0;
	private int tungI = 0;

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
		
		icon = new Planta(50.0,50.0);
		iconT = new Tanque(200.0,50.0);
		gift = new Regalos(100.0, 100.0, 51.0, 51.0);	
		
		this.entorno = new Entorno(this, "Proyecto para TP", 916, 610);
		RoseBlade = new Planta[10];
		Tung = new Tanque[10];

	
		
		
		
		
		inicializarTablero();
		inicializarRegalosPorFila();
		
		
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}
	private void generarPlantas() {
	    contadorTicks++;

	    // Cada 100 ticks, agregamos una nueva planta si queda lugar
	    if (contadorTicks % 500 == 0) {
	        if (roseI < RoseBlade.length) {
	            // Aparecen alineadas en el HUD, por ejemplo una al lado de otra
	            double x = 50.0;
	            double y = 50.0;
	            RoseBlade[roseI] = new Planta(y, x);
	            roseI++;
	        }

	        if (tungI < Tung.length) {
	            double x = 50.0;
	            double y = 200.0;
	            Tung[tungI] = new Tanque(y, x);
	            tungI++;
	        }
	    }
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
		        double xCentro = c.getX() + c.getAncho() / 220;
		        double yCentro = c.getY() + c.getAlto() / 220;
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
	    
	        
	    for (int i = 0; i < filas; i++) {
		    regalosPorFila[i].dibujar(entorno);
		}    
	        
	    icon.dibujar(entorno);
	    iconT.dibujar(entorno);
	    generarPlantas();

	 // Dibujar plantas disponibles
	 for (int i = 0; i < RoseBlade.length; i++) {
	     Planta p = RoseBlade[i];
	     if (p != null) {
	         p.dibujar(entorno);

	         // Detectar selección
	         if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && seleccionada(p)) {
	             p.seleccionada = true;
	             arrastrar(p);
	         }

	         // Soltar sobre casilla
	         if (entorno.seLevantoBoton(entorno.BOTON_IZQUIERDO) && p.seleccionada) {
	             colocarEnCasilla(p);
	             p.seleccionada = false;
	         }
	     }
	 }

	 // Dibujar tanques disponibles
	 for (int i = 0; i < Tung.length; i++) {
	     Tanque t = Tung[i];
	     if (t != null) {
	         t.dibujar(entorno);

	         if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && seleccionada(t)) {
	             t.seleccionada = true;
	             arrastrarT(t);
	         }

	         if (entorno.seLevantoBoton(entorno.BOTON_IZQUIERDO) && t.seleccionada) {
	             colocarEnCasilla(t);
	             t.seleccionada = false;
	         }
	     }
	 }
	

		
	}
		
		
		
	     
		
	
	private void arrastrar(Planta rosa) {
	    rosa.x = entorno.mouseX();
	    rosa.y = entorno.mouseY();
	}

	private void arrastrarT(Tanque tung) {
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
	private void colocarEnCasilla(Planta p) {
	    double mx = entorno.mouseX();
	    double my = entorno.mouseY();

	    for (int i = 0; i < filas; i++) {
	        for (int j = 0; j < columnas; j++) {
	            Campo c = tablero[i][j];

	            double xMin = c.getX() - (anchoCasilla / 2);
	            double xMax = c.getX() + (anchoCasilla / 2);
	            double yMin = c.getY() - (altoCasilla / 2);
	            double yMax = c.getY() + (altoCasilla / 2);

	            if (mx >= xMin && mx <= xMax && my >= yMin && my <= yMax) {
	                if (!c.estaOcupada()) {
	                    p.x = c.getX();
	                    p.y = c.getY();
	                    c.ocupar();
	                    System.out.println("RoseBlade colocada en [" + i + "][" + j + "]");
	                }
	                return;
	            }
	        }
	    }
	}

	private void colocarEnCasilla(Tanque t) {
	    double mx = entorno.mouseX();
	    double my = entorno.mouseY();

	    for (int i = 0; i < filas; i++) {
	        for (int j = 0; j < columnas; j++) {
	            Campo c = tablero[i][j];

	            double xMin = c.getX() - (anchoCasilla / 2);
	            double xMax = c.getX() + (anchoCasilla / 2);
	            double yMin = c.getY() - (altoCasilla / 2);
	            double yMax = c.getY() + (altoCasilla / 2);

	            if (mx >= xMin && mx <= xMax && my >= yMin && my <= yMax) {
	                if (!c.estaOcupada()) {
	                    t.x = c.getX();
	                    t.y = c.getY();
	                    c.ocupar();
	                    System.out.println("Tung colocado en [" + i + "][" + j + "]");
	                }
	                return;
	            }
	        }
	    }
	}

	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
