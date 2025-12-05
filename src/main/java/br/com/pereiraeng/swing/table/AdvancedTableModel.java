package br.com.pereiraeng.swing.table;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 * Classe que extende o {@link DefaultTableModel modelo de tabela padrão}, sendo
 * que há duas features que a distinguem:
 * 
 * <ul>
 * <li>Possibilidade de editar ou não as colunas (no {@link DefaultTableModel
 * padrão} é sempre editável; neste, o padrão é não ser editável);</i>
 * <li>indicar {@link #setColumnClass(Class, int...) explicitamente} o tipo de
 * objeto que cada uma comporta (no padrão é sempre o tipo {@link Object}).</i>
 * </ul>
 * 
 * @author Philipe PEREIRA
 *
 */
public class AdvancedTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a default DefaultTableModel which is a table of zero columns and
	 * zero rows.
	 */
	public AdvancedTableModel() {
		this(0, 0);
	}

	/**
	 * Constructs a DefaultTableModel with rowCount and columnCount of null object
	 * values.
	 * 
	 * @param rowCount    the number of rows the table holds
	 * @param columnCount the number of columns the table holds
	 */
	public AdvancedTableModel(int rowCount, int columnCount) {
		this(rowCount, columnCount, false);
	}

	public AdvancedTableModel(int rowCount, int columnCount, boolean defaultEditability) {
		super(rowCount, columnCount);
		this.setDefaultEditability(defaultEditability);
	}

	/**
	 * Constructs a DefaultTableModel with as many columns as there are elements in
	 * columnNames and rowCount of null object values. Each column's name will be
	 * taken from the columnNames vector.
	 * 
	 * @param columnNames vector containing the names of the new columns; if this is
	 *                    null then the model has no columns
	 * @param rowCount    the number of rows the table holds
	 */
	public AdvancedTableModel(Object[] columnNames, int rowCount) {
		this(columnNames, rowCount, false);
	}

	/**
	 * Constructs a DefaultTableModel with as many columns as there are elements in
	 * columnNames and rowCount of null object values. Each column's name will be
	 * taken from the columnNames vector.
	 * 
	 * @param columnNames        vector containing the names of the new columns; if
	 *                           this is null then the model has no columns
	 * @param rowCount           the number of rows the table holds
	 * @param defaultEditability <code>true</code> for editable, <code>false</code>
	 *                           for not editable
	 */
	public AdvancedTableModel(Object[] columnNames, int rowCount, boolean defaultEditability) {
		super(columnNames, rowCount);
		this.setDefaultEditability(defaultEditability);
	}

	public AdvancedTableModel(Object[][] content, Object[] columnNames) {
		this(content, columnNames, false);
	}

	public AdvancedTableModel(Object[][] content, Object[] columnNames, boolean defaultEditability) {
		super(content, columnNames);
		this.setDefaultEditability(defaultEditability);
	}

	// ---------------- SETTERS AND GETTERS ----------------

	public Object[] getRow(int row) {
		Vector<?> v = (Vector<?>) super.dataVector.get(row);
		return v.toArray(new Object[v.size()]);
	}

	public Object[] getColumn(int column) {
		Object[] out = new Object[this.getRowCount()];
		for (int i = 0; i < this.getRowCount(); i++)
			out[i] = this.getValueAt(i, column);
		return out;
	}

	public void setValueAt(int row, Object[] newObjs) {
		int cols = Math.min(newObjs.length, getColumnCount());
		for (int i = 0; i < cols; i++)
			super.setValueAt(newObjs[i], row, i);
	}

	@SuppressWarnings("unchecked")
	public void addRow(Object[] objs, int row) {
		Vector<?> rowData = convertToVector(objs);
		int rowIndex = dataVector.size();
		dataVector.add(row, rowData);
		super.newRowsAdded(new TableModelEvent(this, rowIndex, rowIndex, -1, TableModelEvent.INSERT));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setDataVector(Object[][] data) {
		if (data == null)
			super.dataVector = new Vector<>();
		else {
			Vector<?> d = convertToVector(data);
			super.dataVector = (Vector<Vector>) d;
		}
		fireTableStructureChanged();
	}

	public void clear() {
		this.setDataVector(null);
	}

	public void removeAllRows() {
		while (this.getRowCount() > 0)
			removeRow(0);
	}

	public void removeColumn(int column) {
		columnIdentifiers.remove(column);
		for (Object row : dataVector)
			((Vector<?>) row).remove(column);
		fireTableStructureChanged();
	}

	/**
	 * Função que esvazia todas as células da tabela (mantendo o número de linhas e
	 * colunas)
	 */
	public void empty() {
		for (int i = 0; i < getRowCount(); i++)
			for (int j = 0; j < getColumnCount(); j++)
				setValueAt(null, i, j);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
	}

	public Object[] getColumnIdentifiers() {
		Vector<?> v = super.columnIdentifiers;
		return v.toArray(new Object[v.size()]);
	}

	/**
	 * Função que indica se um dado objeto está presente numa coluna ou não
	 * 
	 * @param column número da coluna
	 * @param obj    objeto procurado
	 * @return número da primeira linha que contém o objeto, ou -1 se o objeto não
	 *         estiver presente
	 */
	public int contains(int column, Object obj) {
		for (int r = 0; r < getRowCount(); r++) {
			Object rc = getValueAt(r, column);
			if (rc == null ? obj == null : rc.equals(obj))
				return r;
		}
		return -1;
	}

	// ------------------- OVERRIDES -----------------------

	private boolean defaultEditability;

	/**
	 * Conjunto de colunas que podem ser editadas
	 */
	private Set<Integer> editables;

	private Map<Integer, Class<?>> types;

	private Class<?> defaultType;

	@Override
	public boolean isCellEditable(int row, int column) {
		if (this.editables == null)
			return this.defaultEditability;
		else
			return this.editables.contains(column);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (types == null)
			return super.getColumnClass(columnIndex);
		else {
			Class<?> t = types.get(columnIndex);
			if (t != null)
				return t;
			else if (defaultType != null)
				return defaultType;
			else
				return super.getColumnClass(columnIndex);
		}
	}

	// ---------------- AUX OVERRIDES ----------------

	// EDITABILIDADE

	/**
	 * <p>
	 * Função que estabelece se a tabela pode ser editada ou não.
	 * </p>
	 * 
	 * <p>
	 * Ao se utilizar o método
	 * {@link AdvancedTableModel#setEditableColumns(int...)}, a tabela passa a ter
	 * todas as colunas ineditáveis à exceção daquelas que foram explicitamente
	 * indicadas, inutilizando esta função.
	 * </p>
	 * 
	 * @param editable <code>true</code> se todas as colunas podem ser editadas,
	 *                 <code>false</code> senão.
	 */
	public void setDefaultEditability(boolean editable) {
		this.defaultEditability = editable;
	}

	/**
	 * Função que indica se a tabela pode ser editada ou não como padrão.
	 * 
	 * @return <code>true</code> se todas as colunas podem ser editadas,
	 *         <code>false</code> senão
	 */
	public boolean getDefaultEditability() {
		return this.defaultEditability;
	}

	/**
	 * Função em que se estabelece quais colunas podem ser excepcionalmente editadas
	 * 
	 * @param columnIndex vetor com os índices das colunas editáveis
	 */
	public void setEditableColumns(int... columnIndex) {
		if (editables == null)
			editables = new HashSet<>();
		for (int c : columnIndex)
			editables.add(c);
	}

	// CLASSE DAS COLUNAS

	/**
	 * Função em que se estabelece qual classe de objeto ocupará cada uma das
	 * colunas. Se esse método não for invocado em momento algum, a classe a ser
	 * considerada é aquela definida em {@link DefaultTableModel}, que é
	 * {@link Object}.
	 * 
	 * @param type          classe dos objetos que ocuparão as colunas
	 * @param columnIndexes índices das colunas que serão ocupadas pelos objetos da
	 *                      classe indicada. Se nenhuma coluna for indicada (vetor
	 *                      vazio) então considera-se que todas as colunas terão
	 *                      objetos da mesma classe.
	 */
	public void setColumnClass(Class<?> type, int... columnIndexes) {
		if (columnIndexes.length > 0) {
			if (types == null)
				types = new HashMap<>();
			for (int columnIndex : columnIndexes)
				this.types.put(columnIndex, type);
		} else
			this.defaultType = type;
	}
}