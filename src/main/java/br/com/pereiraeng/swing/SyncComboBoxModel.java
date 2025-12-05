package br.com.pereiraeng.swing;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class SyncComboBoxModel<K> implements ComboBoxModel<K> {

	private AbstractListModel<K> model;

	private Object selectedObject;

	public SyncComboBoxModel(AbstractListModel<K> model) {
		this.model = model;
	}

	@Override
	public Object getSelectedItem() {
		return selectedObject;
	}

	@Override
	public void setSelectedItem(Object anObject) {
		if ((selectedObject != null && !selectedObject.equals(anObject))
				|| selectedObject == null && anObject != null) {
			selectedObject = anObject;
			// TODO fire?
		}
	}

	// LIST MODEL

	@Override
	public void addListDataListener(ListDataListener l) {
		model.addListDataListener(l);
	}

	@Override
	public K getElementAt(int index) {
		return model.getElementAt(index);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		model.removeListDataListener(l);
	}
}
