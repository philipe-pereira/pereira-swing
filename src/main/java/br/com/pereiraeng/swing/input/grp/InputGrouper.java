package br.com.pereiraeng.swing.input.grp;

import br.com.pereiraeng.swing.input.Input;

public class InputGrouper<T> extends Grouper<T> {
	private static final long serialVersionUID = 1L;

	private Input<T> input;

	/**
	 * 
	 * @param input
	 * @param columnHeader nome da coluna (<code>null</code> para não haver tabela)
	 * @param n            número inicial de grupos
	 * @param max          número máximo de grupos
	 * @param editable     <code>true</code> para poder alterar o número de grupos,
	 *                     <code>false</code> senão
	 * @param labels       etiquetas do grupo
	 */
	public InputGrouper(Input<T> input, String columnHeader, int n, int max, boolean editable, String... labels) {
		super(input.getComponent(), columnHeader, n, max, editable, labels);
		this.input = input;
	}

	@Override
	protected T getSelected() {
		return this.input.get();
	}

	public String[] get() {
		String[] out = new String[getCount()];
		for (int i = 0; i < out.length; i++) {
			String s = getTextField(i).getText();
			if (!"".equals(s))
				out[i] = s;
		}
		return out;
	}

	public void set(T[] ts) {
		for (int i = 0; i < ts.length; i++)
			getTextField(i).setText(ts[i] != null ? ts[i].toString() : "");
	}
}
