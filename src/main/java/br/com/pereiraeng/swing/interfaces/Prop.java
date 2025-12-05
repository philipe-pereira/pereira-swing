package br.com.pereiraeng.swing.interfaces;

import java.awt.Dimension;

/**
 * Interface dos objetos que precisam das informações do tamanho da tela para
 * serem desenhados, sendo de alguma forma <strong>prop</strong>orcionais à tela
 * de desenho
 * 
 * @author Philipe PEREIRA
 *
 */
public interface Prop {

	/**
	 * Função que repassa ao objeto as dimensões da janela de desenho
	 * 
	 * @param d objeto contendo a largura e a altura da tela
	 */
	public void leafSize(Dimension d);
}
