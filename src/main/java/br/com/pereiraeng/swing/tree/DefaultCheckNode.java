package br.com.pereiraeng.swing.tree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Classe dos objetos que representam o nó de uma árvore que pode
 * {@link #getObject() abrigar um objeto} e ser selecionado ou não
 * 
 * @author Philipe PEREIRA
 *
 */
public class DefaultCheckNode implements CheckNode {

	private String name;

	private boolean selected;

	private Icon icon;

	private Object object;

	public DefaultCheckNode(Object object, boolean selected) {
		this.setObject(object);
		this.setName(object.toString());
		this.setSelected(selected);
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	// -------------------------------------------------------

	/**
	 * Reencapsulamento da árvore: os nó que antes continha um objeto, agora
	 * contém um objeto {@link DefaultCheckNode} que por sua vez
	 * {@link #setObject(Object) contém} o objeto
	 * 
	 * @param node
	 * @param defaultState
	 */
	public static void convertTree(DefaultMutableTreeNode node, boolean defaultState) {
		node.setUserObject(new DefaultCheckNode(node.getUserObject(), defaultState));
		for (int i = 0; i < node.getChildCount(); i++)
			convertTree((DefaultMutableTreeNode) node.getChildAt(i), defaultState);
	}

	public static void convertCheck(DefaultMutableTreeNode node, boolean defaultState) {
		DefaultCheckNode dcn = (DefaultCheckNode) node.getUserObject();
		if (dcn.isSelected()) {
			node.setUserObject(dcn.getObject());
			for (int i = 0; i < node.getChildCount(); i++)
				convertCheck((DefaultMutableTreeNode) node.getChildAt(i), defaultState);
		} else
			node.removeFromParent();
	}

	/**
	 * Função que deseencapsula a árvore, porém mantem somente os nós com um
	 * dado estado de seleção. <br>
	 * A função só tem sentido ser invocada com o estado <code>true</code> para
	 * árvore ascendentes e <code>false</code> para descendentes
	 * 
	 * @param node
	 * @param state
	 */
	public static void chopTree(DefaultMutableTreeNode node, boolean state) {
		// desencapsula
		DefaultCheckNode dcn = (DefaultCheckNode) node.getUserObject();
		node.setUserObject(dcn.getObject());

		for (int i = 0; i < node.getChildCount();) {
			// para cada filho
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			// pega o objeto
			dcn = (DefaultCheckNode) child.getUserObject();
			if (!dcn.isSelected() ^ state) {
				// caso o estado de seleção seja o mesmo indicado, prossegue
				chopTree(child, state);
				i++;
			} else // se não, remove o filho
				node.remove(i);
		}
	}

	/**
	 * Deseencapsulamento da árvore para uma lista, segundo um dado estado de
	 * seleção
	 * 
	 * @param root
	 * @param state
	 * @return
	 */
	public static Collection<Object> getObject(DefaultMutableTreeNode root, boolean state) {
		Collection<Object> out = new LinkedList<>();

		Stack<DefaultMutableTreeNode> arguments = new Stack<>();
		arguments.push(root);

		while (!arguments.empty()) {
			DefaultMutableTreeNode node = arguments.pop();

			// pega o objeto (caso o estado de seleção seja o mesmo indicado)
			DefaultCheckNode dcn = (DefaultCheckNode) node.getUserObject();
			if (!dcn.isSelected() ^ state)
				out.add(dcn.getObject());

			for (int i = 0; i < node.getChildCount(); i++)
				arguments.push((DefaultMutableTreeNode) node.getChildAt(i));
		}
		return out;
	}
}
