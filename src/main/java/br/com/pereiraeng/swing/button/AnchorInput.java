package br.com.pereiraeng.swing.button;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.Input;

/**
 * Componente que contém quatro butões em cada uma das direções
 * 
 * @author Philipe Pereira
 * 
 */
public class AnchorInput extends Grade implements Input<Integer> {
	private static final long serialVersionUID = 1L;

	public static final String UP = "UP", DOWN = "DOWN", LEFT = "LEFT", RIGHT = "RIGHT", NW = "NW", NE = "NE",
			SW = "SW", SE = "SE";

	private JToggleButton n, s, w, e, ne, nw, se, sw;

	/**
	 * Construtor do componente onde já se adiciona aos butões um escutador para
	 * seus butões
	 */
	public AnchorInput() {
		Dimension dimension = new Dimension(24, 24);

		n = new JToggleButton(Icons.loadUtilsIcon("AlignTop24.gif"));
		n.setActionCommand(UP);
		n.setPreferredSize(dimension);
		add(n, 1, 0, 1, 1);

		s = new JToggleButton(Icons.loadUtilsIcon("AlignBottom24.gif"));
		s.setActionCommand(DOWN);
		s.setPreferredSize(dimension);
		add(s, 1, 2, 1, 1);

		w = new JToggleButton(Icons.loadUtilsIcon("AlignLeft24.gif"));
		w.setActionCommand(LEFT);
		w.setPreferredSize(dimension);
		add(w, 0, 1, 1, 1);

		e = new JToggleButton(Icons.loadUtilsIcon("AlignRight24.gif"));
		e.setActionCommand(RIGHT);
		e.setPreferredSize(dimension);
		add(e, 2, 1, 1, 1);

		ne = new JToggleButton(Icons.loadUtilsIcon("AlignNE.gif"));
		ne.setActionCommand(NE);
		ne.setPreferredSize(dimension);
		add(ne, 2, 0, 1, 1);

		nw = new JToggleButton(Icons.loadUtilsIcon("AlignNW.gif"));
		nw.setActionCommand(NW);
		nw.setPreferredSize(dimension);
		add(nw, 0, 0, 1, 1);

		se = new JToggleButton(Icons.loadUtilsIcon("AlignSE.gif"));
		se.setActionCommand(SE);
		se.setPreferredSize(dimension);
		add(se, 2, 2, 1, 1);

		sw = new JToggleButton(Icons.loadUtilsIcon("AlignSW.gif"));
		sw.setActionCommand(SW);
		sw.setPreferredSize(dimension);
		add(sw, 0, 2, 1, 1);

		ButtonGroup bg = new ButtonGroup();
		bg.add(n);
		bg.add(s);
		bg.add(w);
		bg.add(e);
		bg.add(ne);
		bg.add(nw);
		bg.add(se);
		bg.add(sw);
	}

	@Override
	public void set(Integer k) {
//		Integer current = get();
//		if (current != null) // desselecionar o atual (acho que não precisa, pois tem o ButtonGroup)
//			set(false, current);
		if (k != null) // selecionar um outro
			set(true, k);
	}

	private void set(boolean b, int p) {
		switch (p) {
		case SwingConstants.NORTH:
			n.setSelected(b);
			break;
		case SwingConstants.EAST:
			e.setSelected(b);
			break;
		case SwingConstants.SOUTH:
			s.setSelected(b);
			break;
		case SwingConstants.WEST:
			w.setSelected(b);
			break;
		case SwingConstants.NORTH_EAST:
			ne.setSelected(b);
			break;
		case SwingConstants.NORTH_WEST:
			nw.setSelected(b);
			break;
		case SwingConstants.SOUTH_WEST:
			sw.setSelected(b);
			break;
		case SwingConstants.SOUTH_EAST:
			se.setSelected(b);
			break;
		}
	}

	@Override
	public Integer get() {
		if (se.isSelected())
			return SwingConstants.SOUTH_EAST;
		else if (sw.isSelected())
			return SwingConstants.SOUTH_WEST;
		else if (ne.isSelected())
			return SwingConstants.NORTH_EAST;
		else if (nw.isSelected())
			return SwingConstants.NORTH_WEST;
		else if (n.isSelected())
			return SwingConstants.NORTH;
		else if (s.isSelected())
			return SwingConstants.SOUTH;
		else if (w.isSelected())
			return SwingConstants.WEST;
		else if (e.isSelected())
			return SwingConstants.EAST;
		else
			return null;
	}

	@Override
	public Component getComponent() {
		return this;
	}
}