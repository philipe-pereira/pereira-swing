package br.com.pereiraeng.swing.table.renderer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.input.ButtonInput;
import br.com.pereiraeng.swing.input.ColorInput;
import br.com.pereiraeng.swing.input.EventInput;
import br.com.pereiraeng.swing.input.NodeInput;
import br.com.pereiraeng.swing.input.ParseableInput;
import br.com.pereiraeng.swing.input.file.FileInput;
import br.com.pereiraeng.swing.input.time.DateInput;
import br.com.pereiraeng.swing.input.time.TimeInput;

/**
 * Renderizador das células da tabela
 * 
 * @author Philipe Pereira
 * 
 */
public class MultiRenderer extends RendererEditor
		implements TableCellEditor, ActionListener, DocumentListener, ChangeListener {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component component = null;
		if (value == null)
			component = new JLabel();
		else
			component = SwingUtils.getEditor(value);
		return component;
	}

	@Override
	protected void addListener(Component c) {
		// listener
		if (c instanceof JTextComponent || c instanceof ParseableInput<?>) {
			Document d = null;

			if (c instanceof JTextComponent) {
				d = ((JTextComponent) c).getDocument();
			} else if (c instanceof ParseableInput<?>) {
				d = ((ParseableInput<?>) c).getDocument();
//				if (c instanceof ComplexInput) {
//					Document d2 = ((ComplexInput) c).getDocument2();
//					d2.putProperty(SwingUtils.TEXT, c);
//					d2.addDocumentListener(this);
//				}
			}
			d.putProperty(SwingUtils.TEXT, c);
			d.addDocumentListener(this);
		} else if (c instanceof DateInput) {
			((DateInput) c).addChangeListener(this);
		} else if (c instanceof TimeInput) {
			((TimeInput) c).addChangeListener(this);
		} else if (c instanceof AbstractButton) { // ButtonInput, NodeInput, EventInput
			((AbstractButton) c).addActionListener(this);
		} else if (c instanceof JSpinner) {
			JSpinner s = (JSpinner) c;
			((DefaultFormatter) ((JSpinner.DefaultEditor) s.getEditor()).getTextField().getFormatter())
					.setCommitsOnValidEdit(true);
			s.addChangeListener(this);
		} else if (c instanceof JComboBox) {
			((JComboBox<?>) c).addActionListener(this);
		}
//		else if (c instanceof ParametroInput) {
//			((ParametroInput) c).addChangeListener(this);
//		}
	}

	// ----------------------------- LISTENER -----------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		Object newValue = null;

		Object source = event.getSource();

		if (source instanceof JCheckBox)
			newValue = ((JCheckBox) source).isSelected();
		else if (source instanceof JComboBox<?>)
			newValue = ((JComboBox<?>) source).getSelectedItem();
		else if (source instanceof FileInput)
			newValue = ((FileInput) source).get();
		else if (source instanceof ColorInput)
			newValue = ((ColorInput) source).get();
		else if (source instanceof NodeInput)
			newValue = ((NodeInput) source).get();
		else if (source instanceof EventInput) {
			newValue = ((EventInput) source).get();
		} else if (source instanceof ButtonInput<?>) {
			if (ButtonInput.EDITION_CANCELED.equals(event.getActionCommand())) {
				fireEditingCanceled();
				return;
			}
			newValue = ((ButtonInput<?>) source).get();
		} 
//		else if (source instanceof CoordinateInput)
//			newValue = ((CoordinateInput) source).get();

		super.setNewValue(newValue, true);
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		Object newValue = null;

		Object source = event.getSource();

		boolean stopEditing = true;
		if (source instanceof JSpinner) {
			JSpinner s = (JSpinner) source;
			newValue = s.getValue();
			stopEditing = s.isValid();
		} else if (source instanceof DateInput) {
			newValue = ((DateInput) source).get();
		} else if (source instanceof TimeInput) {
			newValue = ((TimeInput) source).get();
		}
//		else if (source instanceof ParametroInput) {
//			newValue = ((ParametroInput) source).get();
//			stopEditing = false;
//		}

		super.setNewValue(newValue, stopEditing);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// Plain text components do not fire these events
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.setNewText(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.setNewText(e);
	}

	private void setNewText(DocumentEvent event) {
		Object newValue = null;

		Component source = (Component) event.getDocument().getProperty(SwingUtils.TEXT);

		Object str = null;
		String name = source.getName();

		if (source instanceof JTextComponent) {
			JTextComponent tc = (JTextComponent) source;
			str = tc.getText();
		} else if (source instanceof ParseableInput<?>) {
			ParseableInput<?> tc = (ParseableInput<?>) source;
			str = tc.get();
		}

		// diferenciar string de um número digitado
		if (name != null) {
			try {
				if (SwingUtils.LONG_FIELD.equals(name))
					newValue = Long.parseLong((String) str);
				else if (SwingUtils.DOUBLE_FIELD.equals(name))
					newValue = Double.parseDouble(((String) str).replace(',', '.'));
			} catch (NumberFormatException e) {
				newValue = null;
			}
		} else
			newValue = str;

		super.setNewValue(newValue, false);
	}
}
