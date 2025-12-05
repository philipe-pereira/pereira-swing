package br.com.pereiraeng.swing.interfaces;

import java.awt.Point;

import br.com.pereiraeng.math.Scale2DiOff;

/**
 * Interface que designa um objeto a serem desenhados numa grade
 * 
 * @author Philipe PEREIRA
 * 
 */
public interface DesG extends Des {

	/**
	 * Função que estabelece as coordenadas na grade do componente
	 * 
	 * @param x coordenada horizontal na grade
	 * @param y coordenada vertical na grade
	 */
	public void setPosition(int x, int y);

	/**
	 * Função que retorna a coordenada horizontal (descontada de um possível offset)
	 * 
	 * @return coordenada horizontal na grade
	 */
	public int getX();

	/**
	 * Função que retorna a coordenada vertical (descontada de um possível offset)
	 * 
	 * @return coordenada vertical na grade
	 */
	public int getY();

	/**
	 * Função que retorna as coordenadas do objeto (ignorando eventual offset)
	 * 
	 * @return coordenadas absolutas do objeto na grade
	 */
	public Point getLocation();

	/**
	 * Função pelo qual a tela de desenho repassa ao objeto o tamanho da grade e o
	 * deslocamento (offset) de modo que este calcule sua posição
	 * 
	 * @param grade vetor de objetos {@link Scale2DiOff} (passo da grade e o
	 *              deslocamento dos objetos - offset)
	 */
	public void setGrade(Scale2DiOff grade);
}