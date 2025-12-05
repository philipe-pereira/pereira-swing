package br.com.pereiraeng.swing.input.cod;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * Formatador de uma caixa de texto {@link JTextPane} para diferentes linguagens
 * de marcação
 * 
 * @author Philipe PEREIRA
 */
public class Formatter implements KeyListener {

	public static final Font FONT_DEFAULT = new Font("Courier New", Font.PLAIN, 15);

	private Sintax sintax;
	private Style def, styles[];
	private JTextPane pane;

	/**
	 * Keywords da linguagem java
	 */
	private static final String[] JAVA_KEYWORDS = { "abstract", "assert", "boolean", "break", "byte", "case", "catch",
			"char", "class", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally",
			"float", "for", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new",
			"package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
			"synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while", "const", "goto",
			"true", "false", "null" };

	/**
	 * Construtor do formatador de caixas de texto
	 * 
	 * @param pane   Caixa de texto a ser formatada
	 * @param sintax sintaxe do código da caixa de texto
	 */
	public Formatter(JTextPane pane, Sintax sintax) {
		this.pane = pane;
		this.sintax = sintax;
		// estilos

		// padrão
		StyleContext sc = new StyleContext();
		this.def = sc.addStyle("DefaultStyle", sc.getStyle(StyleContext.DEFAULT_STYLE));
		StyleConstants.setFontSize(def, FONT_DEFAULT.getSize());
		StyleConstants.setFontFamily(def, FONT_DEFAULT.getFamily());

		// definir estilos conforme a linguagem de marcação
		switch (this.sintax) {
		case HTML:
		case XML:
			styles = new Style[2];

			// azul
			sc = new StyleContext();
			styles[0] = sc.addStyle("values", def);
			StyleConstants.setForeground(styles[0], Color.BLUE);

			// laranja
			sc = new StyleContext();
			styles[1] = sc.addStyle("objects", def);
			StyleConstants.setForeground(styles[1], new Color(163, 21, 21));
			break;
		case JAVA:
		case C:
		case CPP:
		case H:
			styles = new Style[3];

			// violeta - keywords
			sc = new StyleContext();
			styles[0] = sc.addStyle("objects", def);
			StyleConstants.setForeground(styles[0], new Color(127, 0, 85));

			// azul - strings
			sc = new StyleContext();
			styles[1] = sc.addStyle("values", def);
			StyleConstants.setForeground(styles[1], Color.BLUE);

			// verde - comentários
			sc = new StyleContext();
			styles[2] = sc.addStyle("values", def);
			StyleConstants.setForeground(styles[2], new Color(63, 127, 95));
			break;
		case M:
			styles = new Style[1];

			// verde - comentários
			sc = new StyleContext();
			styles[0] = sc.addStyle("values", def);
			StyleConstants.setForeground(styles[0], new Color(63, 127, 95));
			break;
		case TEX:
			styles = new Style[1];

			// verde - comentários
			sc = new StyleContext();
			styles[0] = sc.addStyle("values", def);
			StyleConstants.setForeground(styles[0], new Color(63, 127, 95));
			break;
		case SCI:
			styles = new Style[1];

			// verde - comentários
			sc = new StyleContext();
			styles[0] = sc.addStyle("values", def);
			StyleConstants.setForeground(styles[0], new Color(63, 127, 95));
			break;
		case PWF:
			styles = new Style[1];

			// verde - comentários
			sc = new StyleContext();
			styles[0] = sc.addStyle("values", def);
			StyleConstants.setForeground(styles[0], new Color(63, 127, 95));
			break;
		}

		this.pane.addKeyListener(this);
	}

	/**
	 * Função que inicia processo de formatação do texto, definido os trechos que
	 * receberão uma dado estilo
	 */
	public void format() {
		// todo o documento - preto
		StyledDocument doc = pane.getStyledDocument();
		doc.setCharacterAttributes(0, doc.getLength(), def, true);

		String str = null;
		try {
			str = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		switch (this.sintax) {
		case HTML:
		case XML:
			// grupos entre os símbolos '<' e '>'
			Matcher m1 = Pattern.compile("\\<.*?\\>").matcher(str);

			while (m1.find()) {
				doc.setCharacterAttributes(m1.start(), m1.end() - m1.start(), styles[0], false);

				// palavras de atributos
				Matcher m2 = Pattern.compile("\\w").matcher(m1.group());

				while (m2.find()) {
					doc.setCharacterAttributes(m1.start() + m2.start(), m2.end() - m2.start(), styles[1], false);
				}
			}
			break;
		case JAVA:
		case C:
		case CPP:
		case H:
			// keywords
			for (String kw : JAVA_KEYWORDS) {
				Matcher m = Pattern.compile(kw).matcher(str);
				while (m.find())
					doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[0], false);
			}

			// strings
			Matcher m = Pattern.compile("\\\".*?\\\"").matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[1], false);

			// comments
			m = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL).matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[2], false);

			m = Pattern.compile("//.*$", Pattern.MULTILINE).matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[2], false);
			break;
		case M:
			// comments
			m = Pattern.compile("^%.*$", Pattern.MULTILINE).matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[0], false);
			break;
		case TEX:
			// comments
			m = Pattern.compile("^%.*$", Pattern.MULTILINE).matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[0], false);
			break;
		case SCI:
			// comments
			m = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL).matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[0], false);

			m = Pattern.compile("//.*$", Pattern.MULTILINE).matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[0], false);
			break;
		case PWF:
			// comments
			m = Pattern.compile("^\\(.*$", Pattern.MULTILINE).matcher(str);
			while (m.find())
				doc.setCharacterAttributes(m.start(), m.end() - m.start(), styles[0], false);
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		format();
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}