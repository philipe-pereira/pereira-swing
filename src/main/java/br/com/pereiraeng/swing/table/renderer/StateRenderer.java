package br.com.pereiraeng.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class StateRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	private Boolean state;

	public StateRenderer() {
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	protected void paintComponent(Graphics g) {
		if (this.state != null) {
			Color c = g.getColor();
			g.setColor(state ? Color.GREEN : Color.RED);
			g.fillOval(12, 0, 15, 15);
			g.setColor(c);
		}
		super.paintComponent(g);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		this.state = (Boolean) value;
		return this;
	}
}
