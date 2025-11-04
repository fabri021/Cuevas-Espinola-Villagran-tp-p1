package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Proyectiles {
	private double x, y;
	private double velocidad;
	private double danio;
	private double bordeIzq, bordeDer, bordeSup,bordeInf;
	private boolean impacto;
	double escala;
	Image prImg;
	
	public Proyectiles (double velocidad, double danio, boolean impacto, double x, double y) {
		this.escala = 0.5;
		this.x = x;
		this.y = y;
		this.velocidad = 7.0;
		this.impacto = false;
		this.danio = 1;
		prImg = Herramientas.cargarImagen("bolaFuego.png");
	}
	
	public void dibujar (Entorno entorno) {
		entorno.dibujarImagen(prImg, this.x, this.y, 0, this.escala);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public double getDanio() {
		return danio;
	}

	public void setDanio(double danio) {
		this.danio = danio;
	}

	public double getBordeIzq() {
	     this.bordeIzq = x - (prImg.getWidth(null) * this.escala / 2);
	     return bordeIzq;
	 }
	 
	 public void setBordeIzq(double bordeIzq) {
	  this.bordeIzq = bordeIzq;
	 }

	 public double getBordeDer() {
	     this.bordeDer = x + (prImg.getWidth(null) * this.escala / 2);
	     return bordeDer;
	 }

	 public void setBordeDer(double bordeDer) {
	  this.bordeDer = bordeDer;
	 }

	 public double getBordeSup() {
	     this.bordeSup = y - (prImg.getHeight(null) * this.escala / 2);
	     return bordeSup;
	 }

	 public void setBordeSup(double bordeSup) {
	  this.bordeSup = bordeSup;
	 }

	 public double getBordeInf() {
	     this.bordeInf = y + (prImg.getHeight(null) * this.escala / 2);
	     return bordeInf;
	 }

	 public void setBordeInf(double bordeInf) {
	  this.bordeInf = bordeInf;
	 }

	public boolean isImpacto() {
		return impacto;
	}

	public void setImpacto(boolean impacto) {
		this.impacto = impacto;
	}
	
}
