/**
 * 
 */
package modelo;

import vista.Constants;

/**
 * @author sergio
 *
 */
public class DisparoAlien extends ADisparo {

	private static final String PATH_MISIL = Constants.PATH_MISIL_ALIEN;
	
	public DisparoAlien(int x, int y) {
		super(PATH_MISIL, x, y);
	}
	
	/* (non-Javadoc)
	 * @see modelo.AObjetoEnMovimiento#move()
	 */
	@Override
	public void move() {
		// Actualizar posición.
		y += VEL_MISIL; // Se mueve hacia abajo.
		// Si se supera alto, ya deja de ser visible el misil.
		if (y > (HEIGHT_TABLERO - 10)) {
			visible = false;
		}
	}

}
