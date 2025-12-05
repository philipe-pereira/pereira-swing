package br.com.pereiraeng.swing.interfaces;

import java.awt.Graphics2D;

/**
 * Interface das classes dos objetos gráficos que podem ser selecionados
 * 
 * @author Philipe PEREIRA
 *
 */
public interface Selec {

	/**
	 * Função que faz com que um dado objeto gráfico tenha seu estado de seleção
	 * alterado
	 * 
	 * @param on <code>true</code> para selecionar, <code>false</code> para remover
	 *           a seleção
	 */
	public void setSelected(boolean on);

	/**
	 * Função que indica se o objeto está selecionado
	 * 
	 * @return <code>true</code> se estiver selecionado, <code>false</code> se não
	 *         estiver
	 */
	public boolean isSelected();

	/**
	 * Função que desenha a seleção
	 * 
	 * @param g
	 */
	public void drawSelection(Graphics2D g);
}
