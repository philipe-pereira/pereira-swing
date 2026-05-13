package br.com.pereiraeng.swing.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTree;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.icons.PereiraIcon;
import br.com.pereiraeng.swing.SwingUtils;

/**
 * Classe do objeto gráfico do botão que ao ser clicado expande a árvore
 * 
 * @author Philipe PEREIRA
 *
 */
public class ExpandTree extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTree tree;

	/**
	 * Contrutor do botão que ao ser clicado expande a árvore
	 * 
	 * @param tree árvore a ser expandida
	 */
	public ExpandTree(JTree tree) {
		super(PereiraIcon.EXPAND.create());
		setToolTipText("Expandir árvore");
		setPreferredSize(Icons.DIM_BUTTON_SMALL_ICON);
		addActionListener(this);
		this.tree = tree;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		SwingUtils.expandTree(tree);
	}
}
