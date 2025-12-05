package br.com.pereiraeng.swing.scroll;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class ReguaDiscreta extends Regua {
	private static final long serialVersionUID = 1L;

	private Color color2;
	private boolean trace;

	public ReguaDiscreta(boolean vertical, int tamanho, int unidade) {
		this(vertical, ESPESSURA_DEFAULT, tamanho, unidade, Color.WHITE,
				Color.BLACK, true);
	}

	public ReguaDiscreta(boolean vertical, int espessura, int tamanho,
			int unidade, Color color1, Color color2, boolean trace) {
		super(vertical, espessura, tamanho, unidade, color1);
		this.color2 = color2;
		this.trace = trace;
	}

	protected void paintComponent(Graphics g) {
		g.setColor(color);
		Dimension d = getPreferredSize();
		g.fillRect(0, 0, d.width, d.height);
		if (marcador != -1) {
			int r = marcador / super.unidade;
			g.setColor(this.color2);
			if (super.vertical) {
				g.fillRect(0, r * unidade, espessura, unidade);
				if (trace) {
					g.setColor(this.getBackground());
					int font = g.getFont().getSize() / 2;
					String n = r + "";
					g.drawString(n, this.espessura / 2 - 4 * n.length(),
							(int) ((r + 0.5f) * unidade) + font);
				}
			} else {
				g.fillRect(r * unidade, 0, unidade, espessura);
				if (trace) {
					g.setColor(this.getBackground());
					int font = g.getFont().getSize() / 2;
					String n = r + "";
					g.drawString(n,
							(int) ((r + 0.5f) * unidade) - 4 * n.length(),
							this.espessura / 2 + font);
				}
			}
		}
	}
}
