package br.com.pereiraeng.swing.input.text;

import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import br.com.pereiraeng.core.TimeUtils;
import br.com.pereiraeng.core.WrittenNote;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.InputUneditable;

public class WrittenNoteInput extends Grade implements InputUneditable<WrittenNote> {
	private static final long serialVersionUID = 1L;

	private JTextArea text;
	private JTextField user;
	private JSpinner date;

	public WrittenNoteInput() {
		add(user = new JTextField(6), 0, 0, 1, 1);

		this.date = new JSpinner(new SpinnerDateModel(Calendar.getInstance().getTime(),
				new GregorianCalendar(2013, 0, 1).getTime(), Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH));
		this.date.setEditor(new JSpinner.DateEditor(date, "dd/MM/yyyy"));
		date.setEnabled(false);
		add(date, 1, 0, 1, 1);

		text = new JTextArea(6, 6);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		add(new JScrollPane(text), 0, 1, 2, 1);
	}

	public void setUser() {
		user.setText(System.getProperty("user.name"));
	}

	private transient int id = -1;

	@Override
	public void set(WrittenNote n) {
		if (n != null) {
			id = n.getId();
			user.setText(n.getUser());
			date.setValue(n.getDate().getTime());
			text.setText(n.getText());
		} else {
			id = -1;
			user.setText("");
			date.setValue(((SpinnerDateModel) date.getModel()).getEnd());
			text.setText("");
		}
	}

	@Override
	public WrittenNote get() {
		return new WrittenNote(user.getText(), TimeUtils.date2Calendar((Date) date.getValue()), text.getText(), id);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void setEditable(boolean b) {
		user.setEditable(b);
		if (b)
			setUser();
		text.setEditable(b);
	}
}
