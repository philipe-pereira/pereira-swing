package br.com.pereiraeng.swing.table;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.pereiraeng.core.DisplayableFields;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.button.SearchField;

/**
 * Classe do objeto gráfico da tabela. A tabela [seu modelo] não pode ter seus
 * elementos editados pelo usuário, já vem com um cabeçalho das linhas e com um
 * {@link JScrollPane} integrado, não precisando ser inserido dentro de outro.
 * 
 * @author Philipe PEREIRA
 *
 */
public class Table extends JScrollPane implements ChangeListener {
	private static final long serialVersionUID = 1L;

	/**
	 * Tabela do objeto
	 */
	protected JTable table;

	/**
	 * Modelo da tabela
	 */
	protected AdvancedTableModel model;

	/**
	 * Largura do cabeçalho das linhas
	 */
	private int rowHeaderWidth;

	/**
	 * Modelo da lista do cabeçalho das linhas
	 */
	protected DefaultListModel<String> modelRowHeader;

	/**
	 * Construtor de uma tabela não editável
	 * 
	 * @param rows        número de linhas
	 * @param columnsName nome das colunas
	 */
	public Table(int rows, Object[] columnsName) {
		this(new AdvancedTableModel(columnsName, rows, false));
	}

	/**
	 * Construtor de uma tabela não editável
	 * 
	 * @param rows    número de linhas
	 * @param columns número de colunas
	 */
	public Table(int rows, int columns) {
		this(new AdvancedTableModel(rows, columns, false));
	}

	/**
	 * Construtor de uma tabela
	 * 
	 * @param model modelo avançado da tabela
	 */
	protected Table(AdvancedTableModel model) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.model = model;
		this.table = new JTable(this.model);

		// **** appearence ****
		super.setBackground(null);
		this.setRowHeight(20);

		// **** scroll pane ****
		this.setViewportView(table);
	}

	/**
	 * Função que estabelece como <code>null</code> todos os objetos da tabela,
	 * mantendo inalterados os números de colunas e linhas
	 */
	public void remove() {
		for (int r = 0; r < this.getRowCount(); r++)
			for (int c = 0; c < this.getColumnCount(); c++)
				this.setValueAt(null, r, c);
	}

	/**
	 * Função que zera o número de linhas da tabela
	 */
	public void clear() {
		this.setRowIdentifiers(null);
		this.setRowCount(0);
	}

	public DefaultTableModel getModel() {
		return model;
	}

	/**
	 * Função em que se estabelece a largura do cabeçalho das linhas. <strong>Esta
	 * função deve ser chamada antes de {@link #setRowIdentifiers(Object[])} de modo
	 * que o cabeçalho das linhas assuma as dimensões desejadas</strong>.
	 * 
	 * @param rowHeaderWidth largura, em pixels
	 */
	public void setRowHeaderWidth(int rowHeaderWidth) {
		this.rowHeaderWidth = rowHeaderWidth;
	}

	/**
	 * Adds a child that will appear in the scroll pane corner, if there's room.
	 * 
	 * @param corner a component
	 */
	public void setCorner(Component corner) {
		super.setCorner(JScrollPane.UPPER_LEFT_CORNER, corner);
	}

	// ========================== ROW HEADER ==========================

	public DefaultListModel<String> getModelRH() {
		return modelRowHeader;
	}

	/**
	 * Replaces the row identifiers in the model.
	 * 
	 * @param rowIdentifiers array of column identifiers. If null, set the model to
	 *                       zero columns
	 */
	public void setRowIdentifiers(Object[] rowIdentifiers) {
		if (rowIdentifiers != null) {
			if (super.getRowHeader() == null) // se a lista não está construída
				super.setRowHeaderView(new RowHeader<String>(this, modelRowHeader = new DefaultListModel<>(),
						rowHeaderWidth, this.table.getRowHeight()));
			else if (modelRowHeader != null) // se a lista já está construída, limpa-a
				this.modelRowHeader.clear();
			for (Object s : rowIdentifiers)
				this.modelRowHeader.addElement(s.toString());
		} else if (modelRowHeader != null)
			this.modelRowHeader.clear();
	}

	public void addRowIdentifier(Object rowIdentifier) {
		if (super.getRowHeader() == null) // se a lista não está construída
			super.setRowHeaderView(new RowHeader<String>(this, modelRowHeader = new DefaultListModel<>(),
					rowHeaderWidth, this.table.getRowHeight()));
		this.modelRowHeader.addElement(rowIdentifier.toString());
	}

	/**
	 * Adds the specified component to the end of this list.
	 * 
	 * @param element the component to be added
	 */
	public void addElementRH(String element) {
		if (this.modelRowHeader == null)
			setRowIdentifiers(new Object[] { element });
		else
			this.modelRowHeader.addElement(element);
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 * 
	 * @param index   index at which the specified element is to be inserted
	 * @param element element to be inserted
	 */
	public void addElementRH(int index, String element) {
		if (this.modelRowHeader == null)
			addElementRH(element);
		else
			this.modelRowHeader.add(index, element);
	}

	/**
	 * Returns the element at the specified position in this list.
	 * 
	 * @param row index of element to return
	 * @return the element at the specified position in this list
	 */
	public String getElementRH(int row) {
		return this.modelRowHeader.get(row);
	}

	/**
	 * Removes the element at the specified position in this list.Returns the
	 * element that was removed from the list
	 * 
	 * @param row the index of the element to removed
	 * @return the element previously at the specified position
	 */
	public String removeElementRH(int row) {
		return this.modelRowHeader.remove(row);
	}

	/**
	 * Removes all of the elements from this list. The list will be empty after this
	 * call returns (unless it throws an exception).
	 */
	public void clearRowHeader() {
		this.modelRowHeader.clear();
	}

	/**
	 * Função que estabelece o número de linhas da tabela como sendo igual ao número
	 * de elementos do cabeçalho das linhas
	 */
	public void adjustRowsFromHeader() {
		if (this.modelRowHeader != null)
			this.setRowCount(this.modelRowHeader.size());
	}

	public String getRowIdentifier(int row) {
		if (this.modelRowHeader != null)
			return this.modelRowHeader.get(row);
		else
			return null;
	}

	public List<String> getRowIdentifiers() {
		if (this.modelRowHeader != null)
			return Collections.list(this.modelRowHeader.elements());
		else
			return new ArrayList<String>(0);
	}

	// ====================== ROW HEADER - SEARCH BAR ======================

	public void activeRowHeaderSearch() {
		if (super.getRowHeader() == null)
			setRowIdentifiers(new Object[0]);
		JList<?> list = ((RowHeader<?>) super.getRowHeader().getView()).getList();
		this.setCorner(new SearchField(list));
		this.getRowHeader().addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		// ao se deslocar o cabeçalho das linhas através da busca, se desloca
		// também a tabela inteira
		JViewport viewport = (JViewport) event.getSource();
		this.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
	}

	// ========================== OBJETOS EDITÁVEIS ========================

	/**
	 * Função que carrega a tabela com os valores de um objeto do tipo
	 * {@link DisplayableFields}.
	 * 
	 * @param df objeto cujos parâmetros serão carregados na tabela
	 */
	public void setDisplayableFieldObj(DisplayableFields df) {
		if (df != null) {
			// cabeçalho das linhas
			Object[] rows = new Object[df.getFieldCount()];
			for (int i = 0; i < rows.length; i++)
				rows[i] = df.getFieldName(i);
			this.setRowIdentifiers(rows);
			// espaço na tabela
			this.setRowCount(rows.length);

			// conteúdo
			for (int i = 0; i < rows.length; i++)
				this.setValueAt(df.getField(i), i, 0);

			// como é possível que se tenha mudado o número de campos e o conteúdo deles,
			// refresh sobre toda a estrutura
			model.fireTableStructureChanged();
		} else
			this.clear();
	}

	// ====================== MÉTODOS DE INTERFACEAMENTO ======================

	// ================================= MODEL =================================

	/**
	 * Returns an attribute value for the cell at row and column.
	 * 
	 * @param rowIndex    the row whose value is to be queried
	 * @param columnIndex the column whose value is to be queried
	 * @return the value Object at the specified cell
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.model.getValueAt(rowIndex, columnIndex);
	}

	public Object[] getValueAt(int row) {
		Object[] out = new Object[this.getColumnCount()];
		for (int i = 0; i < out.length; i++)
			out[i] = this.getValueAt(row, i);
		return out;
	}

	public Object[][] getContent() {
		Vector<?> dv = this.model.getDataVector();
		Object[][] out = new Object[dv.size()][];

		int i = 0;
		for (Object o : dv)
			out[i++] = ((Vector<?>) o).toArray();
		return out;
	}

	/**
	 * Sets the object value for the cell at column and row. aValue is the new
	 * value. This method will generate a tableChanged notification.
	 * 
	 * @param obj         the new value; this can be null
	 * @param rowIndex    the row whose value is to be changed
	 * @param columnIndex the column whose value is to be changed
	 */
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		this.model.setValueAt(obj, rowIndex, columnIndex);
	}

	public void setValueAt(int row, Object[] newObjs) {
		this.model.setValueAt(row, newObjs);
	}

	/**
	 * Returns the number of columns in this data table.
	 * 
	 * @return the number of columns in the model
	 */
	public int getColumnCount() {
		return this.model.getColumnCount();
	}

	/**
	 * Returns the number of rows in this data table.
	 * 
	 * @return the number of rows in the model
	 */
	public int getRowCount() {
		return this.model.getRowCount();
	}

	/**
	 * Returns the column name.
	 * 
	 * @param column the column being queried
	 * 
	 * @return a name for this column using the string value of the appropriate
	 *         member in columnIdentifiers. If columnIdentifiers does not have an
	 *         entry for this index, returns the default name provided by the
	 *         superclass.
	 */
	public String getColumnName(int column) {
		return this.model.getColumnName(column);
	}

	/**
	 * Replaces the value in the dataVector instance variable with the values in the
	 * array dataVector. The first index in the Object[][] array is the row index
	 * and the second is the column index. columnIdentifiers are the names of the
	 * new columns.
	 * 
	 * @param dataVector        the new data vector
	 * @param columnIdentifiers the names of the columns
	 */
	public void setDataVector(Object[][] dataVector, Object[] columnIdentifiers) {
		this.model.setDataVector(dataVector, columnIdentifiers);
	}

	/**
	 * Replaces the value in the dataVector instance variable with the values in the
	 * array dataVector. The first index in the Object[][] array is the row index
	 * and the second is the column index.
	 * 
	 * @param dataVector the new data vector
	 */
	public void setDataVector(Object[][] dataVector) {
		this.model.setDataVector(dataVector);
	}

	/**
	 * Replaces the column identifiers in the model. If the number of newIdentifiers
	 * is greater than the current number of columns, new columns are added to the
	 * end of each row in the model. If the number of newIdentifiers is less than
	 * the current number of columns, all the extra columns at the end of a row are
	 * discarded.
	 * 
	 * @param columnIdentifiers array of column identifiers. If null, set the model
	 *                          to zero columns
	 */
	public void setColumnIdentifiers(Object[] columnIdentifiers) {
		this.model.setColumnIdentifiers(columnIdentifiers);
	}

	/**
	 * 
	 * @return
	 */
	public Object[] getColumnIdentifiers() {
		return model.getColumnIdentifiers();
	}

	/**
	 * Função que esvazia todas as células da tabela (mantendo o número de linhas e
	 * colunas)
	 */
	public void empty() {
		this.model.empty();
	}

	public void setColumnIdentifier(int index, Object columnIdentifier) {
		Object[] objs = model.getColumnIdentifiers();
		objs[index] = columnIdentifier;
		this.model.setColumnIdentifiers(objs);
	}

	public void addRow(Object[] row) {
		this.model.addRow(row);
	}

	public Object[] getRow(int row) {
		return this.model.getRow(row);
	}

	/**
	 * Removes the row at row from the model. Notification of the row being removed
	 * will be sent to all the listeners.
	 * 
	 * @param row the row index of the row to be removed
	 */
	public void removeRow(int row) {
		// parar de editar antes de apagar
		TableCellEditor tce = this.table.getCellEditor();
		if (tce != null)
			tce.stopCellEditing();
		// apagar linha do modelo da tabela e do cabeçalho das linhas
		this.model.removeRow(row);
		if (modelRowHeader != null)
			this.modelRowHeader.removeElementAt(row);
	}

	/**
	 * Inserts a row at row in the model. The new row will contain null values
	 * unless rowData is specified. Notification of the row being added will be
	 * generated.
	 * 
	 * @param row     the row index of the row to be inserted
	 * @param rowData optional data of the row being added
	 */
	public void insertRow(int row, Object[] rowData) {
		this.model.insertRow(row, rowData);
//		this.model.fireTableRowsInserted(row, row);
	}

	/**
	 * Adds a listener to the list that's notified each time a change to the data
	 * model occurs.
	 * 
	 * @param l the TableModelListener
	 */
	public void addTableModelListener(TableModelListener l) {
		this.model.addTableModelListener(l);
	}

	/**
	 * Sets the number of rows in the model. If the new size is greater than the
	 * current size, new rows are added to the end of the model If the new size is
	 * less than the current size, all rows at index rowCount and greater are
	 * discarded.
	 * 
	 * @param rowCount the new number of rows in the model
	 */
	public void setRowCount(int rowCount) {
		this.model.setRowCount(rowCount);
	}

	/**
	 * Sets the number of columns in the model. If the new size is greater than the
	 * current size, new columns are added to the end of the model with null cell
	 * values. If the new size is less than the current size, all columns at index
	 * columnCount and greater are discarded.
	 * 
	 * @param columnCount the new number of columns in the model
	 */
	public void setColumnCount(int columnCount) {
		this.model.setColumnCount(columnCount);
	}

	/**
	 * Notifies all listeners that all cell values in the table's rows may have
	 * changed. The number of rows may also have changed and the JTable should
	 * redraw the table from scratch. The structure of the table (as in the order of
	 * the columns) is assumed to be the same.
	 */
	public void fireTableDataChanged() {
		this.model.fireTableDataChanged();
	}

	/**
	 * Função em que se estabelece qual classe de objeto ocupará cada uma das
	 * colunas. Se esse método não for invocado em momento algum, a classe a ser
	 * considerada é aquela definida em {@link DefaultTableModel}, que é
	 * {@link Object}.
	 * 
	 * @param type          classe dos objetos que ocuparão as colunas
	 * @param columnIndexes índices das colunas que serão ocupadas pelos objetos da
	 *                      classe indicada. Se nenhuma coluna for indicada (vetor
	 *                      vazio) então considera-se que todas as colunas terão
	 *                      objetos da mesma classe.
	 */
	public void setColumnClass(Class<?> type, int... columnIndexes) {
		this.model.setColumnClass(type, columnIndexes);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return this.model.getColumnClass(columnIndex);
	}

	public void setDefaultEditability(boolean editable) {
		this.model.setDefaultEditability(editable);
	}

	/**
	 * Função em que se estabelece quais colunas podem ser excepcionalmente editadas
	 * 
	 * @param columnIndex vetor com os índices das colunas editáveis
	 */
	public void setEditableColumns(int... columnIndex) {
		this.model.setEditableColumns(columnIndex);
	}

	// ========================== TABLE ===========================

	public JTable getTable() {
		return this.table;
	}

	/**
	 * Sets whether the table draws grid lines around cells. If showGrid is true it
	 * does; if it is false it doesn't. There is no getShowGrid method as this state
	 * is held in two variables -- showHorizontalLines and showVerticalLines -- each
	 * of which can be queried independently.
	 * 
	 * @param showGrid true if table view should draw grid lines
	 */
	public void setShowGrid(boolean showGrid) {
		this.table.setShowGrid(showGrid);
	}

	public void changeSelection(int rowIndex, int columnIndex) {
		this.table.changeSelection(rowIndex, columnIndex, false, false);
	}

	private static final Class<?>[] HAS_RENDERER = { Number.class, Float.class, Double.class, Date.class, Icon.class,
			ImageIcon.class, Boolean.class };

	/**
	 * <p>
	 * Sets a default cell renderer to be used if no renderer has been set in a
	 * TableColumn. If renderer is null, removes the default renderer for this
	 * column class.
	 * </p>
	 * 
	 * <p>
	 * <strong>Se o objeto renderizador da célula também for {@link TableCellEditor
	 * editor}, então já se estabelece também como editor padrão dessa
	 * classe.</strong>
	 * </p>
	 * 
	 * @param columnClass set the default cell renderer for this columnClass
	 * @param renderer    default cell renderer to be used for this columnClass
	 */
	public void setDefaultRenderer(Class<?> columnClass, TableCellRenderer renderer) {
		this.table.setDefaultRenderer(columnClass, renderer);
		if (renderer instanceof TableCellEditor)
			setDefaultEditor(columnClass, (TableCellEditor) renderer);

		if (Object.class.equals(columnClass)) {
			// se o novo renderizador é para todos os objetos, remover os particulares...
			for (int i = 0; i < HAS_RENDERER.length; i++)
				this.table.setDefaultRenderer(HAS_RENDERER[i], null);
		}
	}

	public void setDefaultEditor(Class<?> columnClass, TableCellEditor editor) {
		this.table.setDefaultEditor(columnClass, editor);

		if (Object.class.equals(columnClass)) {
			// se o novo renderizador é para todos os objetos, remover os particulares...
			for (int i = 0; i < HAS_RENDERER.length; i++)
				this.table.setDefaultEditor(HAS_RENDERER[i], null);
		}
	}

	/**
	 * Sets the tableHeader working with this JTable to newHeader. It is legal to
	 * have a null tableHeader.
	 * 
	 * @param header new tableHeader
	 */
	public void setTableHeader(JTableHeader header) {
		this.table.setTableHeader(header);
	}

	/**
	 * Returns the TableColumnModel that contains all column information of this
	 * table.
	 * 
	 * @return the object that provides the column state of the table
	 */
	public TableColumnModel getColumnModel() {
		return this.table.getColumnModel();
	}

	/**
	 * Sets the table's auto resize mode when the table is resized. For further
	 * information on how the different resize modes work, see
	 * {@link JTable#doLayout()}.
	 * 
	 * @param mode One of 5 legal values: AUTO_RESIZE_OFF, AUTO_RESIZE_NEXT_COLUMN,
	 *             AUTO_RESIZE_SUBSEQUENT_COLUMNS, AUTO_RESIZE_LAST_COLUMN,
	 *             AUTO_RESIZE_ALL_COLUMNS
	 */
	public void setAutoResizeMode(int mode) {
		this.table.setAutoResizeMode(mode);
	}

	/**
	 * Returns the index of the first selected row, -1 if no row is selected.
	 * 
	 * @return the index of the first selected row
	 */
	public int getSelectedRow() {
		return this.table.getSelectedRow();
	}

	public int[] getSelectedRows() {
		return this.table.getSelectedRows();
	}

	public void addTableSelectionListener(ListSelectionListener listSelectionListener) {
		this.table.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	/**
	 * Função que é estabelecido o tamanho das colunas
	 * 
	 * @param columnWidth tamanho de cada coluna, em pixels
	 */
	public void setColumnsWidth(int columnWidth) {
		SwingUtils.setColumnsWidth(this.table, columnWidth);
	}

	public void setColumnsWidth(int[] columnsWidth) {
		SwingUtils.setColumnsWidth(this.table, columnsWidth);
	}

	/**
	 * Função que é estabelecido o tamanho de uma coluna
	 * 
	 * @param column índice da coluna
	 * @param width  tamanho da coluna, em pixels
	 */
	public void setColumnWidth(int column, int width) {
		TableColumnModel tcm = this.table.getColumnModel();
		tcm.getColumn(column).setMinWidth(width);
		tcm.getColumn(column).setMaxWidth(width);
	}

	/**
	 * Sets the height, in pixels, of all cells to rowHeight, revalidates, and
	 * repaints. The height of the cells will be equal to the row height minus the
	 * row margin.
	 * 
	 * @param rowHeight new row height
	 */
	public void setRowHeight(int rowHeight) {
		this.table.setRowHeight(rowHeight);
	}

	/**
	 * Sets the height for row to rowHeight, revalidates, and repaints. The height
	 * of the cells in this row will be equal to the row height minus the row
	 * margin.
	 * 
	 * @param row       the row whose height is being changed
	 * @param rowHeight new row height, in pixels
	 */
	public void setRowHeight(int row, int rowHeight) {
		this.table.setRowHeight(row, rowHeight);
		// JList<?> list = (JList<?>) super.getRowHeader().getView();
		// TODO
	}

	/**
	 * Appends aColumn to the end of the array of columns held by this JTable's
	 * column model. If the column name of aColumn is null, sets the column name of
	 * aColumn to the name returned by getModel().getColumnName(). To add a column
	 * to this JTable to display the modelColumn'th column of data in the model with
	 * a given width, cellRenderer, and cellEditor you can use:
	 * 
	 * addColumn(new TableColumn(modelColumn, width, cellRenderer, cellEditor));
	 * 
	 * 
	 * [Any of the TableColumn constructors can be used instead of this one.] The
	 * model column number is stored inside the TableColumn and is used during
	 * rendering and editing to locate the appropriates data values in the model.
	 * The model column number does not change when columns are reordered in the
	 * view.
	 * 
	 * @param column aColumn - the TableColumn to be added
	 */
	public void addColumn(TableColumn column) {
		this.table.addColumn(column);
	}

	/**
	 * Removes aColumn from this JTable's array of columns. Note: this method does
	 * not remove the column of data from the model; it just removes the TableColumn
	 * that was responsible for displaying it.
	 * 
	 * @param column the TableColumn to be removed
	 */
	public void removeColumn(TableColumn column) {
		this.table.removeColumn(column);
	}

	/**
	 * <p>
	 * Sets the table's selection mode to allow only single selections, a single
	 * contiguous interval, or multiple intervals.
	 * </p>
	 * 
	 * Note: JTable provides all the methods for handling column and row selection.
	 * When setting states, such as setSelectionMode, it not only updates the mode
	 * for the row selection model but also sets similar values in the selection
	 * model of the columnModel. If you want to have the row and column selection
	 * models operating in different modes, set them both directly.
	 * 
	 * Both the row and column selection models for JTable default to using a
	 * DefaultListSelectionModel so that JTable works the same way as the JList. See
	 * the setSelectionMode method in JList for details about the modes.
	 * 
	 * @param selectionMode
	 */
	public void setSelectionMode(int selectionMode) {
		this.table.setSelectionMode(selectionMode);
	}

	// ========================== ROW HEADER ===========================

	public int getSelectedRowHeader() {
		return (((RowHeader<?>) super.getRowHeader().getView()).getList()).getSelectedIndex();
	}
}
