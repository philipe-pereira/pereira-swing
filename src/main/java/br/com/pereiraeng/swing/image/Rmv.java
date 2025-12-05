package br.com.pereiraeng.swing.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

/**
 * Classe do ícone de um "xis"
 * 
 * @author Philipe PEREIRA
 * @version November 18th, 2020
 *
 */
public class Rmv implements Icon {

	private final boolean fr;

	public Rmv(boolean fr) {
		this.fr = fr;
	}

	@Override
	public void paintIcon(Component c, Graphics g0, int x, int y) {
		Graphics2D g = (Graphics2D) g0;
		BasicStroke bs = (BasicStroke) g.getStroke();
		g.setStroke(new BasicStroke(3f));
		g.setColor(fr ? Color.DARK_GRAY : Color.WHITE);
		g.drawLine(x - 10, y - 10, x + 10, y + 10);
		g.drawLine(x - 10, y + 10, x + 10, y - 10);
		g.setStroke(bs);
	}

	@Override
	public int getIconHeight() {
		return 0;
	}

	@Override
	public int getIconWidth() {
		return 0;
	}
}
