/**
 * 
 */
package modelo;

/**
 * @author sergio
 *
 */
public abstract class ADisparo extends AObjetoEnMovimiento {
	protected static final int VEL_MISIL = 2;
	
	public ADisparo(String sImagePath, int x, int y) {
		super(sImagePath);
		
		this.x = x;
		this.y = y;
	}

}
