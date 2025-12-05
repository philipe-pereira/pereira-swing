package br.com.pereiraeng.swing.input.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.core.TimeUtils;

public class TimeChangesSelection extends Grade {
	private static final long serialVersionUID = 1L;

	private JRadioButton rb1, rb3;

	private JSpinner sp;

	/**
	 * 
	 * @param per
	 *            <ul>
	 *            <li>{@link Calendar#YEAR} o intervalo começa no começo do ano da
	 *            data e termina no final do mesmo ano;</i>
	 *            <li>{@link Calendar#MONTH} o intervalo começa no começo do mês da
	 *            data e termina no final do mesmo mês;</i>
	 *            <li>{@link Calendar#DAY_OF_MONTH} o intervalo começa no começo do
	 *            dia da data e termina no final do mesmo dia.</i>
	 *            </ul>
	 */
	public TimeChangesSelection(int per) {
		ButtonGroup bg = new ButtonGroup();
		add(new JLabel("As alterações são válidas a partir:"), 0, 0, 1, 1);
		this.rb1 = new JRadioButton("de agora", true);
		bg.add(rb1);
		add(rb1, 0, 1, 1, 1);
		JRadioButton rb2 = new JRadioButton("de desde sempre");
		bg.add(rb2);
		add(rb2, 0, 2, 1, 1);
		this.rb3 = new JRadioButton(String.format("do %s de:",
				per == Calendar.DAY_OF_MONTH ? "dia" : (per == Calendar.MONTH ? "mês" : "ano")));
		bg.add(rb3);
		add(rb3, 0, 3, 1, 1);

		this.sp = new JSpinner(new SpinnerDateModel(Calendar.getInstance().getTime(),
				new GregorianCalendar(2013, 0, 1).getTime(), Calendar.getInstance().getTime(), per));
		sp.setEditor(new JSpinner.DateEditor(sp,
				per == Calendar.DAY_OF_MONTH ? "dd/MMMM/yyyy" : (per == Calendar.MONTH ? "MMMM/yyyy" : "yyyy")));
		add(sp, 0, 4, 1, 1);
	}

	public Calendar getTime() {
		Calendar out = null;
		if (rb1.isSelected())
			out = Calendar.getInstance();
		else if (rb3.isSelected())
			out = TimeUtils.date2Calendar((Date) sp.getValue());
		return out;
	}
}
