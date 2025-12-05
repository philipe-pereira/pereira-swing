package br.com.pereiraeng.swing;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.JTextComponent;

/**
 * Escutador que chama o foco do cursor para o componente assim que este é
 * adicionado em um outro componente
 * 
 * @author Philipe PEREIRA
 *
 */
public class RequestFocusListener implements AncestorListener {

	private boolean selection = false;

	/**
	 * Construtor do escutador que chama o foco do cursor para um elemento que
	 * está inserido numa caixa de diálogo
	 */
	public RequestFocusListener() {
		this(false);
	}

	/**
	 * Construtor do escutador que chama o foco do cursor para um elemento que
	 * está inserido numa caixa de diálogo
	 * 
	 * @param selection
	 *            se <code>true</code> e se o proprietário do escutador for uma
	 *            caixa de texto, além do foco a caixa de texto tem seu conteúdo
	 *            selecionado; <code>false</code> para somente requisitar o foco
	 */
	public RequestFocusListener(boolean selection) {
		this.selection = selection;
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
	}

	@Override
	public void ancestorAdded(final AncestorEvent event) {
		final AncestorListener al = this;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JComponent component = (JComponent) event.getComponent();
				component.requestFocusInWindow();
				if (selection && component instanceof JTextComponent)
					((JTextComponent) component).selectAll();
				component.removeAncestorListener(al);
			}
		});
	}
}
