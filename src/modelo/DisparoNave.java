/**
 * 
 */
package modelo;

import vista.Constants;

/**
 * @author sergio
 *
 */
public class DisparoNave extends ADisparo {
	private static final String PATH_MISIL = Constants.PATH_MISIL_NAVE;
	
	public DisparoNave(int x, int y) {
		super(PATH_MISIL, x, y);
	}
	
	/* (non-Javadoc)
	 * @see modelo.AObjetoEnMovimiento#move()
	 */
	@Override
	public void move() {
		// Actualizar posición.
		y -= VEL_MISIL; // Se mueve hacia arriba.
		// Si se supera alto, ya deja de ser visible el misil.
		if (y <= 0) {
			visible = false;
		}
	}

}
