package br.com.pereiraeng.swing.list.filter;

/**
 * Interface que indica que o classe possui a função necessária para avaliar se
 * um dado elemento de uma lista de nela aparecer ou não
 * 
 * @author Philipe PEREIRA
 *
 */
public interface ListFilter {
	/**
	 * Função que avalia se um dado elemento de uma lista de nela aparecer ou
	 * não
	 * 
	 * @param element
	 *            elemento da lista
	 * @return <code>true</code> se ele deve aparecer, <code>false</code> senão
	 */
	boolean accept(Object element);
}
