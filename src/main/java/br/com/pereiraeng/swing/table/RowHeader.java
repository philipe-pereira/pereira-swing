package br.com.pereiraeng.swing.table;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.swing.table.renderer.HeaderRenderer;

/**
 * Classe do objeto gráfico do cabeçalho das linhas (de uma tabela ou gráfico) a
 * ser inserido em um painel derolante
 * 
 * @author Philipe Pereira
 *
 * @param <R> classe dos objetos a serem exibidos no cabeçalho das linhas
 */
public class RowHeader<R> extends JPanel {
	private static final long serialVersionUID = 1L;

	private JList<R> list;

	/**
	 * Construtor do objeto gráfico do cabeçalho das linhas
	 * 
	 * @param sp             painel derolante onde será inserido o cabeçalho
	 * @param model          modelo da lista dos objetos no cabeçalho das linhas
	 * @param rowHeaderWidth largura do cabeçalho
	 * @param height         altura das linhas do cabeçalho
	 */
	public RowHeader(JScrollPane sp, ListModel<R> model, int rowHeaderWidth, int height) {
		this(sp, model, null, rowHeaderWidth, height);
	}

	/**
	 * Construtor do objeto gráfico do cabeçalho das linhas
	 * 
	 * @param sp             painel derolante onde será inserido o cabeçalho
	 * @param model          modelo da lista dos objetos no cabeçalho das linhas
	 * @param font           fonte a ser usada no cabeçalho
	 * @param rowHeaderWidth largura do cabeçalho
	 * @param height         altura das linhas do cabeçalho
	 */
	public RowHeader(JScrollPane sp, ListModel<R> model, Font font, int rowHeaderWidth, int height) {
		this(sp, model, font, rowHeaderWidth, height, 0);
	}

	/**
	 * Construtor do objeto gráfico do cabeçalho das linhas
	 * 
	 * @param sp             painel derolante onde será inserido o cabeçalho
	 * @param model          modelo da lista dos objetos no cabeçalho das linhas
	 * @param rowHeaderWidth largura do cabeçalho
	 * @param height         altura das linhas do cabeçalho
	 * @param offset         número de linhas a serem saltadas para o começo do
	 *                       cabeçalho (cada linha saltada terá a altura designada
	 *                       neste construtor)
	 */
	public RowHeader(JScrollPane sp, ListModel<R> model, int rowHeaderWidth, int height, int offset) {
		this(sp, model, null, rowHeaderWidth, height, offset);
	}

	/**
	 * Construtor do objeto gráfico do cabeçalho das linhas
	 * 
	 * @param sp             painel derolante onde será inserido o cabeçalho
	 * @param model          modelo da lista dos objetos no cabeçalho das linhas
	 * @param font           fonte a ser usada no cabeçalho
	 * @param rowHeaderWidth largura do cabeçalho
	 * @param height         altura das linhas do cabeçalho
	 * @param offset         número de linhas a serem saltadas para o começo do
	 *                       cabeçalho (cada linha saltada terá a altura designada
	 *                       neste construtor)
	 */
	public RowHeader(JScrollPane sp, ListModel<R> model, Font font, int rowHeaderWidth, int height, int offset) {
		list = new JList<R>(model);
		if (rowHeaderWidth != 0)
			list.setFixedCellWidth(rowHeaderWidth);
		list.setFixedCellHeight(height);
		list.setCellRenderer(new HeaderRenderer(font));
		list.setBackground(sp.getViewport().getBackground());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		if (offset > 0)
			add(Box.createRigidArea(new Dimension(rowHeaderWidth, height * offset)));
		add(list);
	}

	// ====================== MÉTODOS DE INTERFACEAMENTO ======================

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}

	public R getSelectedValue() {
		return list.getSelectedValue();
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		list.addListSelectionListener(listener);
	}

	public void setSelectionMode(int mode) {
		list.setSelectionMode(mode);
	}

	public int getFixedCellHeight() {
		return list.getFixedCellHeight();
	}

	public void setListData(R[] data) {
		list.setListData(data);
	}

	public JList<R> getList() {
		return list;
	}
}