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
	private int recall;
	
	public Tanque(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.08;
		imgP = Herramientas.cargarImagen("TanqueSigma.jpeg");
		this.recall = 24;
		this.seleccionada = false;
		this.vida = 10;
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
