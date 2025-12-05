package br.com.pereiraeng.swing.input;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import br.com.pereiraeng.core.LocaleConfig;

/**
 * Botão que quando clicado abre uma caixa de diálogo de escolha de cores
 * 
 * @author Philipe Pereira
 * 
 */
public class ColorInput extends JButton implements Input<Color>, ActionListener {
	private static final long serialVersionUID = 1L;

	private Color color;

	public ColorInput() {
		this(null);
	}

	public ColorInput(Color color) {
		set(color);
		super.addActionListener(this);
		super.setPreferredSize(new Dimension(84, 26));
	}

	private void changeButton() {
		if (this.color != null) {
			setText("");
			setBackground(this.color);
		} else {
			setText("Escolher");
			setBackground(null);
		}
	}

	// ------------------------- INPUT -------------------------

	@Override
	public void set(Color color) {
		this.color = color;
		changeButton();
	}

	@Override
	public Color get() {
		return color;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ------------------------- LISTENER -------------------------

	private ActionListener listener;

	@Override
	public void actionPerformed(ActionEvent event) {
		this.set(JColorChooser.showDialog(this,
				LocaleConfig.getString("colorChooser"), this.color));
		if (this.listener != null)
			this.listener.actionPerformed(new ActionEvent(this, event.getID(),
					event.getActionCommand()));
	}

	@Override
	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}
}