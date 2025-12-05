package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.button.CUDpanel;
import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.list.rendering.FormatterRenderer;

public class HourssInput extends Grade implements Input<Calendar[][]>, ActionListener {
	private static final long serialVersionUID = 1L;

	private HoursInput hi;
	private JList<Calendar[]> list;
	private DefaultListModel<Calendar[]> listModel;

	public HourssInput() {
		add(hi = new HoursInput(), 0, 0, 2, 1);
		add(new CUDpanel(false, this), 2, 0, 1, 1);

		JScrollPane sp = new JScrollPane(list = new JList<>(listModel = new DefaultListModel<>()));
		list.setCellRenderer(new FormatterRenderer("%tR-%tR"));
		sp.setPreferredSize(new Dimension(175, 40));
		add(sp, 0, 1, 3, 1);
	}

	@Override
	public void set(Calendar[][] css) {
		listModel.clear();
		for (int i = 0; i < css.length; i++)
			listModel.addElement(css[i]);
	}

	@Override
	public Calendar[][] get() {
		Calendar[][] out = new Calendar[listModel.size()][];
		for (int i = 0; i < listModel.size(); i++)
			out[i] = listModel.get(i);
		return out;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// -------------------------- LISTENER --------------------------

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case CUDpanel.NEW:
			listModel.addElement(hi.get());
			break;
		case CUDpanel.DELETE:
			int index = list.getSelectedIndex();
			if (index >= 0)
				listModel.remove(index);
			break;
		}
	}
}
