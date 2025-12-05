package br.com.pereiraeng.swing.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;

public class LegendaGrad extends JLabel {
	private static final long serialVersionUID = 1L;

	private final Color min, max;

	public LegendaGrad(Color min, Color max) {
		this.min = min;
		this.max = max;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		LinearGradientPaint lgp = new LinearGradientPaint(new Point(0, 0), new Point(0, getHeight()),
				new float[] { 0f, 1f }, new Color[] { min, max });
		g2d.setPaint(lgp);
		g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		super.paintComponent(g);
	}
}