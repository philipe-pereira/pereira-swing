package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.pereiraeng.core.TimeUtils;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico que permite a seleção de uma faixa horária
 * 
 * @author Philipe PEREIRA
 *
 */
public class HoursInput extends JPanel implements Input<Calendar[]>, ChangeListener {
	private static final long serialVersionUID = 1L;

	private JSpinner b, e;

	private Calendar min;

	private Calendar max;

	public HoursInput() {
		this(new GregorianCalendar(2000, 5, 1, 0, 0), new GregorianCalendar(2000, 5, 1, 23, 59));
	}

	public HoursInput(Calendar min, Calendar max) {
		this(min, max, min, max);
	}

	public HoursInput(Calendar min, Calendar max, Calendar begin, Calendar end) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.min = min;
		this.max = max;

		this.b = new JSpinner(new SpinnerDateModel());
		this.b.setEditor(new JSpinner.DateEditor(b, "HH:mm"));
		this.b.addChangeListener(this);
		add(b);

		add(new JLabel("-"));

		this.e = new JSpinner(new SpinnerDateModel());
		this.e.setEditor(new JSpinner.DateEditor(e, "HH:mm"));
		this.e.addChangeListener(this);
		add(e);

		this.set(new Calendar[] { begin, end });
	}

	@Override
	public void set(Calendar[] k) {
		this.b.setValue(k[0].getTime());
		this.e.setValue(k[1].getTime());
	}

	@Override
	public Calendar[] get() {
		return new Calendar[] { TimeUtils.date2Calendar((Date) this.b.getValue()),
				TimeUtils.date2Calendar((Date) this.e.getValue()) };
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.b.setEnabled(enabled);
		this.e.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled() {
		return this.b.isEnabled() && this.e.isEnabled();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (this.b.equals(e.getSource())) {
			// o valor inferior não pode ser menor que o mínimo
			Date newBeginValue = (Date) this.b.getValue();
			Date minD = min.getTime();
			if (TimeUtils.timeIsBefore(newBeginValue, minD))
				this.b.getModel().setValue(minD);

			// o valor inferior não pode ser maior que o superior
			Date end = (Date) this.e.getValue();
			if (TimeUtils.timeIsBefore(end, newBeginValue))
				this.b.getModel().setValue(end);

		} else if (this.e.equals(e.getSource())) {
			// o valor superior não pode ser maior que o máximo
			Date newEndValue = (Date) this.e.getValue();
			Date maxD = max.getTime();
			if (TimeUtils.timeIsBefore(maxD, newEndValue))
				this.e.getModel().setValue(maxD);

			// o valor superior não pode ser menor que o inferior
			Date begin = (Date) this.b.getValue();
			if (TimeUtils.timeIsBefore(newEndValue, begin))
				this.e.getModel().setValue(begin);
		}
	}
}
