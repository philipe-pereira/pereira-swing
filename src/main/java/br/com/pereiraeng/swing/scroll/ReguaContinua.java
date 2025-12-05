package br.com.pereiraeng.swing.scroll;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class ReguaContinua extends Regua {
	private static final long serialVersionUID = 1L;

	public ReguaContinua(boolean vertical, int tamanho, int unidade) {
		this(vertical, ESPESSURA_DEFAULT, tamanho, unidade, Color.WHITE);
	}

	public ReguaContinua(boolean vertical, int espessura, int tamanho,
			int unidade, Color color) {
		super(vertical, espessura, tamanho, unidade, color);
	}

	protected void paintComponent(Graphics g) {
		g.setColor(color);
		Dimension d = getPreferredSize();
		g.fillRect(0, 0, d.width, d.height);
		if (marcador != -1) {
			g.setColor(Color.BLACK);
			if (super.vertical)
				g.drawLine(0, marcador, super.espessura, marcador);
			else
				g.drawLine(marcador, 0, marcador, super.espessura);
		}
	}
}
