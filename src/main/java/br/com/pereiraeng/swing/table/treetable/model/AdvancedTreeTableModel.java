package br.com.pereiraeng.swing.table.treetable.model;

import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * Implementação do modelo da tabela-árvore onde os dados estão organizados numa
 * tabela de dispersão que tem como chave o objeto guardado no nó e como valor
 * um vetor que contém o conteúdo de cada coluna
 * 
 * @author Philipe PEREIRA
 *
 */
public class AdvancedTreeTableModel extends AbstractDefaultTreeTableModel {

	private Map<Object, Object[]> tableData;

	/**
	 * Construtor do modelo avançado para árvores-tabelas
	 * 
	 * @param root raiz da árvore, contendo toda sua estrutura
	 */
	public AdvancedTreeTableModel(DefaultMutableTreeNode root) {
		this(root, new String[] { "Tree" });
	}

	public AdvancedTreeTableModel(DefaultMutableTreeNode root, Object[] columns) {
		this(root, null, columns);
	}

	/**
	 * Construtor do modelo avançado para árvores-tabelas
	 * 
	 * @param root    raiz da árvore, contendo toda sua estrutura
	 * @param table   tabela de dispersão com o conteúdo da tabela, sendo que para
	 *                cada elemento {@link DefaultMutableTreeNode#getUserObject()
	 *                contido} no nó da árvore ela um vetor com n-1 posições, onde n
	 *                é o número de colunas da tabela-árvore
	 * @param columns lista de nomes do cabeçalho das colunas
	 */
	public AdvancedTreeTableModel(DefaultMutableTreeNode root, Map<Object, Object[]> table, Object[] columns) {
		super(root, columns);
		this.tableData = table;
	}

	// ------------------------------------------------------------------

	/**
	 * Função que estabelece a tabela de dispersão que guarda os dados que são
	 * apresentados na tabela
	 * 
	 * @param tableData tabela de dispersão com o conteúdo da tabela, sendo que para
	 *                  cada elemento {@link DefaultMutableTreeNode#getUserObject()
	 *                  contido} no nó da árvore ela um vetor com n-1 posições, onde
	 *                  n é o número de colunas da tabela-árvore
	 */
	public void setTableData(Map<Object, Object[]> tableData) {
		this.tableData = tableData;
		if (super.tableModel != null)
			super.tableModel.fireTableDataChanged();
	}

	/**
	 * Função que retorna a tabela de dispersão que guarda os dados que são
	 * apresentados na tabela
	 * 
	 * @return tabela de dispersão com o conteúdo da tabela, sendo que para cada
	 *         elemento {@link DefaultMutableTreeNode#getUserObject() contido} no nó
	 *         da árvore ela um vetor com n-1 posições, onde n é o número de colunas
	 *         da tabela-árvore
	 */
	public Map<Object, Object[]> getTableData() {
		return tableData;
	}

	@Override
	public Object getValueAt(TreeNode node, int column) {
		Object obj = ((DefaultMutableTreeNode) node).getUserObject();
		Object[] columns = tableData.get(obj);
		if (columns != null ? column <= columns.length : false)
			return columns[column - 1];
		else
			return null;
	}

	@Override
	public void setValueAt(Object aValue, Object node, int column) {
		if (column < 1)
			return;
		Object obj = ((DefaultMutableTreeNode) node).getUserObject();
		Object[] columns = tableData.get(obj);
		if (columns != null)
			columns[column - 1] = aValue;
		// TODO tentar achar a linha precisa (pois a coluna já se achou)
		super.tableModel.fireTableDataChanged();
	}
}
