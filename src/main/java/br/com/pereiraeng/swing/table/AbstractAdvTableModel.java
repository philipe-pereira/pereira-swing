package br.com.pereiraeng.swing.table;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 * Classe que extende o {@link AbstractTableModel modelo de tabela padrão
 * abstrato}, sendo que há duas features que a distinguem:
 * 
 * <ul>
 * <li>Possibilidade de editar ou não as colunas (no padrão é sempre editável;
 * neste, o padrão é não ser editável);</i>
 * <li>indicar explicitamente o tipo de objeto que cada uma comporta (no padrão
 * é sempre o tipo {@link Object}).</i>
 * </ul>
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class AbstractAdvTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	// ----------------- MESMA COISA QUE EM AdvancedTableModel -----------------

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
			return Object.class;
		else {
			Class<?> t = types.get(columnIndex);
			if (t != null)
				return t;
			else if (defaultType != null)
				return defaultType;
			else
				return Object.class;
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
		if (types == null)
			types = new HashMap<>();
		if (columnIndexes.length > 0) {
			for (int columnIndex : columnIndexes)
				this.types.put(columnIndex, type);
		} else
			this.defaultType = type;
	}
}
