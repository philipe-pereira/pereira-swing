package br.com.pereiraeng.swing.list.rendering;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Classe que faz uma adaptação do {@link DefaultListCellRenderer renderizador
 * padrão das listas}, onde o objeto recebido é um vetor onde somente uma das
 * posiões será exibida.
 * 
 * @author Philipe PEREIRA
 *
 */
public class ArrayRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	private final int pos;

	public ArrayRenderer(int pos) {
		this.pos = pos;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value instanceof Object[]) {
			Object[] values = (Object[]) value;
			return super.getListCellRendererComponent(list, pos < values.length ? values[pos] : null, index, isSelected,
					cellHasFocus);
		} else
			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}
