package br.com.pereiraeng.swing.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.pereiraeng.core.ReflectionUtils;
import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.icons.PereiraIcon;
import br.com.pereiraeng.swing.button.IDItablePanel;

/**
 * <p>
 * Classe dos objetos gráfico que representam uma tabela com um número fixo de
 * colunas e um número variável de linhas.
 * </p>
 * 
 * <p>
 * O número de linhas é ajustado com butões que estão dispostos abaixo da tabela
 * </p>
 * 
 * @author Philipe PEREIRA
 *
 */
public class ResizableTable extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final String PASTE = "PASTE";

	private EditableTable t;

	private IDItablePanel idi;

	/**
	 * Construtor da tabela com número variável de linhas
	 * 
	 * @param columns cabeçalho das colunas
	 * @param paste   <code>true</code> para deixar visível o ponto que permite
	 *                colar valores da área de transferência
	 */
	public ResizableTable(String[] columns, boolean paste) {
		super(new BorderLayout());

		add(t = new EditableTable(0, columns), BorderLayout.CENTER);

		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

		this.idi = new IDItablePanel(false);
		idi.addActionListener(this);
		p.add(idi);

		if (paste) {
			JButton b = new JButton(PereiraIcon.PASTE.create());
			b.setActionCommand(PASTE);
			b.setPreferredSize(Icons.DIM_BUTTON_SMALL_ICON);
			b.addActionListener(this);
			p.add(b);
		}

		add(p, BorderLayout.SOUTH);
	}

	// ====================== MÉTODOS DE INTERFACEAMENTO ======================

	@Override
	public void setEnabled(boolean enabled) {
		idi.setEnabled(enabled);
	}

	public DefaultTableModel getModel() {
		return t.getModel();
	}

	@Override
	public void setPreferredSize(Dimension size) {
		t.setPreferredSize(size);
	}

	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		t.setValueAt(obj, rowIndex, columnIndex);
	}

	public void setRowCount(int rowCount) {
		t.setRowCount(rowCount);
	}

	public int getRowCount() {
		return t.getRowCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return t.getValueAt(rowIndex, columnIndex);
	}

	public Object[][] getContent() {
		return t.getContent();
	}

	public void insertRow(int rowIndex, Object[] rowData) {
		t.insertRow(rowIndex, rowData);
	}

	public void setColumnClass(Class<?> type, int... columnIndexes) {
		t.setColumnClass(type, columnIndexes);
	}

	public void setDataVector(Object[][] dataVector) {
		t.setDataVector(dataVector);
	}

	public void addRow(Object[] objects) {
		t.addRow(objects);
	}

	public Object[] getRow(int row) {
		return t.getRow(row);
	}

	public JTable getTable() {
		return t.getTable();
	}

	public TableColumnModel getColumnModel() {
		return t.getColumnModel();
	}

	public void addColumn(TableColumn column) {
		t.addColumn(column);
	}

	public void removeColumn(TableColumn column) {
		t.removeColumn(column);
	}

	// ------------------------- NEW DATA -------------------------

	protected void insertRow(int row) {
		int col = t.getColumnCount();

		Class<?>[] clazz = new Class<?>[col];
		for (int i = 0; i < col; i++)
			clazz[i] = t.getColumnClass(i);
		Object[] emptyRow = ReflectionUtils.getNulls(clazz);

		t.insertRow(row, emptyRow);
	}

	// ------------------------- LISTENER -------------------------

	private ActionListener listener;

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// se não foi o botão que abre a caixa de diálogos que foi apertado, então foi
		// um dos butões que estão dentro da caixa

		int selectedRow = t.getSelectedRow();

		String command = event.getActionCommand();
		switch (command) {
		case IDItablePanel.BEFORE:
			if (selectedRow == -1) // se não tinha
				insertRow(0);
			else
				insertRow(selectedRow);
			break;
		case IDItablePanel.AFTER:
			if (selectedRow == -1) // se não tinha
				insertRow(t.getRowCount());
			else
				insertRow(selectedRow + 1);
			break;
		case IDItablePanel.DELETE:
			if (selectedRow != -1)
				t.removeRow(selectedRow);
			break;
		case PASTE:
			if (listener != null)
				this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command));
			break;
		}
	}
}
