package br.com.pereiraeng.swing.input.cod;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

import br.com.pereiraeng.swing.input.Input;
import br.com.pereiraeng.swing.table.NumberTextHeader;

/**
 * Classe do objeto gráfico de uma caixa de texto (com {@link JScrollPane barra
 * derolante}) em que o texto é formatado de acordo com as regras de uma dada
 * {@link Sintax linguagem de marcação}.
 * 
 * @author Philipe PEREIRA
 *
 */
public class MarkupInput extends JScrollPane implements Input<String> {
	private static final long serialVersionUID = 1L;

	private Formatter form;

	private JTextPane in;

	/**
	 * 
	 * @param sintax linguagem de marcação do texto a ser exibido
	 */
	public MarkupInput(Sintax sintax) {
		this(sintax, 3);
	}

	/**
	 * 
	 * @param sintax linguagem de marcação do texto a ser exibido
	 * @param w      número de dígitos do cabeçalho das linhas
	 */
	public MarkupInput(Sintax sintax, int w) {
		this.in = new MarkupTextPane();
		this.in.setFont(Formatter.FONT_DEFAULT);
		this.form = new Formatter(in, sintax);
		JPanel p = new JPanel(new BorderLayout());
		p.add(in);
		this.setViewportView(p);
		this.setRowHeaderView(new NumberTextHeader(this, in, w));
	}

	@Override
	public void set(String k) {
		in.setText(k);
	}

	@Override
	public String get() {
		return in.getText();
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ----------------------- INTERFACEAMENTO -----------------------

	public void setEditable(boolean b) {
		in.setEditable(b);
	}

	public JTextComponent getTextComponent() {
		return in;
	}

	// ---------------------------------------------------------------

	private class MarkupTextPane extends JTextPane {
		private static final long serialVersionUID = 1L;

		@Override
		public void setText(String t) {
			super.setText(t);
			form.format();
		}

		@Override
		public void replaceSelection(String content) {
			super.replaceSelection(content);
			form.format();
		}
	}
}