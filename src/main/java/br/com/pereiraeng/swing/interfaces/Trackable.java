package br.com.pereiraeng.swing.interfaces;

import br.com.pereiraeng.swing.LeafOM;

/**
 * Interface das classes que precisam recuperar as coordenadas de um ponto
 * quando do deslocamento do mouse sobre um {@link LeafOM painel gráfico}.
 * Difere-se da interface {@link TakePoint} por notificar a classe interfaceada
 * pelo simples movimento do mouse (enquanto que a outra interface notifica
 * apenas quando há o clique)
 * 
 * @author Philipe PEREIRA
 *
 */
public interface Trackable {

	/**
	 * Função que é invocada quando se move o ponteiro do mouse sobre um
	 * {@link LeafOM painel gráfico}, recebendo como argumento as coordenadas dadas
	 * em unidades do gráfico
	 * 
	 * @param x abscissa do ponto nas unidades do gráfico
	 * @param y ordenada do ponto nas unidades do gráfico
	 */
	public void trackPoint(float x, float y);
}
