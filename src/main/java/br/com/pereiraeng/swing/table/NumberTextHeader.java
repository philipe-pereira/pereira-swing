package br.com.pereiraeng.swing.table;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import br.com.pereiraeng.core.collections.ArrayUtils;

/**
 * Numeração a ser posta paralela ao painel de rolagem de um texto de modo que
 * as linhas deste sejam numeradas. O acréscimo ou remoção de linhas no texto já
 * faz com que o cabeçalho seja renumerado
 * 
 * @author Philipe PEREIRA
 * 
 */
public class NumberTextHeader extends TextHeader<Integer> implements ComponentListener {
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 10;

	/**
	 * 
	 * @param sp painel derolante onde será inserido o cabeçalho
	 * @param ta caixa de texto
	 */
	public NumberTextHeader(JScrollPane sp, JTextComponent ta) {
		this(sp, ta, 3);
	}

	/**
	 * 
	 * @param sp painel derolante onde será inserido o cabeçalho
	 * @param ta caixa de texto
	 * @param w  número de dígitos do cabeçalho das linhas
	 */
	public NumberTextHeader(JScrollPane sp, JTextComponent ta, int w) {
		super(sp, new DefaultListModel<Integer>(), w * WIDTH, ta.getFont());
		ta.addComponentListener(this);
	}

	// -------------------------- LISTENER --------------------------

	@Override
	public void componentHidden(ComponentEvent event) {
	}

	@Override
	public void componentMoved(ComponentEvent event) {
	}

	@Override
	public void componentResized(ComponentEvent event) {
		setListData(ArrayUtils.progVec(
				(int) Math.ceil(((double) ((JTextComponent) event.getSource()).getHeight()) / getFixedCellHeight()),
				false));
	}

	@Override
	public void componentShown(ComponentEvent event) {
	}
}
