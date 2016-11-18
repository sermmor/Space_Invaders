/**
 * 
 */
package modelo;

import java.util.ArrayList;

import vista.Constants;

/**
 * La nave con escudo protector, no tiene disparo continuo.
 * @author sergio
 */
public class NaveInvencible extends ANave {
	
	private static final String PATH_NAVE = Constants.PATH_NAVE_INV;
	
	public NaveInvencible(int x, int y) {
		super(PATH_NAVE);
		this.x = x;
		this.y = y;
		misilesActivo = new ArrayList<DisparoNave>();
	}

	/* (non-Javadoc)
	 * @see modelo.ANave#getMisilesActivos()
	 */
	@Override
	public ArrayList<DisparoNave> getMisilesActivos() {
		return misilesActivo;
	}

	/* (non-Javadoc)
	 * @see modelo.ANave#disparar()
	 */
	@Override
	public void disparar() {
		// Creo un misil si no existe o no es visible.
		if ((misilesActivo == null) || misilesActivo.isEmpty() 
				|| (!misilesActivo.isEmpty() && !misilesActivo.get(0).esVisible())) {
			// Vacío la lista de misiles, pues sólo debe haber uno.
			misilesActivo = new ArrayList<DisparoNave>();
			// Añado disparo.
			misilesActivo.add(new DisparoNave(x + (width/2), y));
		}
	}

	/* (non-Javadoc)
	 * @see modelo.AObjetoEnMovimiento#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		// No hace nada (al ser invencible, no setea setVisible).
	}

}
