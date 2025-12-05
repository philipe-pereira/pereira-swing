package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JToggleButton;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.button.DDSbutton;
import br.com.pereiraeng.swing.input.Input;

public class MesInput extends Grade implements Input<boolean[]> {
	private static final long serialVersionUID = 1L;

	private static final Dimension DIM = new Dimension(50, 26);

	public MesInput(boolean[] month) {
		int y = Calendar.getInstance().get(Calendar.YEAR);
		for (int m = 0; m < 12; m++) {
			JToggleButton t = new DDSbutton(String.format("%tb", new GregorianCalendar(y, m, 1)));
			t.setPreferredSize(DIM);
			add(t, m % 4, m / 4, 1, 1);
		}
		this.set(month);
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
