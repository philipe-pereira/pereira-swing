package br.com.pereiraeng.swing.tree;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.pereiraeng.swing.DialogBox;

public class TreeMenu extends DialogBox implements TreeSelectionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private JTree tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode rootNode;

	private JEditorPane texto;

	private JPanel options;
	private CardLayout cl;

	private boolean html;

	/**
	 * Componente na forma de uma caixa de diálogo contendo uma árvore onde cada nó,
	 * ao ser clicado, exibe um texto no formato HTML na área central
	 * 
	 * @param title
	 *            título da caixa de diálogo
	 * @param size
	 *            tamanha da caixa de diálogo
	 * @param frame
	 *            janela que contém a caixa de diálogo
	 * @param dependente
	 *            se <code>true</code>, a caixa de diálogo segura a execução do
	 *            programa até ser fechada
	 * @param root
	 *            raiz da árvore composta por <code>DefaultMutableTreeNode</code>
	 *            que tem como objetos um vetores de <code>String</code>, sendo que
	 *            o da primeira posição é o nome do nó na árvore e o da segunda
	 *            posição é o endereço da página HTML a ser exibida
	 */
	public TreeMenu(String title, Dimension size, JFrame frame, boolean dependente, DefaultMutableTreeNode root) {
		super(frame, title, size, dependente);
		this.html = true;
		this.rootNode = root;
		this.setLayout(new BorderLayout());

		this.buildTree();

		add(new JScrollPane(texto = new JEditorPane()), BorderLayout.CENTER);

		showFrame(false);
	}

	/**
	 * Componente na forma de uma caixa de diálogo contendo uma árvore onde cada nó,
	 * ao ser clicado, exibe um painel na área central
	 * 
	 * @param title
	 *            título da caixa de diálogo
	 * @param size
	 *            tamanha da caixa de diálogo
	 * @param frame
	 *            janela que contém a caixa de diálogo
	 * @param dependente
	 *            se <code>true</code>, a caixa de diálogo segura a execução do
	 *            programa até ser fechada
	 * @param root
	 *            raiz da árvore composta por <code>DefaultMutableTreeNode</code>
	 *            que tem como objetos vetores de <code>String</code>, sendo que o
	 *            da primeira posição é o nome do nó na árvore e o da segunda
	 *            posição é referência do painel a ser exibido
	 * @param panels
	 *            lista de painéis que possuem um nome que serve de referência para
	 *            serem apresentados ao se clicar no nó da árvore
	 */
	public TreeMenu(String title, Dimension size, JFrame frame, boolean dependente, DefaultMutableTreeNode root,
			ArrayList<JPanel> panels) {
		super(frame, title, size, dependente);
		this.html = false;
		this.rootNode = root;
		this.setLayout(new BorderLayout());

		this.buildTree();

		options = new JPanel(cl = new CardLayout());

		for (JPanel p : panels)
			options.add(p.getName(), p);

		add(options, BorderLayout.CENTER);

		showFrame(false);
	}

	private void buildTree() {
		treeModel = new DefaultTreeModel(rootNode);

		tree = new JTree(treeModel);
		tree.setEditable(true);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);
		tree.setCellRenderer(new ArrayTreeCellRenderer(0));
		tree.setRootVisible(false);
		add(new JScrollPane(tree), BorderLayout.WEST);
	}

	private void changePanel() {
		String s = ((String[]) ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject())[1];

		if (html)
			TreeMenu.this.setPageHTML(s);
		else
			TreeMenu.this.setPagePanel(s);
	}

	private void setPageHTML(String url) {
		try {
			texto.setPage((new File(url)).toURI().toURL());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setPagePanel(String name) {
		cl.show(options, name);
	}

	// -------------- Listener --------------

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		changePanel();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		changePanel();
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
}