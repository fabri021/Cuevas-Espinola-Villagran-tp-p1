package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Regalos {

	double x, y;
	private int vida;
	private double ancho, largo;
	private double bordeIzq, bordeDer, bordeSup,bordeInf;
	double escala;
	Image gift;
	
	public Regalos (double x, double y, double ancho, double largo) {
		this.largo = largo;
		this.ancho = ancho;
		this.x=x;
		this.y=y;
		this.escala = 1;
		this.setVida(1);
		gift = Herramientas.cargarImagen("gift.png");
		
	}
	
	

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getLargo() {
		return largo;
	}

	public void setLargo(double largo) {
		this.largo = largo;
	}



	public int getVida() {
		return vida;
	}



	public void setVida(int vida) {
		this.vida = vida;
	}
	
	public double getBordeIzq() {
	     this.bordeIzq = x - (gift.getWidth(null) * this.escala / 2);
	     return bordeIzq;
	 }

	 public void setBordeIzq(double bordeIzq) {
	  this.bordeIzq = bordeIzq;
	 }



	 public double getBordeDer() {
	     this.bordeDer = x + (gift.getWidth(null) * this.escala / 2);
	     return bordeDer;
	 }



	 public void setBordeDer(double bordeDer) {
	  this.bordeDer = bordeDer;
	 }



	 public double getBordeSup() {
	     this.bordeSup = y - (gift.getHeight(null) * this.escala / 2);
	     return bordeSup;
	 }



	 public void setBordeSup(double bordeSup) {
	  this.bordeSup = bordeSup;
	 }



	 public double getBordeInf() {
	     this.bordeInf = y + (gift.getHeight(null) * this.escala / 2);
	     return bordeInf;
	 }



	 public void setBordeInf(double bordeInf) {
	  this.bordeInf = bordeInf;
	 }






	 public void dibujar(Entorno entorno) {
	  entorno.dibujarImagen(gift, this.x, this.y, 0, this.escala);
	 }



	 public double getX() {
	  // TODO Auto-generated method stub
	  return x;
	 }



	 public double getY() {
	  // TODO Auto-generated method stub
	  return y;
	 }



	
}
