/**
 * 
 */
package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import modelo.AAlien;
import modelo.ANave;
import modelo.AUFO;
import modelo.DisparoAlien;
import modelo.DisparoNave;
import modelo.EjercitoAliens;
import modelo.Nave;
import modelo.NaveDisparoContinuo;
import modelo.NaveInvencible;
import modelo.UFO;

/**
 * Estado de juego, cuando mueren todos los aliens, vuelven a aparecer aliens al inicio
 * pero se mantienen vidas y puntuación. Esto es, el juego sólo acaba cuando no quedan
 * vidas.
 * @author sergio
 */
public class EstadoJugando extends AEstadoJuego {
	// Punto inicial para el ejercito de aliens.
	private static final int[] INICIO_EJERCITO = {5, 50};
	
	// Dimensiones del tablero
	private static final int WIDTH_TABLERO = 600;
	private static final int HEIGHT_TABLERO = 400;
	
	/** Tiempo de decisión de disparo del ejercito alien. */
	private static final int TIME_DISPARO_ALIEN = 100; //
	private static final int TIME_INVENCIBLE = 1000;//200 == 1 segundo => 1000 == 5 segundos
	private static final int TIME_DISPARO_CONTINUO = 2000;//200 == 1 segundo => 2000 == 10 segundos
	private static final int TIME_UFO = 10000; // Cada 10 segundos se mira si aparición o no.
	/** Número vidas al inicio. */
	private static final int NUM_VIDAS = 3;
	
	/** Variable para el tiempo del disparo alien, al llegar a cero decidirá si disparar o no. */
	private int contadorDisparo = TIME_DISPARO_ALIEN;
	
	/** Contador que mide la decisión de aparición o no del UFO. */
	private int contadorUFO = TIME_UFO;
	
	/** Puntos obtenidos en el juego. */
	private int puntos;
	private int oldPuntos;
	private int contadorInvencible = 0;
	private int contadorDisparoContinuo = 0;
	/** Vidas que quedan. */
	private int vidas;
	
	/** Contadores de puntos para bonus. */
	private int contadorPuntosVida = 2000;
	private int contadorPuntosInvencible = 500;
	private int contadorPuntosDContinuo = 1000;
	
	/** Nave aliada */
	private ANave nave;
	/** UFO */
	private AUFO ufo;
	/** Ejercito de aliens */
	private EjercitoAliens ejercitoA;
	
	public EstadoJugando() {
		super();
		this.puntos = 0;
		this.vidas = NUM_VIDAS;
		nave = new Nave(WIDTH_TABLERO/2, HEIGHT_TABLERO - 82);
		ufo = null; // El elemento ufo es aleatorio.
		ejercitoA = new EjercitoAliens(INICIO_EJERCITO[0], INICIO_EJERCITO[1]);
	}
	
	/**
	 * Devuelve la puntuación.
	 * @return puntos.
	 */
	public int getPuntos() {
		return puntos;
	}
	
	/**
	 * Comprueba si un disparo de alien pasa o no por encima de un alien.
	 * @param d
	 * @param allAliens
	 * @return true si pasa por encima del alien, false si no.
	 */
	private boolean hayColisionAlienDisparoAlien(DisparoAlien d, ArrayList<AAlien> allAliens) {
		boolean ret = false;
		Rectangle r1 = d.getBounds();
		int i = 0;
		while (!ret && (i < allAliens.size())) {
			ret = allAliens.get(i).getBounds().intersects(r1);
			i++;
		}
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#paintPantalla(java.awt.Graphics2D)
	 */
	@Override
	public void paintPantalla(Graphics2D g2d, JPanel p) {
		if (!this.haAcabado()) {
			// Coloco la nave donde debe (la información estará actualizada por el repaint del actionPerformed).
			g2d.drawImage(nave.getImage(), nave.getX(), nave.getY(), p);
			
			if (ufo != null) {
				// Si hay un ufo, pintarlo.
				g2d.drawImage(ufo.getImage(), ufo.getX(), ufo.getY(), p);
			}
			
			ArrayList<AAlien> allAliens = ejercitoA.getAllAliens();
			if (!allAliens.isEmpty()) {
				for (AAlien a : allAliens) {
					// Pintar aliens.
					g2d.drawImage(a.getImage(), a.getX(), a.getY(), p);
				}
			}
			// Pintar disparos nave aliada y disparos aliens.
			// Mover misiles nave.
			if (nave.getMisilesActivos() != null && !nave.getMisilesActivos().isEmpty()) {
				for (DisparoNave n : nave.getMisilesActivos()) {
					if (n.esVisible()) {
						g2d.drawImage(n.getImage(), n.getX(), n.getY(), p);
					}
				}
			}
			if (ejercitoA.getMisilesActivos() != null && !ejercitoA.getMisilesActivos().isEmpty()) {
				// Mover misiles alien.
				for (DisparoAlien d : ejercitoA.getMisilesActivos()) {
					if (d.esVisible() && !hayColisionAlienDisparoAlien(d, allAliens)) {
						g2d.drawImage(d.getImage(), d.getX(), d.getY(), p);
					}
				}
			}
			// Pintar indicadores.
			Font fTitulo = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 12);
			int yText = 20;
			if (nave instanceof NaveInvencible) {
				// Escribir escudo protector activo.
				writeTextAligIzq(g2d, 0, yText, Color.white, fTitulo, new String[]{"Escudo protector"});
			}
			if (nave instanceof NaveDisparoContinuo) {
				if (((NaveDisparoContinuo) nave).isEscudo()) {
					// Escribir escudo protector activo.
					writeTextAligIzq(g2d, 0, yText, Color.white, fTitulo, new String[]{"Escudo protector"});
				}
				// Escribir disparo continuo activo.
				writeTextAligIzq(g2d, 125, yText, Color.white, fTitulo, new String[]{"Continuo"});
			}
			// Escribir contador puntos.
			writeTextAligIzq(g2d, 400, yText, Color.white, fTitulo, new String[]{"Puntos: " + puntos});
			// Escribir contador vídas.
			writeTextAligIzq(g2d, 525, yText, Color.white, fTitulo, new String[]{"Vidas: " + vidas});
		}
	}

	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!this.haAcabado()) {
			this.checkCollisions();
		}
		
		if (!this.haAcabado()) {
			// - Mover todo
			// Mover ejercito.
			ejercitoA.move();
			// Mover ufo.
			if (ufo != null) {
				ufo.move();
				if (!ufo.esVisible()) {
					ufo = null;
				}
			}
			nave.move();
			// Mover misiles nave.
			ArrayList<DisparoNave> misilesANave = nave.getMisilesActivos();
			for (int i = 0; i < misilesANave.size(); i++) {
				DisparoNave n = misilesANave.get(i);
				if (n.esVisible()) {
					n.move();
				} else {
					// Si no es visible, eliminar.
					misilesANave.remove(i);
				}
			}
			// Mover misiles alien.
			ArrayList<DisparoAlien> misilesAAlien = ejercitoA.getMisilesActivos();
			for (int i = 0; i < misilesAAlien.size(); i++) {
				DisparoAlien d = misilesAAlien.get(i);
				if (d.esVisible()) {
					d.move();
				} else {
					// Si no es visible, eliminar.
					misilesAAlien.remove(i);
				}
			}
			
			// - Ver si se han acabado los bonus.
			if (contadorInvencible > 0) {
				contadorInvencible--;
				if (contadorInvencible == 0 && contadorDisparoContinuo == 0) {
					// Desactivo bonus.
					ANave naveAux = new Nave(nave.getX(), nave.getY());
					naveAux.setMisilesActivos(nave.getMisilesActivos());
					nave = naveAux;
				} else if (contadorInvencible == 0) {
					// Desactivar escudo protector en disparo continuo.
					if (nave instanceof NaveDisparoContinuo) {
						((NaveDisparoContinuo) nave).setEscudo(false);
					}
				}
			}
			if (contadorDisparoContinuo > 0) {
				contadorDisparoContinuo--;
				if (contadorDisparoContinuo == 0) {
					// Desactivo bonus.
					ANave naveAux = new Nave(nave.getX(), nave.getY());
					naveAux.setMisilesActivos(nave.getMisilesActivos());
					nave = naveAux;
				}
			}
			
			// - Mirar si hay bonus y sumar vidas nuevas.
			contadorPuntosVida -= oldPuntos;
			contadorPuntosInvencible -= oldPuntos;
			contadorPuntosDContinuo -= oldPuntos;
			
			if (contadorPuntosInvencible <= 0) {
				// Sabemos que estamos en el caso bonus invencible o en el de disparo continuo.
				if (contadorPuntosDContinuo <= 0) {
					// Disparo continuo y escudo protector.
					contadorDisparoContinuo = TIME_DISPARO_CONTINUO;
					contadorInvencible = TIME_INVENCIBLE;
					ANave naveAux = new NaveDisparoContinuo(nave.getX(), nave.getY());
					naveAux.setMisilesActivos(nave.getMisilesActivos());
					nave = naveAux;
					// Resetear contadores.
					contadorPuntosInvencible = 500;
					contadorPuntosDContinuo = 1000;
				} else {
					// Escudo protector.
					contadorInvencible = TIME_INVENCIBLE;
					ANave naveAux = new NaveInvencible(nave.getX(), nave.getY());
					naveAux.setMisilesActivos(nave.getMisilesActivos());
					nave = naveAux;
					// Resetear contadores.
					contadorPuntosInvencible = 500;
				}
			}
			
			if (contadorPuntosVida <= 0) {
				// Vida extra al llegar a 2000 puntos.
				vidas++;
				// Resetear contadores.
				contadorPuntosVida = 2000;
			}
			// Actualizo puntuación anterior a la actual.
			oldPuntos = 0;
			
			// Mirar si el enemigo ha llegado al final (game over).
			if (ejercitoA.llegadoFinalPantalla(nave.getY())) { 
				if (vidas == 0) {
					// Si no quedan vidas, el juego ha acabado.
					isActiva = false;
				} else {
					// Se pierde una vida y aparece un nuevo ejercito de aliens arriba, 
					ejercitoA = new EjercitoAliens(INICIO_EJERCITO[0], INICIO_EJERCITO[1]);
					if (!(nave instanceof NaveInvencible) 
						&& !((nave instanceof NaveDisparoContinuo) 
							&& ((NaveDisparoContinuo) nave).isEscudo())) {
						vidas--;
					}
				}
			}
			
			// Crear nuevo ejercito, si ejercito destruido.
			if (ejercitoA.isEjercitoDestruido()) {
				ejercitoA = new EjercitoAliens(INICIO_EJERCITO[0], INICIO_EJERCITO[1]);
			}
			
			// - ¿Dispara ejercito alien a nave?
			contadorDisparo--;
			if (contadorDisparo == 0) {
				// Decido si disparo o no.
				if (Math.random() > 0.5) {
					// Disparo.
					ejercitoA.disparar();
				}
				// Reinicio contador.
				contadorDisparo = TIME_DISPARO_ALIEN;
			}
			
			// - ¿Aparición aleatoria de UFO?
			if (ufo == null) {
				contadorUFO--;
				if (contadorUFO == 0) {
					// Decido si aparece o no.
					if (Math.random() > 0.5) {
						// Aparece el UFO.
						ufo = new UFO(5, 30);
					}
					contadorUFO = TIME_UFO;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Si se presiona una tecla, la analiza la nave.
		nave.keyPressed(e);
	}

	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// Si se suelta una tecla, la analiza la nave.
		nave.keyReleased(e);
	}
	
	/** Chequea el tema de las colisiones nave-disparoAlien, disparoNave-alien. */
	public void checkCollisions() {
		// Comprobar colisiones nave-disparoAlien.
		Rectangle r3 = nave.getBounds();
		ArrayList<DisparoAlien> misilesAlien = ejercitoA.getMisilesActivos();
		for (DisparoAlien a : misilesAlien) {
			if (a.esVisible()) {
				Rectangle r2 = a.getBounds();
				
				if (r3.intersects(r2)) {
					// Colisión de nave con un alien. => GAME OVER.
					nave.setVisible(false);
					a.setVisible(false);
					// Decrementar vidas.
					if (!(nave instanceof NaveInvencible) 
							&& !((nave instanceof NaveDisparoContinuo) 
								&& ((NaveDisparoContinuo) nave).isEscudo())) {
						vidas--;
					}
					if (vidas == 0) {
						// Si no quedan vidas, el juego ha acabado.
						isActiva = false;
					}
				}
			}
		}
		
		// Comprobar Comprobar colisiones disparoNave-alien, y sumar puntos.
		oldPuntos = ejercitoA.checkColisiones(nave.getMisilesActivos());
		puntos = puntos + oldPuntos;
		
		// Colisiones con el ufo.
		if (ufo != null) {
			Rectangle r4 = ufo.getBounds();
			for (DisparoNave d : nave.getMisilesActivos()) {
				if (d.esVisible()) {
					Rectangle r1 = d.getBounds();
					if (r1.intersects(r4)) {
						ufo.setVisible(false);
						oldPuntos += ufo.getPuntuacion();
						puntos = puntos + oldPuntos;
					}
				}
			}
				
		}
	}

}
