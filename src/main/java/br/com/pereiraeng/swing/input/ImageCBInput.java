package br.com.pereiraeng.swing.input;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Classe que representa um objeto do tipo <code>JComboBox</code> onde a lista
 * de objetos selecionáveis é formado por etiquetas com desenhos.
 * 
 * Há opção da inserção de uma opção neutra, com valor de constante igual à -1,
 * que aparecerá como a palavra "Selecione", dizendo ao usuário de que ele deve
 * escolher uma dentre as opções
 * 
 * @author Philipe Pereira
 * 
 */
public class ImageCBInput extends JComboBox<Integer> {
	private static final long serialVersionUID = 1L;

	private boolean neutral;
	private String[] messages;
	private Icon[] icon;

	/**
	 * Construtor do <code>JComboBox</code> com imagens dentre as opções
	 * 
	 * @param neutral
	 *            se <code>true</code>, será adicionada uma opção dentre as
	 *            disponíveis com a palavra "Selecione", com o valor -1,
	 *            indicando que o usuário deverá escolher uma dentre as opções
	 * @param messages
	 *            vetor de <code>String</code> com as mensagens para os Tooltip
	 *            que aparecerão ao se posicionar o mouse sobre as imagens
	 * @param icon
	 *            vetor de imagens a serem exibidos
	 */
	public ImageCBInput(boolean neutral, String[] messages, Icon[] icon) {
		this.neutral = neutral;
		this.messages = messages;
		this.icon = icon;

		super.setRenderer(new Renderer());

		if (neutral)
			this.addItem(Integer.valueOf(-1));
		for (int i = 0; i < icon.length; i++)
			this.addItem(Integer.valueOf(i));
	}

	/**
	 * Renderizador dos comboboxs da escolha da instalação e aterramento
	 * 
	 * @author Philipe Pereira
	 * 
	 */
	private class Renderer extends JLabel implements ListCellRenderer<Integer> {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(
				JList<? extends Integer> list, Integer value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setHorizontalAlignment(JLabel.CENTER);
			setOpaque(true);
			setForeground(Color.BLACK);

			// exibição no quadrado superior
			if (index < 0) {
				setToolTipText(null);
				if (value < 0 && neutral) {
					setText("Selecione");
					setIcon(null);
				} else {
					setText("");
					setIcon(icon[value]);
				}
			} else { // itens da lista derolante
				setBackground(isSelected ? new Color(163, 184, 204)
						: new Color(238, 238, 238));

				if (index == 0 && neutral) {
					setToolTipText(null);
					setText("Selecione");
					setIcon(null);
				} else {
					setToolTipText(messages[index - (neutral ? 1 : 0)]);
					setText("");
					setIcon(icon[index - (neutral ? 1 : 0)]);
				}
			}
			return this;
		}
	}
}
