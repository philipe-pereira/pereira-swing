package br.com.pereiraeng.swing.table;

import java.awt.Dimension;

/**
 * Tabela editável com apenas uma coluna, própria para editar uma série de
 * campos. Tais campos devem ser designados
 * 
 * @see {@link ObjEditTable}, {@link ObjectTable}
 * 
 * @author Philipe PEREIRA
 *
 */
public class OneColumnTable extends EditableTable {
	private static final long serialVersionUID = 1L;

	protected Object[] defaultValues;

	public OneColumnTable() {
		this(null, null);
	}

	/**
	 * Construtor da tabela editável com uma só coluna
	 * 
	 * @param rowHeader     cabeçalho das linhas
	 * @param defaultValues valores default de cada uma das células
	 */
	public OneColumnTable(String[] rowHeader, Object[] defaultValues) {
		this(rowHeader, defaultValues, -1);
	}

	/**
	 * Construtor da tabela editável com uma só coluna
	 * 
	 * @param rowHeader      cabeçalho das linhas
	 * @param defaultValues  valores default de cada uma das células
	 * @param rowHeaderWidth largura do cabeçalho das linhas
	 */
	public OneColumnTable(String[] rowHeader, Object[] defaultValues, int rowHeaderWidth) {
		this(rowHeader, defaultValues, rowHeaderWidth, null);
	}

	/**
	 * Construtor da tabela editável com uma só coluna
	 * 
	 * @param defaultValues uma matriz com duas colunas, a primeira com os nomes dos
	 *                      campos e a segunda com o valores default
	 */
	protected OneColumnTable(Object[][] defaultValues) {
		this((String[]) defaultValues[0], defaultValues[1], -1, null);
	}

	public OneColumnTable(String[] rowHeader, Object[] defaultValues, int rowHeaderWidth, Dimension dimension) {
		this(rowHeader, defaultValues, rowHeaderWidth, dimension, 1);
	}

	/**
	 * Construtor da tabela editável com uma só coluna
	 * 
	 * @param rowHeader      cabeçalho das linhas
	 * @param defaultValues  valores default de cada uma das células
	 * @param rowHeaderWidth largura do cabeçalho das linhas
	 * @param dimension      dimensões da tabela
	 */
	protected OneColumnTable(String[] rowHeader, Object[] defaultValues, int rowHeaderWidth, Dimension dimension,
			int columns) {
		super(rowHeader != null ? rowHeader.length : 0, columns);
		setDefaultValues(defaultValues);

		// dimensions
		if (rowHeaderWidth > -1)
			super.setRowHeaderWidth(rowHeaderWidth);
		if (dimension != null)
			super.setPreferredSize(dimension);

		// headers
		super.setTableHeader(null);
		super.setRowIdentifiers(rowHeader);

		// preenche a tabela com os valores default (assim, quando a tabela for
		// renderizada, as células se tornaram os elementos gráficos adequados que se
		// espera)
			this.clear();
	}

	public Object get(int row) {
		return super.getValueAt(row, 0);
	}

	public void set(int row, Object obj) {
		super.setValueAt(obj, row, 0);
	}

	public void set(Object[] values) {
		for (int i = 0; i < values.length; i++)
			set(i, values[i]);
	}

	public void setDefaultValues(Object[] defaultValues) {
		this.defaultValues = defaultValues;
	}

	@Override
	public void clear() {
		if (defaultValues != null ? defaultValues.length > 0 : false)
			for (int row = 0; row < defaultValues.length; row++)
				table.setValueAt(defaultValues[row], row, 0);
		else
			super.clear();
	}
}
