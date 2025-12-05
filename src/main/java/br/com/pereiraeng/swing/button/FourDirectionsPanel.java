package br.com.pereiraeng.swing.button;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.Grade;

/**
 * Componente que contém quatro butões em cada uma das direções
 * 
 * @author Philipe Pereira
 * 
 */
public class FourDirectionsPanel extends Grade {
	private static final long serialVersionUID = 1L;

	public static final String UP = "UP", DOWN = "DOWN", LEFT = "LEFT", RIGHT = "RIGHT";

	private JButton up, down, left, right;

	/**
	 * Construtor do componente
	 */
	public FourDirectionsPanel() {
		this(null);
	}

	/**
	 * Construtor do componente onde já se adiciona aos butões um escutador para
	 * seus butões
	 * 
	 * @param listener objeto <code>ActionListener</code> para os butões
	 */
	public FourDirectionsPanel(ActionListener listener) {
		Dimension dimension = new Dimension(24, 24);
		up = new JButton(Icons.loadUtilsIcon("Up.gif"));
		up.setActionCommand(UP);
		up.setPreferredSize(dimension);
		add(up, 1, 0, 1, 1);

		down = new JButton(Icons.loadUtilsIcon("Down.gif"));
		down.setActionCommand(DOWN);
		down.setPreferredSize(dimension);
		add(down, 1, 2, 1, 1);

		left = new JButton(Icons.loadUtilsIcon("Previous.gif"));
		left.setActionCommand(LEFT);
		left.setPreferredSize(dimension);
		add(left, 0, 1, 1, 1);

		right = new JButton(Icons.loadUtilsIcon("Next.gif"));
		right.setActionCommand(RIGHT);
		right.setPreferredSize(dimension);
		add(right, 2, 1, 1, 1);

		if (listener != null)
			addActionListener(listener);
	}

	/**
	 * Função que adiciona um <code>ActionListener</code> a todos os quatro butões
	 * 
	 * @param listener escutador a ser adicionado
	 */
	public void addActionListener(ActionListener listener) {
		up.addActionListener(listener);
		down.addActionListener(listener);
		left.addActionListener(listener);
		right.addActionListener(listener);
	}
}