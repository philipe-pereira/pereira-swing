package br.com.pereiraeng.swing.button;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.icons.PereiraIcon;

/**
 * Classe do objeto gráfico com uma sequência de botões como controle do fluxo
 * de tempo. As ações disponíveis são:
 * 
 * <ul>
 * <li>recuar rápido;</i>
 * <li>recuar um passo;</i>
 * <li>parar;</i>
 * <li>executar;</i>
 * <li>pausar;</i>
 * <li>avançar um passo;</i>
 * <li>avançar rápido.</i>
 * </ul>
 * 
 * @author Philipe PEREIRA
 *
 */
public class TimeCtrlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String REWIND = "Rewind", STEP_BACK = "StepBack", PAUSE = "Pause", STEP_FORWARD = "StepForward",
			FAST_FORWARD = "FastForward";

	/**
	 * Construtor padrão do objeto gráfico com os botões de controle do fluxo de
	 * tempo (contendo somente os botões parar, executar, pausar e sem escutador,
	 * que pode ser adicionado a posteriori pela função
	 * {@link #addActionListener(ActionListener)})
	 */
	public TimeCtrlPanel() {
		this(new boolean[] { false, false, true, true, true, false, false }, null);
	}

	/**
	 * Construtor do objeto gráfico com os botões de controle do fluxo de tempo
	 * 
	 * @param buttons
	 *                 <ol start="0">
	 *                 <li>Rewind</i>
	 *                 <li>StepBack</i>
	 *                 <li>Stop</i>
	 *                 <li>Play</i>
	 *                 <li>Pause</i>
	 *                 <li>StepForward</i>
	 *                 <li>FastForward</i>
	 *                 </ol>
	 * @param listener {@link ActionListener objeto} com o escutador dos eventos do
	 *                 botões
	 */
	public TimeCtrlPanel(boolean[] buttons, ActionListener listener) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));

		final Icon[] icons = { PereiraIcon.REWIND.create(), PereiraIcon.STEP_BACK.create(), PereiraIcon.STOP.create(),
				PereiraIcon.PLAY.create(), PereiraIcon.PAUSE.create(), PereiraIcon.STEP_FORWARD.create(),
				PereiraIcon.FAST_FORWARD.create() };
		final String[] actions = { REWIND, STEP_BACK, PlayStopButton.STOP, PlayStopButton.PLAY, PAUSE, STEP_FORWARD,
				FAST_FORWARD };

		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i]) {
				JButton b = new JButton(icons[i]);
				b.setActionCommand(actions[i]);
				b.setPreferredSize(Icons.DIM_BUTTON_ICON);
				add(b);
			}
		}

		if (listener != null)
			addActionListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (int i = 0; i < getComponentCount(); i++)
			((JButton) getComponent(i)).setEnabled(enabled);
	}

	/**
	 * Função que adiciona um {@link ActionListener} aos botões
	 * 
	 * @param listener escutador a ser adicionado
	 */
	public void addActionListener(ActionListener listener) {
		for (int i = 0; i < getComponentCount(); i++)
			((JButton) getComponent(i)).addActionListener(listener);
	}
}
