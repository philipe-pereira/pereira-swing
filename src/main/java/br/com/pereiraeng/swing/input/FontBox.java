package br.com.pereiraeng.swing.input;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Caixa de opções para a escolha de fontes, onde cada opção de caixa é
 * formatada com a fonte a ser escolhida
 * 
 * @author Philipe Pereira
 */
public class FontBox extends JComboBox<String> {

	private static final long serialVersionUID = 1L;
	private final String[] html = { "Georgia, serif",
			"\"Palatino Linotype\", \"Book Antiqua\", Palatino, serif",
			"\"Times New Roman\", Times, serif",
			"Arial, Helvetica, sans-serif",
			"\"Arial Black\", Gadget, sans-serif",
			"\"Comic Sans MS\", cursive, sans-serif",
			"Impact, Charcoal, sans-serif",
			"\"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif",
			"Tahoma, Geneva, sans-serif",
			"\"Trebuchet MS\", Helvetica, sans-serif",
			"Verdana, Geneva, sans-serif",
			"\"Courier New\", Courier, monospace",
			"\"Lucida Console\", Monaco, monospace" };

	public FontBox() {
		super.setRenderer(new Renderer());
		for (String f : html)
			addItem(f.split(",")[0].replace("\"", ""));
	}

	public class Renderer implements ListCellRenderer<String> {
		public Component getListCellRendererComponent(
				JList<? extends String> list, String value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel l = new JLabel(value);
			l.setFont(new Font(value, Font.PLAIN, 12));
			return l;
		}
	}

	public String getHTML() {
		return html[this.getSelectedIndex()];
	}
}