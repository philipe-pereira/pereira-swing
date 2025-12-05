package br.com.pereiraeng.swing.input;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.core.ColorUtils;

/**
 * Classe dos objetos gráficos que permitem ao usuário o particionamento sobre
 * um conjunto de objetos de um percentual cuja soma resulta na unidade
 * 
 * @author Philipe PEREIRA
 *
 * @param <T> classe dos objetos do conjunto
 */
public class PartPercInput<T> extends JPanel implements Input<double[]>, ListSelectionListener, ChangeListener {
	private static final long serialVersionUID = 1L;

	private JList<T> l;
	private DefaultListModel<T> lm;

	private JLabel label;
	private JSpinner spn;

	private double[] values;

	private transient boolean editable = true;

	/**
	 * Construtor do objeto gráfico do particionamento percentual
	 * 
	 * @param list    nomes das partes
	 * @param initial partição inicial
	 * @param step    decimal que indica o passo mínimo das partições
	 */
	public PartPercInput(T[] list, double[] initial, final double step) {
		super(new BorderLayout());

		lm = new DefaultListModel<T>();
		for (int i = 0; i < list.length; i++)
			lm.addElement(list[i]);
		l = new JList<T>(lm);
		l.addListSelectionListener(this);
		l.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JScrollPane sp = new JScrollPane(l, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(sp, BorderLayout.CENTER);

		Grade p = new Grade();

		p.add(label = new JLabel(), 0, 0, 1, 1);

		spn = new JSpinner(new SpinnerNumberModel(0., 0., 100., step));
		spn.setEnabled(false);
		spn.addChangeListener(this);
		p.add(spn, 0, 2, 1, 1);

		add(p, BorderLayout.EAST);

		add(new DistChart(initial.length * 30), BorderLayout.WEST);

		set(initial);
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	// ------------------------- INPUT -------------------------

	@Override
	public void set(double[] values) {
		this.values = values;
	}

	@Override
	public double[] get() {
		return values;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ------------------------- LISTENER -------------------------

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			T t = l.getSelectedValue();
			label.setText(t.toString());

			double value = this.values[l.getSelectedIndex()];
			innerChange = true;
			spn.setEnabled(editable);
			spn.setValue(value);
			innerChange = false;
		}
	}

	private transient boolean innerChange = false;

	@Override
	public void stateChanged(ChangeEvent e) {
		if (!innerChange) {
			int index = l.getSelectedIndex();

			double oldValue = this.values[index];
			double newValue = ((Number) this.spn.getValue()).doubleValue();

			double var = newValue - oldValue;
			final double step = ((SpinnerNumberModel) this.spn.getModel()).getStepSize().doubleValue();
			var = Math.round(var / step) * step; // respeitar o step

			// vetor das variações em cada uma das partições
			double[] varDist = new double[this.values.length];

			// tira/dá um pouco de/para todos os outros (proporcional)
			double currentSumOthers = 0.;
			for (int i = 0; i < varDist.length; i++) {
				if (i == index)
					varDist[i] = var;
				else
					currentSumOthers += this.values[i];
			}

			if (currentSumOthers == 0.) // se todos os outros não tinham nada, dê ao próximo
				varDist[(index + 1) % varDist.length] = -var;
			else { // dist como múltiplos do step, priorizando os maiores e respeitando o step
				int st = (int) (var / step);
				for (int i = 0; i < varDist.length && st != 0; i++) {
					if (i != index) {
						long s = Math.round((var * this.values[i] / currentSumOthers) / step);
						varDist[i] = -s * step;
						st -= s;
					}
				}
				if (st != 0)
					varDist[(index + 1) % varDist.length] -= st;
			}

			// atualizar valores
			for (int i = 0; i < varDist.length; i++)
				this.values[i] += varDist[i];

			repaint();
		}
	}

	private class DistChart extends JPanel {
		private static final long serialVersionUID = 1L;

		private static final int WIDTH = 60;

		public DistChart(int height) {
			this.setPreferredSize(new Dimension(WIDTH, height));
		}

		@Override
		protected void paintComponent(Graphics g) {
			int h = this.getHeight();
			// retângulos
			int off = 0;
			for (int i = 0; i < values.length; i++) {
				if (values[i] > 0.) {
					int hr = (int) (values[i] * h / 100.);
					g.setColor(ColorUtils.getColor(i));
					g.fillRect(0, off, WIDTH, hr);
					off += hr;
				}
			}
			// valores
			off = 0;
			for (int i = 0; i < values.length; i++) {
				if (values[i] > 0.) {
					g.setColor(ColorUtils.inverse(ColorUtils.getColor(i)));
					g.drawString(lm.get(i).toString(), 0, off + 12);
					g.drawString(String.format("%.1f%%", values[i]), 0, off + 24);
					off += (int) (values[i] * h / 100.);
				}
			}
		}
	}
}