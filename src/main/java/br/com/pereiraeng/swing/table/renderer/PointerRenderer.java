package br.com.pereiraeng.swing.table.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.pereiraeng.swing.image.Pointer;

/**
 * <p>
 * Classe do renderizador de células de tabela em que são desenhos círculos de
 * diferentes cores para representar os diferentes valores
 * </p>
 * 
 * <ul>
 * <li>caso o valor da célula seja um inteiro, a cor é determinada conforme a
 * tabela a seguir:
 * <ul>
 * <li>{@link Pointer#NO -4}: cinza</i>
 * <li>{@link Pointer#BAD 0}: vermelho</i>
 * <li>{@link Pointer#NEUTRAL 1}: amarelo</i>
 * <li>{@link Pointer#OK 2}: verde</i>
 * <li>{@link Pointer#GOOD 4}: azul</i>
 * </ul>
 * e para qualquer outro valor, azul;</i>
 * <li>caso o valor da célula seja um booleano, o valor <code>true</code> remete
 * a um círculo verde e o <code>false</code> a um círculo vermelho.</i>
 * </ul>
 * 
 * @author Philipe PEREIRA
 *
 */
public class PointerRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
		if (value instanceof Integer)
			l.setIcon(new Pointer((Integer) value));
		else if (value instanceof Byte)
			l.setIcon(new Pointer((Byte) value));
		else if (value instanceof Boolean)
			l.setIcon(new Pointer((Boolean) value));
		return l;
	}
}
