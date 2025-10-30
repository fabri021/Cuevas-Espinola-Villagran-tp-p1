package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Zombies {
	
	private int vida;
	private int danio;
	double x , y , escala;
	
	
	private double bordeIzq, bordeDer, bordeSup,bordeInf;
	Image imgz;
	
	public Zombies ( double x, double y) {
		this.x = x;
		this.y = y; 
		this.danio = 2;
		this.vida  = 4;
		this.escala = 0.1;
		imgz = Herramientas.cargarImagen("zombies1.jpg");
	}
	
	public void dibujar (Entorno entorno) {
		entorno.dibujarImagen(imgz, this.x, this.y, 0, this.escala);
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
	

	public double getBordeIzq() {
		this.bordeIzq = x - imgz.getWidth(null)/2 * this.escala;
	    return bordeIzq;
	}

	public void setBordeIzq(double bordeIzq) {
		this.bordeIzq = bordeIzq;
	}

	public double getBordeDer() {
		 this.bordeDer = x - imgz.getWidth(null)/2 * this.escala;
		 return bordeDer;
	}

	public void setBordeDer(double bordeDer) {
		this.bordeDer = bordeDer;
	}

	public double getBordeSup() {
		 this.bordeSup = y - imgz.getWidth(null)/2 * this.escala;
		 return bordeSup;
	}

	public void setBordeSup(double bordeSup) {
		this.bordeSup = bordeSup;
	}

	public double getBordeInf() {
		this.bordeInf = y - imgz.getWidth(null)/2 * this.escala;
	    return bordeInf;
	}

	public void setBordeInf(double bordeInf) {
		this.bordeInf = bordeInf;
	}
	
}
