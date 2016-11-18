/**
 * 
 */
package controlador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Panel controlador que usa los estados del juego.
 * 
 * @author sergio
 */
public class Tablero extends JPanel implements ActionListener {
	/** Serial por defecto */
	private static final long serialVersionUID = 1L;
	
	/** Milisegundos para el timer. */
	public static final int SWING_TIMER_DELAY = 5; // Velocidad el timer = 5 milisegundos (5 milisegundo será la velocidad máxima)
	
	/** Timer para el action. */
	private Timer timer;
	
	/** Estado actual del juego. */
	private IEstadoJuego iej;
	
	public Tablero() {
		addKeyListener(new TAdapter());
		setFocusable(true); // Dar el foco al tablero.
		setBackground(Color.black);
		
		// Al empezar el juego, el estado del juego será el estado inicial.
		iej = new EstadoInicial();

		timer = new Timer(SWING_TIMER_DELAY, this); // SWING_TIMER_DELAY será la velocidad del timer.
		timer.start();
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); // Esto siempre hay que hacerlo, para que funcione.
		Graphics2D g2d = (Graphics2D) g; // Usar gráficos 2D.
		// Pintar la pantalla.
		iej.paintPantalla(g2d, this);
		
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (iej.haAcabado()) {
			// Si ha acabado pasar a la pantalla correspondiente.
			if (iej instanceof EstadoInicial) {
				// Si era el estado inicial, pasar a la pantalla de juego.
				iej = new EstadoJugando();
			} else if (iej instanceof EstadoJugando) {
				// Si era el estado jugando, pasar al estado game over.
				iej = new EstadoGameOver(((EstadoJugando) iej).getPuntos());
			} else if (iej instanceof EstadoGameOver) {
				// Si era el estado game over, pasar al estado inicial.
				iej = new EstadoInicial();
			}
		} else {
			// Si no acabado, ejecutar método actionPerformed correspondiente.
			iej.actionPerformed(arg0);
		}
		repaint();
	}

	/**
	 * Implementa los aspectos de pulsación de las teclas.
	 * 
	 * @author sergio
	 */
	private class TAdapter extends KeyAdapter {

		/* (non-Javadoc)
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			iej.keyPressed(e);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			iej.keyReleased(e);
		}
		
	}
}
