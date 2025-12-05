package br.com.pereiraeng.swing.table;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.pereiraeng.swing.table.renderer.MultiRenderer;

/**
 * Classe do objeto gráfico da tabela em que cada uma das suas célula pode ser
 * editada, sendo que o editor é personalizado para cada tipo de elemento. Este
 * objeto já vem com um {@link JScrollPane} integrado, não precisando ser
 * inserido dentro de outro.
 * 
 * 
 * @author Philipe PEREIRA
 * 
 */
public class EditableTable extends Table {
	private static final long serialVersionUID = 1L;

	private boolean specialRenderer;

	/**
	 * Construtor da tabela editável vazia. O renderizador especial será usado.
	 */
	public EditableTable() {
		this(0, 0);
	}

	/**
	 * Construtor da tabela editável vazia com um dado número de linhas e colunas. O
	 * renderizador especial será usado.
	 * 
	 * @param rows    número de linhas
	 * @param columns número de colunas
	 */
	public EditableTable(int rows, int columns) {
		this(rows, columns, true);
	}

	/**
	 * Construtor da tabela vazia, editável ou não, com um dado número de linhas e
	 * colunas e podendo-se escolher se o {@link MultiRenderer renderizador
	 * especial} será aplicado ou não
	 * 
	 * @param rows            número de linhas
	 * @param columns         número de colunas
	 * @param specialRenderer se <code>true</code> o renderizador especial será
	 *                        usado, para <code>false</code> o padrão do Java
	 */
	public EditableTable(int rows, int columns, boolean specialRenderer) {
		this(new AdvancedTableModel(rows, columns, true), specialRenderer);
	}

	/**
	 * Construtor da tabela vazia editável com um dado número de linhas e com as
	 * colunas com os dados nomes. O renderizador especial será usado.
	 * 
	 * @param rows        número de linhas
	 * @param columnNames nomes das colunas
	 */
	public EditableTable(int rows, Object[] columnNames) {
		this(rows, columnNames, true);
	}

	/**
	 * Construtor da tabela vazia, editável ou não, com um dado número de linhas,
	 * com as colunas com os dados nomes e pode-se escolher se aplicará ou não o
	 * {@link MultiRenderer renderizador especial}
	 * 
	 * @param rows            número de linhas
	 * @param columnNames     nomes das colunas
	 * @param specialRenderer se <code>true</code> o renderizador especial será
	 *                        usado, para <code>false</code> o padrão do Java
	 */
	public EditableTable(int rows, Object[] columnNames, boolean specialRenderer) {
		this(new AdvancedTableModel(columnNames, rows, true), specialRenderer);
	}

	private EditableTable(AdvancedTableModel advancedTableModel, boolean specialRenderer) {
		super(advancedTableModel);

		// ***** renderer and editor ****
		this.specialRenderer = specialRenderer;
		if (this.specialRenderer)
			super.setDefaultRenderer(Object.class, new MultiRenderer());
	}

	/**
	 * Função que bloqueia ou libera a edição de dados na tabela
	 * 
	 * @param editable <code>true</code> para liberar,<code>false</code> para
	 *                 bloquear a edição
	 */
	public void setEditable(boolean editable) {
		this.model.setDefaultEditability(editable);
		this.table.repaint();

		if (specialRenderer) {
			// quando se usar o 'renderer especial', é preciso mudar na mão a aparência da
			// tabela (se não fizer isso, fica a aparência do editor especial porém
			// estático)
			if (editable)
				super.setDefaultRenderer(Object.class, new MultiRenderer());
			else
				super.setDefaultRenderer(Object.class, (new JTable()).getDefaultRenderer(Object.class));
		}
	}
}