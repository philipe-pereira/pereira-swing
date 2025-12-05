package br.com.pereiraeng.swing.input;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.pereiraeng.swing.button.CUDpanel;
import br.com.pereiraeng.swing.table.EditableTable;

public class PropertiesInput extends JPanel implements Input<Properties>, ActionListener {
	private static final long serialVersionUID = 1L;

	private EditableTable table;

	public PropertiesInput(Dimension dim) {
		super(new BorderLayout());
		add(table = new EditableTable(0, new Object[] { "Valor" }, false), BorderLayout.CENTER);
		table.setRowHeaderWidth(80);
		table.setPreferredSize(dim);
		CUDpanel cud = new CUDpanel(false, this);
		add(cud, BorderLayout.SOUTH);
	}

	@Override
	public void set(Properties prop) {
		if (prop != null) {
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				this.table.addElementRH(name);
				this.table.addRow(new Object[] { prop.getProperty(name) });
			}
		} else
			table.clear();
	}

	@Override
	public Properties get() {
		int rc = this.table.getRowCount();
		if (rc > 0) {
			Properties out = new Properties();
			for (int i = 0; i < rc; i++)
				out.setProperty(this.table.getElementRH(i), (String) this.table.getValueAt(i, 0));
			return out;
		} else
			return null;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case CUDpanel.NEW:
			String name = (String) JOptionPane.showInputDialog(this, "Digitar o nome da propriedade",
					"Nova propriedade", JOptionPane.PLAIN_MESSAGE, null, null, "");
			if (name != null) {
				this.table.addElementRH(name);
				this.table.addRow(new Object[] { "" });
			}
			break;
		case CUDpanel.DELETE:
			int selected = this.table.getSelectedRow();
			if (selected < 0)
				selected = this.table.getSelectedRowHeader();
			if (selected >= 0)
				table.removeRow(selected);
			break;
		}
	}
}
