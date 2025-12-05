package br.com.pereiraeng.swing.interfaces;

import java.awt.geom.Point2D;

/**
 * Interface que designa um objeto que pode ser desenhado. O local onde ele será
 * desenhado dependerá de suas coordenadas e das relações de transformação
 * definas pelo objeto {@link WL WL}.
 * 
 * @author Philipe Pereira
 * 
 */
public interface DesM extends Des {

	/**
	 * Função que faz o objeto receber as informações da localização da janela
	 * 
	 * @param wl informações da localização da janela
	 */
	public void setWL(WL wl);

	/**
	 * Função que indica se este objeto foi desenhado ou não, dada sua localização e
	 * a porção da tela que é exibida TODO acho que é isso, função escrita nos
	 * tempos sombrios...
	 * 
	 * @return <code>true</code> se foi desenhado; <code>false</code> senão
	 */
	public boolean wasDrawn();

	/**
	 * Função que retorna a menor das abscissas e a menor das ordenadas dentre os
	 * pontos que constituem o objeto, nas unidades próprias deste
	 * 
	 * @return ponto que representa o canto inferior do objeto
	 */
	public Point2D.Float getMin();

	/**
	 * Função que retorna a maior das abscissas e a maior das ordenadas dentre os
	 * pontos que constituem o objeto, nas unidades próprias deste
	 * 
	 * @return ponto que representa o canto superior do objeto
	 */
	public Point2D.Float getMax();
}
