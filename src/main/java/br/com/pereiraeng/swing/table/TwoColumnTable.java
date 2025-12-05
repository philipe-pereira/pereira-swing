package br.com.pereiraeng.swing.table;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import br.com.pereiraeng.swing.ActionResult;
import br.com.pereiraeng.swing.SwingUtils;

public class TwoColumnTable extends OneColumnTable {
	private static final long serialVersionUID = 1L;

	private final ActionListener listener;

	/**
	 * Construtor da tabela editável com duas colunas: uma para edição, outra para
	 * gerar um evento validador|
	 */
	public TwoColumnTable(ActionListener listener) {
		this(null, null, listener);
	}

	/**
	 * Construtor da tabela editável com duas colunas: uma para edição, outra para
	 * gerar um evento validador|
	 * 
	 * @param rowHeader     cabeçalho das linhas
	 * @param defaultValues valores default de cada uma das células
	 */
	public TwoColumnTable(String[] rowHeader, Object[] defaultValues, ActionListener listener) {
		this(rowHeader, defaultValues, -1, listener);
	}

	/**
	 * Construtor da tabela editável com duas colunas: uma para edição, outra para
	 * gerar um evento validador|
	 * 
	 * @param rowHeader      cabeçalho das linhas
	 * @param defaultValues  valores default de cada uma das células
	 * @param rowHeaderWidth largura do cabeçalho das linhas
	 */
	public TwoColumnTable(String[] rowHeader, Object[] defaultValues, int rowHeaderWidth, ActionListener listener) {
		this(rowHeader, defaultValues, rowHeaderWidth, null, listener);
	}

	/**
	 * Construtor da tabela editável com duas colunas: uma para edição, outra para
	 * gerar um evento validador|
	 * 
	 * @param rowHeader      cabeçalho das linhas
	 * @param defaultValues  valores default de cada uma das células
	 * @param rowHeaderWidth largura do cabeçalho das linhas
	 * @param dimension      dimensões da tabela
	 */
	public TwoColumnTable(String[] rowHeader, Object[] defaultValues, int rowHeaderWidth, Dimension dimension,
			ActionListener listener) {
		super(rowHeader, defaultValues, rowHeaderWidth, dimension, 2);
		this.listener = listener;

		for (int row = 0; row < this.getRowCount(); row++) {
			ActionResult ar = new ActionResult(row, this.listener, 0, this);
			table.setValueAt(ar, row, 1);
		}
		SwingUtils.setColumnWidth(table, 20, 1);
	}

	public void clearStatus() {
		for (int row = 0; row < this.getRowCount(); row++)
			setStatus(row, 0);
	}

	public void setStatus(int row, int status) {
		((ActionResult) super.getValueAt(row, 1)).setState(status);
	}
}
