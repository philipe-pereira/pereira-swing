package br.com.pereiraeng.swing.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Classe do objeto gráfico tipo botão, que muda ciclicamente o texto ou ícone
 * nele exibido a medida em que é clicado
 * 
 * @author Philipe Pereira
 * 
 */
public class ChangeableButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Object[] states;
	private int selected = 0;

	/**
	 * Construtor do botão
	 * 
	 * @param states vetor com os textos a serem exibidos no botão
	 */
	public ChangeableButton(Object[] objects) {
		this.states = objects;
		super.setText(states[selected].toString());
	}

	/**
	 * Construtor do botão
	 * 
	 * @param icons vetor com os ícones a serem exibidos no botão
	 */
	public ChangeableButton(Icon[] icons) {
		this.states = icons;
		super.setIcon((Icon) states[selected]);
	}

	@Override
	public void addActionListener(ActionListener l) {
		super.addActionListener(l);
		super.addActionListener(this);
	}

	/**
	 * Função que retorna o estado atual do botão
	 * 
	 * @return inteiro representando o estado atual do botão
	 */
	public int getSelected() {
		return selected;
	}

	/**
	 * Função que retorna o próximo estado do botão
	 * 
	 * @return inteiro representando o próximo estado do botão
	 */
	public int getNext() {
		return (getSelected() + 1) % states.length;
	}

	/**
	 * Função que estabelece o estado atual do botão
	 * 
	 * @param s inteiro representando o estado
	 */
	public void setSelected(int s) {
		this.selected = s;
		Object o = states[selected];
		if (o instanceof Icon)
			setIcon((Icon) o);
		else
			setText(o.toString());
	}

	// --------------------------- LISTENERS ---------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		this.setSelected(getNext());
	}
}
