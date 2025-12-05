package br.com.pereiraeng.swing.button;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JToggleButton;

public class DDSbutton extends JToggleButton {
	private static final long serialVersionUID = 1L;

	private String d;

	public DDSbutton(char d) {
		this(String.format("%c", d));
	}

	public DDSbutton(String d) {
		super("");
		this.d = d;
		super.setFont(new Font("Courier New", Font.BOLD, 16));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(d, 12, 17);
	}
}
