package br.com.pereiraeng.swing.interfaces;

/**
 * Interface que designa um objeto que pode ser desenhado
 * 
 * @author Philipe Pereira
 */
public interface De {
	/**
	 * Função na qual se determina se o objeto deve ser desenhado ou não
	 * 
	 * @param drawable <code>true</code> se o objeto deve ser desenhado,
	 *                 <code>false</code> senão
	 */
	public void setDrawable(boolean drawable);

	/**
	 * Função que indica se o objeto deve ser desenhado ou não
	 * 
	 * @return <code>true</code> se o objeto deve ser desenhado, <code>false</code>
	 *         senão
	 */
	public boolean isDrawable();
}
