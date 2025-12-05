package br.com.pereiraeng.swing;

import java.awt.Component;
import java.awt.Dimension;

/**
 * <p>
 * Interface que caracteriza as aplicações do MinPot.
 * </p>
 * 
 * <p>
 * Os procedimentos de partida da aplicação chama as funções na seguinte ordem:
 * </p>
 * 
 * <ol>
 * <li>build</i>
 * <li>open</i>
 * <li>start</i>
 * </ol>
 * 
 * @author Philipe PEREIRA
 *
 */
public interface App {
	/**
	 * Função que contrói sobre um dado componente a tela do aplicativo
	 * 
	 * @param component componente sobre o qual o aplicativo é construído
	 */
	public void build(Component component);

	/**
	 * Função que retorna o título do aplicativo
	 * 
	 * @return título do aplicativo
	 */
	public String getTitle();

	/**
	 * Função que informa se a janela do aplicativo pode ser redimensionada
	 * 
	 * @return <code>true</code> se a janela pode ser redimensionada,
	 *         <code>false</code> senão
	 */
	public boolean isResizable();

	/**
	 * Função que informa se a janela do aplicativo pode ser maximizada
	 * 
	 * @return <code>true</code> se a janela pode ser maximizada, <code>false</code>
	 *         senão
	 */
	public boolean isMaximizable();

	/**
	 * Função que retorna as dimensões iniciais da janela do aplicativo
	 * 
	 * @return dimensões da janela (se for <code>null</code>, a janela terá o
	 *         tamanho da tela)
	 */
	public Dimension getWindowSize();

	/**
	 * Função que organiza a abertura de uma lista de arquivos pelo aplicativo. É
	 * executado pelo programa Principal após a função {@link #build(Component)
	 * build}.
	 * 
	 * @param file caminho do arquivo
	 */
	public void open(String file);

	/**
	 * Função que dá partida na aplicação. É executado pelo programa Principal após
	 * a função {@link #open(String) open}.
	 */
	public void start();

	/**
	 * Função que organiza o fechamento do aplicativo
	 */
	public void close();
}
