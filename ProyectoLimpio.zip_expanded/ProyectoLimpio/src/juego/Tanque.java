package juego;


import entorno.Entorno;

import java.awt.Image;

import entorno.Herramientas;

public class Tanque {
	double x, y, escala;
	private double bordeIzq, bordeDer, bordeSup,bordeInf;
	Image imgP;
	private int vida;
	boolean seleccionada;
	private boolean enTablero = false;
	boolean colocada;

	


	
	
	public Tanque(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.12;
		imgP = Herramientas.cargarImagen("TanqueSigma.png");
		this.seleccionada = false;
		this.vida = 200;
		this.colocada = false;
	}
	
	public void dibujar (Entorno entorno) {
		entorno.dibujarImagen(imgP, this.x, this.y, 0, this.escala);
	}
	//creamos los bordes
	public double getBordeIzq() {
	    this.bordeIzq = x - imgP.getWidth(null)/2 * this.escala;
	    return bordeIzq;
	}

	public boolean estaEnTablero() { 
		return enTablero;
		}
	
	
	public void setEnTablero(boolean b) { 
		enTablero = b; 
		}
	
	public double getBordeDer() {
	    this.bordeDer = x + imgP.getWidth(null)/2 * this.escala;
	    return bordeDer;
	}

	public double getBordeSup() {
	    this.bordeSup = y - imgP.getHeight(null)/2 * this.escala;
	    return bordeSup;
	}

	public double getBordeInf() {
	    this.bordeInf = y + imgP.getHeight(null)/2 * this.escala;
	    return bordeInf;
	}
	//getters y setters
	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	
	//creamos el boolean para seleccionar el Tanque en Juego
	public boolean isSeleccionada() {
		return seleccionada;
	}

	public void setSeleccionada(boolean seleccionada) {
		this.seleccionada = seleccionada;
	}


}
