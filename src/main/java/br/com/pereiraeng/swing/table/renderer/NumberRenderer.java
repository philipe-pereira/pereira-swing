package br.com.pereiraeng.swing.table.renderer;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

public class NumberRenderer extends RendererEditor implements ChangeListener {
	private static final long serialVersionUID = 1L;

	private Number step;

	public NumberRenderer(int step) {
		this.step = step;
	}

	public NumberRenderer(double step) {
		this.step = step;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		SpinnerNumberModel snm = null;
		if (step instanceof Integer)
			snm = new SpinnerNumberModel(((Number) value).intValue(), Integer.MIN_VALUE, Integer.MAX_VALUE, step);
		else if (step instanceof Double)
			snm = new SpinnerNumberModel(((Number) value).doubleValue(), Double.NEGATIVE_INFINITY,
					Double.POSITIVE_INFINITY, step);
		return new JSpinner(snm);
	}

	@Override
	protected void addListener(Component c) {
		JSpinner s = (JSpinner) c;
		s.addChangeListener(this);
		((DefaultFormatter) ((JSpinner.DefaultEditor) s.getEditor()).getTextField().getFormatter())
				.setCommitsOnValidEdit(true);
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		JSpinner s = (JSpinner) event.getSource();
		super.setNewValue(s.getValue(), s.isValid());
	}
}