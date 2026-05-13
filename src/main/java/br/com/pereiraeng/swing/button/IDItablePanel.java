package br.com.pereiraeng.swing.button;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.icons.PereiraIcon;

/**
 * Classe dos objetos gráficos de um conjunto de butões para
 * <strong>I</strong>nserção anterior, <strong>D</strong>eletar ou
 * <strong>I</strong>nserção posterior de linhas ou colunas em tabelas
 * 
 * @author Philipe PEREIRA
 *
 */
public class IDItablePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final String BEFORE = "BEFORE";
	public static final String AFTER = "AFTER";
	public static final String DELETE = "DELETE";

	/**
	 * Construtor do objeto gráfico do conjunto de butões de inserção e deleção de
	 * linhas ou colunas
	 * 
	 * @param column <code>true</code> para colunas, <code>false</code> para linhas
	 */
	public IDItablePanel(boolean column) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JButton b = new JButton(
				column ? PereiraIcon.COLUMN_INSERT_BEFORE.create() : PereiraIcon.ROW_INSERT_BEFORE.create());
		b.setPreferredSize(Icons.DIM_BUTTON_SMALL_ICON);
		b.setActionCommand(BEFORE);
		add(b);

		b = new JButton(column ? PereiraIcon.COLUMN_DELETE.create() : PereiraIcon.ROW_DELETE.create());
		b.setPreferredSize(Icons.DIM_BUTTON_SMALL_ICON);
		b.setActionCommand(DELETE);
		add(b);

		b = new JButton(column ? PereiraIcon.COLUMN_INSERT_AFTER.create() : PereiraIcon.ROW_INSERT_AFTER.create());
		b.setPreferredSize(Icons.DIM_BUTTON_SMALL_ICON);
		b.setActionCommand(AFTER);
		add(b);
	}

	public void addActionListener(ActionListener listener) {
		for (int i = 0; i < this.getComponentCount(); i++)
			((JButton) this.getComponent(i)).addActionListener(listener);
	}

	public void addPrefixCommand(String prefix) {
		for (int i = 0; i < this.getComponentCount(); i++) {
			JButton b = (JButton) this.getComponent(i);
			b.setActionCommand(prefix + b.getActionCommand());
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		((JButton) this.getComponent(0)).setEnabled(enabled);
		((JButton) this.getComponent(1)).setEnabled(enabled);
		((JButton) this.getComponent(2)).setEnabled(enabled);
	}
}