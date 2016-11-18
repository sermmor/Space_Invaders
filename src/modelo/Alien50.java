/**
 * 
 */
package modelo;

import vista.Constants;

/**
 * @author sergio
 *
 */
public class Alien50 extends AAlien {
	private static final String PATH_ALIEN50 = Constants.PATH_ALIEN50;
	
	public Alien50(int x, int y) {
		super(PATH_ALIEN50, x, y);
	}

	/* (non-Javadoc)
	 * @see modelo.AAlien#getPuntuacion()
	 */
	@Override
	public int getPuntuacion() {
		return 50;
	}

}
