package br.com.pereiraeng.swing.input.clc;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import br.com.pereiraeng.swing.button.PNpanel;
import br.com.pereiraeng.swing.input.Input;

public class ListInput<K> extends CollectionInput<K, List<K>> {

	private static final long serialVersionUID = 1L;

	public ListInput(List<K> list) {
		super(list);
		super.buttons.add(new PNpanel(this));
	}

	public ListInput(K defaultValue) {
		super(defaultValue);
		super.buttons.add(new PNpanel(this));
	}

	public ListInput(Input<K> input) {
		super(input);
		super.buttons.add(new PNpanel(this));
	}

	// ------------------------- INPUT -------------------------

	@Override
	public void set(List<K> list) {
		DefaultListModel<K> m = ((DefaultListModel<K>) super.model);
		m.clear();
		if (list != null)
			for (K e : list)
				m.addElement(e);
	}

	@Override
	public List<K> get() {
		return Collections.list(((DefaultListModel<K>) super.model).elements());
	}

	// list model

	@Override
	protected AbstractListModel<K> getListModel(Object... args) {
		return new DefaultListModel<>();
	}

	@Override
	protected K get(int selected) {
		return ((DefaultListModel<K>) super.model).get(selected);
	}

	@Override
	public void addElement(K value) {
		((DefaultListModel<K>) super.model).addElement(value);
	}

	@Override
	protected int indexOf(K value) {
		return ((DefaultListModel<K>) super.model).indexOf(value);
	}

	@Override
	public K set(int selected, K value) {
		return ((DefaultListModel<K>) super.model).set(selected, value);
	}

	@Override
	protected K removeElement(int selected) {
		return ((DefaultListModel<K>) super.model).remove(selected);
	}

	// ------------------------- LISTENER -------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		String comm = event.getActionCommand();
		switch (comm) {
		case PNpanel.PREVIOUS:
		case PNpanel.NEXT:
			int selected = getList().getSelectedIndex();
			if (selected >= 0) {
				int newPos = selected + (PNpanel.NEXT.equals(comm) ? 1 : -1);
				DefaultListModel<K> m = (DefaultListModel<K>) super.model;
				if (newPos >= 0 && newPos < m.getSize()) {
					K value = m.remove(selected);
					m.insertElementAt(value, newPos);
					getList().setSelectedIndex(newPos);
				}
			}
			break;
		default:
			super.actionPerformed(event);
			break;
		}
	}
}
