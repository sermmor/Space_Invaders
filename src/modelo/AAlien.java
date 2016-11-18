/**
 * 
 */
package modelo;

/**
 * @author sergio
 *
 */
public abstract class AAlien extends AObjetoEnMovimiento {
	public enum Direccion {IZQUIERDA, DERECHA, FRENTE};
	
	protected static final int TIME_DELAY_PASO = 300; // Inicial retraso de todos los aliens.
	protected static final int TIME_DELAY_POR_ALIEN = 5; // Retraso por alien.
	protected static final int NUM_PIX_PASO = 10; // Número de pixeles que forman un paso de un alien.
	
	
	protected int numPasosIzq = 4; /* Pasos hacia la izquierda, se incrementa 
									 si todos los aliens de una columna de la izquierda han muerto. */
	protected int numPasosDch = 4; /* Pasos hacia la derecha, se incrementa 
	 								 si todos los aliens de una columna de la izquierda han muerto. */
	protected int timeDelay = TIME_DELAY_PASO; /* Al comienzo tardaremos un segundo en mover los aliens,  
									 esto irá decrementando conforme al número de aliens. */
	
	protected int contadorTime = TIME_DELAY_PASO;
	protected int contadorPasosIzq = 0;
	protected int contadorPasosDch = 4; // Al comienzo todos los aliens estarán a la izquierda.
	
	// Al comienzo todos los aliens estarán a la izquierda.
	protected Direccion dirActual = Direccion.DERECHA;
	
	public AAlien(String sImagePath, int x, int y) {
		super(sImagePath);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Decrementa el timeDelay del alien, para que los aliens se muevan más rápido (usar si un alien
	 * ha muerto).
	 */
	public void decrementDelay() {
		timeDelay = timeDelay - TIME_DELAY_POR_ALIEN;
	}
	
	/* (non-Javadoc)
	 * @see modelo.AObjetoEnMovimiento#move()
	 */
	@Override
	public void move() {
		contadorTime--;
		if (contadorTime == 0) {
			contadorTime = timeDelay;
			// Veo la dirección en la que me estoy moviendo y decido el movimiento.
			switch (dirActual) {
			case IZQUIERDA:
				if (contadorPasosIzq > 0) {
					// Doy un paso a la izquierda.
					x = x - NUM_PIX_PASO;
					contadorPasosIzq--;
				} else {
					// Doy un paso al frente y preparo cambio dirección.
					y = y + NUM_PIX_PASO;
					contadorPasosDch = numPasosDch;
					dirActual = Direccion.DERECHA;
				}
				break;
			case DERECHA:
				if (contadorPasosDch > 0) {
					// Doy un paso a la izquierda.
					x = x + NUM_PIX_PASO;
					contadorPasosDch--;
				} else {
					// Doy un paso al frente y preparo cambio dirección.
					y = y + NUM_PIX_PASO;
					contadorPasosIzq = numPasosIzq;
					dirActual = Direccion.IZQUIERDA;
				}
				break;
			}
		}
	}
	
	/**
	 * Devuelve la puntuación a acabar con el alien.
	 */
	public abstract int getPuntuacion();

}
