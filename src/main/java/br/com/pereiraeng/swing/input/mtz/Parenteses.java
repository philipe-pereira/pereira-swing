package br.com.pereiraeng.swing.input.mtz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

public class Parenteses implements Icon {
	private final boolean b;

	private int rows;

	public Parenteses(boolean b) {
		this.b = b;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public int getIconHeight() {
		return 20 * rows;
	}

	@Override
	public int getIconWidth() {
		return 12;
	}

	@Override
	public void paintIcon(Component c, Graphics g2, int x, int y) {
		g2.setColor(Color.BLACK);

		Graphics2D g = (Graphics2D) g2;
		g.setStroke(new BasicStroke(2f));

		g.drawLine(x + (b ? 1 : 10), y, x + (b ? 1 : 10), y + 20 * rows);

		g.drawLine(x + 1, y + 20 * rows - 1, x + 9, y + 20 * rows - 1);
		g.drawLine(x + 1, y + 1, x + 9, y + 1);
	}
}