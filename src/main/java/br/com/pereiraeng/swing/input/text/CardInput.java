package br.com.pereiraeng.swing.input.text;

import javax.swing.JTextArea;

import br.com.pereiraeng.swing.MonoSpacedFont;
import br.com.pereiraeng.swing.text.ColumnsTextArea;

/**
 * Classe do objeto {@link JTextArea} onde o fundo da caixa de texto é pintado
 * em duas cores para distinguir as posições do texto. A fonte utilizada deve
 * necessariamente ser "Courier New" de modo que os caracteres sejam
 * monoespaçados e assim se encaixam nas posições pintadas.
 * 
 * @author Philipe Pereira
 * 
 */
public class CardInput extends ColumnsTextArea {
	private static final long serialVersionUID = 1L;

	protected MonoSpacedFont font;
	private int cardSpace;

	/**
	 * Construtor da caixa de texto com o fundo pintado nas posições
	 * 
	 * @param font
	 *            escolha entre os dois tamanhos para a fonte: 12 ou 14
	 * @param cardSpace
	 *            número de posições a serem pintadas de uma mesma cor para se
	 *            distinguir os blocos
	 * @param cardPositions
	 *            número total de posições horizontais a serem pintadas
	 */
	public CardInput(MonoSpacedFont font, int cardSpace, int cardPositions) {
		super(font, cardPositions);
		this.cardSpace = cardSpace;
	}

	/**
	 * 
	 * @param cardSpace
	 *            número de posições a serem pintadas de uma mesma cor para se
	 *            distinguir os blocos
	 */
	public void setCardSpace(int cardSpace) {
		this.cardSpace = cardSpace;
		repaint();
	}

	@Override
	protected int getPos(int c) {
		return cardSpace * c;
	}
}
