package modelo;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import java.awt.event.KeyEvent;

/**
 * Implementa la funcionalidad cómun de todos los objetos en movimiento (misil, nave o alien).
 * @author sergio
 *
 */
public abstract class AObjetoEnMovimiento {
	// Teclas de control del juego.
	/** Tecla izquierda. */
	public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	/** Tecla derecha. */
	public static final int KEY_LEFT = KeyEvent.VK_LEFT;
	// Dimensiones del tablero
	protected static final int WIDTH_TABLERO = 600;
	protected static final int HEIGHT_TABLERO = 400;
	
	protected int x, y;
	protected int width = -1, height = -1;
	protected Image image = null;
	protected boolean visible; // Si es false, dará luz verde a eliminar el objeto en movimiento.
	
	public AObjetoEnMovimiento(String sImagePath) {
		// Creamos la imagen.
		//ImageIcon ii = new ImageIcon(this.getClass().getResource(sImagePath));
		ImageIcon ii = new ImageIcon(sImagePath);
		image = ii.getImage();
		visible = true; // Como acabamos de crear el misil, es visible.
		width = image.getWidth(null);
		height = image.getHeight(null);	
	}
	
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Devuelve un rectángulo con las dimensiones del objeto.
	 * @return rectángulo con las dimensiones del objeto.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * @return the visible
	 */
	public boolean esVisible() {
		return visible;
	}
	
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Implementa el movimiento del elemento en movimiento.
	 */
	public abstract void move();
}