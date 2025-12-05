package br.com.pereiraeng.swing.input.clc;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.AbstractListModel;

import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.list.model.ListSetModel;

/**
 * Classe dos objetos gráficos que permitem a inserção de um conjunto de objetos
 * reais
 * 
 * @author Philipe PEREIRA
 *
 */
public class SetInput<K> extends CollectionInput<K, Set<K>> {
	private static final long serialVersionUID = 1L;

	public SetInput(Set<K> set) {
		super(set, set instanceof LinkedHashSet);
	}

	public SetInput(boolean insertionOrder, K defaultValue) {
		super(defaultValue, insertionOrder);
	}

	public SetInput(boolean insertionOrder, Input<K> input) {
		super(input, insertionOrder);
	}

	// ------------------------- INPUT -------------------------

	@Override
	public Set<K> get() {
		return ((ListSetModel<K>) super.model).getSet();
	}

	@Override
	public void set(Set<K> set) {
		((ListSetModel<K>) super.model).clear();
		if (set != null) {
			for (K e : set)
				((ListSetModel<K>) super.model).addElement(e);
		}
	}

	// list model

	@Override
	protected AbstractListModel<K> getListModel(Object... args) {
		return new ListSetModel<>((boolean) args[0]);
	}

	@Override
	protected K get(int selected) {
		return ((ListSetModel<K>) super.model).get(selected);
	}

	@Override
	public void addElement(K value) {
		((ListSetModel<K>) super.model).addElement(value);
	}

	@Override
	protected int indexOf(K value) {
		return ((ListSetModel<K>) super.model).indexOf(value);
	}

	@Override
	public K set(int selected, K value) {
		return ((ListSetModel<K>) super.model).set(selected, value);
	}

	@Override
	protected K removeElement(int selected) {
		return ((ListSetModel<K>) super.model).remove(selected);
	}
}