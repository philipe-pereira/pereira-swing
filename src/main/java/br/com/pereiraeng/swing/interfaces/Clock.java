package br.com.pereiraeng.swing.interfaces;

import java.awt.Dimension;

import br.com.pereiraeng.swing.time.TimeMotor;

/**
 * Interface dos objetos desenháveis em que é necessário saber os
 * {@link TimeMotor valores do contador cadenciado}
 * 
 * @author Philipe PEREIRA
 *
 */
public interface Clock {

	/**
	 * Função que repassa ao objeto os valores do contador
	 * 
	 * @param d objeto contendo o valor atual do contador ({@link Dimension#width
	 *          valor horizontal}) e seu valor máximo ({@link Dimension#height valor
	 *          vertical})
	 */
	public void setT(Dimension d);
}
