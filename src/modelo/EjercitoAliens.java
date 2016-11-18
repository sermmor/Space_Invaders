/**
 * 
 */
package modelo;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase para el manejo del ejercito de aliens.
 * @author sergio
 */
public class EjercitoAliens {
	private final static int NUM_FILAS = 5;
	private final static int NUM_COLUMNAS = 11;
	private final static int NUM_TIPOS_ALIENS = 3;
	private final static int ANCHO_ALIEN = 48;
	private final static int ALTO_ALIEN = 30;
	
	/** Ejercito */
	private AAlien[][] ejercito;
	/** Lista de disparos del ejercito alien. */
	private ArrayList<DisparoAlien> disparos;
	
	private int totalAliens = 0;
	
	public EjercitoAliens(int x, int y) {
		// Creo e inicializo el ejercito de aliens;
		ejercito = new AAlien[NUM_FILAS][NUM_COLUMNAS];
		int[] punto = new int[]{x, y};
		for (int i = 0; i < NUM_FILAS; i++) {
			crearFilaAlienAlAzar(ejercito[i], punto);
			punto[0] = x;
			punto[1] += ALTO_ALIEN;
		}
		// Creo lista de disparos.
		disparos = new ArrayList<DisparoAlien>();
	}
	
	public boolean isEjercitoDestruido() {
		return (totalAliens == 0);
	}
	
	/** Indica si el ejercito de aliens ha llegado a donde está la nave defensora. */
	public boolean llegadoFinalPantalla(int alto) {
		// Compruebo si hay un elemento de la última fila que esté en el final de la pantalla.
		boolean isFinal = false;
		int[] indexA = primerElementoUltimaFila();
		if (indexA != null) {
			AAlien a = ejercito[indexA[0]][indexA[1]];
			isFinal = (a.getY() + a.getBounds().getHeight() > alto);
		}
		return isFinal;
	}
	
	public ArrayList<DisparoAlien> getMisilesActivos() {
		return disparos;
	}
	
	/** Un alien de la primera fila, elegido al azar, dispara a la nave aliada. */
	public void disparar() {
		Random r = new Random();
		int iCol = r.nextInt(NUM_COLUMNAS);
		AAlien a = ejercito[0][iCol];
		int iter = iCol;
		while (a == null && iter < NUM_FILAS) {
			iter++;
			a = ejercito[0][iter];
		}
		if (a == null) {
			iter = iCol;
			while (a == null && iter > 0) {
				iter--;
				a = ejercito[0][iter];
			}
		}
		if (a != null) {
			// Disparar.
			disparos.add(new DisparoAlien(a.getX(), a.getY()));
		}
	}
	
	/**
	 * Comprueba las colisiones con una lista de misiles, devuelve el total de puntos conseguidos.
	 * @param misiles lista de misiles de la nave.
	 * @return número puntos totales.
	 */
	public int checkColisiones(ArrayList<DisparoNave> misiles) {
		int puntos = 0;
		if ((misiles != null) && (!misiles.isEmpty())) {
			for (DisparoNave m : misiles) {
				Rectangle r1 = m.getBounds();
				if (m.esVisible()) {
					for (int i = 0; i < NUM_FILAS; i++) {
						for (int j = 0; j < NUM_COLUMNAS; j++) {
							if (m.esVisible()) { // De una iteración a otra puede cambiar el valor de esVisible del misil.
								AAlien a = ejercito[i][j];
								if (a != null) {
									// Si hay alien, mirar intersección.
									Rectangle r2 = a.getBounds();
									if (r1.intersects(r2)) {
										// Alien muerto, misil destruido.
										m.setVisible(false);
										a.setVisible(false);
										// Sumamos puntuación.
										puntos = puntos + a.getPuntuacion();
										// Decrementar número de aliens.
										totalAliens--;
									}
								}
							}
						}
					}
				}
			}
		}
		return puntos;
	}
	
	/**
	 * Mover ejercito de aliens.
	 */
	public void move() {
		for (int i = 0; i < NUM_FILAS; i++) {
			for (int j = 0; j < NUM_COLUMNAS; j++) {
				AAlien a = ejercito[i][j];
				if (a != null) {
					if (!a.esVisible()) {
						// Eliminar alien muerto.
						ejercito[i][j] = null;
						// Decrementar retardo de los aliens.
						decrementDelay();
					} else {
						// Mover alien.
						a.move();
					}
				}
			}
		}
	}
	
	/**
	 * Devuelve una lista con todos los aliens.
	 * @return Lista con todos los aliens, si no quedan aliens la lista estará vacía.
	 */
	public ArrayList<AAlien> getAllAliens() {
		ArrayList<AAlien> aliens = new ArrayList<AAlien>();
		if (totalAliens != 0) {
			for (int i = 0; i < NUM_FILAS; i++) {
				for (int j = 0; j < NUM_COLUMNAS; j++) {
					AAlien a = ejercito[i][j];
					if (a != null) {
						aliens.add(a);
					}
				}
			}
		}
		return aliens;
	}
	/* ******************************************************************************************************
	 * Métodos de uso privado.*******************************************************************************
	 * ******************************************************************************************************/
	
	private void crearFilaAlienAlAzar(AAlien[] fila, int[] punto) {
		// Elijo al azar un número entre 0 y el número de tipos de aliens.
		Random r = new Random();
		int iTipo = r.nextInt(NUM_TIPOS_ALIENS);
		for (int i = 0; i < NUM_COLUMNAS; i++) {
			switch (iTipo) {
			case 0:
				fila[i] = new Alien50(punto[0], punto[1]);
				totalAliens++;
				break;
			case 1:
				fila[i] = new Alien100(punto[0], punto[1]);
				totalAliens++;
				break;
			default:
				fila[i] = new Alien150(punto[0], punto[1]);
				totalAliens++;
				break;
			}
			punto[0] += ANCHO_ALIEN;
		}
	}
	
	/** Devuelve indice de la última fila, nulo si todo el ejercito está muerto. */
	private int ultimaFila() {
		int i = NUM_FILAS - 1;
		boolean encontrado = false;
		int ultimaFila = -1;
		while (!encontrado && (i > -1)) {
			int j = 0;
			while (!encontrado && (j < NUM_COLUMNAS)) {
				encontrado = (ejercito[i][j] != null);
				if (encontrado) {
					ultimaFila = i;
				}
				j++;
			}
			i--;
		}
		
		return ultimaFila;
	}
	
	/** Devuelve indice al primer elemento de la fila más cercana a la nave. */
	private int[] primerElementoUltimaFila() {
		int[] elemento = null;
		int indexLast = ultimaFila();
		if (indexLast > -1) {
			AAlien[] last = ejercito[indexLast];
			
			if (last != null) {
				
				boolean encontrado = false;
				int i = 0;
				
				while (!encontrado && (i < NUM_FILAS)) {
					encontrado = (last[i] != null);
					if (encontrado) {
						elemento = new int[]{indexLast, i};
					}
					i++;
				}
			}
		}
		return elemento;
	}
	
	/** Decrementar retardo para todo alien. */
	private void decrementDelay() {
		for (int i = 0; i < NUM_FILAS; i++) {
			for (int j = 0; j < NUM_COLUMNAS; j++) {
				AAlien a = ejercito[i][j];
				if (a != null) {
					a.decrementDelay();
				}
			}
		}
	}
	

}
