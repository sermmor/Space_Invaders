/**
 * 
 */
package modelo;

/**
 * @author sergio
 *
 */
public abstract class AUFO extends AObjetoEnMovimiento {

	public AUFO(String sImagePath) {
		super(sImagePath);
	}
	
	/* (non-Javadoc)
	 * @see modelo.AObjetoEnMovimiento#move()
	 */
	@Override
	public void move() {
		if (x > WIDTH_TABLERO) {
			// Poner como no visible.
			setVisible(false);
		} else {
			// Seguir moviendo hasta colisión o puesta como no visible.
			x++;
		}
	}
	
	/**
	 * Devuelve la puntuación a acabar con el alien.
	 */
	public abstract int getPuntuacion();

}
