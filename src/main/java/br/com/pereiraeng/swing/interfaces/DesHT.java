package br.com.pereiraeng.swing.interfaces;

/**
 * Interface que designa um objeto que pode ser desenhado usando uma tabela html
 * como grade. As funções desta interface indicarão a posição ({@link #getX()} e
 * {@link #getY()}) e o número de células mescladas ({@link #getW()} e
 * {@link #getH()}) de cada objeto
 * 
 * @author Philipe PEREIRA
 *
 */
public interface DesHT extends De, Comparable<DesHT> {

	/**
	 * Função que escreve num vetor o conteúdo das linhas da tabela
	 * 
	 * @param tds vetor de sequências de caracteres, onde cada posição indica uma
	 *            linha da tabela, com suas diferentes células para cada coluna (a
	 *            contagem de colunas deve ser feita nesta função, de modo que a
	 *            tabela resultante esteja conforme especificado)
	 */
	public void draw(String[] tds, int minWidthCell);

	public int getX();

	public int getY();

	public int getW();

	public int getH();

}
