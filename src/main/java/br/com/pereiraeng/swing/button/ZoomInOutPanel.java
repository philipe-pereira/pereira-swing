package br.com.pereiraeng.swing.button;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import br.com.pereiraeng.icons.Icons;
import br.com.pereiraeng.swing.SwingUtils;

/**
 * Classe do objeto gráfico do painel com os butões de mais ou menos zoom
 * 
 * @author Philipe PEREIRA
 *
 */
public class ZoomInOutPanel extends PairButtonsPanel {
	private static final long serialVersionUID = 1L;

	public static final String IN = "+", OUT = "-";

	/**
	 * Construtor do painel com os butões de mais ou menos zoom
	 * 
	 * @param listener escutador da ação de clicar sobre os butões
	 */
	public ZoomInOutPanel(ActionListener listener) {
		this(listener, "");
	}

	/**
	 * Construtor do painel com os butões de mais ou menos zoom
	 * 
	 * @param listener      escutador da ação de clicar sobre os butões
	 * @param prefixCommand sequência de caracteres que será o prefixo de todas os
	 *                      comandos das ações de se clicar sobre os butões de mais
	 *                      ou menos zoom (depois do prefixo virá ou {@link #IN} ou
	 *                      {@link #OUT})
	 */
	public ZoomInOutPanel(ActionListener listener, String prefixCommand) {
		this(listener, prefixCommand, SwingUtils.DIM_BUTTON_SMALL_ICON);
	}

	/**
	 * Construtor do painel com os butões de mais ou menos zoom
	 * 
	 * @param listener      escutador da ação de clicar sobre os butões
	 * @param prefixCommand sequência de caracteres que será o prefixo de todas os
	 *                      comandos das ações de se clicar sobre os butões de mais
	 *                      ou menos zoom (depois do prefixo virá ou {@link #IN} ou
	 *                      {@link #OUT})
	 * @param dimension
	 */
	public ZoomInOutPanel(ActionListener listener, String prefixCommand, Dimension dimension) {
		super(listener, prefixCommand, Icons.UTILS_ICON_PATH + "ZoomIn24.gif", IN,
				Icons.UTILS_ICON_PATH + "ZoomOut24.gif", OUT, dimension);
	}
}
