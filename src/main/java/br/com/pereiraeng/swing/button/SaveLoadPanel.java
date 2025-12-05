package br.com.pereiraeng.swing.button;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.SwingUtils;

public class SaveLoadPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String LOAD = "LOAD", SAVE = "SAVE";

	public static final char LOAD_CHAR = 'L', SAVE_CHAR = 'S';

	public SaveLoadPanel(ActionListener listener) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JButton b = new JButton(Icons.loadUtilsIcon("Open.gif"));
		b.setPreferredSize(SwingUtils.DIM_BUTTON_ICON);
		b.setActionCommand(LOAD);
		b.addActionListener(listener);
		b.setToolTipText("Carregar");
		add(b);

		b = new JButton(Icons.loadUtilsIcon("Save.gif"));
		b.setPreferredSize(SwingUtils.DIM_BUTTON_ICON);
		b.setActionCommand(SAVE);
		b.addActionListener(listener);
		b.setToolTipText("Salvar");
		add(b);
	}

	@Override
	public void setToolTipText(String text) {
		JButton b = (JButton) this.getComponent(0);
		b.setToolTipText(b.getToolTipText() + text);
		b = (JButton) this.getComponent(1);
		b.setToolTipText(b.getToolTipText() + text);
	}

	/**
	 * Enables (or disables) the button.
	 * 
	 * @param load    <code>true</code> para o botão load, <code>false</code> para o
	 *                botão save
	 * @param enabled <code>true</code> to enable the button, otherwise false
	 */
	public void setEnabled(boolean load, boolean enabled) {
		((JButton) this.getComponent(load ? 0 : 1)).setEnabled(enabled);
	}
}
