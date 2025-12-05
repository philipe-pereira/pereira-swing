package br.com.pereiraeng.swing.scroll;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;

public abstract class Bordura extends JComponent {
	private static final long serialVersionUID = 1L;

	protected static final int ESPESSURA_DEFAULT = 30;

	protected int espessura;
	protected Color color;

	public Bordura(int espessura, Color color) {
		this.espessura = espessura;
		this.color = color;
	}

	public void setPreferredHeight(int ph) {
		super.setPreferredSize(new Dimension(this.espessura, ph));
	}

	public void setPreferredWidth(int pw) {
		super.setPreferredSize(new Dimension(pw, this.espessura));
	}
}
