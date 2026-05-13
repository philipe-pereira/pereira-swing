package br.com.pereiraeng.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pereiraeng.core.TimeRefresh;
import br.com.pereiraeng.swing.time.TimeMotor;

/**
 * Painel delimitando uma área de desenho. Faz parte do grupo
 * <strong>B</strong>lack<strong>b</strong>oar<strong>d</strong>
 * 
 * @author Philipe Pereira
 * 
 */
public abstract class Leaf extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da tela de desenhos
	 * 
	 * @param background cor de fundo do painel, visível somente se o parâmetro
	 *                   <code>opaque</code> for <code>true</code>
	 * @param width      largura do painel, em pixels
	 * @param height     altura do painel, em pixels
	 * @param opaque     se <code>true</code> o painel será opaco, sem pintado com a
	 *                   cor estipulada no primeiro argumento
	 */
	public Leaf(Color background, int width, int height, boolean opaque) {
		this.setBackground(background);
		if (width > 0 && height > 0)
			this.setPreferredSize(new Dimension(width, height));
		this.setOpaque(opaque);
		super.setBorder(new JTextField().getBorder());
	}

	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		super.paintComponent(g);

		this.draw(g);

		g.dispose();
	}

	protected abstract void draw(Graphics2D g);

	// ------------------------ CHRONOS ------------------------

	protected TimeMotor tm;

	protected void enableTimeMotor() {
		tm = new TimeMotor(this);
	}

	public void play() {
		tm.play();
	}

	public void stop() {
		tm.stop();
	}

	public void pause() {
		tm.pause();
	}

	public Calendar getNow() {
		return tm.getNow();
	}

	public void setStep(long l) {
		tm.setStep(l);
	}

	public void setStep(float f) {
		tm.setStep(f);
	}

	/**
	 * Função que estabelece qual a classe que terá a função
	 * {@link TimeRefresh#fireTimeRefresh()} chamada para cada repintura promovida
	 * pelo motor temporal
	 * 
	 * @param tr classe com a função a ser invocada a cada atualização da
	 *           {@link #leaf tela gráfica associada} ao motor temporal
	 */
	public void setTimeRefresh(TimeRefresh tr) {
		tm.setTimeRefresh(tr);
	}
}