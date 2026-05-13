package br.com.pereiraeng.swing.time;

import br.com.pereiraeng.core.DefaultTimeMotor;
import br.com.pereiraeng.core.TimeRefresh;
import br.com.pereiraeng.swing.Leaf;

/**
 * Classe do objeto que cadencia a {@link Leaf#repaint() reimpressão} das
 * {@link Leaf telas de desenho}.
 * 
 * @author Philipe PEREIRA
 *
 */
public class TimeMotor extends DefaultTimeMotor {

	private final Leaf leaf;

	/**
	 * Construtor do objeto que cadencia a {@link Leaf#repaint() reimpressão} das
	 * {@link Leaf telas de desenho}.
	 * 
	 * @param leaf painel a ser {@link Leaf#repaint() reimpressão} periodicamente
	 */
	public TimeMotor(Leaf leaf) {
		this.leaf = leaf;
		if (leaf instanceof TimeRefresh)
			this.setTimeRefresh((TimeRefresh) leaf);
	}

	@Override
	public void stop() {
		leaf.repaint();
		super.stop();
	}

	@Override
	protected void beforeSleep() {
		leaf.repaint();
	}
}