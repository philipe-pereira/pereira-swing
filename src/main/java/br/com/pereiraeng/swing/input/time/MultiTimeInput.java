package br.com.pereiraeng.swing.input.time;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import br.com.pereiraeng.swing.Grade;

public class MultiTimeInput extends Grade implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel p;

	private TimeInput time;
	private TimesInput times;
	private PeriodInput periods;

	public MultiTimeInput() {
		int i = 0;

		ButtonGroup bg = new ButtonGroup();

		JRadioButton rb = new JRadioButton("Pont.", true);
		rb.setToolTipText("Um dado instante de tempo");
		bg.add(rb);
		rb.setActionCommand("a");
		rb.addActionListener(this);
		add(rb, 0, i, 1, 1);

		rb = new JRadioButton("Vár. inst.");
		rb.setToolTipText("Vários instantes pontuais de tempo");
		bg.add(rb);
		rb.setActionCommand("b");
		rb.addActionListener(this);
		add(rb, 1, i, 1, 1);

		rb = new JRadioButton("Per.");
		rb.setToolTipText("Um período de tempo");
		bg.add(rb);
		rb.setActionCommand("c");
		rb.addActionListener(this);
		add(rb, 2, i++, 1, 1);

		// escolhe dos instantes de tempo

		this.p = new JPanel(new CardLayout());
		add(this.p, 0, i, 3, 1);

		time = new TimeInput(null, -1);
		time.setVisible(true);
		this.p.add(time, "a");

		times = new TimesInput(null);
		times.setVisible(false);
		this.p.add(times, "b");

		periods = new PeriodInput(false);
		periods.setVisible(false);
		this.p.add(periods, "c");
	}

	public void setLimits(Calendar[] cs) {
		if (cs[0] != null && cs[1] != null) {
			time.set(cs[1]);

			times.setTime(cs[1]);

			periods.setMin(cs[0]);
			periods.setMax(cs[1]);
			Calendar c = (Calendar) cs[1].clone();
			c.add(Calendar.DAY_OF_MONTH, -1);
			periods.set(new Calendar[] { c, cs[1] });
		}
	}

	/**
	 * 
	 * @return períodos de tempo, definidos por uma matriz de duas colunas, onde os
	 *         elementos da primeira coluna indicam o começo de um subintervalo e os
	 *         da segunda indicam seu final
	 */
	public Calendar[][] get() {
		if (time.isVisible()) {
			// 1: UM SÓ INSTANTE
			// vetor com uma posição

			if (time.isEnabled()) {
				// minuto escolhido pelo usuário
				return new Calendar[][] { { time.get() } };
			} else {
				// minuto anterior
				Calendar cal0 = Calendar.getInstance();
				cal0 = new GregorianCalendar(cal0.get(Calendar.YEAR), cal0.get(Calendar.MONTH),
						cal0.get(Calendar.DAY_OF_MONTH), cal0.get(Calendar.HOUR_OF_DAY), cal0.get(Calendar.MINUTE));
				cal0.add(Calendar.MINUTE, -1);
				return new Calendar[][] { { cal0 } };
			}
		} else if (times.isVisible()) {
			// 2: VÁRIOS INSTANTES
			// vetor com várias posições
			return new Calendar[][] { times.get() };
		} else {
			// 3: FAIXA DE TEMPO
			// vetor com duas posições (inicial e final)
			return new Calendar[][] { periods.get() };
		}
	}

	public boolean isRange() {
		return periods.isVisible();
	}

	// ------------------------ LISTENER ------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		((CardLayout) this.p.getLayout()).show(this.p, event.getActionCommand());
	}
}
