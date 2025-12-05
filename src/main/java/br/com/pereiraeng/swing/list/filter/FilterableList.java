package br.com.pereiraeng.swing.list.filter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico que contém uma caixa de texto e uma lista. Ao se
 * digitar na caixa de texto, filtra-se o conteúdo da lista mantendo-se somente
 * os itens cuja função {@link Object#toString()} retorna uma sequência de
 * caracteres que contém aquela que foi digitada.
 * 
 * @author Philipe PEREIRA
 *
 * @param <K> classe do objeto que estará contido na lista
 */
public class FilterableList<K> extends JPanel implements DocumentListener, ListFilter, ListSelectionListener, Input<K> {
	private static final long serialVersionUID = 1L;

	private JTextField filterInput;
	private JList<K> jList;
	private FilteredListModel<K> listModel;

	public FilterableList(K[] items) {
		this();
		for (int i = 0; i < items.length; i++)
			addElement(items[i]);
	}

	/**
	 * Construtor da lista filtrável
	 */
	public FilterableList() {
		this((JTextField) null);
	}

	public FilterableList(JTextField textField) {
		super(new BorderLayout());

		// caixa de texto
		if (textField == null)
			super.add(filterInput = new JTextField(), BorderLayout.NORTH);
		else
			filterInput = textField;
		filterInput.getDocument().addDocumentListener(this);

		// modelo da lista
		listModel = new FilteredListModel<>();
		listModel.setFilter(this);
		addFilter(this.dlf = new DefaultListFilter());

		// lista e janela rolável
		jList = new JList<>(listModel);
		jList.addListSelectionListener(this);
		super.add(new JScrollPane(jList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ================ MÉTODOS DE INTERFACEAMENTO ================

	// métodos do JList

	/**
	 * Adds a listener to the list, to be notified each time a change to the
	 * selection occurs; the preferred way of listening for selection state changes.
	 * JList takes care of listening for selection state changes in the selection
	 * model, and notifies the given listener of each change. ListSelectionEvents
	 * sent to the listener have a source property set to this list.
	 * 
	 * @param listener the {@link ListSelectionListener} to add
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		this.listener = listener;
	}

	/**
	 * Sets the delegate that is used to paint each cell in the list. The job of a
	 * cell renderer is discussed in detail in the class level documentation. If the
	 * prototypeCellValue property is non-null, setting the cell renderer also
	 * causes the fixedCellWidth and fixedCellHeight properties to be re-calculated.
	 * Only one PropertyChangeEvent is generated however - for the cellRenderer
	 * property.
	 * 
	 * The default value of this property is provided by the ListUI delegate, i.e.
	 * by the look and feel implementation.
	 * 
	 * This is a JavaBeans bound property.
	 * 
	 * 
	 * @param cellRenderer the ListCellRenderer that paints list cells
	 */
	public void setCellRenderer(ListCellRenderer<? super K> cellRenderer) {
		jList.setCellRenderer(cellRenderer);
	}

	/**
	 * Returns a list of all the selected items, in increasing order based on their
	 * indices in the list.
	 * 
	 * @return the selected items, or an empty list if nothing is selected
	 */
	public List<K> getSelectedValuesList() {
		return jList.getSelectedValuesList();
	}

	/**
	 * Returns the value for the smallest selected cell index; the selected value
	 * when only a single item is selected in the list. When multiple items are
	 * selected, it is simply the value for the smallest selected index. Returns
	 * null if there is no selection.
	 * 
	 * This is a convenience method that simply returns the model value for
	 * getMinSelectionIndex.
	 * 
	 * @return the first selected value
	 * 
	 */
	public K getSelectedValue() {
		return jList.getSelectedValue();
	}

	/**
	 * Returns the smallest selected cell index; the selection when only a single
	 * item is selected in the list. When multiple items are selected, it is simply
	 * the smallest selected index. Returns -1 if there is no selection. This method
	 * is a cover that delegates to getMinSelectionIndex.
	 * 
	 * @return the smallest selected cell index
	 */
	public int getSelectedIndex() {
		return jList.getSelectedIndex();
	}

	/**
	 * Returns the value for the smallest selected cell index; the selected value
	 * when only a single item is selected in the list. When multiple items are
	 * selected, it is simply the value for the smallest selected index. Returns
	 * null if there is no selection. This is a convenience method that simply
	 * returns the model value for getMinSelectionIndex.
	 * 
	 * @return the first selected value
	 */
	@Override
	public K get() {
		return jList.getSelectedValue();
	}

	/**
	 * Selects the specified object from the list.
	 */
	@Override
	public void set(K k) {
		jList.setSelectedValue(k, true);
	}

	/**
	 * Selects a single cell. Does nothing if the given index is greater than or
	 * equal to the model size. This is a convenience method that uses
	 * setSelectionInterval on the selection model. Refer to the documentation for
	 * the selection model class being used for details on how values less than 0
	 * are handled.
	 *
	 * @param index the index of the cell to select
	 */
	public void setSelectedIndex(int index) {
		this.jList.setSelectedIndex(index);
	}

	/**
	 * Sets the selection mode for the list. This is a cover method that sets the
	 * selection mode directly on the selection model. The following list describes
	 * the accepted selection modes:
	 * 
	 * <ul>
	 * <li>ListSelectionModel.SINGLE_SELECTION - Only one list index can be selected
	 * at a time. In this mode, setSelectionInterval and addSelectionInterval are
	 * equivalent, both replacing the current selection with the index represented
	 * by the second argument (the "lead").</i>
	 * <li>ListSelectionModel.SINGLE_INTERVAL_SELECTION - Only one contiguous
	 * interval can be selected at a time. In this mode, addSelectionInterval
	 * behaves like setSelectionInterval (replacing the current selection}, unless
	 * the given interval is immediately adjacent to or overlaps the existing
	 * selection, and can be used to grow the selection.</i>
	 * <li>ListSelectionModel.MULTIPLE_INTERVAL_SELECTION - In this mode, there's no
	 * restriction on what can be selected. This mode is the default.</i>
	 * </ul>
	 * 
	 * @param selectionMode the selection mode
	 */
	public void setSelectionMode(int selectionMode) {
		this.jList.setSelectionMode(selectionMode);
	}

	/**
	 * Clears the selection; after calling this method, isSelectionEmpty will return
	 * true. This is a cover method that delegates to the method of the same name on
	 * the list's selection model.
	 */
	public void clearSelection() {
		this.jList.clearSelection();
	}

	/**
	 * Scrolls the list within an enclosing viewport to make the specified cell
	 * completely visible. This calls scrollRectToVisible with the bounds of the
	 * specified cell. For this method to work, the JList must be within a
	 * JViewport. If the given index is outside the list's range of cells, this
	 * method results in nothing.
	 * 
	 * @param index the index of the cell to make visible
	 */
	public void ensureIndexIsVisible(int index) {
		this.jList.ensureIndexIsVisible(index);
	}

	// métodos do ListModel

	/**
	 * Adds the specified component to the end of this list.
	 * 
	 * @param element the component to be added
	 */
	public void addElement(K element) {
		this.innerChange = true;
		listModel.addElement(element);
		this.innerChange = false;
	}

	/**
	 * Returns the component at the specified index.
	 * 
	 * @param index an index into this list
	 * @return the component at the specified index
	 */
	public K get(int index) {
		return listModel.get(index);
	}

	/**
	 * Tests whether the specified object is a component in this list.
	 * 
	 * @param obj an object
	 * @return <code>true</code> if the specified object is the same as a component
	 *         in this list
	 */
	public boolean contains(K obj) {
		return listModel.contains(obj);
	}

	/**
	 * 
	 * Removes the first (lowest-indexed) occurrence of the argument from this list.
	 * 
	 * @param obj the component to be removed
	 * @return <code>true</code> if the argument was a component of this list;
	 *         <code>false</code> otherwise
	 */
	public boolean remove(K obj) {
		return listModel.removeElement(obj);
	}

	/**
	 * Removes all of the elements from this list. The list will be empty after this
	 * call returns (unless it throws an exception).
	 */
	public void clear() {
		this.clearSelection();
		listModel.clear();
	}

	/**
	 * Returns the number of components in this list. This method is identical to
	 * size, which implements the List interface defined in the 1.2 Collections
	 * framework. This method exists in conjunction with setSize so that size is
	 * identifiable as a JavaBean property.
	 * 
	 * @return the number of components in this list
	 */
	public int getElementsCount() {
		return listModel.size();
	}

	/**
	 * Função que aplica o filtro especificado na lista
	 */
	public void doFilter() {
		listModel.doFilter();
	}

	// métodos do JScrollPane

	private JScrollPane getJScrollPane() {
		return (JScrollPane) jList.getParent().getParent();
	}

	/**
	 * Sets the preferred size of this component. If preferredSize is null, the UI
	 * will be asked for the preferred size.
	 * 
	 * Aplicado ao {@link JScrollPane} da lista filtrável (ou seja, a largura do
	 * conjunto será aquela dada, porém a altura terá sido somado com a da caixa de
	 * texto)
	 */
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		getJScrollPane().setPreferredSize(preferredSize);
	}

	// métodos do JTextField

	public void setText(String text) {
		filterInput.getDocument().removeDocumentListener(this);
		filterInput.setText(text);
		filterInput.getDocument().addDocumentListener(this);
	}

	public void setEditable(boolean b) {
		filterInput.setEditable(b);
		getJScrollPane().setVisible(b);
	}

	public void clearFilter() {
		filterInput.setText("");
	}

	// ========================= LISTENER =========================

	private ListSelectionListener listener;

	private transient boolean innerChange;

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (listener != null && !innerChange)
			// troca-se a fonte (não mais a JList, mas sim este objeto)
			listener.valueChanged(new ListSelectionEvent(this, event.getFirstIndex(), event.getLastIndex(),
					event.getValueIsAdjusting()));
	}

	@Override
	public void changedUpdate(DocumentEvent event) {
		// Plain text components do not fire these events
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		insertUpdate(event);
	}

	@Override
	public void insertUpdate(DocumentEvent event) {
		dlf.setFilter(filterInput.getText());
		listModel.doFilter();
		jList.clearSelection();
		dlf.clearFilter();
	}

	// ========================= LIST FILTER =========================

	private DefaultListFilter dlf;

	private List<ListFilter> filters;

	public void addFilter(ListFilter filter) {
		if (this.filters == null)
			this.filters = new LinkedList<>();
		this.filters.add(filter);
	}

	@Override
	public boolean accept(Object element) {
		for (ListFilter listFilter : filters)
			if (!listFilter.accept(element))
				return false;
		return true;
	}
}