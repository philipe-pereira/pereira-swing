package br.com.pereiraeng.swing.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.com.pereiraeng.core.collections.ListUtils;
import br.com.pereiraeng.core.DisplayableFields;
import br.com.pereiraeng.core.EditableFields;

/**
 * Classe do objeto do modelo de uma tabela em que cada linha da tabela é o
 * objeto contido ou numa lista ou num conjunto. É uma extensão do
 * {@link AbstractAdvTableModel modelo de tabela padrão avançado}
 * 
 * @author Philipe PEREIRA
 *
 * @param <K> objeto cujos campos estarão indicados nas colunas da tabela
 */
public class ListSetTableModel<K extends DisplayableFields> extends AbstractAdvTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnNames;

	private Collection<K> collection;

	public ListSetTableModel(String[] columnNames) {
		this();
		this.setColumnNames(columnNames);
	}

	public ListSetTableModel(String[] columnNames, Collection<K> collection) {
		this(collection);
		this.setColumnNames(columnNames);
	}

	public ListSetTableModel() {
		this(new ArrayList<K>());
	}

	public ListSetTableModel(Collection<K> collection) {
		this.collection = collection;
	}

	// setters

	private void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	private void setColumnNames(K k) {
		String[] rows = new String[k.getFieldCount()];
		for (int r = 0; r < rows.length; r++)
			rows[r] = k.getFieldName(r);
		this.setColumnNames(rows);
	}

	// ====================== MÉTODOS DE INTERFACEAMENTO ======================

	public Collection<K> getCollection() {
		return this.collection;
	}

	public void setCollection(Collection<K> objects) {
		this.collection = objects;
		fireTableDataChanged();
	}

	public boolean add(K obj) {
		int index = -1;

		if (collection instanceof List)
			index = collection.size();// quando é lista, já se sabe que o novo elemento vai para o final

		boolean notContains = collection.add(obj);

		if (notContains && collection instanceof Set) // quando é conjunto, só depois de adicionado (e se foi de fato
														// adicionado) que se descobre para onde foi...
			index = ListUtils.indexOf((Set<K>) collection, obj);

		if (columnNames == null) {
			// se for o primeiro elemento E não tiver sido adiantado as colunas, atualiza o
			// nome das colunas
			setColumnNames(obj);
			fireTableStructureChanged();
		} else
			fireTableRowsInserted(index, index);

		return notContains;
	}

	public void addAll(Collection<K> objs) {
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

		if (index1 > -1) {
			if (columnNames == null) {
				// se for o primeiro elemento E não tiver sido adiantado as colunas, atualiza o
				// nome das colunas
				setColumnNames(objs.iterator().next());
				fireTableStructureChanged();
			} else
				fireTableRowsInserted(index1, index2);
		}
	}

	public int getSize() {
		return collection.size();
	}

	public K get(int index) {
		return getElementAt(index);
	}

	public K getElementAt(int index) {
		K k = null;
		if (collection instanceof Set) {
			Set<K> set = (Set<K>) collection;
			k = ListUtils.getElementAt(set, index);
		} else if (collection instanceof List) {
			List<K> list = (List<K>) collection;
			k = list.get(index);
		}
		return k;
	}

	public int indexOf(K obj) {
		int out = -1;
		if (collection instanceof Set) {
			Set<K> set = (Set<K>) collection;
			out = ListUtils.indexOf(set, obj);
		} else if (collection instanceof List) {
			List<?> list = (List<?>) collection;
			out = list.indexOf(obj);
		}
		return out;
	}

	public K set(int index, K value) {
		K out = null;
		if (collection instanceof Set) {
			Set<K> set = (Set<K>) collection;
			out = (K) ListUtils.setElementAt(set, index, value);
		} else if (collection instanceof List) {
			List<K> list = (List<K>) collection;
			out = list.set(index, value);
		}
		fireTableRowsInserted(index, index);
		return out;
	}

	public K remove(int index) {
		K out = null;
		if (collection instanceof Set) {
			Set<K> set = (Set<K>) collection;
			out = (K) ListUtils.removeElementAt(set, index);
		} else if (collection instanceof List) {
			List<K> list = (List<K>) collection;
			out = list.remove(index);
		}
		fireTableRowsDeleted(index, index);
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
	public boolean removeElement(K obj) {
		boolean out = false;
		if (collection instanceof Set) {
			Set<K> set = (Set<K>) collection;
			out = set.remove(obj);
		} else if (collection instanceof List) {
			List<?> list = (List<?>) collection;
			out = list.remove(obj);
		}
		if (out)
			fireTableRowsDeleted(0, getSize());
		return out;
	}

	/**
	 * Usar esse método quando não se quer modificar uma lista que fora enviada pelo
	 * método {@link #setList(List)}, somente o objeto gráfico
	 */
	public void empty() {
		clear();
	}

	public void clear() {
		int index1 = collection.size() - 1;
		collection.clear();
		if (index1 >= 0)
			fireTableRowsDeleted(0, index1);
	}

	// ====================== OVERRIDES ======================

	@Override
	public String getColumnName(int columnIndex) {
		if (columnNames != null) {
			String columnName = columnNames[columnIndex];
			if (columnName == null)
				return super.getColumnName(columnIndex);
			else
				return columnName;
		} else
			return null;
	}

	@Override
	public int getColumnCount() {
		if (columnNames != null)
			return columnNames.length;
		else
			return 0;
	}

	@Override
	public int getRowCount() {
		return collection.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.get(rowIndex).getField(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		K k = this.get(rowIndex);
		if (k instanceof EditableFields) {
			EditableFields ef = (EditableFields) k;
			ef.setField(columnIndex, aValue);
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}

	public void listChanged(Object src) {
		fireTableDataChanged();
	}
}
