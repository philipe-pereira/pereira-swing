package br.com.pereiraeng.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.pereiraeng.core.ColorUtils;
import br.com.pereiraeng.swing.SwingUtils;

/**
 * Classe dos objetos gráficos que renderizam uma célula de tabela que muda de
 * cor em função do seu valor em meio aos demais
 * 
 * @author Philipe PEREIRA
 *
 */
public class ColorScaleRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	private double[] ref;

	private Color[] colors;

	/**
	 * Absoluto (i.e., os valores máximos e mínimos são fixos e fornecidos pelo
	 * usuário)
	 * 
	 * @param ref
	 */
	public ColorScaleRenderer(double[] ref) {
		this(ref, SwingUtils.TRAFFIC_LIGHT);
	}

	/**
	 * 
	 * Absoluto (i.e., os valores máximos e mínimos são fixos e fornecidos pelo
	 * usuário)
	 * 
	 * @param ref
	 * @param colors
	 */
	public ColorScaleRenderer(double[] ref, Color... colors) {
		this(colors);
		this.ref = ref;
		if (this.ref.length != 2 && this.ref.length != colors.length)
			throw new IllegalArgumentException(
					"São necessários dois valores de referência, ou um número de valores igual ao de cores");
	}

	/**
	 * Contextual (i.e., os valores máximos e mínimos são obtidos a partir da
	 * tabela)
	 * 
	 * @param colors
	 */
	public ColorScaleRenderer(Color... colors) {
		if (colors.length > 1)
			this.colors = colors;
		else
			throw new IllegalArgumentException("São necessárias pelo menos 2 cores");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value != null ? value instanceof Number : false) {
			double[] mM = null;
			if (ref == null) {
				mM = new double[] { Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY };
				for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						Number n = (Number) table.getValueAt(i, j);
						mM[0] = Math.min(mM[0], n.doubleValue());
						mM[1] = Math.max(mM[1], n.doubleValue());
					}
				}
			} else if (ref.length == 2)
				mM = new double[] { ref[0], ref[1] };

			// ------------------------------------------------------

			double t = 0., v = ((Number) value).doubleValue();
			int pos = -1;
			if (mM != null) {
				t = (v - mM[0]) / (mM[1] - mM[0]);
				if (ref != null ? t >= 1. : false) {
					// se for maior que o máximo (o que só pode acontecer quando
					// estes não estipulados)
					t = 0.;
					pos = colors.length - 1;
				} else {
					double step = 1. / (colors.length - 1);
					pos = (int) (t / step);
					t = (t - step * pos) / step;
				}
			} else {
				pos = Arrays.binarySearch(ref, v);
				if (pos < 0)
					pos = -pos - 2;
				if (pos + 1 == ref.length) {
					pos = ref.length - 2;
					t = 1f;
				} else
					t = (v - ref[pos]) / (ref[pos + 1] - ref[pos]);
			}

			super.setBackground(t == 0. ? colors[pos] : ColorUtils.getIntermedColor(t, colors[pos], colors[pos + 1]));
		} else
			super.setBackground(null);

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
