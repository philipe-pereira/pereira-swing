package br.com.pereiraeng.swing.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import br.com.pereiraeng.core.StringUtils;
import br.com.pereiraeng.swing.Grade;

/**
 * Classe do objeto gráfico de um campo de texto com duas setas ao lado para se
 * fazer buscas ao longo de uma tabela ou texto. O campo de texto já conta com
 * um escutador para deslocar a busca caso se utilize as setas do teclado.
 * 
 * @author Philipe PEREIRA
 *
 */
public class SearchField extends Grade implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private int id;

	private JTextField filter;

	private static final int HEIGHT = 20, BUTTON_WIDTH = 15;

	public static final String UP = "UP", DOWN = "DOWN";

	public SearchField(int id) {
		this.id = id;

		this.filter = new JTextField();
		this.filter.setPreferredSize(new Dimension(59, HEIGHT));
		this.filter.addKeyListener(this);
		add(this.filter, 0, 0, 1, 2);

		DirectionButton b = new DirectionButton(true);
		b.addActionListener(this);
		b.setActionCommand(UP);
		add(b, 1, 0, 1, 1);

		b = new DirectionButton(false);
		b.addActionListener(this);
		b.setActionCommand(DOWN);
		add(b, 1, 1, 1, 1);
	}

	public SearchField(final JTable t, int column) {
		this(column);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] as = e.getActionCommand().split("-");

				int column = Integer.parseInt(as[1]);

				search(t, ((SearchField) e.getSource()).getText(), UP.equals(as[0]), column);
			}
		});
	}

	public SearchField(final JList<?> list) {
		this(0);
		this.setWidth(list.getFixedCellWidth());
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] as = e.getActionCommand().split("-");

				search(list, ((SearchField) e.getSource()).getText(), UP.equals(as[0]));
			}
		});
	}

	public void setWidth(int width) {
		int newWidth = width - BUTTON_WIDTH;
		if (newWidth > 10)
			filter.setPreferredSize(new Dimension(newWidth, HEIGHT));
	}

	public String getText() {
		return filter.getText();
	}

	/**
	 * Classe interna privada da imagem das setas dos butões para cima e para baixo
	 * 
	 * @author Philipe PEREIRA
	 *
	 */
	private class DirectionButton extends JButton implements Icon {
		private static final long serialVersionUID = 1L;

		private boolean up;

		public DirectionButton(boolean up) {
			this.up = up;
			super.setIcon(this);
			Dimension d = new Dimension(BUTTON_WIDTH, up ? 10 : 8);
			setPreferredSize(d);
			setMaximumSize(d);
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			// para que os triângulos ficassem pe
			g.setColor(Color.DARK_GRAY);
			if (up)
				g.fillPolygon(new int[] { 1, 12, 7 }, new int[] { 7, 7, 1 }, 3);
			else
				g.fillPolygon(new int[] { 2, 7, 12 }, new int[] { 2, 7, 2 }, 3);
		}

		@Override
		public int getIconWidth() {
			return 16;
		}

		@Override
		public int getIconHeight() {
			return 10;
		}
	}

	// --------------------- LISTENER ---------------------

	private ActionListener listener;

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (this.listener != null)
			this.listener
					.actionPerformed(new ActionEvent(this, event.getID(), event.getActionCommand() + "-" + this.id));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_ENTER:
			this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, DOWN));
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, DOWN));
			break;
		case KeyEvent.VK_UP:
			this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, UP));
			break;
		}
	}

	@Override
	public synchronized void addKeyListener(KeyListener l) {
		super.addKeyListener(l);
	}

	// ----------------------- SEARCHER -----------------------

	/**
	 * Função que executa a varredura de uma dada coluna da tabela procurando uma
	 * dada sequência de caracteres
	 * 
	 * @param table
	 *            tabela
	 * @param text
	 *            sequência de caracteres procurada
	 * @param up
	 * @param column
	 *            numeração da coluna
	 */
	public static void search(JTable table, String text, boolean up, int column) {
		if ("".equals(text))
			return;
		text = StringUtils.removeAccent(text.toUpperCase());

		// célula selecionada
		int currentRow = table.getSelectedRow();
		if (currentRow < 0)
			currentRow = 0;

		// varrer as linhas
		TableModel tm = table.getModel();
		int rows = tm.getRowCount();
		int i = currentRow;
		do {
			i = (i + rows + (up ? -1 : 1)) % rows;
			Object o = tm.getValueAt(i, column);
			if (o != null) {
				String so = StringUtils.removeAccent(o.toString().toUpperCase());
				if (so.contains(text)) {
					// se a busca encontrou um elemento
					table.setRowSelectionInterval(i, i);
					table.scrollRectToVisible(new Rectangle(table.getCellRect(i, 0, true)));
					break;
				}
			}
		} while (i != currentRow);
	}

	public static void search(JList<?> list, String text, boolean up) {
		if ("".equals(text))
			return;
		text = StringUtils.removeAccent(text.toUpperCase());

		// célula selecionada
		int currentRow = list.getSelectedIndex();
		if (currentRow < 0)
			currentRow = 0;

		// varrer as linhas
		ListModel<?> tm = list.getModel();
		int rows = tm.getSize();
		int i = currentRow;
		do {
			i = (i + rows + (up ? -1 : 1)) % rows;
			Object o = tm.getElementAt(i);
			if (o != null) {
				String so = StringUtils.removeAccent(o.toString().toUpperCase());
				if (so.contains(text)) {
					// se a busca encontrou um elemento
					list.setSelectedIndex(i);
					list.ensureIndexIsVisible(i);
					break;
				}
			}
		} while (i != currentRow);
	}
}
