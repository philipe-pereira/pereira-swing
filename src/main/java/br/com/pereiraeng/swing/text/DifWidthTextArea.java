package br.com.pereiraeng.swing.text;

import br.com.pereiraeng.swing.MonoSpacedFont;

public class DifWidthTextArea extends ColumnsTextArea {
	private static final long serialVersionUID = 1L;

	private int[] columnAbs;

	public DifWidthTextArea(MonoSpacedFont font, int columns, int[] columnAbs) {
		super(font, columns);
		this.setColumnAbs(columnAbs);
	}

	public void setColumnAbs(int[] columnAbs) {
		this.columnAbs = columnAbs;
		if (getColumns() < columnAbs[columnAbs.length - 1])
			throw new IllegalArgumentException("A abs");
	}

	@Override
	protected int getPos(int c) {
		return columnAbs[c];
	}
}
