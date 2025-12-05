package br.com.pereiraeng.swing.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * Ícone das nove possíveis posições de ancoragem de um dado elemento HTML
 * 
 * @author Philipe Pereira
 * 
 */
public class DirectionIcon implements Icon {
	private int i;
	private final int min = 2, cen = 10, max = 18;

	public DirectionIcon(int i) {
		this.i = i;
	}

	public int getIconHeight() {
		return 20;
	}

	public int getIconWidth() {
		return 20;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		switch (i) {
		case 0:
			g.drawLine(min, min, min, cen);
			g.drawLine(min, min, cen, min);
			g.drawLine(min, min, max, max);
			break;
		case 1:
			g.drawLine(cen, min, min, cen);
			g.drawLine(cen, min, cen, max);
			g.drawLine(cen, min, max, cen);
			break;
		case 2:
			g.drawLine(max, min, cen, min);
			g.drawLine(max, min, max, cen);
			g.drawLine(max, min, min, max);
			break;
		case 3:
			g.drawLine(min, cen, cen, min);
			g.drawLine(min, cen, cen, max);
			g.drawLine(min, cen, max, cen);
			break;
		case 4:
			g.drawLine(cen, min, cen, max);
			g.drawLine(min, cen, max, cen);
			break;
		case 5:
			g.drawLine(max, cen, cen, min);
			g.drawLine(max, cen, cen, max);
			g.drawLine(max, cen, min, cen);
			break;
		case 6:
			g.drawLine(min, max, min, cen);
			g.drawLine(min, max, cen, max);
			g.drawLine(min, max, max, min);
			break;
		case 7:
			g.drawLine(cen, max, cen, min);
			g.drawLine(cen, max, min, cen);
			g.drawLine(cen, max, max, cen);
			break;
		case 8:
			g.drawLine(max, max, min, min);
			g.drawLine(max, max, cen, max);
			g.drawLine(max, max, max, cen);
			break;
		default:
			break;
		}
	}
}
