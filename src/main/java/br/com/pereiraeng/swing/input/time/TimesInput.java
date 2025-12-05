package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.JList;
import javax.swing.JScrollPane;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.button.CUDpanel;
import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.list.model.ListSetModel;
import br.com.pereiraeng.swing.list.rendering.FormatterRenderer;

/**
 * Classe do objeto gráfico que permite a seleção de mais de uma data, formando
 * assim um conjunto
 * 
 * @author Philipe PEREIRA
 *
 */
public class TimesInput extends Grade implements Input<Calendar[]>, ActionListener {
	private static final long serialVersionUID = 1L;

	private TimeInput ti;
	private JList<Calendar> jList;
	private ListSetModel<Calendar> set;

	public TimesInput() {
		this(Calendar.getInstance());
	}

	public TimesInput(Calendar time) {
		this(time, 60);
	}

	public TimesInput(Calendar time, int listHeight) {
		ti = new TimeInput(time);
		add(ti, 0, 0, 1, 1);

		CUDpanel cud = new CUDpanel(false, this);
		add(cud, 1, 0, 1, 1);

		JScrollPane sp = new JScrollPane(jList = new JList<>(set = new ListSetModel<>(false)));
		jList.setCellRenderer(new FormatterRenderer("%1$tR %1$td-%1$tm-%1$tY"));
		// 223 é a largura do 'TimeInput' com o 'CUDpanel'
		sp.setPreferredSize(new Dimension(223, listHeight));
		add(sp, 0, 1, 2, 1);
	}

	public void setTime(Calendar c) {
		this.ti.set(c);
	}

	@Override
	public void set(Calendar[] k) {
		set.clear();
		for (int i = 0; i < k.length; i++)
			set.addElement(k[i]);
	}

	@Override
	public Calendar[] get() {
		LinkedList<Calendar> l = new LinkedList<Calendar>();
		l.addAll(set.getSet());
		return l.toArray(new Calendar[l.size()]);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	public Calendar get(int i) {
		return set.getElementAt(i);
	}

	public int getItemsCount() {
		return set.getSize();
	}

	@Override
	public void setEnabled(boolean enabled) {
		ti.setEnabled(enabled);
		((CUDpanel) this.getComponent(1)).setEnabled(enabled);
		jList.setEnabled(enabled);
	}

	public boolean isSource(ActionEvent event) {
		return ((CUDpanel) getComponent(1)).isSource(event);
	}

	// ---------------- LISTENER ----------------

	private ActionListener listener;

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		// adicionar ou remover data para análise
		switch (action) {
		case CUDpanel.NEW:
			set.addElement(ti.get());
			break;
		case CUDpanel.DELETE:
			int[] selected = jList.getSelectedIndices();
			for (int j = selected.length - 1; j >= 0; j--)
				set.remove(selected[j]);
			break;
		}
		if (this.listener != null)
			this.listener.actionPerformed(event);
	}
}
