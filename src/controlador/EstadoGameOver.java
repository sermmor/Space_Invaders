/**
 * 
 */
package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

/**
 * Estado de partida terminada debido a que se han acabado todas las vidas.
 * @author sergio
 */
public class EstadoGameOver extends AEstadoJuego {
	/** Puntos obtenidos en el juego. */
	private int puntos;
	
	public EstadoGameOver(int puntos) {
		super();
		this.puntos = puntos;
	}

	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#paintPantalla(java.awt.Graphics2D)
	 */
	@Override
	public void paintPantalla(Graphics2D g2d, JPanel p) {
		int[] centroTablero = {150, 150};
		
		Font fTitulo = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		// Mostrar contador puntos en el sitio correspondiente.
		writeTextAligIzq(g2d, centroTablero[0], centroTablero[1], 
				Color.white, fTitulo, new String[] {"GAME OVER"});
		
		// Mostrar letras de pulsar enter para continuar en texto que parpadea
		centroTablero[1] += 50;
		centroTablero[0] += 50; 
		fTitulo = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 14);
		writeTextAligIzq(g2d, centroTablero[0], centroTablero[1], 
				Color.white, fTitulo, new String[] {"Puntos conseguidos: " + puntos});
		
		centroTablero[1] += 25;
		centroTablero[0] += 15;
		parpadearTexto(g2d, centroTablero[0], centroTablero[1], 
				Color.white, fTitulo, new String[] {"(Pulsar tecla enter)"});
		
		/*int margenIzqCentro = 100;
		int margenArribaCentro = 200;
		int[] centroTablero = {(WIDTH_TABLERO/2) - margenIzqCentro, 
				(HEIGHT_TABLERO/2) - margenArribaCentro};
		Font fTitulo = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		// Mostrar contador puntos en el sitio correspondiente.
		writeTextAligIzq(g2d, centroTablero[0], centroTablero[1], 
				Color.white, fTitulo, new String[] {"GAME OVER"});
		
		// Mostrar puntos y letras de pulsar enter para continuar en texto que parpadea
		centroTablero[1] = (HEIGHT_TABLERO/2) - (margenArribaCentro/2);
		fTitulo = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 14);
		parpadearTexto(g2d, centroTablero[0], centroTablero[1], 
				Color.white, fTitulo, new String[] {"Puntos conseguidos: " + puntos,
				"(Pulsar tecla enter)"});*/
	}

	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Aquí, en este caso, no se hace nada.
	}

	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Comprobar si se ha pulsado la tecla enter, para poner la ventana en inactiva.
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			isActiva = false;
		}
	}

	/* (non-Javadoc)
	 * @see controlador.IEstadoJuego#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// Aquí, en este caso, no se hace nada.
	}

}
