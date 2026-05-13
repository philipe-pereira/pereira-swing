package br.com.pereiraeng.swing.button;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Caixa de butões com diferentes ícones, onde pressionando-se um botão, a que
 * estava selecionado é liberado (<strong>M</strong>ulti <strong>T</strong>oggle
 * <strong>B</strong>uttons)
 * 
 * @author Philipe Pereira
 * 
 */
public class MTBpanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private int selected = -1;

	/**
	 * Construtor do painel de butões onde os butões são distribuídos em um dado
	 * número de colunas e com os ícones do vetor
	 * 
	 * @param col   número de colunas
	 * @param icons vetor de ícones
	 */
	public MTBpanel(int col, Icon[] icons) {
		int row = icons.length / col;

		super.setLayout(new GridLayout(row, col));
		super.setPreferredSize(
				new Dimension(col * (icons[0].getIconWidth() + 4), row * (icons[0].getIconHeight() + 4)));
		super.setBorder(BorderFactory.createEtchedBorder());

		for (int i = 0; i < icons.length; i++) {
			JToggleButton b = new JToggleButton(icons[i]);
			b.addActionListener(this);
			b.setActionCommand(i + "");
			add(b);
		}
	}

	public void actionPerformed(ActionEvent event) {
		int sel = Integer.parseInt(event.getActionCommand());
		if (sel == selected)
			selected = -1;
		else {
			if (selected != -1)
				((JToggleButton) getComponent(selected)).setSelected(false);
			selected = sel;
		}
	}

	public int getSelected() {
		return selected;
	}
}