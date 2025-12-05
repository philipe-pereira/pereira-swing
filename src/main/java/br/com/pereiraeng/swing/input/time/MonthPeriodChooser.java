package br.com.pereiraeng.swing.input.time;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import br.com.pereiraeng.swing.Grade;

public class MonthPeriodChooser extends Grade implements ActionListener {
	private static final long serialVersionUID = -2587766017724004475L;

	public static final Date BEGIN = new GregorianCalendar(1900, 0, 1).getTime();

	public static final Date END = new GregorianCalendar(2099, 11, 31).getTime();

	private JSpinner spBegin;
	private JCheckBox cbBegin;

	private JSpinner spEnd;
	private JCheckBox cbEnd;

	public MonthPeriodChooser() {
		this(BEGIN, END);
	}

	public MonthPeriodChooser(Date d, Date e) {
		boolean ds = d.equals(BEGIN); // desde sempre
		boolean ps = e.equals(END); // para sempre

		this.spBegin = new JSpinner(new SpinnerDateModel(d, BEGIN, END, Calendar.MONTH));
		spBegin.setEditor(new JSpinner.DateEditor(spBegin, "MM/yyyy"));
		spBegin.setEnabled(!ds);
		add(spBegin, 0, 0, 1, 1);

		this.cbBegin = new JCheckBox("Desde sempre", ds);
		this.cbBegin.addActionListener(this);
		this.cbBegin.setActionCommand("D");
		add(cbBegin, 1, 0, 1, 1);

		this.spEnd = new JSpinner(new SpinnerDateModel(e, BEGIN, END, Calendar.MONTH));
		spEnd.setEditor(new JSpinner.DateEditor(spEnd, "MM/yyyy"));
		spEnd.setEnabled(!ps);
		add(spEnd, 0, 1, 1, 1);

		this.cbEnd = new JCheckBox("Para sempre", ps);
		this.cbEnd.setActionCommand("P");
		this.cbEnd.addActionListener(this);
		add(cbEnd, 1, 1, 1, 1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		char c0 = e.getActionCommand().charAt(0);
		boolean s = ((JCheckBox) e.getSource()).isSelected();
		switch (c0) {
		case 'D':
			spBegin.setEnabled(!s);
			break;
		case 'P':
			spEnd.setEnabled(!s);
			break;
		}
	}

	public Date getBegin() {
		if (cbBegin.isSelected()) {
			return BEGIN;
		} else {
			return (Date) spBegin.getValue();
		}
	}

	public Date getEnd() {
		if (cbEnd.isSelected()) {
			return END;
		} else {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(((Date) spEnd.getValue()).getTime());
			int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, day);
			return new Date(c.getTimeInMillis());
		}
	}
}
