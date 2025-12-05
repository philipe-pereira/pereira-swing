package br.com.pereiraeng.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.pereiraeng.swing.button.CoCaPanel;

/**
 * Classe do objeto gráfico da janela que contém os componentes de edição de um
 * dado tipo de objeto
 * 
 * @author Philipe PEREIRA
 *
 */
public class EditionBox extends DialogBox implements ActionListener {
	private static final long serialVersionUID = 1L;

	private EditionBox(Component comp, ActionListener listener, Window root, String title, Dimension size,
			boolean dependent) {
		super(root, title, size, dependent);
		super.setLayout(new BorderLayout());

		super.add(comp, BorderLayout.CENTER);

		CoCaPanel cc = new CoCaPanel(false);
		cc.addActionListener(this);
		if (listener != null)
			cc.addActionListener(listener);
		super.add(cc, BorderLayout.SOUTH);

		showFrame(false);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// o ActionListener local só serve para fechar a janela (é por isso que
		// durante a construção do objeto deve-se enviar um segundo listener
		// para ouvir os tipos de alterações feitas)
		this.dispose();
	}

	// ----------------------------------------------------------------------

	public static Component getMainComponent(Object source) {
		return ((Component) source).getParent().getParent().getComponent(0);
	}

	public static EditionBox getEditionBox(Object source) {
		return (EditionBox) (Container) ((Component) source).getParent().getParent().getParent().getParent()
				.getParent();
	}
}