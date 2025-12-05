package br.com.pereiraeng.swing.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

/**
 * Classe do escutador que transforma os {@link WindowEvent eventos da janela}
 * em {@link ActionEvent eventos de ação}
 * 
 * @author Philipe PEREIRA
 *
 */
public class WindowActionListener implements WindowListener {

	public static final String CLOSED = "CLOSED";

	public static final char CLOSED_CHAR = 'C';

	public static final String[] ID = new String[] { "OPENED", "CLOSING", CLOSED, "ICONIFIED", "DEICONIFIED",
			"ACTIVATED", "DEACTIVATED" };

	private final ActionListener listener;

	private final boolean[] events;

	/**
	 * Construtor do escutador que é acionado somente quando a janela é
	 * {@link #CLOSED fechada}
	 * 
	 * @param listener escutador de ação
	 */
	public WindowActionListener(ActionListener listener) {
		this(listener, new boolean[] { false, false, true, false, false, false, false });
	}

	/**
	 * Construtor do escutador que é acionado para todos os eventos da janela
	 * 
	 * @param listener escutador de ação
	 * @param events
	 *                 <ol>
	 *                 <li>OPENED</i>
	 *                 <li>CLOSING</i>
	 *                 <li>CLOSED</i>
	 *                 <li>ICONIFIED</i>
	 *                 <li>DEICONIFIED</i>
	 *                 <li>ACTIVATED</i>
	 *                 <li>DEACTIVATED</i>
	 *                 </ol>
	 */
	public WindowActionListener(ActionListener listener, boolean[] events) {
		this.listener = listener;
		if (events != null)
			this.events = Arrays.copyOf(events, 7);
		else
			this.events = new boolean[] { true, true, true, true, true, true, true };
	}

	@Override
	public void windowActivated(WindowEvent event) {
		actionPerformed(event.getSource(), ActionEvent.ACTION_PERFORMED, event.getID());
	}

	@Override
	public void windowClosed(WindowEvent event) {
		actionPerformed(event.getSource(), ActionEvent.ACTION_PERFORMED, event.getID());
	}

	@Override
	public void windowClosing(WindowEvent event) {
		actionPerformed(event.getSource(), ActionEvent.ACTION_PERFORMED, event.getID());
	}

	@Override
	public void windowDeactivated(WindowEvent event) {
		actionPerformed(event.getSource(), ActionEvent.ACTION_PERFORMED, event.getID());
	}

	@Override
	public void windowDeiconified(WindowEvent event) {
		actionPerformed(event.getSource(), ActionEvent.ACTION_PERFORMED, event.getID());
	}

	@Override
	public void windowIconified(WindowEvent event) {
		actionPerformed(event.getSource(), ActionEvent.ACTION_PERFORMED, event.getID());
	}

	@Override
	public void windowOpened(WindowEvent event) {
		actionPerformed(event.getSource(), ActionEvent.ACTION_PERFORMED, event.getID());
	}

	private void actionPerformed(Object source, int actionPerformed, int id) {
		id -= 200;
		if (events[id])
			this.listener.actionPerformed(new ActionEvent(source, ActionEvent.ACTION_PERFORMED, ID[id]));
	}
}
