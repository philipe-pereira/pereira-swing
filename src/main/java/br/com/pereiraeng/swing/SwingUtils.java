package br.com.pereiraeng.swing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import br.com.pereiraeng.swing.input.ArrayInput;
import br.com.pereiraeng.swing.input.ButtonInput;
import br.com.pereiraeng.swing.input.ColorInput;
import br.com.pereiraeng.swing.input.EventInput;
import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.input.MapInput;
import br.com.pereiraeng.swing.input.NodeInput;
import br.com.pereiraeng.swing.input.ParseableInput;
import br.com.pereiraeng.swing.input.file.FileInput;
import br.com.pereiraeng.swing.input.mtz.MatrizInput;
import br.com.pereiraeng.swing.input.time.DateInput;
import br.com.pereiraeng.swing.input.time.TimeInput;
import br.com.pereiraeng.swing.table.AdvancedTableModel;
import br.com.pereiraeng.core.LocaleConfig;
import br.com.pereiraeng.icons.Icons;

public class SwingUtils {

	public static final Dimension DIM_BUTTON_ICON = new Dimension(34, 34),
			DIM_BUTTON_SMALL_ICON = new Dimension(27, 27), DIM_BUTTON_VERT_SMALL_ICON = new Dimension(20, 20);

	public static final int SCROLL = 18;

	public static final Color[] TRAFFIC_LIGHT = { Color.GREEN, Color.YELLOW, Color.RED };

	/**
	 * Função que retorna o componente que é de uma dada classe que está entre os
	 * paineis gerenciados pelo layout {@link CardLayout}
	 * 
	 * @param panel o painel que contém os mais diversos componentes, que se
	 *              alternam com o {@link CardLayout}
	 * @param clazz classe indicada
	 * @return o componente da classe indicada
	 */
	public static Component getPanel(JPanel panel, Class<?> clazz) {
		Component out = null;
		for (Component c : panel.getComponents()) {
			if (c.getClass().equals(clazz)) {
				out = c;
				break;
			}
		}
		return out;
	}

	public static Container getPanel(JDesktopPane dp, int i) {
		return (Container) (((JInternalFrame) dp.getComponent(i)).getContentPane()).getComponent(0);
	}

	public static Container getPanel(JDialog db) {
		return (Container) ((Container) ((Container) db.getComponent(0)).getComponent(1)).getComponent(0);
	}

	public static Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	/**
	 * Função que remonta até a {@link Window janela} que contém um dado componente
	 * 
	 * @param component um dos componentes que está sendo exibido nesta janela
	 * @return a janela que contém o componente
	 */
	public static Window getWindow(Component component) {
		Window window = null;
		Component c = component;
		while (c == null ? false : !((c = c.getParent()) instanceof Window))
			;
		if (c == null)
			return null;
		window = (Window) c;
		return window;
	}

	/**
	 * Função que remonta até a {@link JInternalFrame janela interna} que contém um
	 * dado componente
	 * 
	 * @param component um dos componentes que está sendo exibido nesta janela
	 *                  interna
	 * @return a interna janela que contém o componente
	 */
	public static JInternalFrame getInternalWindow(Component component) {
		JInternalFrame f = null;
		Component c = component;
		while (c == null ? false : !((c = c.getParent()) instanceof JInternalFrame))
			;
		if (c == null)
			return null;
		f = (JInternalFrame) c;
		return f;
	}

	/**
	 * Função que retorna o {@link JScrollPane} que tem em seu {@link JViewport} um
	 * dado componente
	 * 
	 * @param component componente que foi incluído no scroll pane
	 * @return o objeto scroll pane
	 */
	public static JScrollPane getScrollPane(Component component) {
		while ((component = component.getParent()) != null)
			if (component instanceof JScrollPane)
				return (JScrollPane) component;
		return null;
	}

	/**
	 * Função que retorna o componente que está sendo exibido num painel que tem
	 * como gerenciador de layout o {@link CardLayout}
	 * 
	 * @param panel o painel que contém os mais diversos componentes, que se
	 *              alternam com o {@link CardLayout}
	 * @return o componente que está sendo exibido agora
	 */
	public static Component getShowed(JPanel panel) {
		Component out = null;
		for (Component c : panel.getComponents()) {
			if (c.isVisible()) {
				out = c;
				break;
			}
		}
		return out;
	}

	/**
	 * Função que retorna um menu de popup, estando estes separados conforme o
	 * agrupamento dos vetores, ilustrados com ícones e com o <code>String</code>
	 * action adicionado.
	 * 
	 * @param imagesFolder caminho da pasta onde se encontram os ícones dos butões
	 *                     do menu
	 * @param buttons      vetor de três dimensões de <code>String</code>, sendo a
	 *                     primeira dimensão a separação dos grupos de butões, a
	 *                     segunda os diferentes butões de um mesmo agrupamento e a
	 *                     terceira o nome do arquivo de imagem e o
	 *                     <code>String</code> action.
	 * @param listener     escutador a ser adicionado a todos os butões criados.
	 * @return o menu <code>JPopupMenu</code> criado
	 */
	public static JPopupMenu getPopup(String imagesFolder, String[][][] buttons, ActionListener listener) {
		JPopupMenu popup = new JPopupMenu();

		for (String[][] b : buttons) {
			for (String[] c : b) {
				JMenuItem mi = new JMenuItem(LocaleConfig.getString(c[1]),
						Icons.loadIcon((imagesFolder + "/" + c[0])));
				mi.setActionCommand(c[1]);
				mi.addActionListener(listener);
				popup.add(mi);
			}
			popup.addSeparator();
		}
		return popup;
	}

	public static JPanel createButtonsBar(String folder, String[] files, Dimension dim, int espacamento,
			String[] actions, ActionListener actionListener, boolean horizontal, boolean focusable) {
		JPanel panel = new JPanel(new FlowLayout());
		JButton[] botoes = new JButton[files.length];
		Box box = null;

		if (horizontal)
			box = Box.createHorizontalBox();
		else
			box = Box.createVerticalBox();

		for (int i = 0; i < files.length; i++) {
			if (files[i] != null)
				botoes[i] = new JButton(Icons.loadIcon(folder + "/" + files[i]));

			botoes[i].setVerticalTextPosition(AbstractButton.BOTTOM);
			botoes[i].setHorizontalTextPosition(AbstractButton.CENTER);
			botoes[i].setHorizontalAlignment(JButton.CENTER);
			botoes[i].setFocusable(focusable);

			if (dim != null)
				botoes[i].setPreferredSize(dim);

			botoes[i].setActionCommand(actions[i].toUpperCase());
			botoes[i].addActionListener(actionListener);

			box.add(botoes[i]);

			if (horizontal) {
				box.add(Box.createVerticalStrut(0));
				box.add(Box.createHorizontalStrut(espacamento));
			} else if (horizontal) {
				box.add(Box.createVerticalStrut(espacamento));
			}
		}
		panel.add(box);

		return panel;
	}

	/**
	 * Função que retorna os itens de uma lista suspensa
	 * 
	 * @param comboBox lista suspensa
	 * @return itens da lista suspensa
	 */
	public static Object[] getItems(JComboBox<? extends Object> comboBox) {
		Object[] out = new Object[comboBox.getItemCount()];
		for (int i = 0; i < out.length; i++)
			out[i] = comboBox.getItemAt(i);
		return out;
	}

	/**
	 * Função que mostra uma tabela em uma caixa de diálogo
	 * 
	 * @param parentComponent determines the Frame in which the dialog is displayed;
	 *                        if <code>null</code>, or if the parentComponent has no
	 *                        Frame, a default Frame is used
	 * @param title           the title string for the dialog
	 * @param header          vector containing the names of the new columns; if
	 *                        this is <code>null</code> then the model has no
	 *                        columns
	 * @param rows            list of rows
	 */
	public static void showTableOptionPane(Component parentComponent, String title, String[] header,
			List<Object[]> rows) {
		AdvancedTableModel model = new AdvancedTableModel(header, 0);
		for (Object[] row : rows)
			model.addRow(row);
		JTable t = new JTable(model);
		JScrollPane sp = new JScrollPane(t);
		sp.setPreferredSize(new Dimension(500, 200));
		JOptionPane.showMessageDialog(parentComponent, sp, title, JOptionPane.INFORMATION_MESSAGE);
	}

	// ================================= JTable =================================

	/**
	 * Função que procura na primeira coluna de uma tabela uma dada entrada
	 * 
	 * @param table tabela
	 * @param k     entrada
	 * @return número da primeira linha que possui esta entrada
	 */
	public static <K> int procv(AbstractTableModel table, K k) {
		int rows = table.getRowCount();
		for (int r = 0; r < rows; r++)
			if (k.equals(table.getValueAt(r, 0)))
				return r;
		return -1;
	}

	/**
	 * Função que procura na primeira coluna de uma tabela uma dada entrada e
	 * retorna o objeto que se entrada nesta linha e numa dada coluna
	 * 
	 * @param table tabela
	 * @param k     entrada
	 * @param col   número da dada coluna
	 * @return objeto que se encontra na coluna indicada e na linha que possui na
	 *         primeira coluna o objeto dado na entrada
	 */
	public static <K> Object procv(AbstractTableModel table, K k, int col) {
		int rows = table.getRowCount();
		for (int r = 0; r < rows; r++)
			if (k.equals(table.getValueAt(r, 0)))
				return table.getValueAt(r, col);
		return null;
	}

	/**
	 * Função que fixa o tamanho de cada uma das colunas de uma tabela determinando
	 * o valor {@link TableColumn#setMaxWidth(int) máximo} e
	 * {@link TableColumn#setMinWidth(int) mínimo} que ela pode ter
	 * 
	 * @param table  tabela cujas colunas terão as larguras estabelecidas
	 * @param widths vetor de inteiros com as larguras de cada coluna, em pixels
	 *               (caso alguma coluna tiver um valor não positivo, ela será
	 *               desconsiderada)
	 */
	public static void setColumnsWidth(JTable table, int[] widths) {
		TableColumnModel tcm = table.getColumnModel();
		int cols = Math.min(widths.length, tcm.getColumnCount());
		for (int i = 0; i < cols; i++) {
			if (widths[i] > 0) {
				tcm.getColumn(i).setMinWidth(widths[i]);
				tcm.getColumn(i).setMaxWidth(widths[i]);
			}
		}
	}

	/**
	 * Função que fixa o tamanho de todas as colunas de uma tabela determinando o
	 * valor {@link TableColumn#setMaxWidth(int) máximo} e
	 * {@link TableColumn#setMinWidth(int) mínimo} que ela pode ter
	 * 
	 * @param table tabela cujas colunas terão as larguras estabelecidas
	 * @param width largura, em pixels
	 */
	public static void setColumnsWidth(JTable table, int width) {
		if (width <= 0)
			return;
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setMinWidth(width);
			tc.setMaxWidth(width);
		}
	}

	public static void setColumnWidth(JTable table, int width, int... indexes) {
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < indexes.length; i++) {
			TableColumn tc = tcm.getColumn(indexes[i]);
			tc.setMaxWidth(width);
			tc.setMinWidth(width);
		}
	}

	public static void setColumnsMinWidth(JTable table, int[] widths) {
		TableColumnModel tcm = table.getColumnModel();
		int cols = Math.min(widths.length, tcm.getColumnCount());
		for (int i = 0; i < cols; i++) {
			if (widths[i] > 0) {
				TableColumn tc = tcm.getColumn(i);
				tc.setPreferredWidth(widths[i]);
				tc.setMinWidth(widths[i]);
			}
		}
	}

	public static void hideColumns(JTable t, int... indexes) {
		TableColumnModel tcm = t.getColumnModel();
		for (int j = 0; j < indexes.length; j++) {
			tcm.getColumn(indexes[j]).setMinWidth(0);
			tcm.getColumn(indexes[j]).setMaxWidth(0);
		}
	}

	/**
	 * Função que converte o modelo de tabela para uma lista de vetores
	 * 
	 * @param data modelo padrão da tabela
	 * @return lista de vetores (a lista tem o mesmo número de linhas da tabela e
	 *         cada vetor tem o número de posições igual ao de colunas)
	 */
	public static List<Object[]> table2list(DefaultTableModel data) {
		int rows = data.getRowCount();
		int cols = data.getColumnCount();

		List<Object[]> out = new ArrayList<>(rows);
		for (int i = 0; i < rows; i++) {
			Object[] objs = new Object[cols];
			for (int j = 0; j < cols; j++)
				objs[j] = data.getValueAt(i, j);
			out.add(objs);
		}
		return out;
	}

	/**
	 * Função que retorna uma das colunas do modelo de tabela na forma de um vetor
	 * de objetos
	 * 
	 * @param data   modelo padrão da tabela
	 * @param column índice da coluna
	 * @return vetor de objetos da coluna
	 */
	public static Object[] getColumn(DefaultTableModel data, int column) {
		int rows = data.getRowCount();
		Object[] out = new Object[rows];
		for (int i = 0; i < rows; i++)
			out[i] = data.getValueAt(i, column);
		return out;
	}

	/**
	 * Função que exclui uma coluna de um {@link DefaultTableModel modelo de tabela}
	 * e desloca o conteúdo da tabela preenchendo o espaço criado
	 * 
	 * @param model modelo da tabela
	 * @param col   número da coluna
	 */
	public static void removeColumn(DefaultTableModel model, int col) {
		if (col >= model.getColumnCount())
			return;

		// desloca colunas
		for (int c = col + 1; c < model.getColumnCount(); c++)
			for (int r = 0; r < model.getRowCount(); r++)
				model.setValueAt(model.getValueAt(r, c), r, c - 1);

		// novo cabeçalho
		String[] cols = new String[model.getColumnCount() - 1];
		int i = 0;
		for (int j = 0; j < model.getColumnCount(); j++)
			if (j != col)
				cols[i++] = model.getColumnName(j);

		// corrige o número de colunas E troca o cabeçalho
		model.setColumnIdentifiers(cols);
	}

	public static void insertColumn(DefaultTableModel model, int col, String[] cells) {
		model.setColumnCount(model.getColumnCount() + 1);

		// desloca colunas
		for (int c = col; c < model.getColumnCount() - 1; c++)
			for (int r = 0; r < model.getRowCount(); r++)
				model.setValueAt(model.getValueAt(r, c), r, c + 1);

		// novo cabeçalho
		String[] cols = new String[model.getColumnCount()];
		int i = 0;
		for (int j = 0; j < model.getColumnCount(); j++)
			if (j != col)
				cols[i++] = model.getColumnName(j);

		// corrige o número de colunas E troca o cabeçalho
		model.setColumnIdentifiers(cols);
	}

	public static String table2tabulation(TableModel table) {
		StringBuilder out = new StringBuilder();

		int cols = table.getColumnCount();
		for (int c = 0; c < cols; c++) {
			out.append(c == 0 ? "" : "\t");
			out.append(table.getColumnName(c));
		}
		out.append("\n");

		int rows = table.getRowCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				Object obj = table.getValueAt(r, c);
				String s = null;
				if (obj instanceof Float)
					s = String.format("%g", (Float) obj);
				else if (obj instanceof Double)
					s = String.format("%g", (Double) obj);
				else
					s = obj.toString();
				out.append(c == 0 ? "" : "\t");
				out.append(s);
			}
			out.append("\n");
		}

		return out.toString();
	}

	public static void copyTable(TableModel table) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(table2tabulation(table)),
				null);
	}

	/**
	 * Função que cola o conteúdo de uma tabela no formato de separação '\t' e '\n'
	 * num {@link DefaultTableModel modelo de tabela}, convertendo o conteúdo em
	 * número decimais
	 * 
	 * @param paste string no formato da tabela
	 * @param table tabela alvo
	 * @param redim se <code>true</code> a tabela será redimensionada
	 */
	public static void pasteTable(String paste, DefaultTableModel table, boolean redim) {
		String[] rows = paste.split("\n");

		// se puder, redimensiona a tabela
		if (redim)
			table.setRowCount(rows.length);

		for (int i = 0; i < rows.length; i++) {
			String[] cells = rows[i].split("\t");

			// se puder (e precisar...), redimensiona a tabela
			if (redim && cells.length > table.getColumnCount())
				table.setColumnCount(cells.length);

			for (int j = 0; j < cells.length; j++) {
				double d = Double.NaN;
				try {
					d = Double.parseDouble(cells[j].replaceAll(",", "."));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				table.setValueAt(d, i, j);
			}
		}
	}

	public static JTable getSelectedTable(ListSelectionEvent event) {
		ListSelectionListener[] list = ((DefaultListSelectionModel) event.getSource())
				.getListeners(ListSelectionListener.class);
		for (int i = 0; i < list.length; i++)
			if (list[i] instanceof JTable)
				return (JTable) list[i];
		return null;
	}

	// ================================= JToolBar =================================

	/**
	 * Função que retorna uma barra de butões, estando estes separados conforme o
	 * agrupamento dos vetores, ilustrados com ícones (extraída na biblioteca
	 * utilitária) e com o <code>String</code> action adicionado.
	 * 
	 * @param buttons  vetor de três dimensões de <code>String</code>, sendo a
	 *                 primeira dimensão a separação dos grupos de butões, a segunda
	 *                 os diferentes butões de um mesmo agrupamento e a terceira:
	 *                 <ol start="0">
	 *                 <li>o nome do arquivo de imagem (ou o texto do botão);</i>
	 *                 <li>o comando de ação;</i>
	 *                 <li>(opcional) tool tip.</i>
	 *                 </ol>
	 * @param listener escutador a ser adicionado a todos os butões criados.
	 * @return a barra <code>JToolBar</code> criada
	 */
	public static JToolBar getBar(String[][][] buttons, ActionListener listener) {
		return getBar(Icons.UTILS_ICON_PATH, buttons, listener);
	}

	/**
	 * Função que retorna uma barra de butões, estando estes separados conforme o
	 * agrupamento dos vetores, ilustrados com ícones e com o <code>String</code>
	 * command action adicionado.
	 * 
	 * @param imagesFolder caminho da pasta onde se encontram os ícones dos butões
	 * @param buttons      vetor de três dimensões de <code>String</code>, sendo a
	 *                     primeira dimensão a separação dos grupos de butões, a
	 *                     segunda os diferentes butões de um mesmo agrupamento e a
	 *                     terceira:
	 *                     <ol start="0">
	 *                     <li>o nome do arquivo de imagem (ou o texto do
	 *                     botão);</i>
	 *                     <li>o comando de ação;</i>
	 *                     <li>(opcional) tool tip.</i>
	 *                     </ol>
	 * @param listener     escutador a ser adicionado a todos os butões criados.
	 * @return a barra <code>JToolBar</code> criada
	 */
	public static JToolBar getBar(String imagesFolder, String[][][] buttons, ActionListener listener) {
		JToolBar bar = new JToolBar();

		if (imagesFolder.length() > 0 ? !imagesFolder.endsWith("/") : false)
			imagesFolder += "/";

		for (String[][] group : buttons) {
			if (bar.getComponentCount() != 0)
				bar.addSeparator();
			for (String[] buttonData : group) {
				JButton button = new JButton();
				if (buttonData[0].endsWith(".gif") || buttonData[0].endsWith(".png")
						|| buttonData[0].endsWith(".jpg")) {
					String str = imagesFolder + buttonData[0];
					ImageIcon ii = Icons.loadIcon(str);
					if (ii != null)
						button.setIcon(ii);
				} else {
					button.setText(buttonData[0]);
					button.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
				}
				button.setActionCommand(buttonData[1]);
				if (buttonData.length > 2)
					button.setToolTipText(buttonData[2]);
				button.addActionListener(listener);
				bar.add(button);
			}
		}
		return bar;
	}

	// ================================= JTree =================================

	/**
	 * Função que expande a árvore, exibindo todos os seus nós
	 * 
	 * @param tree árvore a ser expandida
	 */
	public static void expandTree(JTree tree) {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

	public static DefaultMutableTreeNode addObject(Object child, JTree tree, DefaultTreeModel treeModel) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();

		if (parentPath == null)
			parentNode = (DefaultMutableTreeNode) treeModel.getRoot();
		else
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());

		return addObject(parentNode, child, true, tree, treeModel);
	}

	public static DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible,
			JTree tree, DefaultTreeModel treeModel) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null)
			parent = (DefaultMutableTreeNode) treeModel.getRoot();

		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		if (shouldBeVisible)
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));

		return childNode;
	}

	public static void moveNode(boolean up, JTree tree, DefaultTreeModel treeModel) {
		moveNode(null, up, tree, treeModel);
	}

	public static void moveNode(DefaultMutableTreeNode node, boolean up, JTree tree, DefaultTreeModel treeModel) {
		if (node == null) {
			TreePath currentSelection = tree.getSelectionPath();
			node = currentSelection != null ? (DefaultMutableTreeNode) (currentSelection.getLastPathComponent()) : null;
			if (node == null)
				return;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
		int position = parent.getIndex(node);

		if ((position > 0 && up) || (position < parent.getChildCount() - 1 && !up)) {
			// remove de uma posição
			treeModel.removeNodeFromParent(node);

			// reinsere em outra
			treeModel.insertNodeInto(node, parent, position + (up ? -1 : 1));

			// visibilidade
			TreePath ntp = new TreePath(treeModel.getPathToRoot(node));
			tree.scrollPathToVisible(ntp);
			tree.setSelectionPath(ntp);
		}
	}

	public static DefaultMutableTreeNode becomeChild(JTree tree, DefaultTreeModel treeModel) {
		return becomeChild(null, tree, treeModel);
	}

	public static DefaultMutableTreeNode becomeChild(DefaultMutableTreeNode node, JTree tree,
			DefaultTreeModel treeModel) {
		if (node == null) {
			TreePath currentSelection = tree.getSelectionPath();
			node = currentSelection != null ? (DefaultMutableTreeNode) (currentSelection.getLastPathComponent()) : null;
			if (node == null)
				return null;
		}

		TreeNode parent = node.getParent();
		int position = parent.getIndex(node);
		if (position > 0) {
			DefaultMutableTreeNode newParentNode = (DefaultMutableTreeNode) parent.getChildAt(position - 1);
			tree.setSelectionPath(null);

			// remove o nó
			treeModel.removeNodeFromParent(node);

			// reinsere como filho de seu irmão
			newParentNode.add(node);
			int[] insertedIndex = { newParentNode.getChildCount() - 1 };
			treeModel.nodesWereInserted(newParentNode, insertedIndex);

			// visibilidade
			TreePath ntp = new TreePath(treeModel.getPathToRoot(node));
			tree.scrollPathToVisible(ntp);
			tree.setSelectionPath(ntp);
		}

		return node;
	}

	public static DefaultMutableTreeNode becomeParent(JTree tree, DefaultTreeModel treeModel) {
		return becomeParent(null, tree, treeModel);
	}

	public static DefaultMutableTreeNode becomeParent(DefaultMutableTreeNode node, JTree tree,
			DefaultTreeModel treeModel) {
		if (node == null) {
			TreePath currentSelection = tree.getSelectionPath();
			node = currentSelection != null ? (DefaultMutableTreeNode) (currentSelection.getLastPathComponent()) : null;
			if (node == null)
				return null;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

		DefaultMutableTreeNode grandParent = (DefaultMutableTreeNode) parent.getParent();

		if (grandParent != null && !parent.equals(treeModel.getRoot())) {
			tree.setSelectionPath(null);
			int positionParent = grandParent.getIndex(parent);

			// remove o nó
			treeModel.removeNodeFromParent(node);

			// reinsere como filho de seu avô
			treeModel.insertNodeInto(node, grandParent, positionParent + 1);

			// visibilidade
			TreePath ntp = new TreePath(treeModel.getPathToRoot(node));
			tree.scrollPathToVisible(ntp);
			tree.setSelectionPath(ntp);
		}
		return node;
	}

	public static DefaultMutableTreeNode removeCurrentNode(JTree tree, DefaultTreeModel treeModel) {
		return removeCurrentNode(null, tree, treeModel);
	}

	public static DefaultMutableTreeNode removeCurrentNode(DefaultMutableTreeNode node, JTree tree,
			DefaultTreeModel treeModel) {
		if (node == null) {
			TreePath currentSelection = tree.getSelectionPath();
			node = currentSelection != null ? (DefaultMutableTreeNode) (currentSelection.getLastPathComponent()) : null;
			if (node == null)
				return null;
		}

		TreeNode parent = node.getParent();
		if (parent != null) {
			treeModel.removeNodeFromParent(node);
			return node;
		} else
			return null;
	}

	// ================================= Shape =================================

	public static Shape getShapeFromText(String text, File ttf) {
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, ttf);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(f);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		// TODO esse JPanel esta feio...
		f = f.deriveFont(Font.BOLD, 72);
		GlyphVector v = f.createGlyphVector((new JPanel()).getFontMetrics(f).getFontRenderContext(), text);
		return v.getOutline();
	}

	// ================================= Editor =================================

	public static final String TEXT = "jtc", LONG_FIELD = "long", DOUBLE_FIELD = "double";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComponent getEditor(Object value) {
		Class<?> clazz = value.getClass();
		JComponent out = null;

		if (clazz.equals(String.class) || clazz.equals(Double.class) || clazz.equals(Long.class)) {
			// String, double, long
			out = new JTextField(value.toString(), 15);
			if (clazz.equals(Double.class))
				((JTextField) out).setName(DOUBLE_FIELD);
			if (clazz.equals(Long.class))
				((JTextField) out).setName(LONG_FIELD);
		} else if (clazz.equals(File.class)) { // File
			out = new FileInput((File) value);
		} else if (clazz.equals(Boolean.class)) { // boolean
			out = new JCheckBox();
			((JCheckBox) out).setSelected((Boolean) value);
		} else if (clazz.equals(Integer.class)) { // integer
			out = new JSpinner(new SpinnerNumberModel((Number) value, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		} else if (clazz.equals(Float.class)) { // float
			out = new JSpinner(new SpinnerNumberModel((Number) value, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
		} else if (clazz.equals(Color.class)) { // cor
			out = new ColorInput((Color) value);
		} else if (value instanceof Calendar) { // calendar
			out = new TimeInput((Calendar) value);
		} else if (clazz.equals(Date.class)) { // data
			out = new DateInput((Date) value);
		} else if (clazz.equals(DefaultMutableTreeNode.class)) { // nó
			out = new NodeInput((DefaultMutableTreeNode) value);
		} else if (clazz.equals(ActionResult.class)) {
			out = new EventInput((ActionResult) value);
		} else if (clazz.isArray() ? "[F".equals(clazz.getName()) : false) { // vetor float
			float[] v = (float[]) value;
			ButtonInput<float[]> b = new ButtonInput<>(new ArrayInput(v, 5), "Adicionar vetor");
			b.setNonEmptyMessage("Editar vetor");
			out = b;
		} else if (clazz.isArray() ? "[[D".equals(clazz.getName()) : false) { // matriz
			double[][] m = (double[][]) value;
			ButtonInput<double[][]> b = new ButtonInput<>(new MatrizInput(m, 5), "Adicionar matriz");
			b.setNonEmptyMessage("Editar matriz");
			out = b;
		} else if (Map.class.isAssignableFrom(clazz)) { // tab de dispersão
			out = new ButtonInput<Map<?, ?>>(new MapInput((Map<?, ?>) value), "Adicionar tabela");
		} else if (clazz.isEnum()) { // enum
			out = new JComboBox<>(clazz.getEnumConstants());
			((JComboBox<?>) out).setSelectedItem(value);
		} else {
//			if (clazz.equals(Complex.class)) { // coordenadas TODO solução auto-incremental
//				ComplexInput ci = new ComplexInput((Complex) value, 3);
//				ci.setColumns(2);
//				ci.setColumns2(2);
//				out = ci;
//			} else if (clazz.equals(GeoCoordinate.class)) { // coordenadas
//				out = new ButtonInput<GeoCoordinate>(new CoordinateInput((GeoCoordinate) value),
//						LocaleConfig.hasConfig() ? LocaleConfig.getString("insertCoord") : "Inserir coordenadas");
//			} else if (clazz.equals(Parametro.class)) { // parâmetro
//				out = new ParametroInput((Parametro) value);
//			} else if (clazz.equals(Medida.class)) { // grandeza física
//				out = new MedidaInput((Medida) value);
//			} else
			out = new JLabel(value.toString());
		}

		return out;
	}

	public static Object getValueFromEditor(JComponent source) {
		Object out = null;

		if (source instanceof JTextComponent)
			out = ((JTextComponent) source).getText();
		else if (source instanceof JSpinner)
			out = ((JSpinner) source).getValue();
		else if (source instanceof JCheckBox)
			out = ((JCheckBox) source).isSelected();
		else if (source instanceof JComboBox<?>)
			out = ((JComboBox<?>) source).getSelectedItem();
		else if (source instanceof Input<?>)
			out = ((Input<?>) source).get();
		else
			System.err.println("Editor desconhecido");

		// diferenciar string de um número digitado

		if (source instanceof JTextComponent || source instanceof ParseableInput<?>) {
			String name = source.getName();
			try {
				if (LONG_FIELD.equals(name))
					out = Long.parseLong((String) out);
				else if (DOUBLE_FIELD.equals(name))
					out = Double.parseDouble(((String) out).replace(',', '.'));
			} catch (NumberFormatException e) {
				out = null;
			}
		}

		return out;
	}

	/**
	 * Função que retorna o texto contido na área de transferência
	 * 
	 * @param window janela
	 * @return texto na área de transferência
	 */
	public static String getClipBoardContent(Window window) {
		Clipboard clipboard = window.getToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(window);

		String str = null;
		try {
			str = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}