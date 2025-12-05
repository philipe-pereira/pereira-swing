package br.com.pereiraeng.swing.list;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.SwingUtils;

public class TwoLists<T> extends Grade implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JList<T> l1, l2;
	private DefaultListModel<T> lm1, lm2;

	public TwoLists(T[] list1, T[] list2) {
		this(Arrays.asList(list1), Arrays.asList(list2));
	}

	public TwoLists(Collection<T> list1, Collection<T> list2) {
		this(list1, false, list2, false);
	}

	public TwoLists(boolean search1, boolean search2) {
		this(null, search1, null, search2);
	}

	public TwoLists(Collection<T> list1, boolean search1, Collection<T> list2, boolean search2) {
		lm1 = new DefaultListModel<T>();
		if (list1 != null)
			for (T t : list1)
				lm1.addElement(t);

		if (search1) {
			SearchList<T> sl = new SearchList<>(lm1);
			l1 = sl.getList();
			super.add(sl, 0, 0, 1, 4);
		} else {
			l1 = new JList<T>(lm1);
			super.add(new JScrollPane(l1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 0, 0, 1, 4);
		}
		l1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JButton b = new JButton("\u25C0");
		b.setActionCommand("l");
		b.addActionListener(this);
		super.add(b, 1, 0, 1, 1);

		b = new JButton("\u25C0\u25C0");
		b.setActionCommand("L");
		b.addActionListener(this);
		super.add(b, 1, 1, 1, 1);

		b = new JButton("\u25B6");
		b.setActionCommand("r");
		b.addActionListener(this);
		super.add(b, 1, 2, 1, 1);

		b = new JButton("\u25B6\u25B6");
		b.setActionCommand("R");
		b.addActionListener(this);
		super.add(b, 1, 3, 1, 1);

		lm2 = new DefaultListModel<T>();
		if (list2 != null)
			for (T t : list2)
				lm2.addElement(t);

		if (search2) {
			SearchList<T> sl = new SearchList<>(lm2);
			l2 = sl.getList();
			super.add(sl, 2, 0, 1, 4);
		} else {
			l2 = new JList<T>(lm2);
			super.add(new JScrollPane(l2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 2, 0, 1, 4);
		}
		l2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	// ------------------------- GETTER'S -------------------------

	public List<T> get1() {
		return get(true);
	}

	public List<T> get2() {
		return get(false);
	}

	private List<T> get(boolean left) {
		ListModel<T> model = (left ? l1 : l2).getModel();
		ArrayList<T> out = new ArrayList<T>(model.getSize());
		for (int i = 0; i < model.getSize(); i++)
			out.add(model.getElementAt(i));
		return out;
	}

	// --------------------- MÉTODOS DE INTERFACEAMENTO ---------------------

	public void addElement1(T t) {
		lm1.addElement(t);
	}

	public void addElement2(T t) {
		lm2.addElement(t);
	}

	public void setPreferredSize(Dimension dim, boolean left) {
		SwingUtils.getScrollPane(left ? l1 : l2).setPreferredSize(dim);
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		l1.addListSelectionListener(listener);
		l2.addListSelectionListener(listener);
	}

	// ------------------------- LISTENERS -------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		char c0 = event.getActionCommand().charAt(0);
		switch (c0) {
		case 'l':
			List<T> objs = l2.getSelectedValuesList();
			for (T t : objs) {
				lm2.removeElement(t);
				lm1.addElement(t);
			}
			break;
		case 'L':
			objs = get2();
			for (T t : objs) {
				lm2.removeElement(t);
				lm1.addElement(t);
			}
			break;
		case 'r':
			objs = l1.getSelectedValuesList();
			for (T t : objs) {
				lm1.removeElement(t);
				lm2.addElement(t);
			}
			break;
		case 'R':
			objs = get1();
			for (T t : objs) {
				lm1.removeElement(t);
				lm2.addElement(t);
			}
			break;
		}
	}
}
