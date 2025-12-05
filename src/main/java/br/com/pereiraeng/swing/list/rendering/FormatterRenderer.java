package br.com.pereiraeng.swing.list.rendering;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class FormatterRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	private final String format;

	/**
	 * 
	 * @param format A <a href=
	 *               "https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format
	 *               string</a>
	 */
	public FormatterRenderer(String format) {
		this.format = format;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		return super.getListCellRendererComponent(list, value == null ? value
				: (value instanceof Object[] ? String.format(format, (Object[]) value) : String.format(format, value)),
				index, isSelected, cellHasFocus);
	}
}
