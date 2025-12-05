package br.com.pereiraeng.swing.input;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;

/**
 * Classe abstrata do objeto gráfico com uma caixa de texto na qual será
 * digitada uma sequência de caracteres que será convertida em um {@link Number
 * número}
 * 
 * @author Philipe PEREIRA
 *
 * @param <T> classe do objeto que representa um número a ser obtido a partir do
 *            texto digitado na caixa
 */
public abstract class NumberInput<T extends Number> extends ParseableInput<T> implements DocumentListener {
	private static final long serialVersionUID = 1L;

	protected Number number;

	public NumberInput() {
		super.textField.getDocument().addDocumentListener(this);
	}

	@Override
	protected void setValue(Number number) {
		boolean c = this.number == null ? false : !this.number.equals(number);
		this.number = number;
		if (this.hasChangeListener && c)
			fireChangePerformed(new ChangeEvent(this));
	}

	@Override
	protected boolean textField2value() {
		Number number = null;
		String text = super.textField.getText();
		if (!"".equals(text)) {
			try {
				number = Double.valueOf(text.replace(',', '.'));
				setValue(number);
			} catch (NumberFormatException e) {
				System.err.println(text + " is not a float parseable string");
				setValue(null);
			}
		}
		return number != null;
	}

	// ========================= LISTENER =========================

	protected transient boolean innerChange = false;

//	@Override
//	public void set(T value) {
//		this.number = value;
//		super.set(value);
//	}

	@Override
	public void insertUpdate(DocumentEvent event) {
		if (!this.innerChange)
			textField2value();
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		if (!this.innerChange)
			textField2value();
	}

	@Override
	public void changedUpdate(DocumentEvent event) {
		// Plain text components do not fire these events
	}

	private transient boolean hasChangeListener = false;

	/**
	 * Adds the listener as a listener of the specified type.
	 * 
	 * @param changeListener the listener to be added
	 */
	public void addChangeListener(ChangeListener changeListener) {
		if (changeListener != null) {
			super.listenerList.add(ChangeListener.class, changeListener);
			this.hasChangeListener = true;
		}
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this
	 * event type.
	 *
	 * @param e the <code>ChangeEvent</code> to deliver to listeners
	 * @see EventListenerList
	 */
	protected void fireChangePerformed(ChangeEvent event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying those that are interested in
		// this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				ChangeListener cl = (ChangeListener) listeners[i + 1];
				cl.stateChanged(event);
			}
		}
	}
}
