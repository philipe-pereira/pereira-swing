package br.com.pereiraeng.swing.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import br.com.pereiraeng.core.BinaryUtils;

/**
 * Classe do objetos gráficos que são desenhos círculos de diferentes cores para
 * representar os diferentes valores
 * 
 * @author Philipe PEREIRA
 *
 */
public class Pointer implements Icon {

	public static final int GOOD = 4, OK = 2, NEUTRAL = 1, BAD = 0, NO = -4;

	private int status, states;

	public Pointer(boolean b) {
		this(b ? 2 : 0);
	}

	/**
	 * Construtor do ponteiro
	 * 
	 * @param initialStatus a cor é determinada conforme a tabela a seguir:
	 *                      <ul>
	 *                      <li>{@link #NO -4}: cinza</i>
	 *                      <li>{@link #BAD 0}: vermelho</i>
	 *                      <li>{@link #NEUTRAL 1}: amarelo</i>
	 *                      <li>{@link #OK 2}: verde</i>
	 *                      <li>{@link #GOOD 4}: azul</i>
	 *                      </ul>
	 *                      e para qualquer outro valor, azul.
	 */
	public Pointer(int initialStatus) {
		this(initialStatus, 0);
	}

	public Pointer(int initialStatus, int states) {
		this.status = initialStatus;
		this.states = states;
	}

	/**
	 * 
	 * @param status
	 *               <ul>
	 *               <li>{@link #NO -4}: cinza</i>
	 *               <li>{@link #BAD 0}: vermelho</i>
	 *               <li>{@link #NEUTRAL 1}: amarelo</i>
	 *               <li>{@link #OK 2}: verde</i>
	 *               <li>{@link #GOOD 4}: azul</i>
	 *               </ul>
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	// -----------------------------------------------------

	@Override
	public int getIconHeight() {
		return 17;
	}

	@Override
	public int getIconWidth() {
		return 17;
	}

	@Override
	public void paintIcon(Component comp, Graphics g, int x, int y) {
		int x0 = comp.getWidth() / 2 - 8;
		int y0 = comp.getHeight() / 2 - 8;

		if (states == 0 || status < 1) {
			// um só círculo, que vai mudando de cor
			switch (this.status) {
			case BAD:
				g.setColor(Color.RED);
				break;
			case NEUTRAL:
				g.setColor(Color.YELLOW);
				break;
			case OK:
				g.setColor(Color.GREEN);
				break;
			case NO:
				g.setColor(Color.GRAY);
				break;
			case GOOD:
			default:
				Color c = Color.BLUE;
				c.brighter();
				g.setColor(c);
				break;
			}
			g.fillOval(x0, y0, 16, 16);
		} else {
			// frações vermelhas e verdes...

			// tudo vermelho...
			g.setColor(Color.RED);
			g.fillOval(0, 0, 16, 16);

			// ... exceto se estiver OK (aí vira verde)
			boolean[] bs = BinaryUtils.toBooleans(status, 12, true);
			int aa = 360 / states, sa = 0;
			g.setColor(Color.GREEN);
			for (int i = 0; i < bs.length; i++) {
				if (bs[i]) {
					g.fillArc(x0, y0, 16, 16, sa, aa);
					sa += aa;
				}
			}
		}

		g.setColor(Color.BLACK);
		g.drawOval(x0, y0, 16, 16);
	}
}