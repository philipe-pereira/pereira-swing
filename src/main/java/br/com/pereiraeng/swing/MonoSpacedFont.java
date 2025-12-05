package br.com.pereiraeng.swing;

import java.awt.Font;

/**
 * Enumeração das fontes com métrica constante (MonoSpaced)
 * 
 * @author Philipe PEREIRA
 *
 */
public enum MonoSpacedFont {
	CN12(new Font("Courier New", Font.PLAIN, 12), 7, 11), CN14(new Font("Courier New", Font.PLAIN, 14), 8, 17),
	LC14(new Font("Lucida Console", Font.PLAIN, 14), 8, 14),
	A14(new Font("Arial monospaced for SAP", Font.PLAIN, 14), 8, 17);

	private Font font;
	private int width, height;

	private MonoSpacedFont(Font font, int width, int height) {
		this.font = font;
		this.width = width;
		this.height = height;
	}

	public Font getFont() {
		return font;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
