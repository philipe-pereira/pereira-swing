package br.com.pereiraeng.swing.text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JTextArea;

import br.com.pereiraeng.swing.MonoSpacedFont;

public abstract class ColumnsTextArea extends JTextArea {
	private static final long serialVersionUID = 1L;

	protected MonoSpacedFont font;

	private final Color color1, color2;

	public ColumnsTextArea(MonoSpacedFont font, int columns) {
		this(font, columns, Color.LIGHT_GRAY, Color.WHITE);
	}

	public ColumnsTextArea(MonoSpacedFont font, int columns, Color color1, Color color2) {
		this.font = font;
		this.color1 = color1;
		this.color2 = color2;

		super.setColumns(columns);

		super.setOpaque(false);
		super.setFont(this.font.getFont());
		super.setMinimumSize(new Dimension(columns * font.getWidth(), font.getHeight()));
	}

	@Override
	public void setColumns(int cardPositions) {
		super.setColumns(cardPositions);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		int lines = this.getHeight() / font.getHeight();

		int i = 0, p = getPos(0), cols = super.getColumns();
		while (p < cols) {
			// cor
			if (i % 2 == 0)
				g.setColor(color1);
			else
				g.setColor(color2);

			// próximo ponto
			i++;
			int np = getPos(i);

			// desenha retângulo
			g.fillRect(p * font.getWidth(), 0, (np - p) * font.getWidth(), lines * font.getHeight());

			p = np;
		}
		super.paintComponent(g);
	}

	/**
	 * 
	 * @param c
	 *            column index
	 * @return
	 */
	protected abstract int getPos(int c);
}
