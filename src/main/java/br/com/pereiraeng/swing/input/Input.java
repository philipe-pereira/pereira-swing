package br.com.pereiraeng.swing.input;

import java.awt.Component;

/**
 * Interface de um objeto que recebe entradas para a criação ou edição de um
 * objeto
 * 
 * @author Philipe Pereira
 * 
 * @param <K>
 *            classe do objeto a ser criado ou editado
 */
public interface Input<K> {

	/**
	 * Função que recebe o objeto a ser modificado
	 * 
	 * @param k
	 *            objeto a ser modificado
	 */
	public void set(K k);

	/**
	 * Função que retorna o objeto modificado
	 * 
	 * @return objeto modificado
	 */
	public K get();

	/**
	 * Função que retorna o componente a ser exibido para que o usuário nele
	 * modifique o objeto
	 * 
	 * @return <code>Component</code> a ser exibido
	 */
	public Component getComponent();
}
