package br.com.pereiraeng.swing.image;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class UArrow implements Icon {

	private boolean up;

	public UArrow(boolean up) {
		this.up = up;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x0, int y0) {
		g.drawLine(7, 7, 28, 7);
		g.drawLine(28, 7, 28, 28);
		g.drawLine(28, 28, 7, 28);
		// DrawingUtils.drawArrow(g, 7, up ? 7 : 28, Direction.LEFT);
		int alt = 10, larg = 4;
		int x = 7;
		int y = up ? 7 : 28;
		int[] abs = new int[] { x, x + alt, x + alt }, ord = new int[] { y, y + larg, y - larg };
		g.fillPolygon(abs, ord, 3);
	}

	@Override
	public int getIconWidth() {
		return 28;
	}

	@Override
	public int getIconHeight() {
		return 30;
	}
}
