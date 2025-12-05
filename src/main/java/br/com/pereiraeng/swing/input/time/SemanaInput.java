package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JToggleButton;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.button.DDSbutton;
import br.com.pereiraeng.swing.input.Input;

public class SemanaInput extends Grade implements Input<boolean[]> {
	private static final long serialVersionUID = 1L;

	private static final Dimension DIM = new Dimension(34, 26);

	public SemanaInput(boolean feriado, boolean[] days) {
		this(feriado);
		this.set(days);
	}

	public SemanaInput(boolean feriado) {
		Calendar roll = Calendar.getInstance();
		roll.setFirstDayOfWeek(Calendar.SUNDAY);
		roll.set(Calendar.DAY_OF_WEEK, 1);
		int i;
		for (i = 0; i < 7; i++) {
			JToggleButton t = new DDSbutton(Character.toUpperCase(String.format("%ta", roll).charAt(0)));
			t.setPreferredSize(DIM);
			this.add(t, i % 4, i / 4, 1, 1);
			roll.add(Calendar.DAY_OF_WEEK, 1);
		}
		if (feriado) {
			JToggleButton t = new DDSbutton('F');
			this.add(t, i % 4, i / 4, 1, 1);
		}
	}

	@Override
	public void set(boolean[] k) {
		for (int i = 0; i < super.getComponentCount(); i++)
			((JToggleButton) super.getComponent(i)).setSelected(k[i]);
	}

	public void setAll(boolean k) {
		for (int i = 0; i < super.getComponentCount(); i++)
			((JToggleButton) super.getComponent(i)).setSelected(k);
	}

	@Override
	public boolean[] get() {
		boolean[] out = new boolean[super.getComponentCount()];
		for (int i = 0; i < out.length; i++)
			out[i] = ((JToggleButton) super.getComponent(i)).isSelected();
		return out;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	public boolean hasUnselected() {
		for (int i = 0; i < super.getComponentCount(); i++)
			if (!((JToggleButton) super.getComponent(i)).isSelected())
				return true;
		return false;
	}

	public void setEditable(boolean b) {
		for (int i = 0; i < super.getComponentCount(); i++) {
			((JToggleButton) super.getComponent(i)).setEnabled(b);
		}
	}
}
