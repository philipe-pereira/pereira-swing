package br.com.pereiraeng.swing.scroll;

import java.awt.Color;

public abstract class Regua extends Bordura {
	private static final long serialVersionUID = 1L;

	protected final boolean vertical;
	protected int unidade;

	protected int marcador = -1;

	public Regua(boolean vertical, int tamanho, int unidade) {
		this(vertical, tamanho, ESPESSURA_DEFAULT, unidade, Color.WHITE);
	}

	public Regua(boolean vertical, int espessura, int tamanho, int unidade,
			Color color) {
		super(espessura, color);
		this.vertical = vertical;
		this.unidade = unidade;
		if (this.vertical)
			setPreferredHeight(tamanho);
		else
			setPreferredWidth(tamanho);
	}

	protected void setMarcador(int marcador) {
		this.marcador = marcador;
		this.repaint();
	}
}
