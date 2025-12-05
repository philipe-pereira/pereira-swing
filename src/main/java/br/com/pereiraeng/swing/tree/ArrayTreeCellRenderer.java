package br.com.pereiraeng.swing.tree;

import java.awt.Component;
import java.util.Arrays;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Classe do renderizador de nó de árvores que exibe o conteúdo de um vetor
 * 
 * @author Philipe PEREIRA
 *
 */
public class ArrayTreeCellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;

	private final int pos;

	/**
	 * Construtor do renderizador que mostra {@link Arrays#toString(Object[])
	 * todo} o conteúdo do vetor
	 */
	public ArrayTreeCellRenderer() {
		this(-1);
	}

	/**
	 * Construtor do renderizador que mostra o conteúdo de uma das posições do
	 * vetor
	 * 
	 * @param pos
	 *            inteiro que indica a posição a ser mostrada
	 */
	public ArrayTreeCellRenderer(int pos) {
		this.pos = pos;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		Object obj = ((DefaultMutableTreeNode) value).getUserObject();

		if (obj instanceof Object[]) {
			if (pos < 0)
				obj = Arrays.toString((Object[]) obj);
			else
				obj = ((Object[]) obj)[pos];
		} else
			obj = obj.toString();

		return super.getTreeCellRendererComponent(tree, obj, selected, expanded, leaf, row, hasFocus);
	}
}