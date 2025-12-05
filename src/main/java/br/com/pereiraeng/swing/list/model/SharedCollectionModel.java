package br.com.pereiraeng.swing.list.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;

import br.com.pereiraeng.core.collections.ListUtils;

/**
 * Classe do objeto de um modelo de lista cujos objetos estão em uma coleção
 * 
 * @author Philipe Pereira
 *
 * @param <E> objeto da lista e da coleção
 */
public class SharedCollectionModel<E> extends AbstractListModel<E> {

	private static final long serialVersionUID = 1L;

	protected Collection<E> collection;

	/**
	 * Construtor do objeto do modelo de lista cujos objetos estão em uma coleção
	 * 
	 * @param collection coleção cujos objetos serão os mesmos do modelo da lista
	 */
	public SharedCollectionModel(Collection<E> collection) {
		this.collection = collection;
	}

	/**
	 * Função que retorna a coleção que compartilha os objetos com o modelo da lista
	 * 
	 * @return coleção cujos objetos são os mesmos do modelo da lista
	 */
	public Collection<E> getCollection() {
		return this.collection;
	}

	@Override
	public int getSize() {
		return collection.size();
	}

	@Override
	public E getElementAt(int index) {
		E k = null;
		if (collection instanceof Set) {
			Set<E> set = (Set<E>) collection;
			k = ListUtils.getElementAt(set, index);
		} else if (collection instanceof List) {
			List<E> list = (List<E>) collection;
			k = list.get(index);
		}
		return k;
	}

	public E remove(int index) {
		E out = null;
		if (collection instanceof Set) {
			Set<E> set = (Set<E>) collection;
			out = (E) ListUtils.removeElementAt(set, index);
		} else if (collection instanceof List) {
			List<E> list = (List<E>) collection;
			out = list.remove(index);
		}
		fireIntervalRemoved(this, index, index);
		return out;
	}

	public int indexOf(E obj) {
		int out = -1;
		if (collection instanceof Set) {
			Set<E> set = (Set<E>) collection;
			out = ListUtils.indexOf(set, obj);
		} else if (collection instanceof List) {
			List<?> list = (List<?>) collection;
			out = list.indexOf(obj);
		}
		return out;
	}

	public E set(int index, E value) {
		E out = null;
		if (collection instanceof Set) {
			Set<E> set = (Set<E>) collection;
			out = (E) ListUtils.setElementAt(set, index, value);
		} else if (collection instanceof List) {
			List<E> list = (List<E>) collection;
			out = list.set(index, value);
		}
		fireContentsChanged(this, index, index);
		return out;
	}

	/**
	 * Removes the argument from this list.
	 * 
	 * @param obj the component to be removed
	 *
	 * @return <code>true</code> if the argument was a component of this list;
	 *         <code>false</code> otherwise
	 */
	public boolean removeElement(E obj) {
		boolean out = false;
		if (collection instanceof Set) {
			Set<E> set = (Set<E>) collection;
			out = set.remove(obj);
		} else if (collection instanceof List) {
			List<?> list = (List<?>) collection;
			out = list.remove(obj);
		}
		if (out)
			fireContentsChanged(this, 0, getSize());
		return out;
	}

	/**
	 * Função que remove todos os itens da coleção e, por consequência, do modelo da
	 * lista
	 */
	public void clear() {
		int index1 = collection.size() - 1;
		collection.clear();
		if (index1 >= 0)
			fireIntervalRemoved(this, 0, index1);
	}

	/**
	 * Função que adiciona um objeto à coleção e, por consequência, ao modelo da
	 * lista
	 * 
	 * @param obj objeto a ser adicionado
	 * @return <code>true</code> se a lista foi modificada após a inserção,
	 *         <code>false</code> se a lista permaneceu inalterada
	 */
	public boolean addElement(E obj) {
		int index = -1;

		if (collection instanceof List)
			index = collection.size();// quando é lista, já se sabe que o novo elemento vai para o final

		boolean notContains = collection.add(obj);

		if (notContains && collection instanceof Set) // quando é conjunto, só depois de adicionado (e se foi de fato
														// adicionado) que se descobre para onde foi...
			index = ListUtils.indexOf((Set<E>) collection, obj);

		if (notContains)
			fireIntervalAdded(this, index, index);
		return notContains;
	}

	/**
	 * Função que adiciona objetos à coleção e, por consequência, ao modelo da lista
	 * 
	 * @param objs coleção de objetos
	 */
	public void addAll(Collection<E> objs) {
		int index1 = -1, index2 = -1;
		int current = collection.size();

		if (collection instanceof List) {
			index1 = current;// quando é lista, já se sabe que o novo elemento vai para o final
			index2 = index1 + objs.size();
		}

		collection.addAll(objs);

		if (collection instanceof Set && current != collection.size()) {
			// quando é conjunto (e se houve alteração no número de elementos), não se sabe
			// onde cada elemento entrou, então é melhor atualizar tudo...
			index1 = 0;
			index2 = collection.size();
		}

		if (index1 > -1)
			fireIntervalAdded(this, index1, index2);
	}

	public void collectionChanged(Object src) {
		fireContentsChanged(this, 0, getSize());
	}

	// ============= NOMES ALTERNATIVOS =============

	public int size() {
		return this.getSize();
	}

	public E get(int index) {
		return this.getElementAt(index);
	}
}
