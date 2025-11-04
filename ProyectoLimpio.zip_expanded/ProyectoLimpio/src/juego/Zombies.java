package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Zombies {
	
	boolean detenido;
	private double vida;
	private int danio;
	double x , y , escala;
	double velocidad;
	private double bordeIzq, bordeDer, bordeSup,bordeInf;
	Image imgz;
	
	public Zombies ( double x, double y) {
		this.detenido = false;
		this.velocidad = 3.5;
		this.x = x;
		this.y = y; 
		this.danio = 2;
		this.vida  = 4;
		this.escala = 0.1;
		imgz = Herramientas.cargarImagen("zombies1.jpg");
	}
	
	
	public boolean estaDetenido() {
		return false;
	}
	public void setDetenido(boolean detenido) {
		this.detenido = detenido;
	}



	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public void dibujar (Entorno entorno) {
		entorno.dibujarImagen(imgz, this.x, this.y, 0, this.escala);
	}


	public double getVida() {
		return vida;
	}

	public void setVida(double vida) {
		this.vida = vida;
	}

	public int getDanio() {
		return danio;
	}

	public void setDanio(int danio) {
		this.danio = danio;
	}
	

	public double getBordeIzq() {
	     this.bordeIzq = x - (imgz.getWidth(null) * this.escala / 2);
	     return bordeIzq;
	 }
	 
	 public void setBordeIzq(double bordeIzq) {
	  this.bordeIzq = bordeIzq;
	 }

	 public double getBordeDer() {
	     this.bordeDer = x + (imgz.getWidth(null) * this.escala / 2);
	     return bordeDer;
	 }

	 public void setBordeDer(double bordeDer) {
	  this.bordeDer = bordeDer;
	 }

	 public double getBordeSup() {
	     this.bordeSup = y - (imgz.getHeight(null) * this.escala / 2);
	     return bordeSup;
	 }

	 public void setBordeSup(double bordeSup) {
	  this.bordeSup = bordeSup;
	 }

	 public double getBordeInf() {
	     this.bordeInf = y + (imgz.getHeight(null) * this.escala / 2);
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


	


	

	 

	
}
