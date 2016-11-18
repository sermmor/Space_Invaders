/**
 * 
 */
package modelo;

import java.util.ArrayList;

import vista.Constants;

/**
 * La nave con escudo protector seleccionable, tiene disparo continuo.
 * @author sergio
 */
public class NaveDisparoContinuo extends ANave {
	
	private static final String PATH_NAVE = Constants.PATH_NAVE_CONT;
	
	private boolean bEscudo;
	
	public NaveDisparoContinuo(int x, int y) {
		super(PATH_NAVE);
		this.x = x;
		this.y = y;
		misilesActivo = new ArrayList<DisparoNave>();
		bEscudo = true;
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
		if (misilesActivo == null) {
			misilesActivo = new ArrayList<DisparoNave>();
		}
		misilesActivo.add(new DisparoNave(x + (width/2), y));
	}

	/* (non-Javadoc)
	 * @see modelo.AObjetoEnMovimiento#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		if (!bEscudo) {
			// Si ya no tiene el escudo protector, setea visible. 
			super.setVisible(visible);
		}
	}

	/**
	 * @return the bEscudo
	 */
	public boolean isEscudo() {
		return bEscudo;
	}

	/**
	 * @param bEscudo the bEscudo to set
	 */
	public void setEscudo(boolean bEscudo) {
		this.bEscudo = bEscudo;
	}

}
