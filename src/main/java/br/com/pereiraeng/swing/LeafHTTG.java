package br.com.pereiraeng.swing;

import java.util.Collection;
import java.util.Map.Entry;

import br.com.pereiraeng.swing.interfaces.DesHT;

import java.util.TreeMap;
import java.util.TreeSet;

public abstract class LeafHTTG<T extends DesHT> extends LeafHT {
	private static final long serialVersionUID = -2962652053713467591L;

	private int minWidthCell;

	protected int rows, cols;

	public LeafHTTG(int rows, int cols) {
		this(rows, cols, 80);
	}

	public LeafHTTG(int rows, int cols, int minWidthCell) {
		this.rows = rows;
		this.cols = cols;
		this.minWidthCell = minWidthCell;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * Função que retorna ao componente gráfico a lista de objetos a serem
	 * desenhados
	 * 
	 * @return relação de objetos a serem desenhados sobre o painel
	 */
	public abstract Collection<T> getList();

	// -------------------------- DRAWER --------------------------

	public final void draw() {
		String tbody = "";
		Collection<T> l = getList();
		if (l != null)
			tbody = getHTML(l, rows, minWidthCell);
		super.setText("<table cellspacing=\"0\">" + tbody + "</table>");
	}

	public static <T extends DesHT> String getHTML(Collection<T> l, int rows, int minWidthCell) {
		StringBuffer out = new StringBuffer();
		TreeSet<T> ds = new TreeSet<>(l);
		TreeMap<Integer, TreeMap<Integer, Object[]>> map = new TreeMap<>();
		for (DesHT d : ds) {
			if (d.isDrawable()) {
				String[] cs = new String[d.getH()];
				d.draw(cs, minWidthCell);
				for (int i = 0; i < cs.length; i++) {
					TreeMap<Integer, Object[]> rm = map.get(d.getY() + i);
					if (rm == null)
						map.put(d.getY() + i, rm = new TreeMap<>());
					rm.put(d.getX(), new Object[] { cs[i], d.getW() });
				}
			}
		}

		int x = 0;
		int y = 0;
		out.append("<tr>");
		for (Entry<Integer, TreeMap<Integer, Object[]>> e1 : map.entrySet()) {
			int yo = e1.getKey();
			for (Entry<Integer, Object[]> e2 : e1.getValue().entrySet()) {
				Object[] objs = e2.getValue();
				int xo = e2.getKey();
				int w = (int) objs[1];

				while (y < yo) {
					int resto = rows - x;
					if (resto > 1)
						out.append("<td colspan=\"" + resto + "\"></td>");
					else if (resto == 1)
						out.append("<td></td>");
					out.append("</tr>\r\n<tr>");
					// carriage return
					y++;
					x = 0;
				}

				if (y > 13)
					System.out.println();

				if (x < xo) {
					int resto = xo - x;
					out.append("<td width=\"" + (minWidthCell * resto));
					if (resto > 1)
						out.append("\" colspan=\"" + resto);
					out.append("\"></td>");
					x = xo;
				}
				out.append(objs[0]);
				x += w;
			}
		}
		int resto = rows - x;
		if (resto > 1)
			out.append("<td colspan=\"" + resto + " \"></td>");
		else if (resto == 1)
			out.append("<td width=\"" + minWidthCell + "\"></td>");
		out.append("</tr>");

		return out.toString();
	}
}
