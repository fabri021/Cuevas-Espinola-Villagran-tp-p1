package juego;

import entorno.Entorno;

import java.awt.Image;

import entorno.Herramientas;

public class Planta {
	double x, y, escala;
	private double bordeIzq, bordeDer, bordeSup,bordeInf;
	Image imgP;
	private int vida;
	boolean seleccionada;
	private boolean enTablero = false;
	boolean colocada;
	
	public Planta(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.15;
		imgP = Herramientas.cargarImagen("pd.png");
		this.seleccionada = false;
		this.vida = 1;
		this.colocada = false;
	}
	
	public void dibujar (Entorno entorno) {
		entorno.dibujarImagen(imgP, this.x, this.y, 0, this.escala);
	}
	
	
	public boolean estaEnTablero() { 
		return enTablero; 
		}
	public void setEnTablero(boolean b) { 
		enTablero = b; 
		}
	
	
	//Creamos los bordes
	public double getBordeIzq() {
	     this.bordeIzq = x - (imgP.getWidth(null) * this.escala / 2);
	     return bordeIzq;
	 }
	 
	 public void setBordeIzq(double bordeIzq) {
	  this.bordeIzq = bordeIzq;
	 }

	 public double getBordeDer() {
	     this.bordeDer = x + (imgP.getWidth(null) * this.escala / 2);
	     return bordeDer;
	 }

	 public void setBordeDer(double bordeDer) {
	  this.bordeDer = bordeDer;
	 }

	 public double getBordeSup() {
	     this.bordeSup = y - (imgP.getHeight(null) * this.escala / 2);
	     return bordeSup;
	 }

	 public void setBordeSup(double bordeSup) {
	  this.bordeSup = bordeSup;
	 }

	 public double getBordeInf() {
	     this.bordeInf = y + (imgP.getHeight(null) * this.escala / 2);
	     return bordeInf;
	 }

	 public void setBordeInf(double bordeInf) {
	  this.bordeInf = bordeInf;
	 }
	 public double getX() {
	     return x;  
	 }

	 public double getY() {
	     return y;  
	 }
	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public boolean isSeleccionada() {
		return seleccionada;
	}

	public void setSeleccionada(boolean seleccionada) {
		this.seleccionada = seleccionada;
	}


}
