package br.com.pereiraeng.swing.table;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.ListModel;

/**
 * Classe do objeto gráfico do cabeçalho das linhas (de uma tabela ou gráfico) a
 * ser inserido em um painel derolante
 * 
 * @author Philipe PEREIRA
 *
 * @param <R> classe dos objetos a serem exibidos no cabeçalho das linhas
 */
public class TextHeader<R> extends RowHeader<R> {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor do objeto gráfico do cabeçalho das linhas
	 * 
	 * @param sp             painel derolante onde será inserido o cabeçalho
	 * @param model          modelo da lista dos objetos no cabeçalho das linhas
	 * @param rowHeaderWidth largura do cabeçalho
	 * @param font           largura do cabeçalho
	 */
	public TextHeader(JScrollPane sp, ListModel<R> model, int rowHeaderWidth, Font font) {
		super(sp, model, font, rowHeaderWidth, font.getSize() + 3);
	}
}
