package br.com.pereiraeng.swing.input.mip;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import br.com.pereiraeng.core.ReflectionUtils;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.table.AdvancedTableModel;

/**
 * Tabela que contém somente uma linha sendo que cada célula é uma objeto
 * gráfico do tipo {@link InputEmb}, servindo para editar um vetor de grandezas
 * 
 * @author Philipe Pereira
 *
 * @param <K> Classe de cada um dos objetos a serem editados nas células
 */
public class InputsTable<K> extends JTable implements Input<K[]>, ActionListener {
	private static final long serialVersionUID = 1L;

	private Class<? extends InputEmb<K>> classInput;

	/**
	 * Largura da coluna
	 */
	private int columnWidth;

	/**
	 * Valor a ser usado para preencher células vazias
	 */
	private K empty;

	/**
	 * Construtor da tabela
	 * 
	 * @param classInput classe do objeto gráfico
	 * @param n          número inicial de entradas (colunas da tabela)
	 * @param width      largura da célula
	 * @param height     altura da célula
	 * @param empty      objeto que preencherá as células vazias
	 */
	public InputsTable(Class<? extends InputEmb<K>> classInput, int n, int width, int height, K empty) {
		this(classInput, null, n, width, height, empty);
	}

	/**
	 * Construtor da tabela
	 * 
	 * @param classInput classe do objeto gráfico
	 * @param header     nome das entradas (será o cabeçalho das colunas da tabela)
	 * @param width      largura da célula
	 * @param height     altura da célula
	 * @param empty      objeto que preencherá as células vazias
	 */
	public InputsTable(Class<? extends InputEmb<K>> classInput, String[] header, int width, int height, K empty) {
		this(classInput, header, header.length, width, height, empty);
	}

	private InputsTable(Class<? extends InputEmb<K>> classInput, String[] header, int n, int width, int height,
			K empty) {
		this.classInput = classInput;
		this.columnWidth = width;
		this.empty = empty;

		// conteúdo
		AdvancedTableModel listModel = null;
		if (header != null)
			listModel = new AdvancedTableModel(header, 1, true);
		else
			listModel = new AdvancedTableModel(1, n, true);
		for (int i = 0; i < n; i++)
			listModel.setValueAt(this.empty, 0, i);
		super.setModel(listModel);

		// renderizadores (normal e edição)
		super.setDefaultRenderer(Object.class, new EmbRenderer());
		super.setDefaultEditor(Object.class, new EmbEditor());

		// dimensões eaparência
		if (header == null)
			super.setTableHeader(null);
		super.setShowGrid(true);
		super.setRowHeight(height);
		super.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		SwingUtils.setColumnsWidth(this, columnWidth);
	}

	public void setN(int n) {
		AdvancedTableModel listModel = (AdvancedTableModel) this.getModel();
		int oldN = listModel.getColumnCount();
		// atualiza o número de colunas no modelo
		listModel.setColumnCount(n);
		// adiciona colunas vazias caso haja novas colunas
		if (n > oldN)
			for (int i = oldN; i < n; i++)
				listModel.setValueAt(this.empty, 0, i);

		// forçar que a nova coluna (e as velhas) tem um tamanho mínimo
		SwingUtils.setColumnsWidth(this, columnWidth);
	}

	// ---------------------- INPUT ----------------------

	@Override
	public void set(K[] k) {
		AdvancedTableModel listModel = (AdvancedTableModel) this.getModel();
		for (int i = 0; i < k.length; i++)
			listModel.setValueAt(k[i], 0, i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public K[] get() {
		AdvancedTableModel listModel = (AdvancedTableModel) this.getModel();
		K[] out = (K[]) new Object[listModel.getColumnCount()];
		for (int i = 0; i < out.length; i++)
			out[i] = (K) listModel.getValueAt(0, i);
		return out;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// -------------------------- TABLE --------------------------

	public void setHeader(String[] header) {
		if (header != null) {
			this.setTableHeader(new JTableHeader(this.getColumnModel()));
			((AdvancedTableModel) this.getModel()).setColumnIdentifiers(header);
			SwingUtils.setColumnsWidth(this, columnWidth);
		}
	}

	// ------------------------ LISTENERS ------------------------

	/**
	 * Adds a listener to the list that is notified each time a change to the data
	 * model occurs.
	 * 
	 * @param listener the TableModelListener
	 */
	public void addTableModelListener(TableModelListener listener) {
		super.getModel().addTableModelListener(listener);
	}

	// ---------------------- RENDERIZADORES ----------------------

	/**
	 * Renderizador das células da tabela
	 * 
	 * @author Philipe Pereira
	 *
	 */
	private class EmbRenderer implements TableCellRenderer {

		private InputEmb<K> input;

		@SuppressWarnings("unchecked")
		public EmbRenderer() {
			try {
				Constructor<?> c = ReflectionUtils.getNoArgsConst(classInput);

				if (c != null)
					input = (InputEmb<K>) c.newInstance();
				else
					throw new IllegalArgumentException(
							"Este InputEmb não tem um construtor que não recebe argumentos.");
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			input.set((K) value);
			return input.getComponent();
		}
	}

	/**
	 * Renderizador do editor das células da tabela
	 * 
	 * @author Philipe Pereira
	 *
	 */
	private class EmbEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener {
		private static final long serialVersionUID = 1L;

		private K values;

		private InputEmb<K> input;

		@Override
		public Object getCellEditorValue() {
			return this.values;
		}

		@Override
		public boolean isCellEditable(EventObject anEvent) {
			if (anEvent instanceof MouseEvent) {
				return ((MouseEvent) anEvent).getClickCount() >= 1;
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.values = (K) value;

			try {
				Constructor<?> constructor = ReflectionUtils.getNoArgsConst(classInput);

				if (constructor != null)
					input = (InputEmb<K>) constructor.newInstance();
				else
					throw new IllegalArgumentException(
							"Este InputEmb não tem um construtor que não recebe argumentos.");
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException e) {
				e.printStackTrace();
			}
			input.set(values);
			input.setSelected();
			input.addChangeListener(this);

			return input.getComponent();
		}

		private void setNewValue(K newValue) {
			this.values = newValue;
			int row = getEditingRow();
			int col = getEditingColumn();
			if (row >= 0 && col >= 0)
				getModel().setValueAt(newValue, row, col);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void stateChanged(ChangeEvent e) {
			setNewValue((K) ((InputEmb<K>) e.getSource()).get());
		}
	}

	// ----------------- LISTENER -----------------

	@Override
	public void actionPerformed(ActionEvent event) {
		int selected = this.getSelectedColumn();
		if (selected >= 0) {
			AdvancedTableModel listModel = (AdvancedTableModel) this.getModel();
			listModel.removeColumn(selected);
		}
	}
}