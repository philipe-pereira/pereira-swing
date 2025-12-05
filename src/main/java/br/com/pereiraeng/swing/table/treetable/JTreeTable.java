package br.com.pereiraeng.swing.table.treetable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import br.com.pereiraeng.swing.table.treetable.model.TreeTableModel;
import br.com.pereiraeng.swing.table.treetable.model.TreeTableModelAdapter;

/**
 * This example shows how to create a simple JTreeTable component, by using a
 * JTree as a renderer (and editor) for the cells in a particular column in the
 * JTable.
 * 
 * @author Philip Milne
 * @author Scott Violet
 */
public class JTreeTable extends JTable {
	private static final long serialVersionUID = 1L;

	protected TreeTableCellRenderer tree;

	public JTreeTable(TreeTableModel treeTableModel) {

		// Create the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(treeTableModel);

		// Install a tableModel representing the visible rows in the tree.
		super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

		// Force the JTable and JTree to share their row selection models.
		tree.setSelectionModel(new DefaultTreeSelectionModel() {
			private static final long serialVersionUID = 1L;

			// Extend the implementation of the constructor, as if:
			/* public this() */ {
				setSelectionModel(listSelectionModel);
			}
		});
		// Make the tree and table row heights the same.
		tree.setRowHeight(getRowHeight());

		/*
		 * RETIRAR ESSE COMENTÁRIO CASO SE QUEIRA MUDAR A FORMA COMO A PRIMEIRA COLUNA
		 * (A DA ÁRVORE) É RENDERIZADA
		 * 
		 * tree.setCellRenderer(new DefaultTreeCellRenderer() {
		 * 
		 * @Override public Component getTreeCellRendererComponent(JTree tree, Object
		 * value, boolean sel, boolean expanded, boolean leaf, int row, boolean
		 * hasFocus) { return super.getTreeCellRendererComponent(tree, value, sel,
		 * expanded, leaf, row, hasFocus); } });
		 */

		// Install the tree editor renderer and editor.
		setDefaultRenderer(TreeTableModel.class, tree);
		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());

		setIntercellSpacing(new Dimension(0, 0));
	}

	/**
	 * Workaround for BasicTableUI anomaly. Make sure the UI never tries to paint
	 * the editor. The UI currently uses different techniques to paint the renderers
	 * and editors and overriding setBounds() below is not the right thing to do for
	 * an editor. Returning -1 for the editing row in this case, ensures the editor
	 * is never painted.
	 */
	@Override
	public int getEditingRow() {
		return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1 : editingRow;
	}

	/**
	 * The renderer used to display the tree nodes, a JTree.
	 * 
	 * @author Philipe Pereira
	 * 
	 */
	public class TreeTableCellRenderer extends JTree implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		protected int visibleRow;

		public TreeTableCellRenderer(TreeModel model) {
			super(model);
		}

		@Override
		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, 0, w, JTreeTable.this.getHeight());
		}

		@Override
		public void paint(Graphics g) {
			g.translate(0, -visibleRow * getRowHeight());
			super.paint(g);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected)
				setBackground(table.getSelectionBackground());
			else
				setBackground(table.getBackground());

			visibleRow = row;
			return this;
		}
	}

	/**
	 * The editor used to interact with tree nodes, a JTree.
	 * 
	 * @author Philipe Pereira
	 * 
	 */
	public class TreeTableCellEditor extends TTCellEditor implements TableCellEditor {
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
			return tree;
		}
	}

	@Override
	public void repaint() {
		if (this.tree != null)
			this.tree.repaint();
		super.repaint();
	}

	// ================ MÉTODOS DE INTERFACEAMENTO ================

	/**
	 * Determines whether or not the root node from the TreeModel is visible. This
	 * is a bound property.
	 * 
	 * @param rootVisible true if the root node of the tree is to be displayed
	 */
	public void setRootVisible(boolean rootVisible) {
		this.tree.setRootVisible(rootVisible);
	}

	/**
	 * Determines whether the tree is editable. Fires a property change event if the
	 * new setting is different from the existing setting. This is a bound property.
	 * 
	 * @param flag a boolean value, true if the tree is editable
	 */
	public void setEditable(boolean flag) {
		this.tree.setEditable(flag);
	}

	/**
	 * Makes sure all the path components in path are expanded (except for the last
	 * path component) and scrolls so that the node identified by the path is
	 * displayed. Only works when this JTree is contained in a JScrollPane.
	 *
	 * @param path the TreePath identifying the node to bring into view
	 */
	public void scrollPathToVisible(TreePath path) {
		this.tree.scrollPathToVisible(path);
	}

	/**
	 * Selects the node identified by the specified path. If any component of the
	 * path is hidden (under a collapsed node), and getExpandsSelectedPaths is true
	 * it is exposed (made viewable).
	 * 
	 * @param path the TreePath specifying the node to select
	 */
	public void setSelectionPath(TreePath path) {
		this.tree.setSelectionPath(path);
	}

	/**
	 * Ensures that the node in the specified row is expanded and viewable. If row
	 * is < 0 or >= getRowCount this will have no effect.
	 * 
	 * @param row an integer specifying a display row, where 0 is the first row in
	 *            the display
	 */
	public void expandRow(int row) {
		this.tree.expandRow(row);
	}

	public JTree getTree() {
		return this.tree;
	}

	public TreeModel getTreeModel() {
		return this.tree.getModel();
	}

	public TreeTableModel getTreeTableModel() {
		return ((TreeTableModelAdapter) this.getModel()).getTreeTableModel();
	}
}
