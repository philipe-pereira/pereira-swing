package br.com.pereiraeng.swing.input;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

/**
 * Componente do tipo <code>JComboBox</code> onde os itens são representados
 * numa <code>JCheckBox</code> selecionável
 * 
 * @author Philipe PEREIRA
 * 
 * @param <K> objetos que podem ser selecionados na <code>JComboBox</code>
 */
public class CheckCBInput<K> extends JComboBox<K> implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static final String SEPARATOR = "; ";
	private LinkedList<Boolean> itemsState;

	/**
	 * Construtor do componente
	 */
	public CheckCBInput() {
		this(1);
	}

	/**
	 * Construtor do componente
	 * 
	 * @param rows número de linhas da caixa de texto das opções selecionadas
	 */
	public CheckCBInput(int rows) {
		this(rows, null);
	}

	/**
	 * Construtor do componente
	 * 
	 * @param format A <a href=
	 *               "https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format
	 *               string</a>
	 */
	public CheckCBInput(String format) {
		this(1, format);
	}

	/**
	 * Construtor do componente
	 * 
	 * @param rows   número de linhas da caixa de texto das opções selecionadas
	 * @param format A <a href=
	 *               "https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format
	 *               string</a>
	 */
	public CheckCBInput(int rows, String format) {
		this.itemsState = new LinkedList<Boolean>();
		super.setRenderer(new CCBRenderer(rows, format));
		super.addActionListener(this);
	}

	@Override
	public void addItem(K k) {
		this.innerChange = true;
		this.itemsState.add(false);
		super.addItem(k);
		this.innerChange = false;
	}

	@Override
	public void removeItem(Object anObject) {
		int removed = -1;
		for (int i = 0; i < this.getItemCount(); i++) {
			if (super.getItemAt(i).equals(anObject)) {
				removed = i;
				break;
			}
		}
		if (removed > -1) {
			this.innerChange = true;
			this.setSelectedIndex(-1);
			this.itemsState.remove(removed);
			super.removeItemAt(removed);
			this.innerChange = false;
		}
	}

	@Override
	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	/**
	 * Função que indica se um dado item está selecionado
	 * 
	 * @param index o índice do item
	 * @return <code>true</code> se o item está selecionado, <code>false</code>
	 *         senão
	 */
	public boolean isSelected(int index) {
		return itemsState.get(index);
	}

	public void setSelected(int index, boolean selected) {
		this.itemsState.set(index, selected);
	}

	public void setSelected(K k, boolean selected) {
		for (int i = 0; i < super.getItemCount(); i++) {
			if (super.getItemAt(i).equals(k)) {
				this.itemsState.set(i, selected);
				break;
			}
		}
	}

	public void setAllSelected(boolean state) {
		for (int i = 0; i < this.itemsState.size(); i++)
			this.itemsState.set(i, state);
		repaint();
	}

	public boolean isAllSelected() {
		for (int i = 0; i < this.itemsState.size(); i++)
			if (!itemsState.get(i))
				return false;
		return true;
	}

	public boolean isAllNonSelected() {
		for (int i = 0; i < this.itemsState.size(); i++)
			if (itemsState.get(i))
				return false;
		return true;
	}

	public void clear() {
		for (int i = 0; i < this.itemsState.size(); i++)
			this.setSelected(i, false);
	}

	public K getSelectedItem(int index) {
		int j = 0;
		for (int i = 0; i < super.getItemCount(); i++) {
			if (this.isSelected(i)) {
				if (j == index)
					return this.getItemAt(i);
				j++;
			}
		}
		return null;
	}

	/**
	 * Função que retorna os itens selecionados
	 * 
	 * @return lista dos itens que foram selecionados
	 */
	public List<K> getSelectedItems() {
		List<K> items = new LinkedList<>();
		for (int i = 0; i < super.getItemCount(); i++)
			if (this.isSelected(i))
				items.add(this.getItemAt(i));
		return items;
	}

	public void setSelectedItems(Collection<K> selecteds) {
		for (int i = 0; i < super.getItemCount(); i++)
			this.itemsState.set(i, selecteds.contains(super.getItemAt(i)));
	}

	/**
	 * Função que retorna o vetor com o estado dos itens, se eles estão selecionados
	 * ou não
	 * 
	 * @return vetor com <code>boolean</code> indicando se os itens estão
	 *         selecionados ou não
	 */
	public boolean[] getItemsSelection() {
		boolean[] out = new boolean[this.itemsState.size()];
		for (int i = 0; i < this.itemsState.size(); i++)
			out[i] = this.itemsState.get(i);
		return out;
	}

	@Override
	public void removeAllItems() {
		super.removeAllItems();
		this.itemsState.clear();
	}

	/**
	 * Renderizador das caixas de seleção do <code>JComboBox</code>
	 * 
	 * @author Philipe PEREIRA
	 * 
	 */
	private class CCBRenderer extends JCheckBox implements ListCellRenderer<K> {
		private static final long serialVersionUID = 1L;

		private final int rows;

		private final String format;

		private CCBRenderer(int rows, String format) {
			this.rows = rows;
			this.format = format;
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends K> list, K value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if (index >= 0) {
				setText(format == null ? value.toString() : String.format(format, value));
				setSelected(CheckCBInput.this.isSelected(index));
				setBackground(isSelected ? new Color(163, 184, 204) : new Color(238, 238, 238));
				return this;
			} else {
				StringBuilder text = new StringBuilder();
				for (int i = 0; i < itemsState.size(); i++) {
					if (CheckCBInput.this.isSelected(i)) {
						Object k = getItemAt(i);
						text.append(format == null ? k.toString() : String.format(format, k));
						text.append(SEPARATOR);
					}
				}
				JTextArea ta = new JTextArea(text.toString());
				ta.setRows(rows);
				ta.setLineWrap(true);
				ta.setWrapStyleWord(true);
				return ta;
			}
		}
	}

	// ----------------------- LISTENER -----------------------

	private transient boolean innerChange = false;

	private ActionListener listener;

	@Override
	public void actionPerformed(ActionEvent event) {
		if (!innerChange) {
			int index = getSelectedIndex();
			if (index >= 0) {
				boolean state = !itemsState.get(index);
				itemsState.set(index, state);
				((JCheckBox) this.getRenderer()).setSelected(state);
				if (listener != null)
					listener.actionPerformed(event);
			}
		}
	}
}