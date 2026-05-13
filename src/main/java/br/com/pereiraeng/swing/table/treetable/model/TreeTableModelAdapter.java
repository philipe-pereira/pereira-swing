package br.com.pereiraeng.swing.table.treetable.model;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

/**
 * This is a wrapper class takes a TreeTableModel and implements the table model
 * interface. The implementation is trivial, with all of the event dispatching
 * support provided by the superclass: the AbstractTableModel.
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class TreeTableModelAdapter extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private JTree tree;
	private TreeTableModel treeTableModel;

	public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
		this.tree = tree;
		this.treeTableModel = treeTableModel;
		this.treeTableModel.setTableModel(this);

		tree.addTreeExpansionListener(new TreeExpansionListener() {
			// Don't use fireTableRowsInserted() here;
			// the selection model would get updated twice.
			public void treeExpanded(TreeExpansionEvent event) {
				fireTableDataChanged();
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				fireTableDataChanged();
			}
		});
	}

	@Override
	/**
	 * Wrappers, implementing TableModel interface.
	 */
	public int getColumnCount() {
		return treeTableModel.getColumnCount();
	}

	@Override
	public String getColumnName(int column) {
		return treeTableModel.getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return treeTableModel.getColumnClass(column);
	}

	@Override
	public int getRowCount() {
		return tree.getRowCount();
	}

	protected Object nodeForRow(int row) {
		TreePath treePath = tree.getPathForRow(row);
		return treePath.getLastPathComponent();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return treeTableModel.getValueAt(nodeForRow(row), column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return treeTableModel.isCellEditable(nodeForRow(row), column);
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		treeTableModel.setValueAt(value, nodeForRow(row), column);
	}

	public TreeTableModel getTreeTableModel() {
		return treeTableModel;
	}
}
