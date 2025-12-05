package br.com.pereiraeng.swing.input;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * Classe abstrata do objeto gráfico com uma caixa de texto na qual será
 * digitada uma sequência de caracteres que será convertida em um objeto
 * 
 * @author Philipe PEREIRA
 *
 * @param <T> classe do objeto a ser obtido a partir do texto digitado na caixa
 */
public abstract class ParseableInput<T> extends JPanel implements Input<T> {
	private static final long serialVersionUID = 1L;

	protected JTextField textField;

	public ParseableInput() {
		super(new BorderLayout());
		this.textField = new JTextField();
		this.add(this.textField, BorderLayout.CENTER);
	}

	/**
	 * Função que faz <strong>primeira</strong> etapa do processo de conversão do
	 * texto em objeto: um pré-tratamento em que a sequência de caracteres é
	 * analisada e validada, sendo então {@link #setValue(Object) guardada}.
	 * 
	 * @return <code>true</code> se a sequência de caracteres digitado é válida e
	 *         gera um objeto, <code>false</code> senão
	 */
	protected abstract boolean textField2value();

	/**
	 * Função que faz <strong>segunda</strong> etapa do processo de conversão do
	 * texto em objeto: o {@link ParseableInput#textField2value() valor tratado} é
	 * guardado
	 * 
	 * @param value objeto obtido a partir de uma sequência válida de caracteres
	 */
	protected abstract void setValue(T value);

	/**
	 * Função que faz <strong>terceira</strong> etapa do processo de conversão do
	 * texto em objeto: o {@link ParseableInput#textField2value() valor tratado} e
	 * que {@link ParseableInput#setValue(Number) estava guardado} é retornado,
	 * fazendo-se as operação necessárias (geralmente "cast's", uma vez que a
	 * entrada pode ser de tipos muito diferentes)
	 * 
	 * @return objeto obtido a partir de uma sequência válida de caracteres
	 */
	protected abstract T getValue();

	/**
	 * Função que reconverte o objeto em uma sequência de caracteres. É a função que
	 * faz o inverso daquilo que é feito em {@link #textField2value()}.
	 * 
	 * @param value valor guardado
	 * @return sequência de caracteres que será exibida na caixa de texto
	 */
	protected abstract String value2string(T value);

	@Override
	public T get() {
		if (textField2value())
			return getValue();
		else
			return null;
	}

	@Override
	public void set(T value) {
		if (value != null)
			this.textField.setText(value2string(value));
		else
			this.textField.setText("");
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ================ MÉTODOS DE INTERFACEAMENTO ================

	/**
	 * Sets the number of columns in this TextField, and then invalidate the layout.
	 * 
	 * @param columns the number of columns >= 0
	 */
	public void setColumns(int columns) {
		this.textField.setColumns(columns);
	}

	public void setLabelFor(JLabel label) {
		label.setLabelFor(this.textField);
	}

	/**
	 * Sets the specified boolean to indicate whether or not this TextComponent
	 * should be editable. A PropertyChange event ("editable") is fired when the
	 * state is changed.
	 * 
	 * @param b the boolean to be set
	 */
	public void setEditable(boolean b) {
		this.textField.setEditable(b);
	}

	/**
	 * Returns the boolean indicating whether this TextComponent is editable or not.
	 * 
	 * @return the boolean value
	 */
	public boolean isEditable() {
		return this.textField.isEditable();
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (this.textField != null)
			this.textField.setFont(font);
	}

	public static final String TF = "TF";

	/**
	 * Registers the given observer to begin receiving notifications when changes
	 * are made to the document.
	 * 
	 * @param listener the observer to register
	 */
	public void addDocumentListener(DocumentListener listener) {
		this.getDocument().addDocumentListener(listener);
		this.getDocument().putProperty(TF, this.textField);
	}

	@Override
	public void addKeyListener(KeyListener listener) {
		this.textField.addKeyListener(listener);
	}

	public Document getDocument() {
		return this.textField.getDocument();
	}

	@Override
	public void addAncestorListener(AncestorListener listener) {
		this.textField.addAncestorListener(listener);
	}
}