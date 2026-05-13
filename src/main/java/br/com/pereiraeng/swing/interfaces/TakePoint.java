package br.com.pereiraeng.swing.interfaces;

import java.awt.geom.Point2D;

import br.com.pereiraeng.swing.LeafOM;

/**
 * Interface das classes que precisam recuperar as coordenadas de um ponto
 * clicado sobre um {@link LeafOM painel gráfico}. Difere-se da interface
 * {@link Trackable} por precisar do clique sobre o painel (enquanto que a outra
 * interface notifica a classe interfaceada pelo simples movimento do mouse)
 * 
 * @author Philipe PEREIRA
 *
 */
public interface TakePoint {

	/**
	 * Função que é invocada quando se clica sobre um {@link LeafOM painel gráfico},
	 * recebendo como argumento as coordenadas dadas em unidades do gráfico
	 * 
	 * @param point coordenadas do ponto nas unidades do gráfico
	 */
	public void clickOver(Point2D.Float point);
}
