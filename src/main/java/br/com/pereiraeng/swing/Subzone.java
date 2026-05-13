package br.com.pereiraeng.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class Subzone extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JDesktopPane zone;

	/**
	 * Componente do tipo <code>JInternalFrame</code> que contém uma área de
	 * trabalho própria, onde nesta podemos abrir aplicativos a partir da barra de
	 * butões superior
	 * 
	 * @param title       título da janela
	 * @param resizable   se <code>true</code> a janela pode ser redimensionada
	 * @param maximizable se <code>true</code> a janela pode ser maximizada
	 * @param size        dimensões da janela
	 * @param buttons     butões da barra superior
	 */
	public Subzone(String title, boolean resizable, boolean maximizable, Dimension size, String[][][] buttons) {

		// propriedades do JInternalFrame

		this.setTitle(title);
		this.setClosable(true);
		this.setResizable(resizable);
		this.setMaximizable(maximizable);
		this.setSize(size);

		// conteúdo

		this.getContentPane().add(SwingUtils.getBar(buttons, this), BorderLayout.NORTH);

		// zona de trabalho

		this.zone = new JDesktopPane();
		this.zone.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		this.getContentPane().add(zone, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent event) {
		String[] commands = event.getActionCommand().split(";");
		JInternalFrame f = Subzone.loadingApp(commands);

		if (f != null) {
			f.setVisible(true);
			try {
				f.setSelected(true);
			} catch (PropertyVetoException exception) {
				System.err.println(exception.getMessage());
			}
			zone.add(f);
		}
	}

	// ---------------------- AUXILIAR ----------------------

	/**
	 * Função que dá partida num dado aplicativo do Subespaço seguindo certos
	 * comandos. As funções são chamadas na seguinte ordem:
	 * 
	 * <ul>
	 * <li>{@link App#build(java.awt.Component)}</i>
	 * <li>{@link App#open(String)}</i>
	 * <li>{@link App#start()}</i>
	 * </ul>
	 * 
	 * 
	 * @param commands comandos a serem seguidos
	 * @return janela a ser adicionada na zona de trabalho
	 */
	public static JInternalFrame loadingApp(String... commands) {
		App a = null;
		JInternalFrame f = null;

		try {
			a = (App) Class.forName(commands[0]).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (a != null) {
			f = new JInternalFrame();

			// propriedades do JInternalFrame
			f.setTitle(a.getTitle());
			f.setClosable(true);
			f.setResizable(a.isResizable());
			f.setMaximizable(a.isMaximizable());
			f.setSize(a.getWindowSize());

			f.addInternalFrameListener(new WindowCloser(a));

			// conteúdo
			a.build(f);

			// abrir arquivos
			for (int i = 1; i < commands.length; i++)
				a.open(commands[i]);

			// começa execução
			a.start();
		}
		return f;
	}
}