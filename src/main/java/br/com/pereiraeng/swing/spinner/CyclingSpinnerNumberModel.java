package br.com.pereiraeng.swing.spinner;

import javax.swing.SpinnerNumberModel;

public class CyclingSpinnerNumberModel extends SpinnerNumberModel {
	private static final long serialVersionUID = 1L;

	public CyclingSpinnerNumberModel(int value, int minimum, int maximum, int stepSize) {
		super(value, minimum, maximum, stepSize);
	}

	public Object getNextValue() {
		Object value = super.getNextValue();
		if (value == null)
			value = super.getMinimum();
		return value;
	}

	public Object getPreviousValue() {
		Object value = super.getPreviousValue();
		if (value == null)
			value = super.getMaximum();
		return value;
	}
}
