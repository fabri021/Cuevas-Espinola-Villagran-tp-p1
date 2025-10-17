package juego;

import entorno.Entorno;

import java.awt.Image;

import entorno.Herramientas;

public class Planta {
	double x, y, escala;
	private double bordeIzq, bordeDer, bordeSup,bordeInf;
	Image imgP;
	private int vida;
	private int danio;
	boolean seleccionada;
	private int recall;
	
	public Planta(double y, double x) {
		this.x = x;
		this.y = y;
		this.escala = 0.15;
		imgP = Herramientas.cargarImagen("pd.png");
		this.danio = 1;
		this.recall = 24;
		this.seleccionada = false;
		this.vida = 1;
	}
	
	public void dibujar (Entorno entorno) {
		entorno.dibujarImagen(imgP, this.x, this.y, 0, this.escala);
	}

	public double getBordeIzq() {
	    this.bordeIzq = x - imgP.getWidth(null)/2 * this.escala;
	    return bordeIzq;
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

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getDanio() {
		return danio;
	}

	public void setDanio(int danio) {
		this.danio = danio;
	}

	public boolean isSeleccionada() {
		return seleccionada;
	}

	public void setSeleccionada(boolean seleccionada) {
		this.seleccionada = seleccionada;
	}

	public int getRecall() {
		return recall;
	}

	public void setRecall(int recall) {
		this.recall = recall;
	}
}
