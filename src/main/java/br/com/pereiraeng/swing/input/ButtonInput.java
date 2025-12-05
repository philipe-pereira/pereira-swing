package br.com.pereiraeng.swing.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe do objeto gráfico de um botão que, ao ser pressionado, abre uma
 * {@link EditionBox caixa de diálogo} que permitirá a edição de um objeto
 * 
 * @author Philipe PEREIRA
 *
 * @param <I> classe do objeto a ser editado
 */
public class ButtonInput<I> extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final String EDITION_PERFORMED = "EDITION_PERFORMED", EDITION_CANCELED = "EDITION_CANCELED";

	private Input<I> input;

	private String emptyMessage;

	private String nonEmptyMessage;

	/**
	 * Construtor do objeto gráfico do botão que abra a caixa de edição
	 * 
	 * @param input        editor do objeto que será adicionado na caixa de diálogo
	 *                     a ser aberta ao se pressionar o botão
	 * @param emptyMessage texto que será exibido no botão quando não houver um
	 *                     objeto a ser editado
	 */
	public ButtonInput(Input<I> input, String emptyMessage) {
		super.addActionListener(this);

		this.input = input;

		this.emptyMessage = emptyMessage;

		changeButton();
	}

	/**
	 * Função que estabelece o texto que será exibido no botão, quando houver um
	 * objeto a ser editado (se for <code>null</code>, o texto a ser exibido é
	 * aquele obtido a partir da função {@link Object#toString()})
	 * 
	 * @param nonEmptyMessage texto que será exibido no botão quando houver um
	 *                        objeto a ser editado
	 */
	public void setNonEmptyMessage(String nonEmptyMessage) {
		this.nonEmptyMessage = nonEmptyMessage;
		changeButton();
	}

	public void changeButton() {
		I i = input.get();
		if (input instanceof InputRenderer) {
			InputRenderer<I> ir = (InputRenderer<I>) input;
			ir.getRendererComponent(this, i, emptyMessage);
		} else {
			if (i == null)
				setText(emptyMessage);
			else
				setText(nonEmptyMessage == null ? i.toString() : nonEmptyMessage);
		}
	}

	public I get() {
		return input.get();
	}

	public void set(I k) {
		this.input.set(k);
		changeButton();
	}

	// ------------------------------- LISTENER -------------------------------

	private ActionListener listener;

	@Override
	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(this)) {
			// ao se clicar no botão, abre-se a caixa de diálogo

			int out = JOptionPane.showConfirmDialog(this, input, "Entrada de dados", JOptionPane.DEFAULT_OPTION);
			if (out == JOptionPane.OK_OPTION)
				changeButton();
			if (this.listener != null)
				this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
						out == JOptionPane.OK_OPTION ? EDITION_PERFORMED : EDITION_CANCELED));
		}
	}
}
