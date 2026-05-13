package br.com.pereiraeng.swing.interfaces;

import br.com.pereiraeng.swing.LeafOM;

/**
 * Interface de localização da janela, que permite a um objeto do tipo
 * {@link DesM DesM} calcular suas próprias coordenadas dentro do componente
 * {@link LeafOM LeafOM}.
 * 
 * @author Philipe PEREIRA
 * 
 */
public interface WL {

	/**
	 * Função que retorna a posição horizontal do canto da janela, em unidades do
	 * desenho
	 * 
	 * @return coordenada horizontal do canto da janela
	 */
	public float getX0();

	/**
	 * Função que retorna a largura da janela, em unidades do desenho
	 * 
	 * @return largura da janela
	 */
	public float getDx();

	/**
	 * Função que retorna a posição vertical do canto da janela, em unidades do
	 * desenho
	 * 
	 * @return coordenada vertical do canto da janela
	 */
	public float getY0();

	/**
	 * Função que retorna a altura da janela, em unidades do desenho
	 * 
	 * @return altura da janela
	 */
	public float getDy();

	/**
	 * Função que retorna a relação entre os pixels na direção horizontal e a
	 * unidade de medida relativa do desenho
	 * 
	 * @return pixels por unidade horizontal
	 */
	public float getPPH();

	/**
	 * Função que retorna a relação entre os pixels na direção vertical e a unidade
	 * de medida relativa do desenho
	 * 
	 * @return pixels por unidade vertical
	 */
	public float getPPV();
}
