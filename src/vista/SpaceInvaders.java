/**
 * 
 */
package vista;

import javax.swing.JFrame;

import controlador.Tablero;

/**
 * @author sergio
 *
 */
public class SpaceInvaders extends JFrame {
	// Dimensiones del tablero
	private static final int WIDTH_TABLERO = 600;
	private static final int HEIGHT_TABLERO = 400;
	/** Título del juego. */
	public static final String TITLE_GAME = "Space Invaders";
	
	
	/** Serial por defecto */
	private static final long serialVersionUID = 1L;

	public SpaceInvaders() {
		add(new Tablero());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH_TABLERO, HEIGHT_TABLERO);
		setLocationRelativeTo(null);
		setTitle(TITLE_GAME);
		setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SpaceInvaders();
	}

}
