package br.com.pereiraeng.swing.interfaces;

import java.util.Calendar;

public interface Time {
	/**
	 * Função de um dado objeto que implemente a interface <code>Clock</code>
	 * indicando se, num dado momento, ele deve ser desenhado ou não
	 * 
	 * @param calendar
	 *            objeto <code>Calendar</code> indicando o momento a ser
	 *            analisado
	 * @return <code>true</code> se o objeto deve ser desenhado,
	 *         <code>false</code> senão
	 */
	public boolean exist(Calendar calendar);
}
