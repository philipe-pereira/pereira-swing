package br.com.pereiraeng.swing;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import br.com.pereiraeng.html.HTML;

/**
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class LeafHT extends JScrollPane {
	private static final long serialVersionUID = -7991975765856924495L;

	private JEditorPane ep;

	private HTMLEditorKit kit;

	public LeafHT() {
		ep = new JEditorPane();
		// make it read-only
		ep.setEditable(false);
		ep.setContentType("text/html");

		ep.setEditorKit(this.kit = new HTMLEditorKit());

		super.setViewportView(ep);
	}

	public void setStyle(String... rules) {
		StyleSheet style = kit.getStyleSheet();
		for (int i = 0; i < rules.length; i++)
			style.addRule(rules[i]);

	}

	public void setText(String text) {
		ep.setText(HTML.START + "<html><head></head><body>" + text + HTML.TAIL);
	}
}
