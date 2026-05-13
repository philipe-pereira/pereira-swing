package br.com.pereiraeng.swing.dialog;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import br.com.pereiraeng.core.Password;
import br.com.pereiraeng.core.StringUtils;
import br.com.pereiraeng.io.FileType;
import br.com.pereiraeng.swing.DialogBox;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.button.CoCaPanel;
import br.com.pereiraeng.swing.input.ButtonInput;
import br.com.pereiraeng.swing.input.MapInput;
import br.com.pereiraeng.swing.input.file.FileInput;
import br.com.pereiraeng.swing.input.time.DateInput;
import br.com.pereiraeng.swing.input.time.TimeInput;

/**
 * Classe que cria caixas de diálogos para serem preenchidas e em seguida
 * devolverem os valores digitados
 * 
 * @author Philipe Pereira
 *
 */
public class FillingFields extends DialogBox implements  ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	/**
	 * Função que cria um caixa de diálogo onde serão digitados valores. Ao se
	 * confirmar a edição, os valores são devolvidos pela função
	 * 
	 * @param root       janela que contém a caixa de diálogo
	 * @param fieldNames vetor com o nome dos campos a serem preenchidos
	 * @param title      título da caixa de diálogo
	 * @return vetor com as sequências de caracteres que foram inseridas nas caixas
	 *         de texto
	 */
	public static String[] fillFields(Window root, String[] fieldNames, String title) {
		return fillFields(root, title, fieldNames, new String[fieldNames.length]);
	}

	public static String[] fillFields(Window root, String title, String[] fieldNames, String[] oldValues) {
		FillingFields dialog = new FillingFields(root, title, fieldNames, oldValues);
		// após apertar 'OK' (ou fechar-se a caixa) coleta-se os valores
		Object[] objects = dialog.getValues();
		String[] out = null;
		if (objects != null) {
			out = new String[objects.length];
			for (int i = 0; i < out.length; i++)
				out[i] = (String) objects[i];
			return out;
		}
		return out;
	}

	public static Object[] fillFields(Window root, String title, String[] fieldNames, Object[] oldValues) {
		FillingFields dialog = new FillingFields(root, title, fieldNames, oldValues);
		// após apertar 'OK' (ou fechar-se a caixa) coleta-se os valores
		return dialog.getValues();
	}

	// ------------------------------------------------------------------------

	private Object[] values = null;

	private JComponent[] fieldComps;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private FillingFields(Window raiz, String titulo, String[] fieldNames, Object[] values) {
		super(raiz, titulo,
				new Dimension(200 + 10 * StringUtils.maxStringLength(fieldNames), 54 + 22 * fieldNames.length), true);

		Grade g = new Grade();

		fieldComps = new JComponent[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			g.add(new JLabel(fieldNames[i]), 0, i, 1, 1);

			if (values[i] instanceof Boolean) {
				// verdadeiro ou falso -> check box
				Boolean booleanValue = (Boolean) values[i];
				fieldComps[i] = new JCheckBox("", booleanValue);
			} else if (values[i] instanceof Integer || values[i] instanceof Short || values[i] instanceof Byte) {
				// número inteiro -> spinner
				int integerValue = ((Number) values[i]).intValue();
				fieldComps[i] = new JSpinner(
						new SpinnerNumberModel(integerValue, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
			} else
//				if (values[i] instanceof Medida) {
//				// grandeza -> caixa de texto com a unidade
//				Medida medidaValue = (Medida) values[i];
//				fieldComps[i] = new MedidaInput(medidaValue);
//			} else
				if (values[i] instanceof File) {
				// arquivo -> botão que abre a caixa de seleção
				File fileValue = (File) values[i];
				fieldComps[i] = new FileInput(fileValue,
						fileValue != null ? (fileValue.isDirectory() ? FileType.DIRETORIO : null) : null);
			} else if (values[i] instanceof Enum) {
				// enumeração -> caixa de combo
				Enum<?> e = (Enum<?>) values[i];
				JComboBox<?> c = new JComboBox<>(e.getClass().getEnumConstants());
				((JComboBox<?>) c).setSelectedItem(e);
				fieldComps[i] = c;
			} else if (values[i] instanceof Object[]) {
				// vetor -> caixa de combo
				Object[] os = (Object[]) values[i];
				JComboBox<Object> c = new JComboBox<>(os);
				c.setSelectedIndex(-1);
				fieldComps[i] = c;
			} else if (values[i] instanceof Map<?, ?>) {
				// tabela de dispersão -> tabela
				Map<?, ?> map = (Map<?, ?>) values[i];
				ButtonInput<Map<?, ?>> bi = new ButtonInput<Map<?, ?>>(new MapInput(map), "Adicionar tabela");
				bi.setNonEmptyMessage("Editar tabela");
				bi.setPreferredSize(new Dimension(84, 26));
				fieldComps[i] = bi;
			} else if (values[i] instanceof Date) {
				// date -> DateInput
				Date date = (Date) values[i];
				DateInput di = new DateInput(date);
				fieldComps[i] = di;
			} else if (values[i] instanceof Calendar) {
				// calendar -> TimeInput
				Calendar cal = (Calendar) values[i];
				TimeInput ti = new TimeInput(cal);
				fieldComps[i] = ti;
			} else if (values[i] instanceof Password) {
				// Password -> JPasswordField
				String pass = ((Password) values[i]).getPassword();
				JPasswordField ti = new JPasswordField(pass != null ? pass : "", 15);
				fieldComps[i] = ti;
			} else {
				// para os demais -> caixa de texto
				fieldComps[i] = new JTextField(values[i] != null ? values[i].toString() : "", 15);
			}

			g.add(fieldComps[i], 1, i, 2, 1);
			fieldComps[i].addKeyListener(this);
		}

		// butões

		CoCaPanel p = new CoCaPanel();
		p.addActionListener(this);
		g.add(p, 0, fieldNames.length, 3, 1);

		setContentPane(g);
		showFrame(false);
	}

	private void gatherContent() {
		this.values = new Object[fieldComps.length];
		for (int i = 0; i < fieldComps.length; i++) {
			if (fieldComps[i] instanceof JTextField) {
				JTextField field = (JTextField) fieldComps[i];
				this.values[i] = field.getText();
			} else if (fieldComps[i] instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) fieldComps[i];
				this.values[i] = box.isSelected();
			} else if (fieldComps[i] instanceof JSpinner) {
				JSpinner spinner = (JSpinner) fieldComps[i];
				this.values[i] = spinner.getValue();
			} 
//			else if (fieldComps[i] instanceof MedidaInput) {
//				MedidaInput mi = (MedidaInput) fieldComps[i];
//				this.values[i] = mi.get();
//			} TODO solução auto-incremental
			else if (fieldComps[i] instanceof ButtonInput<?>) {
				ButtonInput<?> bi = (ButtonInput<?>) fieldComps[i];
				this.values[i] = bi.get();
			} else if (fieldComps[i] instanceof FileInput) {
				FileInput fi = (FileInput) fieldComps[i];
				this.values[i] = fi.get();
			} else if (fieldComps[i] instanceof DateInput) {
				DateInput di = (DateInput) fieldComps[i];
				this.values[i] = di.get();
			} else if (fieldComps[i] instanceof TimeInput) {
				TimeInput ti = (TimeInput) fieldComps[i];
				this.values[i] = ti.get();
			} else if (fieldComps[i] instanceof JComboBox) {
				JComboBox<?> cb = (JComboBox<?>) fieldComps[i];
				this.values[i] = cb.getSelectedItem();
			} else
				System.err.println(fieldComps[i].getClass() + " componente desconhecido");
		}
	}

	private Object[] getValues() {
		return this.values;
	}

	// ------------------------ LISTENER ------------------------

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (CoCaPanel.EDITION_OK.equals(command)) {
			// se clicar em OK, recolhe os valores digitados
			gatherContent();
		} // se clicar em CANCELAR, retorna vetor vazio
		this.dispose();
	}

	public void keyTyped(KeyEvent event) {
		switch (event.getKeyChar()) {
		case KeyEvent.VK_ENTER:
			// se apertar ENTER, recolhe os valores digitados
			gatherContent();
			this.dispose();
			break;
		case KeyEvent.VK_ESCAPE:
			// se apertar ESC, retorna vetor vazio
			this.dispose();
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
	}

	@Override
	public void keyReleased(KeyEvent event) {
	}
}