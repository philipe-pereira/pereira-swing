package br.com.pereiraeng.swing;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;

import br.com.pereiraeng.swing.time.ClockPanel;

public abstract class Manager extends JPanel {
	private static final long serialVersionUID = 5073320398385514033L;

	// parte gráfica

	protected ClockPanel clock;

	protected JDesktopPane dp;

	public Manager(ClockPanel clock) {
		super(new BorderLayout());
		this.clock = clock;
		add(this.dp = new JDesktopPane(), BorderLayout.CENTER);
	}
}
