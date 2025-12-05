package br.com.pereiraeng.swing.input;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import br.com.pereiraeng.core.LocaleConfig;


public class NodeInput extends JButton implements ActionListener, Input<DefaultMutableTreeNode> {
	private static final long serialVersionUID = 1L;

	private DefaultMutableTreeNode node;

	private boolean leaf;

	/**
	 * Construtor do objeto gráfico em que se seleciona nós de uma árvore
	 * 
	 * @param node
	 *            um dos nós da árvore (o da seleção inicial)
	 */
	public NodeInput(DefaultMutableTreeNode node) {
		this(node, true);
	}

	/**
	 * Construtor do objeto gráfico em que se seleciona nós de uma árvore
	 * 
	 * @param node
	 *            um dos nós da árvore (o da seleção inicial)
	 * @param leaf
	 *            se <code>true</code> somente folhas poderão ser selecionadas,
	 *            senão pode-se selecionar todos os nós
	 */
	public NodeInput(DefaultMutableTreeNode node, boolean leaf) {
		super.addActionListener(this);
		this.leaf = leaf;
		this.set(node);
		super.setPreferredSize(new Dimension(84, 26));
	}

	private void changeButton() {
		Object o = this.get().getUserObject();
		if (o != null) {
			setText(o.toString());
		} else
			setText(LocaleConfig.hasConfig() ? LocaleConfig.getString("select") : "Selecionar");
	}

	// ------------------------- INPUT -------------------------

	@Override
	public DefaultMutableTreeNode get() {
		return this.node;
	}

	@Override
	public void set(DefaultMutableTreeNode node) {
		this.node = node;
		this.changeButton();
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ------------------------- LISTENER -------------------------

	private ActionListener listener;

	@Override
	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JTree tree = new JTree(this.node.getRoot());
		tree.setSelectionPath(new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(node)));
		int out = JOptionPane.showConfirmDialog(this, new JScrollPane(tree), "Selecionar", JOptionPane.DEFAULT_OPTION);
		if (out == JOptionPane.OK_OPTION) {
			TreePath tp = tree.getSelectionPath();
			if (tp != null) {
				// se há algum nó selecionado
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tp.getLastPathComponent();

				if (!leaf || selectedNode.isLeaf()) {
					// se há a restrição de só escolher-se folhas, verifica-se se é nó
					this.set(selectedNode);
					if (listener != null)
						this.listener.actionPerformed(new ActionEvent(this, event.getID(), event.getActionCommand()));
				}
			}
		}
	}
}
