package br.com.pereiraeng.swing.button;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Classe do objeto gráfico que é constituído por quatro {@link JSpinner
 * spinner's}, um para cada direção. Há a possibilidade de incluir um botão na
 * posição central que permite escolher a unidade de medida relativa à grandeza
 * dos spinner's
 * 
 * @author Philipe PEREIRA
 *
 */
public class BordersChooser extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor do objeto gráfico
	 */
	public BordersChooser() {
		this(null);
	}

	/**
	 * Construtor do objeto gráfico
	 * 
	 * @param units
	 *            objetos que representam as unidades que podem ser atribuídas
	 *            aos valores dos spinner's
	 */
	public BordersChooser(Object[] units) {
		super.setLayout(new GridLayout(3, 3));

		super.setPreferredSize(new Dimension(150, 72));
		super.setBorder(BorderFactory.createEtchedBorder());

		add(new JLabel());
		add(new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)));
		add(new JLabel());

		add(new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)));
		if (units == null)
			add(new JLabel());
		else
			add(new ChangeableButton(units));

		add(new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)));

		add(new JLabel());
		add(new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)));
		add(new JLabel());
	}
}
