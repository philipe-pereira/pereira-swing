package br.com.pereiraeng.swing.input;

/**
 * Entrada de dados que também serve para exposição de dados somente para
 * leitura (não permitindo, em certos momentos, fazer-se edições)
 * 
 * @author Philipe Pereira
 *
 * @param <O> classe do objeto a ser editado
 */
public interface InputUneditable<O> extends Input<O> {

	public void setEditable(boolean editable);
}
