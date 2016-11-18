/**
 * 
 */
package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Contiene variables y métodos de uso común para todos los estados del juego.
 * @author sergio
 */
public abstract class AEstadoJuego implements IEstadoJuego {
	/** Margen de la y entre líneas (para varias líneas de letras). */
	public static final int TEXT_MARGIN_Y = 25;
	/** Tiempo aparición/desaparición. */
	public static final int TIME_PARPADEO = 100; // Cada medio segundo.
	
	/** Si está a cero, desparece o aparece la imagen. */
	private int nextParpadeo;
	private boolean isOculto; // Indica si están ocultos los elementos que parpadean.
	
	/** Indica si la pantalla está activa o podemos pasar a la pantalla siguiente. */
	protected boolean isActiva;
	
	public AEstadoJuego() {
		nextParpadeo = TIME_PARPADEO;
		isOculto = false;
		isActiva = true;
	}
	
	
	
	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#haAcabado()
	 */
	@Override
	public boolean haAcabado() {
		return !isActiva;
	}



	/**
	 * Realiza la función temporal de parpadear texto (sólo debe usarse en el método paint).
	 * @param g2d
	 */
	protected void parpadearTexto(Graphics2D g2d, int xWrite, int yWrite, 
			Color colorText, Font fontText, String[] text) {
		// Evaluo para hacer el parpadeo
		nextParpadeo--;
		if (nextParpadeo == 0) {
			// Ya estamos en el parpadeo.
			nextParpadeo = TIME_PARPADEO; // Inicializo de nuevo el contador del parpadeo.
			isOculto = !isOculto;
		}
		if (!isOculto) {
			// Si el siguiente texto no está en tiempo oculto.
			writeTextAligIzq(g2d, xWrite, yWrite, colorText, fontText, text);
		}
	}
	
	/** 
	 * Escribe un texto (alineación izquierda) en la pantalla.
	 * @param g2d Gráfico desde el que pintar.
	 * @param xWrite Posición x desde la que pintar el texto.
	 * @param yWrite Posición y desde la que pintar el texto.
	 * @param colorText Color del texto.
	 * @param fontText fuente del texto.
	 * @param text Lineas del texto (cada String es una nueva línea).
	 */
	protected static final void writeTextAligIzq(Graphics2D g2d, int xWrite, int yWrite, 
			Color colorText, Font fontText, String[] text) {
		// Guardamos los viejos valores
		Font oldFont = g2d.getFont();
		Color oldColor = g2d.getColor();
		
		// Preparamos los nuevos valores.
		g2d.setFont(fontText);
		g2d.setColor(colorText);
		
		// Pinto la cadena.
		int yWriteLine = yWrite;
		for (String str : text) {
			if ((str != null) && !("").equals(str)) {
				/* Si no es la cadena vacía ni nulo, crea la nueva cadena;
				 *  en otro caso, se hará el efecto de un salto de línea. */
				g2d.drawString(str, xWrite, yWriteLine);
			}
			// Preparar posición y para la siguiente línea.
			yWriteLine += TEXT_MARGIN_Y;
		}
		
		// Restablecer antiguos valores.
		g2d.setFont(oldFont);
		g2d.setColor(oldColor);
	}
	
	
}
