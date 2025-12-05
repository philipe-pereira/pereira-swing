package br.com.pereiraeng.swing.list;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.button.SearchField;

/**
 * Classe do objeto gráfico que contém uma caixa de texto e uma lista. Ao se
 * digitar na caixa de texto e acionar a busca, desloca-se a lista até se
 * selecionar cada um dos itens cuja função {@link Object#toString()} retorna
 * uma sequência de caracteres que contém aquela que foi digitada.
 * 
 * @author Philipe PEREIRA
 *
 * @param <V>
 *            classe dos elementos contidos na lista
 */
public class SearchList<V> extends JPanel {
	private static final long serialVersionUID = 1L;

	private JList<V> list;

	/**
	 * Construtor do objeto gráfico da lista com a caixa de texto para busca
	 * 
	 * @param model
	 *            modelo da lista
	 */
	public SearchList(ListModel<V> model) {
		super(new BorderLayout());
		this.list = new JList<V>(model);
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(new SearchField(list), BorderLayout.NORTH);
	}

	// MÉTODOS DE INTERFACEAMENTO

	public JList<V> getList() {
		return list;
	}

	public void setSelectionMode(int selectionMode) {
		list.setSelectionMode(selectionMode);
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		list.addListSelectionListener(listener);
	}

	public V getSelectedValue() {
		return list.getSelectedValue();
	}

	public List<V> getSelectedValuesList() {
		return list.getSelectedValuesList();
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		SwingUtils.getScrollPane(list).setPreferredSize(preferredSize);
	}

	public void setCellRenderer(ListCellRenderer<? super V> s) {
		list.setCellRenderer(s);
	}

	public ListModel<V> getModel() {
		return list.getModel();
	}

	public void setSelectedValue(Object anObject, boolean shouldScroll) {
		this.list.setSelectedValue(anObject, shouldScroll);
	}

	public void clearSelection() {
		this.list.clearSelection();
	}

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}
}