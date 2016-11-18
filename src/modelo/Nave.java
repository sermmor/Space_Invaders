/**
 * 
 */
package modelo;

import java.util.ArrayList;

import vista.Constants;

/**
 * La nave normal, no tiene disparo continuo, ni escudo protector.
 * @author sergio
 */
public class Nave extends ANave {
	private static final String PATH_NAVE = Constants.PATH_NAVE;
	
	public Nave(int x, int y) {
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
	
}
