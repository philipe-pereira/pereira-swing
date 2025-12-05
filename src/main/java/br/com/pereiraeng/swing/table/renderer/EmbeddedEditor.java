package br.com.pereiraeng.swing.table.renderer;

import javax.swing.JComponent;

public interface EmbeddedEditor<K> {
	public JComponent getEditor(K value);
}
