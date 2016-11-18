package modelo;


import java.awt.event.KeyEvent;
import java.util.ArrayList;


/**
 * Modelo para la nave.
 * 
 * @author sergio
 */
public abstract class ANave extends AObjetoEnMovimiento {
	
	protected int dx;
	
	/** Guarda los misiles que disparemos. */
	protected ArrayList<DisparoNave> misilesActivo;
	
	
	public ANave(String sImagePath) {
		super(sImagePath);
	}
	
	/* (non-Javadoc)
	 * @see modelo.AObjetoEnMovimiento#move()
	 */
	@Override
	public void move() {
		// Controlar que no nos pasemos por la izquierda, ni por la derecha.
		int fin = WIDTH_TABLERO - width;
		if (((x + dx) < fin || (x > fin && dx < 0)) 
				&& ((x + dx)>= 0 || (x < 0 && dx > 0))) {
			x += dx;
		}
	}
	
	/**
	 * Añade una lista de misiles.
	 * @param misiles lista de misiles.
	 */
	public void setMisilesActivos(ArrayList<DisparoNave> misiles) {
		misilesActivo.addAll(misiles);
	}
	
	/**
	 * @return the misilesActivos
	 */
	public abstract ArrayList<DisparoNave> getMisilesActivos();

	/**
	 * Realiza el disparo de un misil.
	 */
	public abstract void disparar();
	
	/**
	 * Se presiona la tecla: arriba, abajo, izquierda o derecha.
	 * @param e Evento de tecla.
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		/* Al hacer las cosas en ifs separados tanto en este método como en el siguiente, evito problemas
		 * al pulsar dos teclas a la vez. */
		if (key == KeyEvent.VK_SPACE) {
			disparar(); // Cuando se pulsa espacio, se dispara.
		}
		
		if (key == KEY_LEFT) {
			dx = -1; // Hacia la izquierda => desplazamiento negativo.
		}
		
		if (key == KEY_RIGHT) {
			dx = 1; // Hacia la derecha => desplazamiento positivo.
		}
	}
	
	/**
	 * Se suelta la tecla: arriba, abajo, izquierda o derecha.
	 * @param e Evento de tecla.
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		// Quito el desplazamiento en cualquier caso.
		
		/* Al hacer las cosas en ifs separados tanto en este método como en el anterior, evito problemas
		 * al pulsar dos teclas a la vez. */
		if (key == KEY_LEFT) {
			dx = 0;
		}
		
		if (key == KEY_RIGHT) {
			dx = 0;
		}
	}
	
}