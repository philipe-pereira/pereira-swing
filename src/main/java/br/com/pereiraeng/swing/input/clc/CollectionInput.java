package br.com.pereiraeng.swing.input.clc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.core.ReflectionUtils;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.RequestFocusListener;
import br.com.pereiraeng.swing.button.CUDpanel;
import br.com.pereiraeng.swing.button.PasteListButton;
import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.list.rendering.FormatterRenderer;

public abstract class CollectionInput<K, C extends Collection<K>> extends JPanel implements Input<C>, ActionListener {
	private static final long serialVersionUID = 1L;

	protected final K defaultValue;

	protected AbstractListModel<K> model;

	protected JPanel buttons;

	private Input<K> input;

	private boolean question = false;

	public CollectionInput(C collection, Object... args) {
		this(collection, null, null, args);
	}

	public CollectionInput(K defaultValue, Object... args) {
		this(null, defaultValue, null, args);
	}

	public CollectionInput(Input<K> input, Object... args) {
		this(null, null, input, args);
	}

	@SuppressWarnings("unchecked")
	private CollectionInput(C collection, K defaultValue, Input<K> input, Object... args) {
		super(new BorderLayout());

		JList<K> jlist = new JList<>(this.model = getListModel(args));
		this.add(new JScrollPane(jlist), BorderLayout.CENTER);

		this.buttons = new JPanel();
		CUDpanel cud = new CUDpanel(this);
		buttons.add(cud);

		this.add(buttons, BorderLayout.SOUTH);

		if (defaultValue == null) {
			if (collection != null) {
				if (collection.size() != 0)
					this.defaultValue = (K) ReflectionUtils.getNull(collection.iterator().next().getClass());
				else
					throw new IllegalArgumentException("A coleção está vazia.");
			} else {
				this.defaultValue = null;
				if (input != null)
					this.input = input;
				else
					throw new IllegalArgumentException(
							"Se não for dado um objeto padrão, deve-se ao menos indicar o componente editor.");
			}
		} else
			this.defaultValue = defaultValue;

		this.set(collection);
	}

	public void setConfirmQuestion(boolean question) {
		this.question = question;
	}

	public void setEditionItem(boolean edition) {
		((CUDpanel) this.buttons.getComponent(0)).setEditable(edition);
	}

	// ------------------------- INPUT -------------------------

	public void clear() {
		this.set(null);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// -------------------- MÉTODOS DE INTERFACEMENTO --------------------

	// list model

	/**
	 * Função que instancia o modelo da lista
	 * 
	 * @param args argumentos usados ao se instanciar o modelo da lista (pode ser
	 *             vazio, eventualmente)
	 * @return modelo da lista
	 */
	protected abstract AbstractListModel<K> getListModel(Object... args);

	protected abstract K get(int selected);

	public abstract void addElement(K value);

	protected abstract int indexOf(K value);

	public abstract K set(int selected, K value);

	protected abstract K removeElement(int selected);

	public AbstractListModel<K> getModel() {
		return model;
	}

	// buttons

	public void setEditable(boolean b) {
		((CUDpanel) this.buttons.getComponent(0)).setEnabled(b);
	}

	/**
	 * 
	 * @param d
	 *          <ol>
	 *          <li>para {@link Double#valueOf(String) converter} o texto em um
	 *          número decimal longo;</i>
	 *          <li>para {@link Integer#parseInt(String) converter} o texto em um
	 *          número inteiro;</i>
	 *          <li>para {@link Float#parseFloat(String) converter} o texto em um
	 *          número decimal;</i>
	 *          <li>para {@link Long#parseLong(String) converter} o texto em um
	 *          número inteiro longo.</i>
	 *          </ol>
	 * 
	 *          Qualquer outro valor para manter a sequência de caracteres
	 */
	public void addPasteButton(int d) {
		PasteListButton<K> plb = new PasteListButton<>(this.model, d);
		plb.setPreferredSize(SwingUtils.DIM_BUTTON_ICON);
		buttons.add(plb);
	}

	// JList

	public JList<?> getList() {
		return ((JList<?>) ((JScrollPane) this.getComponent(0)).getViewport().getView());
	}

	public int getSelectedIndex() {
		return getList().getSelectedIndex();
	}

	@SuppressWarnings("unchecked")
	public K getSelectedValue() {
		return (K) getList().getSelectedValue();
	}

	public void setFormatter(String format) {
		getList().setCellRenderer(new FormatterRenderer(format));
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		getList().addListSelectionListener(listener);
	}

	// JScrollPane

	@Override
	public void setPreferredSize(Dimension size) {
		((JScrollPane) this.getComponent(0)).setPreferredSize(size);
	}

	// ------------------------- LISTENER -------------------------

	private ActionListener listener;

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent event) {
		int selected = getList().getSelectedIndex();
		K value = null;

		switch (event.getActionCommand()) {
		case CUDpanel.EDIT:
			if (selected >= 0)
				value = (K) get(selected);
			else
				break;
		case CUDpanel.NEW:
			if (value == null) {
				value = defaultValue;
				selected = -1;
			}

			JComponent c = null;
			if (input == null)
				c = SwingUtils.getEditor(value);
			else {
				input.set(value);
				c = (JComponent) input.getComponent();
			}

			// esse listener chama o foco do cursor para a 'DoubleInput' assim que este é
			// inserido no 'JOptionPane'
			c.addAncestorListener(new RequestFocusListener(true));

			int out = JOptionPane.showConfirmDialog(this, c, "Entrada de dados", JOptionPane.DEFAULT_OPTION);
			if (out != JOptionPane.OK_OPTION)
				break;

			if (input == null)
				value = (K) SwingUtils.getValueFromEditor(c);
			else
				value = input.get();

			if (value != null) {
				if (selected == -1) {
					addElement(value);
					if (this.listener != null)
						this.listener.actionPerformed(
								new ActionEvent(value, ActionEvent.ACTION_PERFORMED, event.getActionCommand()));
					// selecionar o recém-adicionado
					selected = indexOf(value);
					((JList<?>) ((JScrollPane) this.getComponent(0)).getViewport().getView())
							.setSelectedIndex(selected);
				} else {
					K removed = set(selected, value);
					if (!removed.equals(value) && this.listener != null) {
						ArrayList<K> list = new ArrayList<>(2);
						list.add(removed);
						list.add(value);
						this.listener.actionPerformed(
								new ActionEvent(list, ActionEvent.ACTION_PERFORMED, event.getActionCommand()));
					}
				}
			}
			break;
		case CUDpanel.DELETE:
			if (selected < 0)
				break;

			boolean erase = true;
			if (question)
				erase = JOptionPane.showConfirmDialog(SwingUtils.getWindow(this), "Deseja eliminar o item?",
						"Apagar o item", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

			if (erase) {
				K removed = removeElement(selected);
				if (this.listener != null)
					this.listener.actionPerformed(
							new ActionEvent(removed, ActionEvent.ACTION_PERFORMED, event.getActionCommand()));
			}
			break;
		}
	}
}
