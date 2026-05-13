package br.com.pereiraeng.swing.button;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pereiraeng.icons.IconFactory;
import br.com.pereiraeng.icons.Icons;

/**
 * Classe do objeto gráfico de um painel com um par de botões
 * 
 * @author Philipe PEREIRA
 *
 */
public abstract class PairButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor do objeto gráfico de um par de botões
	 * 
	 * @param listener           escutador da ação de clicar sobre os butões
	 * @param prefixCommand      prefixo
	 * @param leftIconPath       endereço do arquivo da imagem do botão esquerdo
	 * @param leftActionCommand  ação do botão esquerdo
	 * @param rightIconPath      endereço do arquivo da imagem do botão direito
	 * @param rightActionCommand ação do botão direito
	 * @param dimension          tamanho dos botões
	 */
	protected PairButtonsPanel(ActionListener listener, String prefixCommand, String leftIconPath,
			String leftActionCommand, String rightIconPath, String rightActionCommand, Dimension dimension) {
		this(listener, prefixCommand, Icons.loadIcon(leftIconPath), leftActionCommand, Icons.loadIcon(rightIconPath),
				rightActionCommand, dimension);
	}

	/**
	 * Construtor do objeto gráfico de um par de botões
	 * 
	 * @param listener           escutador da ação de clicar sobre os butões
	 * @param prefixCommand      prefixo
	 * @param leftIconPath       imagem do botão esquerdo
	 * @param leftActionCommand  ação do botão esquerdo
	 * @param rightIconPath      imagem do botão direito
	 * @param rightActionCommand ação do botão direito
	 * @param dimension          tamanho dos botões
	 */
	protected PairButtonsPanel(ActionListener listener, String prefixCommand, IconFactory leftIcon,
			String leftActionCommand, IconFactory rightIcon, String rightActionCommand, Dimension dimension) {
		this(listener, prefixCommand, leftIcon.create(), leftActionCommand, rightIcon.create(), rightActionCommand,
				dimension);
	}

	private PairButtonsPanel(ActionListener listener, String prefixCommand, Icon left, String leftActionCommand,
			Icon right, String rightActionCommand, Dimension dimension) {
		// zoom in
		JButton button = new JButton(left);
		if (dimension != null)
			button.setPreferredSize(dimension);
		button.setActionCommand(prefixCommand + leftActionCommand);
		add(button);

		// zoom out
		button = new JButton(right);
		if (dimension != null)
			button.setPreferredSize(dimension);
		button.setActionCommand(prefixCommand + rightActionCommand);
		add(button);

		if (listener != null)
			addActionListener(listener);
	}

	/**
	 * Função que adiciona um <code>ActionListener</code> aos dois butões
	 * 
	 * @param listener escutador a ser adicionado
	 */
	public void addActionListener(ActionListener listener) {
		((JButton) getComponent(0)).addActionListener(listener);
		((JButton) getComponent(1)).addActionListener(listener);
	}
}
