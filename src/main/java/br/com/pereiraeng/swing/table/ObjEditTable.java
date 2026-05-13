package br.com.pereiraeng.swing.table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import br.com.pereiraeng.core.EditableFields;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico de uma tabela editável com apenas uma coluna e com
 * número de linhas variável que recebe como entrada objetos de um classe que
 * implemente a interface {@link EditableFields}
 * 
 * @author Philipe PEREIRA
 *
 * @param <K>
 *            classe que implementa a interface {@link EditableFields}
 */
public class ObjEditTable<K extends EditableFields> extends OneColumnTable implements Input<K>, TableModelListener {
	private static final long serialVersionUID = 1L;

	private K k;

	public ObjEditTable() {
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		addTableModelListener(this);
	}

	@Override
	public void set(K k) {
		innerChange = true;
		this.k = k;
		if (this.k != null)
			setDisplayableFieldObj(k);
		else
			clear();
		innerChange = false;
	}

	@Override
	public K get() {
		return k;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ------------------------- LISTENER -------------------------

	private transient boolean innerChange = false;

	@Override
	public void tableChanged(TableModelEvent event) {
		if (event.getType() == TableModelEvent.UPDATE && !innerChange) {
			int row = event.getFirstRow();
			Object o = super.getValueAt(row, 0);
			if (o != null)
				k.setField(row, o);
		}
	}
}
