package br.com.pereiraeng.swing.table.renderer;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Classe abstrata dos objetos gráficos que são, ao mesmo tempo, o renderizador
 * das células das tabelas e seu editor
 * 
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class RendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = 1L;

	// -------------------- EDITOR --------------------

	private transient JTable table;

	private transient Object current;

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.table = table;
		this.current = value;
		Component c = table.getCellRenderer(row, column).getTableCellRendererComponent(table, this.current, isSelected,
				true, row, column);
		addListener(c);
		return c;
	}

	/**
	 * Função que adiciona o <code>listener</code> pertinente ao componente que
	 * representa a célula da tabela.
	 * 
	 * @param c componente que representa a célula da tabela.
	 */
	protected abstract void addListener(Component c);

	@Override
	public Object getCellEditorValue() {
		return current;
	}

	/**
	 * Função que faz a atualização do modelo da tabela após se editar a célula.
	 * Este método deve ser chamado pelo <code>listener</code> pertinente ao
	 * componente que representa a célula da tabela.
	 * 
	 * @param newValue    novo valor da célula
	 * @param stopEditing se <code>true</code> atualiza-se a célula que é mostrada
	 *                    pelo {@link RendererEditor}. Quando a edição já altera
	 *                    automaticamente a célula (e.g. edição de texto em caixas),
	 *                    não precisa (e não se pode) ordenar essa atualização.
	 */
	protected void setNewValue(Object newValue, boolean stopEditing) {
		this.current = newValue;
		int row = table.getEditingRow(), column = table.getEditingColumn();
		if (row != -1 && column != -1) {
			if (stopEditing) // a ação já leva a um objeto definitivo
				fireEditingStopped();
			else // a ação leva a objeto que está sendo parcialmente editado
				table.getModel().setValueAt(current, row, column);
		}
	}
}
