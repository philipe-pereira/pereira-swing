package br.com.pereiraeng.swing.image;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabelPointer extends JLabel {
	private static final long serialVersionUID = 1L;

	private Pointer pointer;

	/**
	 * Um só círculo, que vai mudando de cor
	 * 
	 * @param initialStatus
	 *            {@link Pointer#Pointer(int)}
	 */
	public LabelPointer(int initialStatus) {
		this(initialStatus, 0);
	}

	/**
	 * Frações vermelhas e verdes
	 * 
	 * @param initialStatus
	 *            número inteiro cuja representação binária indica os estados em
	 *            verde ou vermelho
	 * @param states
	 *            número de divisões
	 */
	public LabelPointer(int initialStatus, int states) {
		this.pointer = new Pointer(initialStatus, states);
		super.setHorizontalAlignment(SwingConstants.CENTER);
		super.setPreferredSize(new Dimension(17, 17));
	}

	/**
	 * 
	 * @param status
	 *            ver {@link Pointer#setStatus(int)}
	 */
	public void setStatus(int status) {
		pointer.setStatus(status);
		repaint();
	}

	@Override
	public void setText(String text) {
		if (text.length() > 0) {
			super.setText(String.format("%c", text.charAt(0)));
			super.setToolTipText(text);
		} else
			super.setText(text);
	}

	@Override
	protected void paintComponent(Graphics g) {
		pointer.paintIcon(this, g, 0, 0);
		super.paintComponent(g);
	}
}