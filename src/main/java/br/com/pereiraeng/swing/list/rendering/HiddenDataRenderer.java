package br.com.pereiraeng.swing.list.rendering;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import br.com.pereiraeng.core.StringUtils;

/**
 * Classe que faz uma adaptação do renderizador padrão das listas, onde parte da
 * sequência de caracteres é ocultada por se tratar de um código de referência.
 * Um separador a ser escolhido pelo usuário define onde termina o código e
 * começa o que será exibido na lista.
 * 
 * @author Philipe PEREIRA
 *
 */
public class HiddenDataRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	/**
	 * sequência de caracteres que separa os dados em blocos
	 */
	private final String separator;

	/**
	 * posição do bloco
	 */
	private final int pos;

	/**
	 * <code>true</code> para mostrar a parte da sequência de caracteres na posição
	 * indicada, <code>false</code> para mostrar todas, exceto a indicada
	 */
	private final boolean show;

	/**
	 * Construtor que instancia um objeto que esconde a informação exibida na célula
	 * da lista que está antes de um hífen ("-")
	 */
	public HiddenDataRenderer() {
		this("-");
	}

	/**
	 * Construtor que instancia um objeto que esconde a informação exibida na célula
	 * da lista que está antes de uma dada sequência de caracteres
	 * 
	 * @param separator sequência de caracteres que separa os dados em blocos
	 */
	public HiddenDataRenderer(String separator) {
		this(separator, 1, true);
	}

	/**
	 * Construtor que instancia um objeto que esconde a informação exibida na célula
	 * da lista que está fora de uma dada posição, isolada por uma sequência de
	 * caracteres
	 * 
	 * @param separator sequência de caracteres que separa os dados em blocos
	 * @param pos       posição do bloco
	 * @param show      <code>true</code> para mostrar a parte da sequência de
	 *                  caracteres na posição indicada, <code>false</code> para
	 *                  mostrar todas, exceto a indicada
	 */
	public HiddenDataRenderer(String separator, int pos, boolean show) {
		this.separator = separator;
		this.pos = pos;
		this.show = show;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		String valueS = value.toString();
		if (show)
			valueS = StringUtils.substring(this.pos, valueS, separator);
		else {
			if (this.pos == 0) // só tem depois
				valueS = valueS.substring(valueS.indexOf(separator) + separator.length());
			else {
				// antes
				int p1 = StringUtils.ordinalIndexOf(valueS, separator, this.pos - 1) + separator.length();
				String out = valueS.substring(0, p1);
				// depois
				int p2 = valueS.indexOf(separator, p1);
				if (p2 >= 0)
					out += valueS.substring(p2 + separator.length());
				valueS = out;
			}
		}
		return super.getListCellRendererComponent(list, valueS, index, isSelected, cellHasFocus);
	}
}
