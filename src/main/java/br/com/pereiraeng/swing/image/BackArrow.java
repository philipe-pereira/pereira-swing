package br.com.pereiraeng.swing.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class BackArrow implements Icon {
	@Override
	public int getIconHeight() {
		return 70;
	}

	@Override
	public int getIconWidth() {
		return 70;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int[] xs = new int[] { 10, 35, 35, 60, 60, 35, 35 }, ys = new int[] { 35, 10, 25, 25, 45, 45, 60 };
		g.setColor(Color.GREEN);
		g.fillPolygon(xs, ys, 7);
		g.setColor(Color.BLACK);
		g.drawPolygon(xs, ys, 7);
	}
}
