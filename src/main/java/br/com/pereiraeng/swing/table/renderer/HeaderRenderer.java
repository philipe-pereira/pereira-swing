package br.com.pereiraeng.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 * Coluna no nome das linhas - renderer
 * 
 * @author Philipe Pereira
 */
public class HeaderRenderer extends JLabel implements ListCellRenderer<Object> {
	private static final long serialVersionUID = 1L;

	private static final Font FONT_DEFAULT = new Font(Font.DIALOG, Font.PLAIN, 12);

	private final Color bg;

	public HeaderRenderer() {
		this(FONT_DEFAULT);
	}

	public HeaderRenderer(Font font) {
		this(font, new Color(51, 51, 51), new Color(238, 238, 238));
	}

	public HeaderRenderer(JTable table) {
		this(table.getTableHeader().getFont(), table.getTableHeader().getForeground(),
				table.getTableHeader().getBackground());
	}

	public HeaderRenderer(Font font, Color foreground, Color background) {
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(foreground);
		this.bg = background;
		setFont(font == null ? FONT_DEFAULT : font);
	}

	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (value != null) {
			if (value instanceof Integer)
				setText(String.format("%03d", value));
			else if (value instanceof String)
				setText((String) value);
			else
				setText(value.toString());
		} else
			setText(" ");
		setBackground(isSelected ? bg.darker() : bg);
		return this;
	}
}