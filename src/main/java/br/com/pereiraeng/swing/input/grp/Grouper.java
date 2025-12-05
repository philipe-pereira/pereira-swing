package br.com.pereiraeng.swing.input.grp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import br.com.pereiraeng.swing.Grade;

public abstract class Grouper<T> extends Grade implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;

	private static final int FIX = 3;

	private static final int ROW_HEIGHT = 26, SEP_HEIGHT = 2, HEADER_HEIGHT = 22;

	/**
	 * 
	 * @param component
	 * @param columnHeader nome da coluna (<code>null</code> para não haver tabela)
	 * @param n            número inicial de grupos
	 * @param max          número máximo de grupos
	 * @param editable     <code>true</code> para poder alterar o número de grupos,
	 *                     <code>false</code> senão
	 * @param labels       etiquetas do grupo
	 */
	public Grouper(Component component, String columnHeader, int n, int max, boolean editable, String... labels) {
		super.add(component, 0, 0, 1, max * (labels.length + 1) + 2);

		JTable t = new JTable(new DefaultTableModel(new String[] { columnHeader }, n));
		t.setRowHeight(ROW_HEIGHT * labels.length + SEP_HEIGHT);
		JScrollPane sp = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(80, ((ROW_HEIGHT * labels.length + SEP_HEIGHT) * n) + HEADER_HEIGHT));
		sp.setVisible(columnHeader != null);
		super.add(sp, 4, 0, 1, max * (labels.length + 1) + 1);

		JSpinner groups = new JSpinner(new SpinnerNumberModel(n, 1, max, 1));
		groups.addChangeListener(this);
		groups.setVisible(editable);
		super.add(groups, 1, 0, 3, 1);

		createFields(labels, 0, n);
	}

	protected abstract T getSelected();

	private void createFields(String[] labels, int oldValue, int newValue) {
		if (oldValue > 0)
			super.add(new JSeparator(), 1, oldValue * (labels.length + 1), 3, 1);

		for (int i = oldValue; i < newValue; i++) {
			for (int j = 0; j < labels.length; j++) {
				int k = j + labels.length * i;

				JButton b = new JButton("\u25B6");
				b.setActionCommand(String.valueOf(k));
				b.addActionListener(this);
				super.add(b, 1, k + i + 1, 1, 1);

				super.add(new JLabel(labels[j]), 2, k + i + 1, 1, 1);

				JTextField tf = new JTextField(10);
				tf.setEditable(false);
				super.add(tf, 3, k + i + 1, 1, 1);
			}
			if (i < newValue - 1)
				super.add(new JSeparator(), 1, (i + 1) * (labels.length + 1), 3, 1);
		}
	}

	private int getLabelCount() {
		int i;
		for (i = 0; i < super.getComponentCount(); i++)
			if (super.getComponent(i) instanceof JSeparator)
				break;
		return (i - FIX) / 3;
	}

	private String[] getLabels() {
		List<String> out = new LinkedList<>();
		for (int i = 0; i < super.getComponentCount(); i++) {
			Component c = super.getComponent(i);
			if (c instanceof JLabel)
				out.add(((JLabel) c).getText());
			else if (c instanceof JSeparator)
				break;
		}
		return out.toArray(new String[out.size()]);
	}

	private int getBlocks() {
		return (getComponentCount() - (FIX - 1)) / (3 * getLabelCount() + 1);
	}

	protected int getCount() {
		int ls = getLabelCount();
		return (getComponentCount() - (FIX - 1)) / (3 * ls + 1) * ls;
	}

	protected JTextField getTextField(int i) {
		int ls = getLabelCount();
		int j = i / ls;
		int k = i % ls;
		return (JTextField) getComponent(FIX + j * (3 * ls + 1) + 2 + 3 * k);
	}

	// --------------------- MÉTODOS DE INTERFACEAMENTO ---------------------

	// métodos do JTable

	public JTable getTable() {
		JScrollPane sp = (JScrollPane) getComponent(1);
		if (sp.isVisible())
			return (JTable) sp.getViewport().getView();
		else
			return null;
	}

	// ------------------------ LISTENER ------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		T t = getSelected();
		if (t != null)
			getTextField(Integer.parseInt(event.getActionCommand())).setText(t.toString());
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		int newValue = (int) ((JSpinner) event.getSource()).getValue();
		int oldValue = getBlocks();

		if (newValue > oldValue) // criar novas células
			createFields(getLabels(), oldValue, newValue);
		else {
			// remover
			int ls = getLabelCount();
			int e = (oldValue - newValue) * (3 * ls + 1);
			int r = (FIX - 1) + newValue * (3 * ls + 1);
			for (int i = 0; i < e; i++)
				remove(r);
		}
		revalidate();
		repaint();

		JTable t = getTable();
		if (t != null) { // se a tabela for visível (a cada grupo está associado um item na tabela)
			JScrollPane sp = (JScrollPane) t.getParent().getParent();
			sp.setPreferredSize(
					new Dimension(80, ((ROW_HEIGHT * getLabelCount() + SEP_HEIGHT) * newValue) + HEADER_HEIGHT));
			DefaultTableModel dtm = (DefaultTableModel) t.getModel();
			dtm.setRowCount(newValue);
			if (newValue > oldValue)
				dtm.setValueAt(dtm.getValueAt(newValue - 2, 0), newValue - 1, 0);
		}
	}
}
