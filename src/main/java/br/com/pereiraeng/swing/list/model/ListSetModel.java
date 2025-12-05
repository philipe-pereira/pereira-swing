package br.com.pereiraeng.swing.list.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Modelo de lista onde cada elemento é único. A maneira como se verifica a
 * unicidade se cada elemento é através da implementação de um {@link Set
 * conjunto} sendo que a ordem é definida pela {@link LinkedHashSet inserção} ou
 * pela {@link TreeSet ordem natural} dos elementos.
 * 
 * @author Philipe PEREIRA
 *
 * @param <V>
 *            classe dos elementos contidos na lista
 */
public class ListSetModel<V> extends SharedCollectionModel<V> {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor do modelo de lista onde cada elemento é único. A ordem dos
	 * elementos na lista é definida pela ordem inserção.
	 */
	public ListSetModel() {
		this(true);
	}

	/**
	 * Construtor do modelo de lista onde cada elemento é único
	 * 
	 * @param insertionOrder
	 *            se <code>true</code>, a ordem dos elementos na lista é definida
	 *            pela {@link LinkedHashSet inserção}, se <code>false</code> é pela
	 *            {@link TreeSet ordem natural} dos elementos
	 */
	public ListSetModel(boolean insertionOrder) {
		super(insertionOrder ? new LinkedHashSet<V>() : new TreeSet<V>());
	}

	/**
	 * Função que retorna o {@link Set conjunto} de itens da lista, selecionados ou
	 * não
	 * 
	 * @return conjunto de itens da lista
	 */
	public Set<V> getSet() {
		return (Set<V>) super.collection;
	}
}