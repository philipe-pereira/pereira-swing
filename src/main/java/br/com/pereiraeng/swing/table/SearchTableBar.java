package br.com.pereiraeng.swing.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.button.SearchField;

/**
 * Classe do objeto gráfico que representa um conjunto de campos de texto a
 * serem ajustados sobre uma tabela, sendo que ao se clicar sobre os butões do
 * lado do campo a coluna correspondente da tabela será variada e a linha que
 * contiver seu contéudo será selecionada
 * 
 * @author Philipe PEREIRA
 *
 */
public class SearchTableBar extends Grade implements ActionListener, TableModelListener, ComponentListener {
	private static final long serialVersionUID = 1L;

	private JTable table;

	/**
	 * Construtor da barra de busca na tabela.
	 * 
	 * @param t tabela associa a esta barra de busca
	 */
	public SearchTableBar(JTable t) {
		this.table = t;
		// começar a monitorar as ações de alterações do conteúdo da tabela (ao
		// haver uma mudança, altera-se também as caixas de busca)
		this.table.getModel().addTableModelListener(this);
		this.addComponentListener(this);
	}

	/**
	 * Função que altera o número de campos de texto de modo que seja compatível com
	 * o número de colunas da tabela
	 * 
	 * @param numCols número de colunas
	 */
	private void setColumns(int numCols) {
		super.removeAll();
		for (int i = 0; i < numCols; i++) {
			SearchField sf = new SearchField(i);
			sf.addActionListener(this);
			super.add(sf, i, 0, 1, 1);
		}
		super.add(Box.createHorizontalStrut(16), numCols, 0, 1, 1);
	}

	/**
	 * Função que altera o tamanho dos campos de texto de modo que seja compatível
	 * com o comprimento de cada uma das colunas da tabela
	 * 
	 * @param numCols número de colunas
	 */
	public void refreshFieldsWidth() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// depois que a tabela for redesenhada (com seu novo conteúdo,
				// redefinido através das alterações no modelo) pega-se os
				// comprimentos das colunas
				int numCols = table.getColumnCount();
				if (numCols >= 0) {
					SearchTableBar stb = SearchTableBar.this;
					try {
						for (int i = 0; i < numCols && i < stb.getComponentCount(); i++) {
							SearchField sf = null;
							while ((sf = (SearchField) stb.getComponent(i)) == null)
								;
							sf.setWidth(table.getColumnModel().getColumn(i).getWidth());
							sf.revalidate();
							sf.repaint();
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						// TODO que porra!!!
						System.out.println(e.getMessage() + "\t" + stb.getComponentCount());
					}
				}
			}
		});
	}

	public void setFieldsWidth(int[] widths) {
		if (widths == null)
			return;
		for (int i = 0; i < widths.length; i++)
			((SearchField) SearchTableBar.this.getComponent(i)).setWidth(widths[i]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] as = e.getActionCommand().split("-");

		int column = Integer.parseInt(as[1]);

		SearchField.search(table, ((SearchField) e.getSource()).getText(), SearchField.UP.equals(as[0]), column);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// se for INSERT ou DELETE
		SearchTableBar.this.setColumns(table.getModel().getColumnCount());
		refreshFieldsWidth();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		refreshFieldsWidth();
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}
}