package br.com.pereiraeng.swing.table.treetable.model;

import java.util.HashMap;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.TreeNode;

/**
 * An abstract implementation of the TreeTableModel interface
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class AbstractDefaultTreeTableModel extends AbstractTreeTableModel {

	private Object[] columns;

	/**
	 * Construtor do modelo abstrato padrão do Subespaço para árvores-tabelas,
	 * utilizando uma árvore com nós do tipo <code>DefaultMutableTreeNode</code>
	 * 
	 * @param root    raiz da árvore, contendo toda sua estrutura
	 * @param columns lista de nomes do cabeçalho das colunas
	 */
	public AbstractDefaultTreeTableModel(TreeNode root, Object[] columns) {
		super(root);
		this.columns = columns;
	}

	/**
	 * Função que estabelece o cabeçalho das colunas da tabela-árvore. Esta função
	 * só deverá ser chamada após o modelo ser enviado ao objeto JTreeTable
	 * 
	 * @param columns vetor com os cabeçalhos das colunas
	 */
	public void setColumnIdentifiers(Object[] columns) {
		this.columns = columns;
		if (super.tableModel != null)
			super.tableModel.fireTableStructureChanged();
	}

	/**
	 * Função que estabelece altera o cabeçalho das colunas da tabela-árvore, sem
	 * mudar o número de colunas.
	 * 
	 * @param columns vetor com os cabeçalhos das colunas
	 */
	public void changeColumnIdentifiers(Object[] columns) {
		for (int i = 0; i < Math.min(columns.length, this.columns.length); i++)
			this.columns[i] = columns[i];
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int column) {
		return this.columns[column].toString();
	}

	@Override
	public Class<?> getColumnClass(int column) {
		if (column == 0)
			return TreeTableModel.class;
		else {
			if (types == null)
				return Object.class;
			else {
				Class<?> t = types.get(column);
				if (t != null)
					return t;
				else if (defaultType != null)
					return defaultType;
				else
					return Object.class;
			}
		}
	}

	@Override
	public Object getChild(Object parent, int index) {
		TreeNode node = (TreeNode) parent;
		return node.getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		TreeNode node = (TreeNode) parent;
		return node.getChildCount();
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (column == 0)
			return node;
		else
			return getValueAt((TreeNode) node, column);
	}

	/**
	 * Função que retorna os objetos a serem exibidos na tabela, com exceção da
	 * primeira coluna, onde será mostrado o nó em si
	 * 
	 * @param node   nó padrão
	 * @param column índice da coluna, inteiro maior ou igual a 1
	 * @return objeto a ser exibido numa dada posição da tabela
	 */
	public abstract Object getValueAt(TreeNode node, int column);

	// CLASSE DAS COLUNAS

	private HashMap<Integer, Class<?>> types;

	private Class<?> defaultType;

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
