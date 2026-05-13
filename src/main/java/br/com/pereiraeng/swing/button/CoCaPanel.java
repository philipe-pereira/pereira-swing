package br.com.pereiraeng.swing.button;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pereiraeng.core.LocaleConfig;
import br.com.pereiraeng.icons.PereiraIcon;

/**
 * Classe do objeto gráfico com dois butões, um para <strong>Co</strong>nfirmar
 * a ação e outro para <strong>Ca</strong>ncelar
 * 
 * @author Philipe PEREIRA
 *
 */
public class CoCaPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String EDITION = "EDITION";

	public static final String EDITION_OK = EDITION + "_OK", EDITION_CANCEL = EDITION + "_CANCEL";

	/**
	 * Construtor do objeto gráfico com dois butões (ambos contendo textos)
	 */
	public CoCaPanel() {
		this(true);
	}

	/**
	 * Construtor do objeto gráfico com dois butões
	 * 
	 * @param text se <code>true</code> os botões mostram textos, se
	 *             <code>false</code> figuras.
	 */
	public CoCaPanel(boolean text) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));

		// confirmar
		JButton b = null;
		if (text)
			b = new JButton(LocaleConfig.hasConfig() ? LocaleConfig.getString("ok") : "Confirmar");
		else
			b = new JButton(PereiraIcon.OK.create());
		b.setActionCommand(EDITION_OK);
		b.setMnemonic('\n');
		this.add(b);

		// cancelar
		if (text)
			b = new JButton(LocaleConfig.hasConfig() ? LocaleConfig.getString("cancel") : "Cancelar");
		else
			b = new JButton(PereiraIcon.CANCEL.create());
		b.setActionCommand(EDITION_CANCEL);
		b.setMnemonic(27);
		this.add(b);
	}

	public void addActionListener(ActionListener listener) {
		((JButton) this.getComponent(0)).addActionListener(listener);
		((JButton) this.getComponent(1)).addActionListener(listener);
	}

	/**
	 * Enables (or disables) the buttons.
	 *
	 * @param confirm <code>true</code> for Confirm Button, <code>false</code> for
	 *                Cancel Button
	 * @param enable  <code>true</code> to enable the button, otherwise
	 *                <code>false</code>
	 */
	public void setEnabled(boolean confirm, boolean enable) {
		((JButton) this.getComponent(confirm ? 0 : 1)).setEnabled(enable);
	}
}
