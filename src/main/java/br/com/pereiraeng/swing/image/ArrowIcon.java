package br.com.pereiraeng.swing.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ArrowIcon implements Icon {

	private boolean right;

	public ArrowIcon(boolean right) {
		this.right = right;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {

		int[][] position = new int[][] {
				(this.right ? new int[] { 100, 50, 50, 0, 0, 50, 50 }
						: new int[] { 0, 50, 50, 100, 100, 50, 50 }),
				{ y, y + 50, y + 30, y + 30, y - 30, y - 30, y - 50, y } };

		g.setColor(new Color(0, 162, 232));
		g.fillPolygon(position[0], position[1], position[0].length);
		g.setColor(Color.BLACK);
		g.drawPolygon(position[0], position[1], position[0].length);
	}

	@Override
	public int getIconWidth() {
		return 120;
	}

	@Override
	public int getIconHeight() {
		return 120;
	}
}
