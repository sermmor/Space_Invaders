/**
 * 
 */
package controlador;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

/**
 * Interfaz para los estados por los que pasa el juego. 
 * No implementa ActionListener aunque tenga un actionPerformed, pues el actionPerformed de esta
 * clase debe ser un método que deba ser llamado por el mismo actionListener que integrará
 * la aplicación.
 * @author sergio
 */
public interface IEstadoJuego {
	/** Ancho y alto del tablero. */
	public static final int WIDTH_TABLERO = 600;
	public static final int HEIGHT_TABLERO = 400;
	
	/**
	 * Indica si la pantalla ha acabado o no.
	 * @return true si la pantalla ha acabado, false si no.
	 */
	public boolean haAcabado();
	
	/**
	 * Paint pantalla
	 * @param g2d Gráfico donde pintar.
	 */
	public void paintPantalla(Graphics2D g2d, JPanel p);
	/**
	 * Realiza las acciones que se deben de realizar cada cinco milisegundos en 
	 * la presente pantalla (se excluye en este caso el repaint()).
	 * @param e Evento de la acción.
	 */
	public void actionPerformed(ActionEvent e);
	/**
	 * Acciones que se deben de realizar al pulsar una tecla.
	 * @param e Evento tecla.
	 */
	public void keyPressed(KeyEvent e);
	/**
	 * Acciones que se deben de realizar al soltar una tecla.
	 * @param e Evento tecla.
	 */
	public void keyReleased(KeyEvent e);	
}
