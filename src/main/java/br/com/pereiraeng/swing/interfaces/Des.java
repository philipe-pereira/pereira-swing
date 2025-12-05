package br.com.pereiraeng.swing.interfaces;

import java.awt.Graphics2D;

/**
 * Interface que designa um objeto que pode ser desenhado através do
 * {@link Graphics2D GUI do java}
 * 
 * @author Philipe Pereira
 *
 */
public interface Des extends De {

	/**
	 * Função que desenha o objeto a partir das funções contidas no objeto
	 * {@link Graphics2D} que fora enviado pelo painel de desenho.
	 * 
	 * @param g objeto {@link Graphics2D}
	 */
	public void drawObject(Graphics2D g);
}
