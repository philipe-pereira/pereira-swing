package br.com.pereiraeng.swing;

import java.awt.Graphics2D;

import br.com.pereiraeng.swing.interfaces.WL;

/**
 * Classe abstrata dos objetos desenhadores de grades nos gráficos
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class Grid {

	/**
	 * Função que é chamada para desenhar sobre a tela gráfica uma grade, que pode
	 * ser desde a latitutde e longitude de um mapa até as linhas de um gráfico
	 * 
	 * @param g    objeto gráfico
	 * @param leaf tela gráfica
	 */
	public void drawGrid(Graphics2D g, LeafOM<?> leaf) {
		drawGrid(g, leaf, leaf);
	}

	public abstract void drawGrid(Graphics2D g, Leaf leaf, WL wl);
}
