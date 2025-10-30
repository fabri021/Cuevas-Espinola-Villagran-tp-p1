package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;


public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
	
    Planta icon;
    Tanque iconT;
    Regalos gift;
    Zombies zom;
    // arreglo para generar las plantas
    private Planta[] RoseBlade;
    private Tanque[] Tung;
    
    private Zombies[] zombie;
    private int zomI = 0;
    
    
    private int contadorTicks = 0;
    private int contadorTicksZ = 0;
    // indices para recorrer los arreglos
    private int roseI = 0;
    private int tungI = 0;
    //random para aparicion zombies
    private Random random = new Random();

    private Entorno entorno;
    private Campo[][] tablero;
    private int filas = 5;
    private int columnas = 10;
    private double anchoCasilla = 102;
    private double altoCasilla = 102;
    private double margenX = 51;
    private double margenY = 151;
    private Regalos[] regalosPorFila;
    boolean modoMover = false;
    private Planta plantaEnMovimiento = null;
    private Tanque tanqueEnMovimiento = null;
    // Nueva variable para manejar selección única
    private Object objetoSeleccionado = null;
    // llamamos la clase planta

    // Variables y métodos propios de cada grupo
    // ...

    Juego() {
        // Inicializa el objeto entorno

        icon = new Planta(50.0, 50.0);
        iconT = new Tanque(200.0, 50.0);
        gift = new Regalos(100.0, 100.0, 51.0, 51.0);

        this.entorno = new Entorno(this, "Proyecto para TP", 1016, 610);
        RoseBlade = new Planta[10];
        Tung = new Tanque[10];
        zombie = new Zombies[10]; // O el tamaño que prefieras

        inicializarTablero();
        inicializarRegalosPorFila();

        // Inicializar lo que haga falta para el juego
        // ...

        // Inicia el juego!
        this.entorno.iniciar();
    }

    private void moverPlanta(Planta p) {
        int filaActual = -1, colActual = -1;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j].getX() == p.x && tablero[i][j].getY() == p.y) {
                    filaActual = i;
                    colActual = j;
                }
            }
        }
        if (filaActual == -1) return;

        if (entorno.sePresiono(entorno.TECLA_ARRIBA) && filaActual > 0 &&
            !tablero[filaActual - 1][colActual].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            filaActual--;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_ABAJO) && filaActual < filas - 1 &&
            !tablero[filaActual + 1][colActual].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            filaActual++;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_IZQUIERDA) && colActual > 0 &&
            !tablero[filaActual][colActual - 1].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            colActual--;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_DERECHA) && colActual < columnas - 1 &&
            !tablero[filaActual][colActual + 1].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            colActual++;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
    }

    private void moverTanque(Tanque t) {
        int filaActual = -1, colActual = -1;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j].getX() == t.x && tablero[i][j].getY() == t.y) {
                    filaActual = i;
                    colActual = j;
                }
            }
        }
        if (filaActual == -1) return;

        if (entorno.sePresiono(entorno.TECLA_ARRIBA) && filaActual > 0 &&
            !tablero[filaActual - 1][colActual].estaOcupada()) {
        	
            tablero[filaActual][colActual].liberar();            
            filaActual--;       
            colocarTanqueEnCasilla(t, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_ABAJO) && filaActual < filas - 1 &&
            !tablero[filaActual + 1][colActual].estaOcupada()) {
        	
        	tablero[filaActual][colActual].liberar();
            filaActual++;
            colocarTanqueEnCasilla(t, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_IZQUIERDA) && colActual > 0 &&
            !tablero[filaActual][colActual - 1].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            colActual--;
            colocarTanqueEnCasilla(t, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_DERECHA) && colActual < columnas - 1 &&
            !tablero[filaActual][colActual + 1].estaOcupada()) {
            	tablero[filaActual][colActual].liberar();
            	colActual++;
            	colocarTanqueEnCasilla(t, filaActual, colActual);
        }
    }

    private void colocarPlantaEnCasilla(Planta p, int fila, int col) {
        Campo c = tablero[fila][col];
        p.x = c.getX();
        p.y = c.getY();
        c.ocupar();
    }

    private void colocarTanqueEnCasilla(Tanque t, int fila, int col) {
        Campo c = tablero[fila][col];
        t.x = c.getX();
        t.y = c.getY();
        c.ocupar();
    }

    private boolean hayPlantaDisponible() {
        for (int i = 0; i < roseI; i++) {
            if (RoseBlade[i] != null && !RoseBlade[i].colocada) {
                return true; // Hay al menos una planta generada y no colocada
            }
        }
        return false;
    }

    private boolean hayTanqueDisponible() {
        for (int i = 0; i < tungI; i++) {
            if (Tung[i] != null && !Tung[i].colocada) {
                return true;
            }
        }
        return false;
    }

    private void generarPlantas() {
        contadorTicks++;

        // Cada 300 ticks, agregamos una nueva planta si queda lugar
        if (contadorTicks % 300 == 0) {

            if (!hayPlantaDisponible() && roseI < RoseBlade.length) {

                double x = 50.0;
                double y = 50.0;
                RoseBlade[roseI] = new Planta(y, x);
                roseI++;
                System.out.println("RoseBlade está disponible");
            }

            if (!hayTanqueDisponible() && tungI < Tung.length) {
                double x = 50.0;
                double y = 200.0;
                Tung[tungI] = new Tanque(y, x);
                tungI++;
                System.out.println("Tanque está disponible");
            }

        }
    }

    private void inicializarTablero() {
        tablero = new Campo[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                double x = margenX + j * anchoCasilla;
                double y = margenY + i * altoCasilla;
                tablero[i][j] = new Campo(x, y, anchoCasilla - 2, altoCasilla - 2);
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
            c.ocupar();
        }
    }
    
    private void generarZombies() {
        contadorTicksZ++;
        if (contadorTicksZ % 300 == 0 && zomI < zombie.length) {
            int filaAleatoria = random.nextInt(filas);
            double x = tablero[filaAleatoria][columnas - 1].getX();
            double y = tablero[filaAleatoria][columnas - 1].getY();
            zombie[zomI] = new Zombies(x + 50, y);
            zomI++;
            System.out.println("Zombie generado en fila " + filaAleatoria);
        }
    }

    private void movimientoZombie() {
        for (int i = 0; i < zomI; i++) {
            if (zombie[i] != null) {  
                zombie[i].x -= 3.5;              
            }
        }
    }
    
    public boolean colision(double x1, double y1, double x2, double y2, double dist) {
    	return (x1 - x2) * (x1 - x2) + (y1-y2) * (y1-y2) < dist*dist;
    }
    
    

    /**
     * Durante el juego, el método tick() será ejecutado en cada instante y
     * por lo tanto es el método más importante de esta clase. Aquí se debe
     * actualizar el estado interno del juego para simular el paso del tiempo
     * (ver el enunciado del TP para mayor detalle).
     */
    public void tick() {

        // mostramos las casillas
        entorno.colorFondo(new Color(0, 120, 0)); // Césped
        dibujarTablero();

        for (int i = 0; i < filas; i++) {
            regalosPorFila[i].dibujar(entorno);
        }
        
        for (int i = 0; i < zomI; i++) {
            if (zombie[i] != null) {
                zombie[i].dibujar(entorno);
            }
        }
        // mostramos los iconos
        icon.dibujar(entorno);
        iconT.dibujar(entorno);
        generarPlantas();
        generarZombies();
        movimientoZombie();
        
        
        for( Zombies z : zombie) {
        	 if (colision(z.x, z.y, gift.x, gift.y, 10  )) {
        		 System.out.println("El zombie devoro tu regalo :( !!!");
        	 }
        }
       

        // Dibujar plantas disponibles y manejar selección
        for (int i = 0; i < roseI; i++) {
            Planta planta = RoseBlade[i];
            if (planta != null) {
                planta.dibujar(entorno);
                
                // Detectar selección solo si no hay nada seleccionado
                if (objetoSeleccionado == null && !planta.colocada &&
                    entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && seleccionada(planta)) {
                    objetoSeleccionado = planta;
                    planta.seleccionada = true;
                }
            }
        }

        // Dibujar tanques disponibles y manejar selección
        for (int i = 0; i < tungI; i++) {
            Tanque tanque = Tung[i];
            if (tanque != null) {
                tanque.dibujar(entorno);
                if (objetoSeleccionado == null && !tanque.colocada &&
                    entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && seleccionada(tanque)) {
                    objetoSeleccionado = tanque;
                    tanque.seleccionada = true;
                }
            }
        }

        // Arrastrar el objeto seleccionado
        if (objetoSeleccionado != null) {
            if (objetoSeleccionado instanceof Planta) {
                arrastrar((Planta) objetoSeleccionado);
            } else if (objetoSeleccionado instanceof Tanque) {
                arrastrarT((Tanque) objetoSeleccionado);
            }
        }

        // Soltar el objeto seleccionado
        if (entorno.seLevantoBoton(entorno.BOTON_IZQUIERDO) && objetoSeleccionado != null) {
            if (objetoSeleccionado instanceof Planta) {
                colocarEnCasilla((Planta) objetoSeleccionado);
                ((Planta) objetoSeleccionado).seleccionada = false;
            } else if (objetoSeleccionado instanceof Tanque) {
                colocarEnCasilla((Tanque) objetoSeleccionado);
                ((Tanque) objetoSeleccionado).seleccionada = false;
            }
            objetoSeleccionado = null;
        }

        if (entorno.sePresionoBoton(entorno.BOTON_DERECHO)) {
            if (!modoMover) {
                for (Planta p : RoseBlade) {
                    if (p != null && p.colocada && clickSobrePlanta(p)) {
                        plantaEnMovimiento = p;
                        modoMover = true;
                        return;
                    }
                }
                for (Tanque t : Tung) {
                    if (t != null && t.colocada && clickSobreTanque(t)) {
                        tanqueEnMovimiento = t;
                        modoMover = true;
                        return;
                    }
                }
            } else {
                modoMover = false;
                plantaEnMovimiento = null;
                tanqueEnMovimiento = null;
            }
        }

        if (modoMover) {
            if (plantaEnMovimiento != null) moverPlanta(plantaEnMovimiento);
            else if (tanqueEnMovimiento != null) moverTanque(tanqueEnMovimiento);
        }
    }

    private boolean clickSobrePlanta(Planta p) {
        double mx = entorno.mouseX();
        double my = entorno.mouseY();
        double ancho = 50;
        double alto = 50;
        return mx >= p.x - ancho / 2 && mx <= p.x + ancho / 2 && my >= p.y - alto / 2 && my <= p.y + alto / 2;
    }

    private boolean clickSobreTanque(Tanque t) {
        double mx = entorno.mouseX();
        double my = entorno.mouseY();
        double ancho = 50;
        double alto = 50;
        return mx >= t.x - ancho / 2 && mx <= t.x + ancho / 2 && my >= t.y - alto / 2 && my <= t.y + alto / 2;
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

    // coloca la planta en un casillero disponible y lo centra
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
                        p.colocada = true;
                        System.out.println("RoseBlade colocada en [" + i + "][" + j + "]");
                    }
                    return;
                }
            }
        }
    }

    // coloca al tanque en un casillero disponible y lo centra
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
                        t.colocada = true;
                        c.ocupar();
                        System.out.println("Tung colocado en [" + i + "][" + j + "]");
                    }
                    return;
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}
