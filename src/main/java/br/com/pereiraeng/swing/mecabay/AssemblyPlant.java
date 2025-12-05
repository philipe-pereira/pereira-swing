package br.com.pereiraeng.swing.mecabay;

import java.awt.Font;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.mecabay.Change.AlinHrz;
import br.com.pereiraeng.swing.mecabay.Change.AlinVrt;
import br.com.pereiraeng.swing.mecabay.Change.Fonte;
import br.com.pereiraeng.swing.mecabay.Change.Orientacao;
import br.com.pereiraeng.swing.mecabay.Change.TipoAlteracao;
import br.com.pereiraeng.swing.mecabay.Componente.TipoComponente;

public class AssemblyPlant {
	public static JComponent addComponent(Grade grade, TipoComponente tipo, String nome, int column, int row, int width,
			int height, HashMap<String, Change> alteracoes) {
		JComponent component = getComponente(tipo, nome, alteracoes);
		if (component != null)
			grade.add(component, column, row, width, height);
		return component;
	}

	public static JComponent getComponente(TipoComponente tipo, String nome, HashMap<String, Change> propriedades) {
		JComponent comp = null;
		switch (tipo) {
		case GRADE:
			try {
				Class<?> c = Class.forName(nome);
				System.out.println(c);
				Constructor<?> constructor = c.getConstructors()[0];
				comp = (JComponent) constructor.newInstance(new Object[constructor.getParameterTypes().length]);
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();
				e.printStackTrace();
			}
			break;
		case BUTAO:
			comp = new JButton();
			break;
		case TOOGLE_BUTAO:
			comp = new JToggleButton();
			break;
		case CHECK:
			comp = new JCheckBox();
			break;
		case LABEL:
			comp = new JLabel();
			break;
		case SEPARADOR:
			comp = new JSeparator();
			break;
		case SLIDER:
			comp = new JSlider();
			break;
		case SPINNER:
			comp = new JSpinner();
			break;
		case TEXTO:
			/*
			 * if (propriedades.containsValue(new Property(TipoPropriedade.LINHAS, null)))
			 * comp = new JTextArea(); else
			 */// TODO
			comp = new JTextField();
			break;
		default:
			comp = null;
			break;
		}

		if (comp != null) {
			if (!nome.equals(""))
				comp.setName(nome);
			for (Change p : propriedades.values())
				changeComponente(p, comp);
		}
		return comp;
	}

	public static void changeComponente(final Change p, JComponent componente) {
		switch ((TipoAlteracao) p.getType()) {
		case ACTION:
			if (componente instanceof AbstractButton) {
				AbstractButton b = (AbstractButton) componente;
				b.setActionCommand((String) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case AH:
			int i = -1;
			switch ((AlinHrz) p.getValue()) {
			case CENTRO:
				i = SwingConstants.CENTER;
				break;
			case DIREITA:
				i = SwingConstants.RIGHT;
				break;
			case ESQUERDA:
				i = SwingConstants.LEFT;
				break;
			default:
				System.err.println(errorMessage3((TipoAlteracao) p.getValue(), p.getValue()));
				break;
			}

			if (componente instanceof AbstractButton) {
				AbstractButton b = (AbstractButton) componente;
				b.setHorizontalAlignment(i);

			} else if (componente instanceof JLabel) {
				JLabel l = (JLabel) componente;
				l.setHorizontalAlignment(i);
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case AV:
			int j = -1;
			switch ((AlinVrt) p.getValue()) {
			case CENTRO:
				j = SwingConstants.CENTER;
				break;
			case BAIXO:
				j = SwingConstants.BOTTOM;
				break;
			case CIMA:
				j = SwingConstants.TOP;
				break;
			default:
				System.err.println(errorMessage3((TipoAlteracao) p.getValue(), p.getValue()));
				break;
			}

			if (componente instanceof AbstractButton) {
				AbstractButton b = (AbstractButton) componente;
				b.setVerticalAlignment(j);

			} else if (componente instanceof JLabel) {
				JLabel l = (JLabel) componente;
				l.setVerticalAlignment(j);
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case B:
			Font f = componente.getFont();
			componente.setFont(f.deriveFont(
					((Boolean) p.getValue() ? Font.BOLD : Font.PLAIN) + (f.isItalic() ? Font.ITALIC : Font.PLAIN)));
			break;
		case COLUNAS:
			if (componente instanceof JTextArea) {
				JTextArea t = (JTextArea) componente;
				t.setColumns((Integer) p.getValue());
			} else if (componente instanceof JTextField) {
				JTextField t = (JTextField) componente;
				t.setColumns((Integer) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case ORIENT:
			int k = -1;
			switch ((Orientacao) p.getValue()) {
			case HORIZONTAL:
				k = SwingConstants.HORIZONTAL;
				break;
			case VERTICAL:
				k = SwingConstants.VERTICAL;
				break;
			default:
				break;
			}

			if (componente instanceof JSeparator) {
				JSeparator s = (JSeparator) componente;
				s.setOrientation(k);
			} else if (componente instanceof JSlider) {
				JSlider l = (JSlider) componente;
				l.setOrientation(k);
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case EDICAO:
			if (componente instanceof JTextComponent) {
				JTextComponent tc = (JTextComponent) componente;
				tc.setEditable((Boolean) p.getValue());
			} else if (componente instanceof AbstractButton) {
				AbstractButton ab = (AbstractButton) componente;
				ab.setEnabled((Boolean) p.getValue());
			} else if (componente instanceof JComponent) {
				componente.setVisible((Boolean) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case FONTE:
			f = componente.getFont();
			switch ((Fonte) p.getValue()) {
			case DIALOG:
				f = new Font(Font.DIALOG, f.getStyle(), f.getSize());
				break;
			case DIALOG_INPUT:
				f = new Font(Font.DIALOG_INPUT, f.getStyle(), f.getSize());
				break;
			case MONOSPACED:
				f = new Font(Font.MONOSPACED, f.getStyle(), f.getSize());
				break;
			case SANSSERIF:
				f = new Font(Font.SANS_SERIF, f.getStyle(), f.getSize());
				break;
			case SERIF:
				f = new Font(Font.SERIF, f.getStyle(), f.getSize());
				break;
			default:
				f = null;
				break;
			}
			componente.setFont(f);
			break;
		case I:
			f = componente.getFont();
			componente.setFont(f.deriveFont(
					((Boolean) p.getValue() ? Font.ITALIC : Font.PLAIN) + (f.isBold() ? Font.BOLD : Font.PLAIN)));
			break;
		case IMAGE_1:
			if (componente instanceof AbstractButton) {
				AbstractButton b = (AbstractButton) componente;
				b.setIcon(new ImageIcon(((File) p.getValue()).getAbsolutePath()));
			} else if (componente instanceof JLabel) {
				JLabel l = (JLabel) componente;
				l.setIcon(new ImageIcon(((File) p.getValue()).getAbsolutePath()));
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case IMAGE_2:
			if (componente instanceof JToggleButton) {
				JToggleButton tb = (JToggleButton) componente;
				tb.addChangeListener(new ChangeListener() {
					private Icon i1 = null, i2 = new ImageIcon(((File) p.getValue()).getAbsolutePath());

					public void stateChanged(ChangeEvent event) {
						JToggleButton b = (JToggleButton) event.getSource();
						if (i1 == null)
							i1 = b.getIcon();
						if (b.isSelected())
							b.setIcon(i2);
						else
							b.setIcon(i1);
					}
				});
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case LINHAS:
			if (componente instanceof JTextArea) {
				JTextArea ta = (JTextArea) componente;
				ta.setRows((Integer) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case SELECTED:
			if (componente instanceof JToggleButton) {
				JToggleButton tb = (JToggleButton) componente;
				tb.setSelected((Boolean) p.getValue());
			} else if (componente instanceof JCheckBox) {
				JCheckBox cb = (JCheckBox) componente;
				cb.setSelected((Boolean) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case SIZE:
			f = componente.getFont();
			componente.setFont(f.deriveFont((Float) p.getValue()));
			break;
		case STEP:
			if (componente instanceof JSpinner) {
				((SpinnerNumberModel) ((JSpinner) componente).getModel()).setStepSize((Integer) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case TEXTO_VALOR_1:
			if (componente instanceof AbstractButton) {
				AbstractButton b = (AbstractButton) componente;
				b.setText((String) p.getValue());
			} else if (componente instanceof JLabel) {
				JLabel l = (JLabel) componente;
				l.setText((String) p.getValue());
			} else if (componente instanceof JSpinner) {
				((SpinnerNumberModel) ((JSpinner) componente).getModel())
						.setValue(Integer.parseInt((String) p.getValue()));
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case TEXTO_VALOR_2:
			if (componente instanceof JToggleButton) {
				JToggleButton tb = (JToggleButton) componente;
				final String text1 = tb.getText();
				if (text1 != null) {
					tb.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent event) {
							JToggleButton b = (JToggleButton) event.getSource();
							b.setText(b.isSelected() ? (String) p.getValue() : text1);
						}
					});
				} else {
					System.err.println(errorMessage2((TipoAlteracao) p.getValue()));
				}
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case VALOR_MAX:
			if (componente instanceof JSpinner) {
				((SpinnerNumberModel) ((JSpinner) componente).getModel()).setMaximum((Integer) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		case VALOR_MIN:
			if (componente instanceof JSpinner) {
				((SpinnerNumberModel) ((JSpinner) componente).getModel()).setMinimum((Integer) p.getValue());
			} else {
				System.err.println(errorMessage1((TipoAlteracao) p.getValue(), componente));
			}
			break;
		default:
			break;
		}
	}

	private static String errorMessage1(TipoAlteracao prop, JComponent componente) {
		return "invalid component : the property '" + prop.name() + "' can't be associed to the component '"
				+ componente.getName() + "'";
	}

	private static String errorMessage2(TipoAlteracao prop) {
		return "invalid property : the first property associated to '" + prop.name() + "' wasn't declared";
	}

	private static String errorMessage3(TipoAlteracao prop, Object object) {
		return "invalid object : the object '" + object + "' isn't a valid value for '" + prop.name() + "'";
	}
}
