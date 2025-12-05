package br.com.pereiraeng.swing.table.treetable.model;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * An abstract implementation of the TreeTableModel interface, handling the list
 * of listeners.
 * 
 * @author Philip Milne, Philipe PEREIRA
 */
public abstract class AbstractTreeTableModel implements TreeTableModel {
	protected Object root;
	protected EventListenerList listenerList = new EventListenerList();

	protected AbstractTableModel tableModel;

	public AbstractTreeTableModel(Object root) {
		this.root = root;
	}

	public void setRoot(Object root) {
		this.root = root;
		this.fireTreeStructureChanged(root, new Object[] { root }, new int[0], new Object[0]);
	}

	//
	// Default implmentations for methods in the TreeModel interface.
	//
	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	@Override
	/**
	 * This is not called in the JTree's default mode: use a naive implementation.
	 */
	public int getIndexOfChild(Object parent, Object child) {
		for (int i = 0; i < getChildCount(parent); i++) {
			if (getChild(parent, i).equals(child))
				return i;
		}
		return -1;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(TreeModelListener.class, l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(TreeModelListener.class, l);
	}

	/**
	 * Notify all listeners that have registered interest for notification on this
	 * event type. The event instance is lazily created using the parameters passed
	 * into the fire method.
	 * 
	 * @see EventListenerList
	 * 
	 * @param source
	 * @param path
	 * @param childIndices
	 * @param children
	 */
	protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
	}

	/**
	 * Notify all listeners that have registered interest for notification on this
	 * event type. The event instance is lazily created using the parameters passed
	 * into the fire method.
	 * 
	 * @see EventListenerList
	 * 
	 * @param source
	 * @param path
	 * @param childIndices
	 * @param children
	 */
	protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
			}
		}
	}

	/**
	 * Notify all listeners that have registered interest for notification on this
	 * event type. The event instance is lazily created using the parameters passed
	 * into the fire method.
	 * 
	 * @see EventListenerList
	 * 
	 * @param source
	 * @param path
	 * @param childIndices
	 * @param children
	 */
	protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
			}
		}
	}

	/**
	 * Notify all listeners that have registered interest for notification on this
	 * event type. The event instance is lazily created using the parameters passed
	 * into the fire method.
	 * 
	 * @see EventListenerList
	 * 
	 * @param source
	 * @param path
	 * @param childIndices
	 * @param children
	 */
	protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}

	//
	// Default impelmentations for methods in the TreeTableModel interface.
	//

	@Override
	/**
	 * By default, make the column with the Tree in it the only editable one. Making
	 * this column editable causes the JTable to forward mouse and keyboard events
	 * in the Tree column to the underlying JTree.
	 */
	public boolean isCellEditable(Object node, int column) {
		return getColumnClass(column) == TreeTableModel.class;
	}

	@Override
	public void setValueAt(Object aValue, Object node, int column) {
	}

	@Override
	public void setTableModel(AbstractTableModel tableModel) {
		this.tableModel = tableModel;
	}

	@Override
	public AbstractTableModel getTableModel() {
		return tableModel;
	}

	// Left to be implemented in the subclass:

	/*
	 * public Object getChild(Object parent, int index) public int
	 * getChildCount(Object parent) public int getColumnCount() public String
	 * getColumnName(Object node, int column) public Object getValueAt(Object node,
	 * int column)
	 */

	/**
	 * Builds the parents of node up to and including the root node, where the
	 * original node is the last element in the returned array. The length of the
	 * returned array gives the node's depth in the tree.
	 * 
	 * @param node - the TreeNode to get the path for
	 * @return TreeNode[] - the path from node to the root
	 */
	public TreeNode[] getPathToRoot(TreeNode node) {
		return getPathToRoot(node, 0);
	}

	/**
	 * Builds the parents of node up to and including the root node, where the
	 * original node is the last element in the returned array. The length of the
	 * returned array gives the node's depth in the tree.
	 * 
	 * @param node  - the TreeNode to get the path for
	 * @param depth - an int giving the number of steps already taken towards the
	 *              root (on recursive calls), used to size the returned array
	 * @return an array of TreeNodes giving the path from the root to the specified
	 *         node
	 */
	protected TreeNode[] getPathToRoot(TreeNode node, int depth) {
		if (node == null) {
			if (depth == 0)
				return null;
			return new TreeNode[depth];
		}
		TreeNode[] path = getPathToRoot(node.getParent(), depth + 1);
		path[path.length - depth - 1] = node;
		return path;
	}
}
