package br.com.pereiraeng.swing.input.mip;

import javax.swing.event.ChangeListener;

import br.com.pereiraeng.swing.input.Input;

/**
 * Interface de um objeto que recebe entradas para a criação ou edição de um
 * objeto e que está incluído dentro de uma tabela
 * 
 * @author Philipe PEREIRA
 *
 * @param <K>
 *            classe do objeto a ser criado ou editado
 */
public interface InputEmb<K> extends Input<K> {

	public void addChangeListener(ChangeListener listener);

	public void setSelected();
}
