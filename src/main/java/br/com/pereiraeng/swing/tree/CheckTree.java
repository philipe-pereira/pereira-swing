package br.com.pereiraeng.swing.tree;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Classe do objeto gráfico que representa uma árvore cujos nós são
 * {@link JCheckBox check boxes}.
 * 
 * @author Philipe PEREIRA
 *
 */
public class CheckTree extends JTree implements MouseListener {
	private static final long serialVersionUID = 1L;

	/**
	 * <ol start="0">
	 * <li><i>ascendente</i>: ao se selecionar um nó, se seleciona também seu
	 * ancestrais. Ao se desselecionar um nó, desseleciona-se também todos os seus
	 * descendentes (i.e., se um nó está selecionado, seu pai também o está);</i>
	 * <li><i>descendente fraca</i>: ao se selecionar um nó, se seleciona também
	 * todos os seus descendentes. Ao se desselecionar um nó, desseleciona-se também
	 * seu ancestrais (i.e., se um nó está selecionado, seus descendentes também o
	 * estão);</i>
	 * <li><i>descendente forte</i>: ao se selecionar um nó, se seleciona também
	 * todos os seus descendentes. Ao se desselecionar um nó, desseleciona-se também
	 * seu ancestrais <strong>e descendentes</strong>;</i>
	 * </ol>
	 */
	private final int descendente;

	/**
	 * Construtor da árvore, com modelo de seleção <strong>descendente</strong>
	 */
	public CheckTree() {
		this(null);
	}

	/**
	 * Construtor da árvore, já indicando a raiz e com modelo de seleção
	 * <strong>descendente</strong>
	 * 
	 * @param rootNode
	 */
	public CheckTree(DefaultMutableTreeNode rootNode) {
		this(rootNode, 1);
	}

	/**
	 * 
	 * @param descendente
	 *                    <ol start="0">
	 *                    <li><i>ascendente</i>: ao se selecionar um nó, se
	 *                    seleciona também seu ancestrais. Ao se desselecionar um
	 *                    nó, desseleciona-se também todos os seus descendentes
	 *                    (i.e., se um nó está selecionado, seu pai também o
	 *                    está);</i>
	 *                    <li><i>descendente fraca</i>: ao se selecionar um nó, se
	 *                    seleciona também todos os seus descendentes. Ao se
	 *                    desselecionar um nó, desseleciona-se também seu ancestrais
	 *                    (i.e., se um nó está selecionado, seus descendentes também
	 *                    o estão);</i>
	 *                    <li><i>descendente forte</i>: ao se selecionar um nó, se
	 *                    seleciona também todos os seus descendentes. Ao se
	 *                    desselecionar um nó, desseleciona-se também seu ancestrais
	 *                    <strong>e descendentes</strong>;</i>
	 *                    </ol>
	 */
	public CheckTree(int descendente) {
		this(null, descendente);
	}

	/**
	 * 
	 * @param rootNode
	 * @param descendente
	 *                    <ol start="0">
	 *                    <li><i>ascendente</i>: ao se selecionar um nó, se
	 *                    seleciona também seu ancestrais. Ao se desselecionar um
	 *                    nó, desseleciona-se também todos os seus descendentes
	 *                    (i.e., se um nó está selecionado, seu pai também o
	 *                    está);</i>
	 *                    <li><i>descendente fraca</i>: ao se selecionar um nó, se
	 *                    seleciona também todos os seus descendentes. Ao se
	 *                    desselecionar um nó, desseleciona-se também seu ancestrais
	 *                    (i.e., se um nó está selecionado, seus descendentes também
	 *                    o estão);</i>
	 *                    <li><i>descendente forte</i>: ao se selecionar um nó, se
	 *                    seleciona também todos os seus descendentes. Ao se
	 *                    desselecionar um nó, desseleciona-se também seu ancestrais
	 *                    <strong>e descendentes</strong>;</i>
	 *                    </ol>
	 */
	public CheckTree(DefaultMutableTreeNode rootNode, int descendente) {
		super(rootNode);
		this.descendente = descendente;
		this.setModel(new DefaultTreeModel(rootNode));

		// edition
		// tem de ser false pois senão pode-se alterar o nome do nó (o que nesse
		// caso não é permitido)
		this.setEditable(false);

		// listeners
		this.addMouseListener(this);

		// renderers
		NodeRenderer nr = new NodeRenderer();
		this.setCellRenderer(nr);
		this.setCellEditor(new NodeEditor(this, nr));
	}

	/**
	 * Função que troca (ou estabelece) o nó raiz da árvore
	 * 
	 * @param root novo nó da árvore
	 */
	public void setRoot(TreeNode root) {
		DefaultTreeModel dtm = ((DefaultTreeModel) this.getModel());
		dtm.setRoot(root);
		this.repaint();
	}

	public TreeNode getRoot() {
		return (TreeNode) this.getModel().getRoot();
	}

	/**
	 * Função que desseleciona todos os nós
	 */
	public void clear() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.getRoot();
		CheckNode cn = (CheckNode) root.getUserObject();
		if (cn != null)
			cn.setSelected(false);
		changeChildrenSelection(root, false);
	}

	// ------------------------ renderers ------------------------

	private class NodeRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			Object uo = ((DefaultMutableTreeNode) value).getUserObject();
			if (uo instanceof CheckNode) {
				CheckNode n = (CheckNode) uo;

				Box b = Box.createHorizontalBox();
				b.setFocusable(false);

				JCheckBox c = new JCheckBox();
				c.setOpaque(false);
				c.setSelected(n.isSelected());
				b.add(c);

				Icon i = n.getIcon();
				if (i != null)
					b.add(new JLabel(i));

				b.add(new JLabel(n.getName() + " "));

				if (selected)
					b.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_MITER, 1f, new float[] { 2f, 2f }, 0f)));

				return b;
			} else
				return new JLabel(value.toString());
		}
	}

	private class NodeEditor extends DefaultTreeCellEditor {
		private CheckNode n;

		public NodeEditor(JTree tree, DefaultTreeCellRenderer dtcr) {
			super(tree, dtcr);
		}

		@Override
		public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
				boolean leaf, int row) {
			Object uo = ((DefaultMutableTreeNode) value).getUserObject();
			if (uo instanceof CheckNode) {
				n = (CheckNode) uo;
				return super.getTreeCellEditorComponent(tree, n.getName(), isSelected, expanded, leaf, row);
			} else {
				return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
			}
		}

		@Override
		public Object getCellEditorValue() {
			n.setName(super.getCellEditorValue().toString());
			return n;
		}
	}

	// --------------------- listeners ---------------------

	@Override
	public void mouseClicked(MouseEvent event) {
		TreePath tp = this.getPathForLocation(event.getX(), event.getY());

		if (tp != null) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
			int x = event.getX();
			int marge = tp.getPathCount();

			if (x < 20 * marge + 1) {
				CheckNode cn = (CheckNode) node.getUserObject();
				boolean newState = !cn.isSelected();
				// troca o estado deste nó
				cn.setSelected(newState);

				if (descendente == 2) {
					changeChildrenSelection(node, newState);
					if (!newState)
						changeParentsSelection(node, newState);
				} else {
					if (newState ^ (descendente == 1)) // e o dos pais
						changeParentsSelection(node, newState);
					else // ou dos filhos
						changeChildrenSelection(node, newState);
				}

				if (hasChangeListener)
					fireChangePerformed(new ChangeEvent(this));
			}
		}

		this.repaint();
	}

	private void changeParentsSelection(TreeNode node, boolean state) {
		TreeNode d = node.getParent();
		if (d != null) {
			CheckNode cn = (CheckNode) ((DefaultMutableTreeNode) d).getUserObject();
			if (cn != null)
				cn.setSelected(state);
			changeParentsSelection(d, state);
		}
	}

	private void changeChildrenSelection(DefaultMutableTreeNode node, boolean state) {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			CheckNode cn = (CheckNode) child.getUserObject();
			if (cn != null)
				cn.setSelected(state);
			changeChildrenSelection(child, state);
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}

	private transient boolean hasChangeListener = false;

	public void addChangeListener(ChangeListener changeListener) {
		if (changeListener != null) {
			super.listenerList.add(ChangeListener.class, changeListener);
			this.hasChangeListener = true;
		}
	}

	protected void fireChangePerformed(ChangeEvent event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying those that are interested in
		// this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				ChangeListener cl = (ChangeListener) listeners[i + 1];
				cl.stateChanged(event);
			}
		}
	}
}