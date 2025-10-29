package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Regalos {

	double x, y;
	private int vida;
	private double ancho, largo;
	double escala;
	Image gift;
	
	public Regalos (double x, double y, double ancho, double largo) {
		this.largo = largo;
		this.ancho = ancho;
		this.x=x;
		this.y=y;
		this.escala = 0.1;
		this.setVida(1);
		gift = Herramientas.cargarImagen("gift.jpg");
		
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



	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(gift, this.x, this.y, 0, this.escala);
	}



	
}
