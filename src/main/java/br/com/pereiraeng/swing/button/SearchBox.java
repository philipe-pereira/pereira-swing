package br.com.pereiraeng.swing.button;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.SwingUtils;

/**
 * Classe do objeto gráfico de de uma caixa de texto com um botão de busca. As
 * ações geradas pelo botão de busca geram evento que tem como fonte não o
 * botão, mas a caixa de texto
 * 
 * @author Philipe PEREIRA
 *
 */
public class SearchBox extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	public SearchBox() {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JTextField tf = new JTextField(7);
		tf.addKeyListener(this);
		add(tf);

		JButton b = new JButton(Icons.loadUtilsIcon("Zoom24.gif"));
		b.setPreferredSize(SwingUtils.DIM_BUTTON_SMALL_ICON);
		b.addActionListener(this);
		add(b);
	}

	/**
	 * Sets the command name for the action event fired by this button. By default
	 * this action command is set to match the label of the button.
	 * 
	 * @param actionCommand a string used to set the button's action command. If the
	 *                      string is <code>null</code> then the action command is
	 *                      set to match the label of the button.
	 */
	public void setActionCommand(String actionCommand) {
		((JButton) getComponent(1)).setActionCommand(actionCommand);
	}

	/**
	 * Adds the specified action listener to receive action events from this button.
	 * Action events occur when a user presses or releases the mouse over this
	 * button. If l is <code>null</code>, no exception is thrown and no action is
	 * performed.
	 * 
	 * @param l the action listener
	 */
	public void addActionListener(ActionListener l) {
		if (l != null)
			super.listenerList.add(ActionListener.class, l);
	}

	// =============================== LISTENER ===============================

	@Override
	public void actionPerformed(ActionEvent e) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying those that are interested in
		// this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == ActionListener.class)
				((ActionListener) listeners[i + 1]).actionPerformed(new ActionEvent((JTextField) getComponent(0),
						ActionEvent.ACTION_PERFORMED, ((JButton) getComponent(1)).getActionCommand()));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER)
			actionPerformed(new ActionEvent((JTextField) getComponent(0), ActionEvent.ACTION_PERFORMED,
					((JButton) getComponent(1)).getActionCommand()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
