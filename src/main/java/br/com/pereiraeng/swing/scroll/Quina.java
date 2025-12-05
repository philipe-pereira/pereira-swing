package br.com.pereiraeng.swing.scroll;

import java.awt.Color;
import java.awt.Graphics;

public class Quina extends Bordura {
	private static final long serialVersionUID = 1L;

	public Quina(Color color) {
		this(ESPESSURA_DEFAULT, color);
		this.setPreferredHeight(this.espessura);
	}

	public Quina(int espessura, Color color) {
		super(espessura, color);
	}

	protected void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
