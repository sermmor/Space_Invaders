/**
 * 
 */
package modelo;

import vista.Constants;

/**
 * @author sergio
 *
 */
public class UFO extends AUFO {
	private static final String PATH_UFO = Constants.PATH_UFO;
	
	public UFO(int x, int y) {
		super(PATH_UFO);
		this.x = x;
		this.y = y;
	}
	
	/* (non-Javadoc)
	 * @see modelo.AUFO#getPuntuacion()
	 */
	@Override
	public int getPuntuacion() {
		return 300;
	}

}
