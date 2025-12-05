package br.com.pereiraeng.swing.scroll;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Classe do objeto gráfico de um
 * @author Philipe PEREIRA
 *
 */
public class JanelaRolavel extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public JanelaRolavel(Component view, Dimension size, Bordura rowHeader,
			Bordura columnHeader, Bordura cornerUL, Bordura cornerLL,
			Bordura cornerRC) {
		super(view, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		view.revalidate();
		super.setPreferredSize(size);

		super.setViewportBorder(BorderFactory.createLineBorder(Color.black));

		if (columnHeader != null)
			super.setColumnHeaderView(columnHeader);
		if (rowHeader != null)
			super.setRowHeaderView(rowHeader);

		if (cornerUL != null)
			super.setCorner(JScrollPane.UPPER_LEFT_CORNER, cornerUL);
		if (cornerLL != null)
			super.setCorner(JScrollPane.LOWER_LEFT_CORNER, cornerLL);
		if (cornerRC != null)
			super.setCorner(JScrollPane.UPPER_RIGHT_CORNER, cornerRC);
	}

	public JanelaRolavel(Component view, Dimension size) {
		this(view, size, null, null, null, null, null);
	}

	public void setMousePosition(Point point) {
		Component c = super.getColumnHeader().getView();
		if (c instanceof Regua) {
			Regua regua = (Regua) c;
			regua.setMarcador(point.x);
		}

		c = super.getRowHeader().getView();
		if (c instanceof Regua) {
			Regua regua = (Regua) c;
			regua.setMarcador(point.y);
		}
	}

	public void setFolhaSize(Dimension dimension) {
		Component c = super.getViewport().getComponent(0);
		if (c instanceof Component) {
			Component panel = (Component) c;
			panel.setPreferredSize(dimension);
			panel.revalidate();
		}

		c = super.getColumnHeader().getView();
		if (c instanceof Regua) {
			Regua regua = (Regua) c;
			regua.setPreferredWidth(dimension.width);
			regua.revalidate();
		}

		c = super.getRowHeader().getView();
		if (c instanceof Regua) {
			Regua regua = (Regua) c;
			regua.setPreferredHeight(dimension.height);
			regua.revalidate();
		}
	}
}