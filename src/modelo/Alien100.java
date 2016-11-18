/**
 * 
 */
package modelo;

import vista.Constants;

/**
 * @author sergio
 *
 */
public class Alien100 extends AAlien {
	private static final String PATH_ALIEN100 = Constants.PATH_ALIEN100;
	
	public Alien100(int x, int y) {
		super(PATH_ALIEN100, x, y);
	}
	
	
	/* (non-Javadoc)
	 * @see modelo.AAlien#getPuntuacion()
	 */
	@Override
	public int getPuntuacion() {
		return 100;
	}

}
