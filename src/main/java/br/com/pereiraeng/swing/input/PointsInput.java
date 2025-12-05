package br.com.pereiraeng.swing.input;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import br.com.pereiraeng.swing.table.ResizableTable;

/**
 * Classe do objeto gráfico que permite a inserção de uma lista ordenada de
 * pontos através de uma tabela
 * 
 * @author Philipe PEREIRA
 *
 */
public class PointsInput extends ResizableTable implements Input<List<Point2D.Double>>, ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor do objeto de inserção de uma lista de pontos
	 * 
	 * @param paste  <code>true</code> para deixar visível o ponto que permite colar
	 *               valores da área de transferência
	 * @param points lista inicial de pontos
	 */
	public PointsInput(boolean paste, List<Point2D.Double> points) {
		super(new String[] { "x", "y" }, paste);
		this.set(points);
	}

	// para os elementos que são inseridos ao se clicar no botão 'nova linha', ao
	// invés de gerar um novo elemento a partir de sua classe, usa-se a sobrescrição
	// do método definida abaixo:

	@Override
	protected void insertRow(int row) {
		super.insertRow(row, new Double[] { 0., 0. });
	}

	// ------------------------- INPUT -------------------------

	@Override
	public void set(List<Point2D.Double> k) {
		if (k == null) {
			// clear
			setRowCount(0);
		} else {
			// ajusta o tamanho da tabela
			setRowCount(k.size());
			// repassa para a interface gráfica
			int r = 0;
			for (Point2D.Double e : k) {
				setValueAt(e.x, r, 0);
				setValueAt(e.y, r, 1);
				r++;
			}
		}
	}

	@Override
	public List<Point2D.Double> get() {
		int rows = super.getRowCount();

		ArrayList<Point2D.Double> newList = new ArrayList<>(rows);
		// unload table
		for (int r = 0; r < rows; r++) {
			Object x = super.getValueAt(r, 0);
			Object y = super.getValueAt(r, 1);
			newList.add(new Point2D.Double((Double) x, (Double) y));
		}

		return newList;
	}

	@Override
	public Component getComponent() {
		return this;
	}
}
