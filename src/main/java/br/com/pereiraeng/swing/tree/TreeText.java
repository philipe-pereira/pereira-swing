package br.com.pereiraeng.swing.tree;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import br.com.pereiraeng.io.FileType;
import br.com.pereiraeng.io.IOutils;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.button.CUDpanel;
import br.com.pereiraeng.swing.button.FourDirectionsPanel;
import br.com.pereiraeng.swing.input.cod.Formatter;
import br.com.pereiraeng.swing.input.cod.Sintax;
import br.com.pereiraeng.swing.input.file.FileChooser;
import br.com.pereiraeng.swing.input.file.FileFilterAdapter;
import br.com.pereiraeng.core.collections.ArrayUtils;
import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.core.LocaleConfig;

/**
 * Componente composto de uma árvore onde cada nó, ao ser clicado, exibe um
 * texto no formato HTML no painel central
 * 
 * @author Philipe PEREIRA
 * 
 */
public class TreeText extends JPanel implements ActionListener, TreeSelectionListener {
	private static final long serialVersionUID = 1L;

	public static final String OPEN = "open;";

	public static final String SAVE = "save";

	private String folder;

	// ------------------------------------------

	private DefaultTreeModel treeModel;
	private JTree tree;

	private JTextPane text;

	private JPanel editionPane;

	/**
	 * Construtor do componente
	 * 
	 * @param cea interface de comunicação entre os aplicativos, para que seja
	 *            possível editar os arquivos HTML com o HTMLEditor
	 */
	public TreeText() {
		this.setLayout(new BorderLayout());

		// ------------------------------------

		JSplitPane p = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		this.tree = new JTree(this.treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("")));
		tree.setEditable(false);
		tree.setRootVisible(false);
		tree.setCellRenderer(new ArrayTreeCellRenderer(0));
		tree.addTreeSelectionListener(this);
		p.setLeftComponent(new JScrollPane(tree));

		p.setRightComponent(new JScrollPane(text = new JTextPane()));
		text.setEditable(false);
		p.setDividerLocation(0.5);

		add(p, BorderLayout.CENTER);

		// ------------------------------------

		editionPane = new JPanel();

		editionPane.add(new FourDirectionsPanel(this));
		editionPane.add(new CUDpanel(this));

		JButton b = new JButton(Icons.loadUtilsIcon("Open.gif"));
		b.addActionListener(this);
		b.setToolTipText("Adicionar diretório");
		b.setActionCommand("ADD");
		editionPane.add(b);

		b = new JButton("OK");
		b.addActionListener(this);
		b.setActionCommand("CLOSE");
		editionPane.add(b);

		editionPane.setVisible(false);
		add(editionPane, BorderLayout.SOUTH);
	}

	/**
	 * Função que carrega uma nova árvore ao componente
	 * 
	 * @param root   raiz da árvore
	 * @param folder diretório onde se encontram os arquivos
	 */
	public void set(DefaultMutableTreeNode root) {
		this.treeModel.setRoot(root);
		this.folder = ((String[]) root.getUserObject())[1];
	}

	/**
	 * Função que exibe ou esconde a caixa de edição da árvore
	 * 
	 * @param editable <code>true</code> para se poder fazer a edição,
	 *                 <code>false</code> para se proibir
	 */
	public void setEditionMode(boolean editable) {
		editionPane.setVisible(editable);
	}

	/**
	 * Função que adiciona arquivos à arborescência, transferindo a estrutura das
	 * pastas para a árvore
	 * 
	 * @param node nó onde será adicionado os arquivos
	 * @param file arquivos ou diretórios
	 */
	private void add(DefaultMutableTreeNode node, File file) {
		if (file.isDirectory()) {
			DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(new String[] { file.getName(), null });
			node.add(folderNode);
			for (File f : file.listFiles())
				add(folderNode, f);
		} else
			node.add(new DefaultMutableTreeNode(new String[] { file.getName(), file.getAbsolutePath()
					.substring(System.getProperty("user.dir").length() + this.folder.length() + 2) }));
	}

	// ---------------------- LISTENER ----------------------

	private ActionListener listener;

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();

		switch (action) {
		case "CLOSE":
			setEditionMode(false);
			break;
		case "ADD": // adicionar diretório de arquivos
			File folder = FileChooser.fileChooserLoad(this.folder, new FileFilterAdapter(FileType.DIRETORIO));
			if (folder != null) {
				DefaultMutableTreeNode folderNode = SwingUtils.addObject(new String[] { folder.getName(), null }, tree,
						treeModel);
				for (File f : folder.listFiles())
					add(folderNode, f);
			}
			break;
		case FourDirectionsPanel.UP:
			SwingUtils.moveNode(true, tree, treeModel);
			break;
		case FourDirectionsPanel.DOWN:
			SwingUtils.moveNode(false, tree, treeModel);
			break;
		case FourDirectionsPanel.LEFT:
			SwingUtils.becomeParent(tree, treeModel);
			break;
		case FourDirectionsPanel.RIGHT:
			SwingUtils.becomeChild(tree, treeModel);
			break;
		case CUDpanel.NEW: // novo nó e arquivo
			String name = JOptionPane.showInputDialog(this, LocaleConfig.getString("writeName"));
			// se o nome não estiver em branco ou se o usuário não apertar o botão
			// 'cancelar'
			if (!"".equals(name) && name != null) // adicionar o nó à arvore
				SwingUtils.addObject(new String[] { name, null }, tree, treeModel);
			break;
		case CUDpanel.EDIT: // editar arquivo (e talvez o nó)
			final String[] options = new String[] { "Renomear nó", "Renomear arquivo", "Modificar conteúdo",
					"Associar a um arquivo" };

			String s = (String) JOptionPane.showInputDialog(this, "Escolhe a edição a ser realizada", "Editar",
					JOptionPane.PLAIN_MESSAGE, null, options, null);

			int opt = ArrayUtils.indexOf(options, s);
			if (opt > -1) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				String[] content = (String[]) node.getUserObject();
				switch (opt) {
				case 0:
					// alterar nome do nó
					String newName = (String) JOptionPane.showInputDialog(this, LocaleConfig.getString("renameNode"),
							LocaleConfig.getString("renameNodeTitle"), JOptionPane.PLAIN_MESSAGE, null, null,
							content[0]);
					if (newName != null ? !"".equals(newName) : false)
						content[0] = newName;
					break;
				case 1: // renomear arquivo
					String filepath = this.folder + File.separatorChar + content[1];
					File f = new File(filepath);
					if (f.exists()) {
						int p = content[1].lastIndexOf(File.separatorChar) + 1, e = content[1].lastIndexOf('.');
						String oldName = content[1].substring(p, e);
						newName = (String) JOptionPane.showInputDialog(this, LocaleConfig.getString("renameFile"),
								LocaleConfig.getString("renameFileTitle"), JOptionPane.PLAIN_MESSAGE, null, null,
								oldName);

						if (newName != null ? (!"".equals(newName) ? !newName.equals(oldName) : false) : false) {
							newName = content[1].substring(0, p) + newName + content[1].substring(e);
							f.renameTo(new File(this.folder + File.separatorChar + newName));
							content[1] = newName;
						}
					} else
						JOptionPane.showMessageDialog(this,
								"<html>Não há um arquivo associado<br>a este nó para ser renomeado.</html>",
								"Arquivo inexistente", JOptionPane.ERROR_MESSAGE);
					break;
				case 2: // abrir editor
					filepath = this.folder + File.separatorChar + content[1];
					f = new File(filepath);
					if (f.exists()) {
						if (content[1].endsWith(".html")) // Para HTML, tem-se programa locais para edição
							listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
									OPEN + "constelacao.editor.html.HTMLEditor;" + filepath));
						else { // Para os demais tipos de arquivo, usa-se os programa padrão windows
							try {
								if (Desktop.isDesktopSupported()) {
									Desktop desktop = Desktop.getDesktop();
									desktop.open(f);
								}
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					} else
						JOptionPane.showMessageDialog(this,
								"<html>Não há um arquivo associado<br>a este nó para ser editado.</html>",
								"Arquivo inexistente", JOptionPane.ERROR_MESSAGE);
					break;
				case 3: // associar a um arquivo
					f = FileChooser.fileChooserLoad(this.folder, null);
					if (f != null)
						content[1] = f.getAbsolutePath()
								.substring(System.getProperty("user.dir").length() + this.folder.length() + 2);
					break;
				}
			}
			break;
		case CUDpanel.DELETE: // apagar nó (e talvez o arquivo)
			DefaultMutableTreeNode removed = SwingUtils.removeCurrentNode(tree, treeModel);
			if (removed != null) {
				File file = (new File(this.folder + File.separatorChar + ((String[]) removed.getUserObject())[1]));
				if (file.exists()) // se o usuário quiser já apagar o arquivo associado
					if (JOptionPane.showConfirmDialog(this, LocaleConfig.getString("deleteFile"),
							LocaleConfig.getString("deleteFileTitle"),
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
						file.delete();
			}
			break;
		}
		// salvar alterações
		if (listener != null)
			listener.actionPerformed(new ActionEvent(this.treeModel.getRoot(), ActionEvent.ACTION_PERFORMED, SAVE));
	}

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		// ao se selecionar um nó da árvore

		TreePath tp = event.getNewLeadSelectionPath();
		if (tp != null) {
			File file = new File(this.folder + File.separatorChar
					+ ((String[]) ((DefaultMutableTreeNode) tp.getLastPathComponent()).getUserObject())[1]);
			String extension = IOutils.getExtension(file);
			if (file.exists() && extension != null) {
				switch (extension) {
				case "html":
					try {
						if (text.getPage() != null)
							text.getDocument().putProperty(Document.StreamDescriptionProperty, null);
						text.setPage(file.toURI().toURL());
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "txt":
					text.setText(IOutils.readFile(file));
					break;
				case "tex":
					File tmpTex = new File("temp/tmpTex.html");
					if (tmpTex.exists())
						tmpTex.delete();

					// TODO IOutils.writeFile(tmpTex, LgHtml.tex2html(file));
					try {
						if (text.getPage() != null)
							text.getDocument().putProperty(Document.StreamDescriptionProperty, null);
						text.setPage(tmpTex.toURI().toURL());
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				default:
					// linguagem de marcação
					Sintax sintax = null;
					try {
						sintax = Sintax.valueOf(extension.toUpperCase());
					} catch (IllegalArgumentException e) {
					}
					if (sintax != null) {
						text.setText(IOutils.readFile2(file));
						(new Formatter(text, sintax)).format();
					} else {
						text.setText(null);
						if (JOptionPane.showConfirmDialog(this,
								"Deseja abrir este arquivo\ncom programa indicado pelo SO?", "Formato inválido",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							try {
								if (Desktop.isDesktopSupported()) {
									Desktop desktop = Desktop.getDesktop();
									desktop.open(file);
								}
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
					break;
				}
			} else
				text.setText("File not found");
		} else
			text.setText(null);
	}
}