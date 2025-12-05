package br.com.pereiraeng.swing.input.elet;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.Input;

/**
 * Objeto gráfico que permite a inserção do valor de fator
 * 
 * @author Philipe PEREIRA
 *
 */
public class CosPhiInput extends Grade implements Input<Double>, ChangeListener {
	private static final long serialVersionUID = 1L;

	private JTextField cosText;

	private JSlider cosSlider;

	/**
	 * Construtor do objeto gráfico do fator de potência com valor inicial 0,92
	 * indutivo
	 */
	public CosPhiInput() {
		this(0.4027158415806615);
	}

	/**
	 * Construtor do objeto gráfico do fator de potência
	 * 
	 * @param arg
	 *            valor inicial do atraso de fase
	 */
	public CosPhiInput(double arg) {
		add(new JLabel("cos(\u03C6)"), 0, 0, 1, 1);
		add(cosText = new JTextField(4), 1, 0, 1, 1);
		cosText.setEditable(false);
		add(cosSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0), 0,
				1, 2, 1);
		cosSlider.setPreferredSize(new Dimension(114, 16));
		cosSlider.addChangeListener(this);
		this.set(arg);
	}

	private static String getCos(int posicao) {
		double selected = posicao - 50.;
		double cosPhi = 1. - Math.abs(selected) / 100;
		String ic = (Math.signum(selected) == 1. ? "cap." : (Math
				.signum(selected) == -1. ? "ind." : ""));
		return String.format("%.2f %s", cosPhi, ic);
	}

	private static int getCosSlider(double arg) {
		float cos = (float) Math.cos(arg);
		int pos = Math.round(50f + (arg > 0f ? -1f : 1f) * 100f * (1f - cos));
		return pos;
	}

	private static double getArg(int posicao) {
		double selected = posicao - 50.;
		double cosPhi = 1. - Math.abs(selected) / 100;
		return -1 * Math.signum(selected) * Math.acos(cosPhi);
	}

	// ------------------------ INPUT ------------------------

	@Override
	public void set(Double arg) {
		this.cosSlider.setValue(getCosSlider(arg));
	}

	@Override
	public Double get() {
		return getArg(cosSlider.getValue());
	}

	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Função com a qual se estabelece o valor do fator de potência indicado
	 * pelo editor gráfico
	 * 
	 * @param cos
	 *            valor de cos(phi)
	 * @param ind
	 *            <code>true</code>, fator de potência indutivo, capacitivo
	 *            senão
	 */
	public void set(double cos, boolean ind) {
		this.cosSlider.setValue(Math.round(50f + (ind ? -1f : 1f) * 100f
				* (1f - (float) cos)));
	}

	// ------------------------ LISTENER ------------------------

	@Override
	public void stateChanged(ChangeEvent e) {
		// atualiza caixa de texto
		this.cosText.setText(getCos(cosSlider.getValue()));
		// repassa o evento
		if (listener != null)
			listener.stateChanged(new ChangeEvent(this));
	}

	private transient ChangeListener listener;

	/**
	 * Adds a listener to the list that is notified each time a change occurs.
	 * The source of ChangeEvents delivered to ChangeListeners will be this
	 * {@link CosPhiInput CosPhiInput}.
	 * 
	 * @param listener
	 *            the <code>ChangeListener</code> to add
	 */
	public void addChangeListener(ChangeListener listener) {
		this.listener = listener;
	}
}
