package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    // Objetos HUD
    Planta icon;
    Tanque iconT;
    Regalos gift;

    // Arreglos para plantas y tanques
    private Planta[] RoseBlade;
    private Tanque[] Tung;
    private int contadorTicks = 0;
    private int roseI = 0;
    private int tungI = 0;

    // Entorno y tablero
    private Entorno entorno;
    private Campo[][] tablero;
    private int filas = 5;
    private int columnas = 10;
    private double anchoCasilla = 102;
    private double altoCasilla = 102;
    private double margenX = 51;
    private double margenY = 151;
    private Regalos[] regalosPorFila;

    // Arrastre click izquierdo
    private Planta searrastraPlanta = null;
    private Tanque searrastraTanque = null;

    // Modo mover click derecho + flechas
    private Planta plantaEnMovimiento = null;
    private Tanque tanqueEnMovimiento = null;
    private boolean modoMover = false;

    Juego() {
        icon = new Planta(50.0, 50.0);
        iconT = new Tanque(200.0, 50.0);
        gift = new Regalos(100.0, 100.0, 51.0, 51.0);

        this.entorno = new Entorno(this, "Proyecto para TP", 916, 610);
        RoseBlade = new Planta[10];
        Tung = new Tanque[10];

        inicializarTablero();
        inicializarRegalosPorFila();

        this.entorno.iniciar();
    }

    // --------------------- GENERACIÓN DE OBJETOS ---------------------
    private void generarPlantas() {
        contadorTicks++;

        if (contadorTicks % 300 == 0) {
            boolean hayPlantaLibre = false;
            for (Planta p : RoseBlade) {
                if (p != null && !p.seleccionada) {
                    hayPlantaLibre = true;
                    break;
                }
            }
            if (!hayPlantaLibre && roseI < RoseBlade.length) {
                RoseBlade[roseI] = new Planta(50.0, 50.0);
                roseI++;
                System.out.println("Planta disponible");
            }

            boolean hayTanqueLibre = false;
            for (Tanque t : Tung) {
                if (t != null && !t.seleccionada) {
                    hayTanqueLibre = true;
                    break;
                }
            }
            if (!hayTanqueLibre && tungI < Tung.length) {
                Tung[tungI] = new Tanque(200.0, 50.0);
                tungI++;
                System.out.println("Tanque disponible");
            }
        }
    }

    // --------------------- INICIALIZAR TABLERO ---------------------
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
            Campo c = tablero[i][0]; // primera columna
            double xCentro = c.getX();
            double yCentro = c.getY();
            regalosPorFila[i] = new Regalos(xCentro, yCentro, 50.0, 50.0);
            // Bloqueamos la casilla para plantas/tanques
            c.bloquearRegalo();
        }
    }

    // --------------------- TICK ---------------------
    public void tick() {
        entorno.colorFondo(new Color(0, 120, 0));
        dibujarTablero();

        for (int i = 0; i < filas; i++) {
            regalosPorFila[i].dibujar(entorno);
        }

        icon.dibujar(entorno);
        iconT.dibujar(entorno);

        generarPlantas();

        for (Planta p : RoseBlade) {
            if (p != null) p.dibujar(entorno);
        }
        for (Tanque t : Tung) {
            if (t != null) t.dibujar(entorno);
        }

        // ----------------- ARRRASTRE CLICK IZQUIERDO -----------------
        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
            if (searrastraPlanta == null && searrastraTanque == null) {
                for (Planta p : RoseBlade) {
                    if (p != null && !p.seleccionada && !p.estaEnTablero()) {
                        searrastraPlanta = p;
                        p.seleccionada = true;
                        break;
                    }
                }
                if (searrastraPlanta == null) {
                    for (Tanque t : Tung) {
                        if (t != null && !t.seleccionada && !t.estaEnTablero()) {
                            searrastraTanque = t;
                            t.seleccionada = true;
                            break;
                        }
                    }
                }
            }
        }

        if (searrastraPlanta != null) {
            searrastraPlanta.x = entorno.mouseX();
            searrastraPlanta.y = entorno.mouseY();
        } else if (searrastraTanque != null) {
            searrastraTanque.x = entorno.mouseX();
            searrastraTanque.y = entorno.mouseY();
        }

        if (entorno.seLevantoBoton(entorno.BOTON_IZQUIERDO)) {
            if (searrastraPlanta != null) {
                if (colocarEnCasilla(searrastraPlanta)) {
                    searrastraPlanta.seleccionada = false;
                    searrastraPlanta = null;
                }
            } else if (searrastraTanque != null) {
                if (colocarEnCasilla(searrastraTanque)) {
                    searrastraTanque.seleccionada = false;
                    searrastraTanque = null;
                }
            }
        }

        // ----------------- MODO MOVER CLICK DERECHO -----------------
        if (entorno.sePresionoBoton(entorno.BOTON_DERECHO)) {
            if (!modoMover) {
                for (Planta p : RoseBlade) {
                    if (p != null && p.estaEnTablero() && clickSobrePlanta(p)) {
                        plantaEnMovimiento = p;
                        modoMover = true;
                        return;
                    }
                }
                for (Tanque t : Tung) {
                    if (t != null && t.estaEnTablero() && clickSobreTanque(t)) {
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

    // --------------------- MÉTODOS AUXILIARES ---------------------
    private void dibujarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j].dibujar(entorno);
            }
        }
    }

    private boolean clickSobrePlanta(Planta p) {
        double mx = entorno.mouseX();
        double my = entorno.mouseY();
        double ancho = 50;
        double alto = 50;
        return mx >= p.x - ancho / 2 && mx <= p.x + ancho / 2 &&
               my >= p.y - alto / 2 && my <= p.y + alto / 2;
    }

    private boolean clickSobreTanque(Tanque t) {
        double mx = entorno.mouseX();
        double my = entorno.mouseY();
        double ancho = 50;
        double alto = 50;
        return mx >= t.x - ancho / 2 && mx <= t.x + ancho / 2 &&
               my >= t.y - alto / 2 && my <= t.y + alto / 2;
    }

    private boolean colocarEnCasilla(Planta p) {
        double mx = entorno.mouseX();
        double my = entorno.mouseY();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Campo c = tablero[i][j];
                if (mx >= c.getX() - anchoCasilla / 2 && mx <= c.getX() + anchoCasilla / 2 &&
                    my >= c.getY() - altoCasilla / 2 && my <= c.getY() + altoCasilla / 2) {
                    if (!c.estaOcupada()) {
                        p.x = c.getX();
                        p.y = c.getY();
                        c.ocupar();
                        p.setEnTablero(true);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    private boolean colocarEnCasilla(Tanque t) {
        double mx = entorno.mouseX();
        double my = entorno.mouseY();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Campo c = tablero[i][j];
                if (mx >= c.getX() - anchoCasilla / 2 && mx <= c.getX() + anchoCasilla / 2 &&
                    my >= c.getY() - altoCasilla / 2 && my <= c.getY() + altoCasilla / 2) {
                    if (!c.estaOcupada()) {
                        t.x = c.getX();
                        t.y = c.getY();
                        c.ocupar();
                        t.setEnTablero(true);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    // ----------------- MOVER CON FLECHAS -----------------
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
            !tablero[filaActual-1][colActual].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            filaActual--;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_ABAJO) && filaActual < filas-1 &&
            !tablero[filaActual+1][colActual].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            filaActual++;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_IZQUIERDA) && colActual > 0 &&
            !tablero[filaActual][colActual-1].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            colActual--;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_DERECHA) && colActual < columnas-1 &&
            !tablero[filaActual][colActual+1].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            colActual++;
            colocarPlantaEnCasilla(p, filaActual, colActual);
        }
    }

    private void colocarPlantaEnCasilla(Planta p, int fila, int col) {
        Campo c = tablero[fila][col];
        p.x = c.getX();
        p.y = c.getY();
        c.ocupar();
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
            !tablero[filaActual-1][colActual].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            filaActual--;
            colocarTanqueEnCasilla(t, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_ABAJO) && filaActual < filas-1 &&
            !tablero[filaActual+1][colActual].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            filaActual++;
            colocarTanqueEnCasilla(t, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_IZQUIERDA) && colActual > 0 &&
            !tablero[filaActual][colActual-1].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            colActual--;
            colocarTanqueEnCasilla(t, filaActual, colActual);
        }
        if (entorno.sePresiono(entorno.TECLA_DERECHA) && colActual < columnas-1 &&
            !tablero[filaActual][colActual+1].estaOcupada()) {
            tablero[filaActual][colActual].liberar();
            colActual++;
            colocarTanqueEnCasilla(t, filaActual, colActual);
        }
    }

    private void colocarTanqueEnCasilla(Tanque t, int fila, int col) {
        Campo c = tablero[fila][col];
        t.x = c.getX();
        t.y = c.getY();
        c.ocupar();
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        new Juego();
    }
}
