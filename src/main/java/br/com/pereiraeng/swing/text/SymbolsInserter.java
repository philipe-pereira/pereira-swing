package br.com.pereiraeng.swing.text;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.text.JTextComponent;

import br.com.pereiraeng.swing.Grade;

/**
 * Componente do tipo Popup que mostra uma tabela de caracteres especiais que
 * podem ser adicionado numa dada caixa de texto.
 * 
 * @author Philipe Pereira
 * 
 */
public class SymbolsInserter extends JPopupMenu implements ActionListener {
	private static final long serialVersionUID = 1L;

	public enum SymbolSintax {
		HTML, EqDr, LaTeX;
	}

	private JTextComponent textBox;

	private final SymbolSintax sintax;

	private static final int[] GROUP_SIZE = { 25, 25, 154, 91, 56 };
	private static final int[] GROUP_OFFSET = { 0x03B1, 0x0391, 0x2200, 0x2190, 0x2100 };
	private static final String[] GROUPS_NAMES = { "Letras gregas minísculas", "Letras gregas maiúsculas",
			"Operadores matemáticos", "Flechas", "Outros" };

	private static final int COLUMNS = 15;

	public SymbolsInserter(JTextComponent textBox, SymbolSintax sintax) {
		this.textBox = textBox;
		this.sintax = sintax;

		Grade g = new Grade();
		Font f = new Font(this.getFont().getFontName(), Font.PLAIN, 15);

		int k = 0;
		for (int i = 0; i < GROUP_SIZE.length; i++) {
			g.add(new JLabel(GROUPS_NAMES[i]), 0, k, COLUMNS, 1);
			for (int j = 0; j < GROUP_SIZE[i]; j++) {
				JButton b = new JButton(String.format("%c", j + GROUP_OFFSET[i]));
				b.setFont(f);
				b.addActionListener(this);
				g.add(b, j % COLUMNS, k + 1 + j / COLUMNS, 1, 1);
			}
			k += GROUP_SIZE[i] / 10 + 3;
			if (i != GROUP_SIZE.length - 1)
				g.add(new JSeparator(), 0, k - 1, COLUMNS, 1);
		}
		add(g);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		char c = ((JButton) event.getSource()).getText().charAt(0);
		String string = "";
		switch (SymbolsInserter.this.sintax) {
		case HTML:
			string = "&#x" + String.format("%04x", (int) c);
			break;
		case EqDr:
			string = "\\" + String.format("%04x", (int) c) + "\\";
			break;
		default:
			string = c + "";
			break;
		}
		SymbolsInserter.this.textBox.replaceSelection(string);
	}
}
