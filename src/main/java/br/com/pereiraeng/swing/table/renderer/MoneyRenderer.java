package br.com.pereiraeng.swing.table.renderer;

import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MoneyRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return super.getTableCellRendererComponent(table,
				value != null ? NumberFormat.getCurrencyInstance().format(value) : "-", isSelected, hasFocus, row,
				column);
	}
}
