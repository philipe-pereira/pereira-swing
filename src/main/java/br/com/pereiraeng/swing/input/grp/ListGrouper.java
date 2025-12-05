package br.com.pereiraeng.swing.input.grp;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

/**
 * Classe do objeto gráfico de uma lista de itens, em meios aos quais alguns
 * serão selecionados e associados a uma dada etiqueta
 * 
 * @author Philipe PEREIRA
 *
 * @param <T> classe do objeto a ser selecionado
 */
public class ListGrouper<T> extends Grouper<T> {
	private static final long serialVersionUID = 1L;

	private JList<T> l;
	private DefaultListModel<T> lm;

	/**
	 * Construtor do objeto gráfico da lista de itens, em meios aos quais alguns
	 * serão selecionados a associados a uma dada etiqueta
	 * 
	 * @param list   vetor com a lista de itens
	 * @param labels etiquetas do grupo
	 */
	public ListGrouper(T[] list, String... labels) {
		this(list, null, 1, 10, false, labels);
	}

	/**
	 * Construtor do objeto gráfico da lista de itens, em meios aos quais alguns
	 * serão selecionados a associados a uma dada etiqueta
	 * 
	 * @param list         vetor com a lista de itens
	 * @param columnHeader nome da coluna (<code>null</code> para não haver tabela)
	 * @param n            número inicial de grupos
	 * @param max          número máximo de grupos
	 * @param editable     <code>true</code> para poder alterar o número de grupos,
	 *                     <code>false</code> senão
	 * @param labels       etiquetas do grupo
	 */
	@SuppressWarnings("unchecked")
	public ListGrouper(T[] list, String columnHeader, int n, int max, boolean editable, String... labels) {
		super(new JScrollPane(new JList<T>(new DefaultListModel<T>()), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), columnHeader, n, max, editable, labels);
		this.l = (JList<T>) ((JScrollPane) getComponent(0)).getViewport().getView();
		this.l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.lm = (DefaultListModel<T>) l.getModel();
		for (int i = 0; i < list.length; i++)
			this.lm.addElement(list[i]);
	}

	protected T getSelected() {
		return this.l.getSelectedValue();
	}

	/**
	 * Função que retorna os índices os elementos da lista inicial
	 * 
	 * @return vetor com a indicação dos índices da lista dos item selecionados. -1
	 *         se para a dada posição nenhum elemento foi selecionado
	 */
	public int[] get() {
		int[] out = new int[getCount()];
		for (int i = 0; i < out.length; i++) {
			String s = getTextField(i).getText();
			if (!"".equals(s)) {
				for (int j = 0; j < this.lm.size(); j++) {
					if (this.lm.get(j).equals(s)) {
						out[i] = j;
						break;
					}
				}
			} else
				out[i] = -1;
		}
		return out;
	}

	/**
	 * Função que estabelece os elementos da lista a serem indicados em cada uma das
	 * etiquetas
	 * 
	 * @param index vetor com a indicação dos índices da lista dos item
	 *              selecionados. -1 se para a dada posição nenhum elemento foi
	 *              selecionado
	 */
	public void set(int[] index) {
		for (int i = 0; i < index.length; i++)
			if (index[i] >= 0 && index[i] < lm.getSize())
				getTextField(i).setText(lm.get(index[i]).toString());
	}

	// --------------------- MÉTODOS DE INTERFACEAMENTO ---------------------

	// métodos do JList

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
	public void setCellRenderer(ListCellRenderer<? super T> cellRenderer) {
		l.setCellRenderer(cellRenderer);
	}
}
