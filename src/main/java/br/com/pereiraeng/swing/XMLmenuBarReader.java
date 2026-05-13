package br.com.pereiraeng.swing;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import br.com.pereiraeng.core.LocaleConfig;
import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.xml.XMLadapter;

/**
 * Classe dos objetos que fazem a leitura de um arquivo XML trazendo informações
 * de como deve ser uma barra de menu de um aplicativo ou o PopUp que será
 * aberto
 * 
 * @author Philipe PEREIRA
 *
 */
public class XMLmenuBarReader extends XMLadapter {

	private enum TipoMenu {
		BARRA_MENU, POPUP, MENU, SUB_MENU, RADIO_MENU, CHECK_MENU, LABEL, SEPARADOR;
	}

	private JPopupMenu popUp;
	private JMenuBar menuBar;
	private ActionListener listener;

	private Stack<JComponent> comps;

	/**
	 * Função que faz a leitura do arquivo xml que contem a estrutura da barra de
	 * menus e a adiciona à janela do programa
	 * 
	 * @param frame    janela onde a barra será adicionada
	 * @param arquivo  xml contendo a descrição da barra
	 * @param listener escutador para onde serão enviados os eventos relativos aos
	 *                 butões da barra
	 */
	public JMenuBar createMenu(Component frame, InputStream inputStream, ActionListener listener) {
		this.listener = listener;

		this.comps = new Stack<JComponent>();

		parse(inputStream);

		if (frame instanceof JInternalFrame) {
			JInternalFrame f = (JInternalFrame) frame;
			f.setJMenuBar(menuBar);
		} else if (frame instanceof JFrame) {
			JFrame f = (JFrame) frame;
			f.setJMenuBar(menuBar);
		}

		this.comps = null;
		return this.menuBar;
	}

	public JPopupMenu createPopUp(InputStream inputStream, ActionListener listener) {
		this.listener = listener;

		this.comps = new Stack<JComponent>();

		parse(inputStream);

		this.comps = null;
		return this.popUp;
	}

	// =============================== LEITURA ===============================

	private transient TipoMenu type;

	private transient JMenuItem item;

	private transient ButtonGroup group;

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String qName, Attributes atts) {
		TipoMenu t = null;
		try {
			t = TipoMenu.valueOf(qName.toUpperCase());
		} catch (IllegalArgumentException exception) {
			System.err.println("unknown type '" + qName + "'");
		}

		this.type = t;
		if (this.type != null) {
			switch (this.type) {
			case BARRA_MENU:
				this.menuBar = new JMenuBar();
				this.comps.push(this.menuBar);
				break;
			case POPUP:
				this.popUp = new JPopupMenu();
				this.comps.push(this.popUp);
				break;
			case MENU:
				String label = atts.getValue("name");
				item = new JMenu(LocaleConfig.getString(label));

				ImageIcon imageIcon = Icons.getIcon(atts.getValue("icon"));
				if (imageIcon != null)
					item.setIcon(imageIcon);

				String enable = atts.getValue("enable");
				if (enable != null)
					item.setEnabled(Boolean.parseBoolean(enable));

				this.comps.peek().add(item);
				this.comps.push(item);
				break;
			case SEPARADOR:
				JComponent comp = this.comps.peek();
				if (comp instanceof JMenu)
					((JMenu) comp).addSeparator();
				else if (comp instanceof JPopupMenu)
					((JPopupMenu) comp).addSeparator();
				break;
			case LABEL:
				comp = this.comps.peek();
				item = new JMenuItem(LocaleConfig.getString(atts.getValue("name")));
				item.setEnabled(false);
				comp.add(item);
				break;
			case SUB_MENU:
				label = atts.getValue("name");
				String action = atts.getValue("action");

				comp = this.comps.peek();
				if (this.group != null) {
					// se estiver dentro de um grupo de butões -> RadioButton
					item = new JRadioButtonMenuItem(LocaleConfig.getString(label));
					item.setSelected(Boolean.parseBoolean(atts.getValue("state")));
					this.group.add(item);
				} else {
					item = new JMenuItem(LocaleConfig.getString(label));
				}
				item.setActionCommand(action);
				item.addActionListener(listener);

				imageIcon = Icons.getIcon(atts.getValue("icon"));
				if (imageIcon != null)
					item.setIcon(imageIcon);

				enable = atts.getValue("enable");
				if (enable != null)
					item.setEnabled(Boolean.parseBoolean(enable));

				comp.add(item);
				break;
			case CHECK_MENU:
				label = atts.getValue("name");
				action = atts.getValue("action");

				comp = this.comps.peek();
				item = new JCheckBoxMenuItem(LocaleConfig.getString(label));
				item.setActionCommand(action);
				item.addActionListener(listener);
				item.setSelected(Boolean.parseBoolean(atts.getValue("state")));

				imageIcon = Icons.getIcon(atts.getValue("icon"));
				if (imageIcon != null)
					item.setIcon(imageIcon);

				comp.add(item);
				break;
			case RADIO_MENU:
				this.group = new ButtonGroup();
				break;
			default:
				System.err.println("unknown type '" + qName + "'");
				break;
			}
		}
	}

	@Override
	public void characters(String s) {
	}

	@Override
	public void endElement(String qName) {
		TipoMenu tipo = null;
		try {
			tipo = TipoMenu.valueOf(qName.toUpperCase());
		} catch (IllegalArgumentException exception) {
			System.err.println("unknown type '" + qName + "'");
		}

		this.type = tipo;
		if (this.type != null) {
			switch (this.type) {
			case MENU:
				this.comps.pop();
				break;
			case RADIO_MENU:
				this.group = null;
				break;
			default:
				break;
			}
			this.type = null;
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}
}