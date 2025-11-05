package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;


public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
	
	//iconos del HUD
	Tanque iconT;
    Planta iconP;
    

    Regalos gift;
    Zombies zom;
    Proyectiles proyectil;
    
    
    private boolean juegoTerminado = false;  // Bandera para saber si el juego terminó
    private String mensajeGameOver = "GAME OVER - Presiona ENTER para reiniciar";  // Mensaje a mostrar
    private boolean juegoGanado = false;
    private String mensajeWIN= "¡¡¡Felicidades, has ganado!!!  -  Presiona ENTER para reiniciar";
    private int zombiesDerrotados = 0;
    // arreglo para generar las plantas
    private Planta[] RoseBlade;
    private Tanque[] Tung;
    
    //Arreglo para generar zombies y su indice
    private Zombies[] zombie;
    private int zomI = 0;
    
    //Contadores de ticks para generación de plantas y zombies
    private int contadorTicks = 0;
    private int contadorTicksZ = 0;
    private int contadorTicksProyectil = 0;


    // indices para recorrer los arreglos
    private int roseI = 0;
    private int tungI = 0;
    
    //random para aparicion zombies
    private Random random = new Random();

    private Entorno entorno;
    
    //generacion del Campo
    private Campo[][] tablero;
    private int filas = 5;
    private int columnas = 10;
    private double anchoCasilla = 100;
    private double altoCasilla = 100;
    private double margenX = 50;
    private double margenY = 150;
    
    private Proyectiles[] proyectiles;  
    private int proyI = 0;              

    //Arreglo para generar Regalos
    private Regalos[] regalosPorFila;
    
    //variable para poder mover las plantas
    boolean modoMover = false;
    private Planta plantaEnMovimiento = null;
    private Tanque tanqueEnMovimiento = null;
    // Nueva variable para manejar selección única
    private Object objetoSeleccionado = null;
    

    

    Juego() {
        // Inicializa el objeto entorno

        iconP = new Planta(50,50);
        iconT = new Tanque(175.0, 50.0);
        gift = new Regalos(100.0, 100.0, 51.0, 51.0);

        this.entorno = new Entorno(this, "Proyecto para TP", 1000, 600);
        RoseBlade = new Planta[15];
        Tung = new Tanque[15];
        zombie = new Zombies[50];
        proyectiles = new Proyectiles[100];  

        
        inicializarTablero();
        inicializarRegalosPorFila();

      
        this.entorno.iniciar();
    }

    // METODOS PARA MOVER LAS PLANTAS CON LAS FLECHAS DIRECCIONALES, SOLO SI LA CASILLA ESTA LIBRE
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
    
    // METODO PARA COLOCAR LAS PLANTAS Y LOS TANQUE EN LAS CASILLAS SELECCIONADAS CON LOS METODOS DE MOVER (planta y tanque)
    
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
    
    // METODOS PARA VERIFICAR SI YA HAY UNA PLANTA O TANQUE DISPONIBLE PARA SER COLOCADA EN EL TABLERO
   
    private boolean hayPlantaDisponible() {
        for (int i = 0; i < roseI; i++) {
            if (RoseBlade[i] != null && !RoseBlade[i].colocada) {
                return true; 
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

    // GENERAMOS LAS PLANTA Y TANQUE, CADA 300 TICKS, EN LOS ARREGLOS 
   
    private void generarPlantas() {
        contadorTicks++;

       
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
                double y = 175.0;
                Tung[tungI] = new Tanque(y, x);
                tungI++;
                System.out.println("Tanque está disponible");
            }

        }
    }
    
    // GENERAMOS LAS CASILLAS DEL TABLERO
   
    private void inicializarTablero() {
        tablero = new Campo[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                double x = margenX + j * anchoCasilla;
                double y = margenY + i * altoCasilla;
                tablero[i][j] = new Campo(x, y, anchoCasilla , altoCasilla );
            }
        }
    }

    // GENERAMOS LOS REGALOS EN LA PRIMER COLUMNA DEL TABLERO
    
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
    
    // GENERAMOS UN ZOMBIE EN EL ARRAY ZOMBIES[] CADA 300 TICKS EN UNA FILA ALEATORIA AFUERA DEL ENTORNO
    
    private void generarZombies() {
        contadorTicksZ++;
        if (contadorTicksZ % 200 == 0 && zomI < zombie.length) {
            int filaAleatoria = random.nextInt(filas);
            double x = tablero[filaAleatoria][columnas - 1].getX();
            double y = tablero[filaAleatoria][columnas - 1].getY();
            zombie[zomI] = new Zombies(x + 50, y);
            zomI++;
            System.out.println("Zombie generado en fila " + filaAleatoria);
        }
    }

    // INDICAMOS EL MOVIMIENTO DEL ZOMBIE POR LA FILA
   
    private void movimientoZombie() {
        for (int i = 0; i < zomI; i++) {
            if (zombie[i] != null && !(zombie[i]).detenido) {  
            	zombie[i].x -= zombie[i].velocidad;                
            }
        }
    }
    
    // METODO PARA QUE ROSEBLADE DETECTE SI HAY ZOMBIES EN SU FILA
    
    private boolean detectarZ(Planta p) {
    	int filaplanta = (int) ((p.y - margenY) / altoCasilla);
    	if (filaplanta < 0 || filaplanta >= filas) {
    		return false;  
    	}

    	for (int i = 0; i < zomI; i++) {
    		Zombies z = zombie[i];
    		if (z != null) {

    			int filaZombie = (int) ((z.getY() - margenY) / altoCasilla);
    			if (filaZombie == filaplanta) {
    				return true;  
    			}
    		}
    	}
    	return false;  
    }

    // METODOS PARA LAS COLISIONES
    private boolean colisionaConRegalo(Zombies z, Regalos g) {
        return !(z.getBordeDer() < g.getBordeIzq() ||
                 z.getBordeIzq() > g.getBordeDer() ||
                 z.getBordeSup() > g.getBordeInf() || 
                 z.getBordeInf() < g.getBordeSup());
    }
    
    public boolean colisionConPlanta(Zombies z, Planta p) {
    	return !(z.getBordeDer() < p.getBordeIzq() ||
    			 z.getBordeIzq() > p.getBordeDer() ||
    			 z.getBordeSup() > p.getBordeInf() ||
    			 z.getBordeInf() < p.getBordeSup());
    }
    
    public boolean colisionConTanque(Zombies z, Tanque t) {
    	return !(z.getBordeDer() < t.getBordeIzq() ||
    			 z.getBordeIzq() > t.getBordeDer() ||
    			 z.getBordeSup() > t.getBordeInf() ||
    			 z.getBordeInf() < t.getBordeSup());
    }
    
    private boolean colisionConProyectil(Proyectiles pr, Zombies z) {
        return !(pr.getBordeDer() < z.getBordeIzq() ||
                 pr.getBordeIzq() > z.getBordeDer() ||
                 pr.getBordeSup() > z.getBordeInf() ||
                 pr.getBordeInf() < z.getBordeSup());
    }

        
      
    public void tick() {

        // mostramos las casillas
        
        dibujarTablero();
        // mostramos los iconos
        iconP.dibujar(entorno);
        iconT.dibujar(entorno);
        // generamos zombies, plantas y el movimiento
        generarPlantas();
        generarZombies();
        movimientoZombie();

        for (int i = 0; i < filas; i++) {
            if (regalosPorFila[i] != null) { 
                regalosPorFila[i].dibujar(entorno);
            }
        }
        
        
        
        for (int i = 0; i < zomI; i++) {

        	if (zombie[i] != null) {
        		zombie[i].dibujar(entorno);


        		for (int j = 0; j < filas; j++) {
        			Regalos regalo = regalosPorFila[j];
        			if (regalo != null && colisionaConRegalo(zombie[i], regalo)) {

        				System.out.println("¡Colisión! Zombie ha tocado el regalo.");
        				regalo.setVida(regalo.getVida() - 1);  
        				if (regalo.getVida() <= 0) {
        					regalosPorFila[j] = null;
        					juegoTerminado = true;
        				}
        			}                
        		}
        		for (int p = 0; p < filas; p++) {
        			Planta roseBlade = RoseBlade[p];

        			if(roseBlade != null && colisionConPlanta(zombie[i], roseBlade)) {
        				roseBlade.setVida(roseBlade.getVida() - 1);
        				System.out.println("colision planta");
        				zombie[i].detenido = true;               	                		
        				if (roseBlade.getVida() <= 0) {
        					// Liberar la casilla ocupada por la planta
        					for (int fila = 0; fila < filas; fila++) {
        						for (int col = 0; col < columnas; col++) {
        							if (tablero[fila][col].getX() == roseBlade.x && tablero[fila][col].getY() == roseBlade.y) {
        								tablero[fila][col].liberar();
        								break;
        							}
        						}
        					}
        					RoseBlade[p] = null;
        					zombie[i].detenido = false;
        				}
        			}
        		}
        		for (int t = 0; t < filas; t++) {
        			Tanque tanque = Tung[t];
        			if(tanque != null && colisionConTanque(zombie[i], tanque)) {
        				tanque.setVida(tanque.getVida() - 1);
        				System.out.println("colision tanque");
        				zombie[i].detenido = true;               	                		
        				if (tanque.getVida() <= 0) {
        					// Liberar la casilla ocupada por el tanque
        					for (int fila = 0; fila < filas; fila++) {
        						for (int col = 0; col < columnas; col++) {
        							if (tablero[fila][col].getX() == tanque.x && tablero[fila][col].getY() == tanque.y) {
        								tablero[fila][col].liberar();
        								break;
        							}
        						}
        					}
        					Tung[t] = null;
        					zombie[i].detenido = false;
        				}
        			}
        		}
        	}
        }
        
        for (int i = 0; i<roseI;i++) {
        	Planta p = RoseBlade[i];
        	if(p!= null && p.colocada) {
        		if(detectarZ(p)) {
        			
        		}
        	}
        }
       
        
        
        
        
       

        
       
        for (int i = 0; i < roseI; i++) {
            Planta planta = RoseBlade[i];
            if (planta != null) {
                planta.dibujar(entorno);
                
                
                if (objetoSeleccionado == null && !planta.colocada &&
                    entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && seleccionada(planta)) {
                    objetoSeleccionado = planta;
                    planta.seleccionada = true;
                }
            }
        }
        for (int i = 0; i < proyI; i++) {
        	Proyectiles pr = proyectiles[i];
        	if (pr != null) {
        		pr.setX(pr.getX() + pr.getVelocidad());
        		pr.dibujar(entorno);

        		// verificar colisión con zombies
        		for (int j = 0; j < zomI; j++) {
        			Zombies z = zombie[j];
        			if (z != null && colisionConProyectil(pr, z)) {
        				z.setVida(z.getVida() - pr.getDanio());
        				pr.setImpacto(true); 

        				if (z.getVida() <= 0) {
        					zombie[j] = null; 
        					zombiesDerrotados++;
        					if (zombiesDerrotados >= 50) {
        						juegoGanado = true;
        					}

        				}
        				break; 
        			}
        		}


        		if (pr.isImpacto() || pr.getX() > entorno.ancho()) {
        			proyectiles[i] = null;
        		}
        	}
        }

        
        int nuevoProyI = 0;
        for (int i = 0; i < proyI; i++) {
            if (proyectiles[i] != null) {
                proyectiles[nuevoProyI] = proyectiles[i];
                nuevoProyI++;
            }
        }
        proyI = nuevoProyI;

        contadorTicksProyectil++;
        if (contadorTicksProyectil >= 60) {
            for (int i = 0; i < roseI; i++) {
                Planta p = RoseBlade[i];
                if (p != null && p.colocada) {
                    if (detectarZ(p)) {
                        if (proyI < proyectiles.length) {
                            proyectiles[proyI] = new Proyectiles(6.0, 1, false, p.x + 25, p.y);
                            proyI++;
                        }
                    }
                }
            }
            contadorTicksProyectil = 0; 
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
        
        for (int i = 0; i < zomI; i++) {
            if (zombie[i] != null && zombie[i].x < 0) {  
                juegoTerminado = true;
                break;  
            }
        }
        entorno.cambiarFont("Arial", 20, Color.WHITE);
        entorno.escribirTexto("Zombies derrotados: " + zombiesDerrotados, entorno.ancho() - 250, 55);
        if (juegoTerminado) {
        	
        	entorno.dibujarRectangulo(entorno.ancho() / 2 , entorno.alto() / 2, 1016, 610, 0, Color.BLACK);
            entorno.cambiarFont("Arial", 30, Color.LIGHT_GRAY);  
            entorno.escribirTexto(mensajeGameOver, entorno.ancho() / 2 - 300 , entorno.alto() / 2);
            
            if (entorno.sePresiono(entorno.TECLA_ENTER)) {  
                reiniciarJuego();  
            }
            return; 
        }
        if (juegoGanado) {
            entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, 1016, 610, 0, Color.BLACK);
            entorno.cambiarFont("Arial", 30, Color.LIGHT_GRAY);
            entorno.escribirTexto(mensajeWIN, entorno.ancho() / 2 - 450, entorno.alto() / 2);
            if (entorno.sePresiono(entorno.TECLA_ENTER)) {
                reiniciarJuego();
            }
            return;
        }
    }
    
    // REICNICIAMOS CONTADORES Y ARREGLOS PARA VOLVER A JUGAR
    private void reiniciarJuego() {
        
        juegoTerminado = false;
        contadorTicks = 0;
        contadorTicksZ = 0;
        roseI = 0;
        tungI = 0;
        zomI = 0;
        proyI = 0;
        zombiesDerrotados = 0;
        juegoGanado = false;
        
        for (int i = 0; i < proyectiles.length; i++) {
            proyectiles[i] = null;
        }
        for (int i = 0; i < RoseBlade.length; i++) {
            RoseBlade[i] = null;
        }
        for (int i = 0; i < Tung.length; i++) {
            Tung[i] = null;
        }
        for (int i = 0; i < zombie.length; i++) {
            zombie[i] = null;
        }
        
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j].liberar();
            }
        }
        
     
        inicializarRegalosPorFila();
        
       
        System.out.println("Juego reiniciado");
    }

    // DETECTA SI EL MOUSE ESTA SOBRE LA PLANTA O EL TANQUE PARA SER SELECCIONADA
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

    // METODOS DE ARRASTRE DE PLANTA Y TANQUE PARA PODER COLOCARLAR EN UNA CASILLA
    private void arrastrar(Planta rosa) {
        rosa.x = entorno.mouseX();
        rosa.y = entorno.mouseY();
    }

    private void arrastrarT(Tanque tung) {
        tung.x = entorno.mouseX();
        tung.y = entorno.mouseY();
    }

    // COMPARADORES PARA HACER SELECCION UNICA
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

    // DIBUJAMOS EL TABLERO
    private void dibujarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j].dibujar(entorno);
            }
        }
    }

    // CENTRA LA PLANTA Y EL TANQUE EN UN CASILLERO SELECCIONADO
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
