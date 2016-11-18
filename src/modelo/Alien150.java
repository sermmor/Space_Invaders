/**
 * 
 */
package modelo;

import vista.Constants;

/**
 * @author sergio
 *
 */
public class Alien150 extends AAlien {
	private static final String PATH_ALIEN150 = Constants.PATH_ALIEN150;
	
	public Alien150(int x, int y) {
		super(PATH_ALIEN150, x, y);
	}
	
	/* (non-Javadoc)
	 * @see modelo.AAlien#getPuntuacion()
	 */
	@Override
	public int getPuntuacion() {
		// TODO Auto-generated method stub
		return 150;
	}

}
