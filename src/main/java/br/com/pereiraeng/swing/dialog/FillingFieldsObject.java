package br.com.pereiraeng.swing.dialog;

import java.awt.Window;

import br.com.pereiraeng.core.EditFields;

public class FillingFieldsObject implements EditFields {

	private final Window owner;

	public FillingFieldsObject(Window owner) {
		this.owner = owner;
	}

	@Override
	public Object[] editFields(String description, String[] fieldNames, Object... initialValues) {
		return FillingFields.fillFields(this.owner, description, fieldNames, initialValues);
	}
}
