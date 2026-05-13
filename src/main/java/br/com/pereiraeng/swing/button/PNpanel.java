package br.com.pereiraeng.swing.button;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.icons.PereiraIcon;

/**
 * Classe do objeto gráfico de um painel com os butões de avançar ou recuar
 * 
 * @author Philipe PEREIRA
 *
 */
public class PNpanel extends PairButtonsPanel {
	private static final long serialVersionUID = 1L;

	public static final String PREVIOUS = "P", NEXT = "N";

	/**
	 * Construtor do painel com os butões de avançar ou recuar. As setas estão na
	 * direção vertical e não há prefixo nos comandos das ações.
	 * 
	 * @param listener escutador da ação de clicar sobre os butões
	 */
	public PNpanel(ActionListener listener) {
		this(listener, false);
	}

	/**
	 * Construtor do painel com os butões de avançar ou recuar. Não há prefixo nos
	 * comandos das ações.
	 * 
	 * @param listener   escutador da ação de clicar sobre os butões
	 * @param horizontal se <code>true</code>, as setas estão na direção horizontal,
	 *                   <code>false</code> na vertical
	 */
	public PNpanel(ActionListener listener, boolean horizontal) {
		this(listener, "", horizontal, Icons.DIM_BUTTON_SMALL_ICON);
	}

	/**
	 * Construtor do painel com os butões de avançar ou recuar
	 * 
	 * @param listener      escutador da ação de clicar sobre os butões
	 * @param prefixCommand sequência de caracteres que será o prefixo de todas os
	 *                      comandos das ações de se clicar sobre os butões de
	 *                      avançar ou recuar (depois do prefixo virá ou
	 *                      {@link #PREVIOUS} ou {@link #NEXT})
	 * @param horizontal    se <code>true</code>, as setas estão na direção
	 *                      horizontal, senão na vertical
	 */
	public PNpanel(ActionListener listener, String prefixCommand, boolean horizontal) {
		this(listener, prefixCommand, horizontal, Icons.DIM_BUTTON_SMALL_ICON);
	}

	/**
	 * Construtor do painel com os botões de avançar ou recuar
	 * 
	 * @param listener      escutador da ação de clicar sobre os botões
	 * @param prefixCommand sequência de caracteres que será o prefixo de todas os
	 *                      comandos das ações de se clicar sobre os butões de
	 *                      avançar ou recuar (depois do prefixo virá ou
	 *                      {@link #PREVIOUS} ou {@link #NEXT})
	 * @param horizontal    se <code>true</code>, as setas estão na direção
	 *                      horizontal, senão na vertical
	 * @param dimension     tamanho dos botões
	 */
	public PNpanel(ActionListener listener, String prefixCommand, boolean horizontal, Dimension dimension) {
		super(listener, prefixCommand, (horizontal ? PereiraIcon.PREVIOUS : PereiraIcon.UP), PREVIOUS,
				horizontal ? PereiraIcon.NEXT : PereiraIcon.DOWN, NEXT, dimension);
	}
}
