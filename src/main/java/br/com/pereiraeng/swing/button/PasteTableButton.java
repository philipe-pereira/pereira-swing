package br.com.pereiraeng.swing.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.table.TableModel;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.SwingUtils;
import br.com.pereiraeng.swing.table.AdvTable;

/**
 * Classe do objeto gráfico do botão que ao ser pressionado busca os dados da
 * área de transferência e repassa ao modelo de uma tabela
 * 
 * @author Philipe PEREIRA
 *
 */
public class PasteTableButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final TableModel tableModel;

	private final boolean resizeRows;

	/**
	 * <code>true</code> para {@link Double#valueOf(String) converter} o texto em um
	 * número decimal; <code>false</code> para manter a sequência de caracteres
	 */
	private final boolean d;

	private final int rowOffset;

	private final int columnOffset;

	private DefaultListModel<String> rowHeader;

	/**
	 * Construtor do objeto gráfico do botão que cola o conteúdo na área de
	 * transferência na tabela
	 * 
	 * @param tableModel modela da tabela
	 */
	public PasteTableButton(TableModel tableModel) {
		this(tableModel, false, false);
	}

	/**
	 * Construtor do objeto gráfico do botão que cola o conteúdo na área de
	 * transferência na tabela
	 * 
	 * @param tableModel modela da tabela
	 * @param d          <code>true</code> para {@link Double#valueOf(String)
	 *                   converter} o texto em um número decimal; <code>false</code>
	 *                   para manter a sequência de caracteres
	 * @param resizeRows <code>true</code>, para mudar o número de linhas (se o
	 *                   modelo de tabela permitir isso); <code>false</code> para
	 *                   manter o número de linhas
	 */
	public PasteTableButton(TableModel tableModel, boolean d, boolean resizeRows) {
		this(tableModel, d, resizeRows, 0, 0);
	}

	/**
	 * Construtor do objeto gráfico do botão que cola o conteúdo na área de
	 * transferência na tabela
	 * 
	 * @param tableModel   modela da tabela
	 * @param d            <code>true</code> para {@link Double#valueOf(String)
	 *                     converter} o texto em um número decimal;
	 *                     <code>false</code> para manter a sequência de caracteres
	 * @param resizeRows   <code>true</code>, para mudar o número de linhas (se o
	 *                     modelo de tabela permitir isso); <code>false</code> para
	 *                     manter o número de linhas
	 * @param rowOffset    índice da linha onde deve-se começar a colar
	 * @param columnOffset índice da coluna onde deve-se começar a colar
	 */
	public PasteTableButton(TableModel tableModel, boolean d, boolean resizeRows, int rowOffset, int columnOffset) {
		super(Icons.loadUtilsIcon(("Paste.gif")));
		addActionListener(this);
		this.tableModel = tableModel;
		this.d = d;
		this.resizeRows = resizeRows;
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
	}

	/**
	 * Função que repassa para o objeto gráfico o cabeçalho das linhas
	 * 
	 * @param rowHeader cabeçalho das linhas
	 */
	public void setRowHeader(DefaultListModel<String> rowHeader) {
		this.rowHeader = rowHeader;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		AdvTable.paste(rowOffset, columnOffset, SwingUtils.getClipBoardContent(SwingUtils.getWindow(this)), resizeRows,
				tableModel, rowHeader, d);
	}
}
