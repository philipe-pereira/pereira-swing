package br.com.pereiraeng.swing.interfaces;

import java.awt.geom.Point2D;

/**
 * Interface das classes que precisam recuperar as coordenadas de uma retângulo
 * selecionado sobre um {@link LeafO painel gráfico}
 * 
 * @author Philipe PEREIRA
 *
 */
public interface AreaSelection {

	/**
	 * Função que é chamada por uma {@link LeafO painel gráfico} sempre que uma
	 * caixa de seleção é criada
	 * 
	 * @param min coordenada, nas unidades do gráfico, do ponto de menores abscissa
	 *            e ordenada
	 * @param max coordenada, nas unidades do gráfico, do ponto de maiores abscissa
	 *            e ordenada
	 */
	public void setSelectedArea(Point2D.Float min, Point2D.Float max);

}
