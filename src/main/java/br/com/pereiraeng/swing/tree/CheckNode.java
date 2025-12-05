package br.com.pereiraeng.swing.tree;

import javax.swing.Icon;

/**
 * Interface que caracteriza as classes de objetos que são representados por nós
 * marcáveis numa árvore
 * 
 * @author Philipe PEREIRA
 *
 */
public interface CheckNode {
	public boolean isSelected();

	public void setSelected(boolean selected);

	public Icon getIcon();

	public String getName();

	public void setName(String name);
}
