package br.com.pereiraeng.swing.list.filter;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Modelo de lista onde um elemento pode ser exibido ou não dependendo de suas
 * propriedades. A avaliação de tais propriedades é feita por um objeto que
 * implemente {@link ListFilter}.
 * 
 * @author Philipe PEREIRA
 *
 * @param <K>
 *            classe do objeto que será contido na lista
 */
public class FilteredListModel<K> extends DefaultListModel<K> implements ListDataListener {
	private static final long serialVersionUID = 1L;

	/**
	 * objeto que faz a triagem dos elementos da lista
	 */
	private ListFilter filter;

	/**
	 * Índice dos elementos da lista que serão exibidos
	 */
	private final ArrayList<Integer> indices;

	public FilteredListModel() {
		this(null);
	}

	public FilteredListModel(ListFilter filter) {
		this.addListDataListener(this);
		this.filter = filter;
		this.indices = new ArrayList<Integer>();
	}

	// ========================= List Model =========================

	@Override
	public int getSize() {
		return (filter != null) ? indices.size() : super.getSize();
	}

	@Override
	public K getElementAt(int index) {
		return (filter != null) ? super.getElementAt(indices.get(index)) : super.getElementAt(index);
	}

	@Override
	public void clear() {
		this.indices.clear();
		super.clear();
	}

	@Override
	public boolean removeElement(Object obj) {
		this.indices.clear();
		return super.removeElement(obj);
	}

	// ========================= Filtering =========================

	public void setFilter(ListFilter filter) {
		this.filter = filter;
	}

	/**
	 * Função que aplica o filtro especificado na lista
	 */
	public void doFilter() {
		indices.clear();

		if (this.filter != null) {
			innerChange = true;
			int count = super.getSize();
			for (int i = 0; i < count; i++) {
				if (this.filter.accept(super.getElementAt(i))) 
					indices.add(i);
			}
			fireContentsChanged(this, 0, getSize() - 1);
			innerChange = false;
		}
	}

	// =================== List Data Listener ===================

	private transient boolean innerChange = false;

	@Override
	public void intervalRemoved(ListDataEvent e) {
		if (!innerChange)
			doFilter();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		if (!innerChange)
			doFilter();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		if (!innerChange)
			doFilter();
	}
}
