package br.com.pereiraeng.swing.table.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FormatterRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	private final String format;

	public FormatterRenderer(String format) {
		this.format = format;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return super.getTableCellRendererComponent(table, value == null ? value
				: (value instanceof Object[] ? String.format(format, (Object[]) value) : String.format(format, value)),
				isSelected, hasFocus, row, column);
	}
}
