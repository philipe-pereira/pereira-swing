package br.com.pereiraeng.swing.interfaces;

import java.awt.geom.Area;

/**
 * Interface que caracteriza as classes do objeto gráficos em que se pode clicar
 * em cima
 * 
 * @author Philipe PEREIRA
 *
 */
public interface Click {
	/**
	 * Retorna <code>true</code> se as coordenadas <code>x</code> e <code>y</code>
	 * do cursor do mouse estão sobre o objeto, <code>false</code> senão.
	 * 
	 * @param x coordenada <code>x</code>
	 * @param y coordenada <code>y</code>
	 * @return presença do cursor sobre o objeto
	 */
	public boolean isOn(int x, int y);

	/**
	 * Retorna a área sobre a qual pode-se clicar sobre o objeto
	 * 
	 * @return Objeto <code>Area</code> delimitando a área sobre a qual pode-se
	 *         clicar
	 */
	public Area getClickableArea();
}
