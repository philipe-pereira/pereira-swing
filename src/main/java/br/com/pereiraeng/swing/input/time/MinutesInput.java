package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import br.com.pereiraeng.swing.input.Input;

public class MinutesInput extends JPanel implements Input<Calendar[]> {
	private static final long serialVersionUID = 1L;

	private TimeInput timeInput;

	private JSpinner minSpinner;

	/**
	 * 
	 */
	public MinutesInput() {
		this(Calendar.getInstance(), 1, 120);
	}

	/**
	 * Constr
	 * @param c
	 * @param min
	 * @param max
	 */
	public MinutesInput(Calendar c, int min, int max) {
		add(timeInput = new TimeInput(c));
		add(new JLabel("Minutos da análise"));
		add(minSpinner = new JSpinner(new SpinnerNumberModel(5, min, max, 1)));
	}

	@Override
	public Calendar[] get() {
		// instante inicial
		Calendar cb = timeInput.get();

		// instante final
		Calendar ce = (Calendar) cb.clone();
		ce.add(Calendar.MINUTE, getMinutes());

		return new Calendar[] { cb, ce };
	}

	@Override
	public void set(Calendar[] k) {
		timeInput.set(k[0]);

		int min = (int) ((k[1].getTimeInMillis() - k[0].getTimeInMillis()) / 60000);
		minSpinner.setValue(min);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	public int getMinutes() {
		return (int) minSpinner.getValue();
	}

	public String[] getMinutesString(int espace) {
		int num = getMinutes() / espace;
		String[] out = new String[num];
		Calendar[] cs = get();

		Calendar c = (Calendar) cs[0].clone();

		for (int k = 0; k < num; k++) {
			out[k] = String.format("%tR", c);
			c.add(Calendar.MINUTE, espace);
		}
		return out;
	}
}
