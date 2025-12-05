package br.com.pereiraeng.swing.input.clc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.button.CUDpanel;
import br.com.pereiraeng.swing.button.CoCaPanel;
import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.input.InputUneditable;

public class ListObjectInput<O> extends JPanel implements Input<List<O>>, ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private JList<O> list;
	private DefaultListModel<O> model;
	private InputUneditable<O> input;

	private CUDpanel cud;
	private CoCaPanel coca;

	public ListObjectInput(InputUneditable<O> i) {
		super(new BorderLayout());
		input = i;
		add(new JScrollPane(list = new JList<O>(model = new DefaultListModel<O>())), BorderLayout.WEST);
		list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		add(i.getComponent(), BorderLayout.CENTER);
		input.setEditable(false);

		JPanel p = new JPanel();

		this.cud = new CUDpanel(this);
		p.add(this.cud);

		this.coca = new CoCaPanel(false);
		this.coca.setVisible(false);
		this.coca.addActionListener(this);
		p.add(this.coca);

		add(p, BorderLayout.SOUTH);
	}

	private String prefix;

	private ActionListener listener;

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	public void addActionCommandPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void set(List<O> ns) {
		model.clear();
		if (ns != null)
			for (O n : ns)
				model.addElement(n);
	}

	@Override
	public List<O> get() {
		return Collections.list(model.elements());
	}

	@Override
	public Component getComponent() {
		return this;
	}

	public void setCreateButtonEnabled(boolean e) {
		this.cud.setCreateButtonEnabled(e);
	}

	private transient boolean edit;

	private transient int editPos = -1;

	@Override
	public void actionPerformed(ActionEvent event) {
		String comm = event.getActionCommand();
		switch (comm) {
		case CUDpanel.NEW:
			edit = false;
			input.set(null);
			input.setEditable(true);
			cud.setVisible(false);
			coca.setVisible(true);
			break;
		case CUDpanel.EDIT:
			int p = list.getSelectedIndex();
			if (p >= 0) {
				edit = true;
				editPos = p;
				input.set(list.getSelectedValue());
				input.setEditable(true);
				cud.setVisible(false);
				coca.setVisible(true);
			}
			break;
		case CUDpanel.DELETE:
			p = list.getSelectedIndex();
			if (p >= 0) {
				if (JOptionPane.showConfirmDialog(SwingUtils.getWindow(this), "Deseja eliminar o objeto?", "Apagar",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (listener != null)
						this.listener.actionPerformed(new ActionEvent(input.get(), ActionEvent.ACTION_PERFORMED,
								prefix != null ? prefix + comm : comm));
					model.removeElementAt(p);
				}
			}
			break;
		case CoCaPanel.EDITION_OK:
			O o = input.get();
			if (listener != null)
				this.listener.actionPerformed(new ActionEvent(o, ActionEvent.ACTION_PERFORMED,
						prefix != null ? prefix + (edit ? CUDpanel.EDIT : CUDpanel.NEW)
								: (edit ? CUDpanel.EDIT : CUDpanel.NEW)));
			if (edit) // editar
				model.setElementAt(o, editPos);
			else // novo
				model.addElement(o);
			input.set(null);
		case CoCaPanel.EDITION_CANCEL:
			cud.setVisible(true);
			coca.setVisible(false);
			input.setEditable(false);
			input.set(list.getSelectedValue());
			break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (!event.getValueIsAdjusting()) {
			O o = list.getSelectedValue();
			input.set(o);
		}
	}
}
